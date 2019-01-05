<template>
  <div class="source">
    <div class="crumbs">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item><i class="el-icon-lx-location"/>小程序管理</el-breadcrumb-item>
        <el-breadcrumb-item>整妆库</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-input v-model="searchName" placeholder="整妆名称" class="handle-input"/>
        <el-button type="primary" icon="el-icon-search" @click="getMakeupData">搜索</el-button>
        <!--<el-button type="primary" icon="delete" class="handle-del mr10" @click="delAll">批量删除</el-button>-->
        <div class="handle-box-right">
          <el-button type="primary" icon="el-icon-plus" @click="handleAdd()">添加整妆</el-button>
          <el-button class="create-makeup" type="success" @click="checkDetail('edit')">创建整妆<i class="el-icon-back"></i></el-button>
        </div>
      </div>
      <el-table :data="makeupData" border class="table" ref="multipleTable">
        <!--<el-table-column type="selection" width="55" align="center"/>-->
        <el-table-column label="整妆名称" prop="makeupName" width="300"/>
        <el-table-column label="封面图" prop="coverImg">
          <template slot-scope="scope">
            <img :src="scope.row.coverImg" width="300" height="200" class="coverImg"/>
          </template>
        </el-table-column>
        <el-table-column label="当前状态" prop="status" width="180" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.status == 0" class="red">下线</span>
            <span v-if="scope.row.status == 1" class="green">上线</span>
          </template>
        </el-table-column>
        <el-table-column label="切换状态" prop="status" width="180" align="center">
          <template slot-scope="scope">
            <el-button type="success" plain v-if="scope.row.status == 0" class="online"><i class="el-icon-back"></i>上线</el-button>
            <el-button type="danger" plain v-if="scope.row.status == 1" class="offline"><i class="el-icon-back"></i>下线</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template slot-scope="scope">
            <el-button type="text" v-if="scope.row.status == 1" icon="el-icon-document" @click="checkDetail('check',scope.row.id)">查看</el-button>
            <el-button type="text" v-if="scope.row.status == 0" icon="el-icon-document" @click="checkDetail('edit',scope.row.id)">编辑</el-button>
            <el-button type="text" icon="el-icon-delete" class="red" @click="handleDelete(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分页-->
      <div class="pagination">
        <el-pagination 
          background 
          @current-change="handleCurrentChange" 
          @size-change="handleSizeChange"
          :page-sizes="[5, 10, 50, 100]" 
          :page-size="2" 
          layout="total,sizes,prev,pager,next,jumper" 
          :total=total>
        </el-pagination>
      </div>
    </div>

    <!-- 新增整妆弹出框 -->
    <el-dialog title="添加整妆" :visible.sync="addVisible" width="30%">
      我是一个上线状态的整妆列表
    </el-dialog>

    <!-- 删除提示框 -->
    <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
      <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="delVisible = false">取 消</el-button>
        <el-button type="primary" @click="deleteRow">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: 'makeup',
    data(){
      return{
        searchName: '', // 搜索品牌商名称
        makeupData: [], // 品牌商列表数据
        total: 0, // 品牌商列表总数
        addVisible: false, // 编辑框状态
        delVisible: false,
        idx: -1,
      }
    },
    created() {
      this.getMakeupData()
    },
    methods: {
      getMakeupData(){
        this.makeupData = [
          { id: 1, makeupName: '圣诞妆', status: 1, coverImg: '/src/assets/login-bg.jpg' },
          { id: 2, makeupName: '我是一个整妆名', status: 0, coverImg: '/src/assets/login-bg.jpg' },
          { id: 3, makeupName: '哇哇哇哇', status: 1, coverImg: '/src/assets/login-bg.jpg' },
        ]
        this.total = this.makeupData.length
      },
      // 每页显示数目
      handleSizeChange(){
        this.size = val;
        this.getMakeupData();
      },
      // 分页导航
      handleCurrentChange(){
        this.page = val;
        this.getMakeupData();
      },
      // 新增 | 编辑
      handleAdd(){
        this.addVisible = true
      },
      saveOperation(){

      },
      // 查看整妆详情
      checkDetail(type, id){
        this.$router.push({
          name: 'detail',
          params: {type: type, id: id}
        })
      },
      // 删除这个整妆
      handleDelete(index){
        this.idx = index;
        this.delVisible = true;
      },
      // 确定删除
      deleteRow(){
        const id = this.makeupData[this.idx].id;
        this.makeupData.splice(this.idx, 1);
        this.delVisible = false;
      }
    }
  }
</script>

<style lang="scss">
  .source{
    .handle-box {
      margin-bottom: 20px;
    }
    .handle-box-right {
      float: right;
      margin-bottom: 20px;
    }
    .handle-input {
      width: 300px;
      display: inline-block;
    }
    .create-makeup i{
      margin-left: 5px;
      transform: rotate(180deg);
    }
    .del-dialog-cnt{
      font-size: 16px;
      text-align: center;
    }
    .table{
      width: 100%;
      font-size: 14px;
    }
    .online i{
      transform: rotate(90deg)
    }
    .offline i{
      transform: rotate(-90deg)
    }
    .green{
      color: rgb(61, 194, 0);
    }
    .red{
      color: #ff0000;
    }
  }
</style>