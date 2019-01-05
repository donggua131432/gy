<template>
  <div class="table">
    <div class="crumbs">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item><i class="el-icon-lx-location"/>小程序管理</el-breadcrumb-item>
          <el-breadcrumb-item>配置</el-breadcrumb-item>
          <el-breadcrumb-item>整妆管理</el-breadcrumb-item>
        </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-input v-model="searchName" placeholder="整妆名称" class="handle-input"/>
        <el-button type="primary" icon="el-icon-search" @click="getMakeupData">搜索</el-button>
        <!--<el-button type="primary" icon="delete" class="handle-del mr10" @click="delAll">批量删除</el-button>-->
        <div class="handle-box-right">
          <el-button type="success" icon="el-icon-plus" @click="handleAdd()">添加整妆</el-button>
        </div>
      </div>
      <el-table :data="makeupData" border class="table" ref="multipleTable">
        <!--<el-table-column type="selection" width="55" align="center"/>-->
        <el-table-column width="300" prop="makeupName" label="整妆名称"/>
        <el-table-column prop="coverImg" label="封面图">
          <template slot-scope="scope">
            <img :src="scope.row.coverImg" width="300" height="200" class="coverImg"/>
          </template>
        </el-table-column>
        <el-table-column label="上移 / 下移" width="180" align="center">
          <template slot-scope="scope">
            <el-button 
              type="button" 
              icon="el-icon-sort-up" 
              :disabled="scope.$index == 0" 
              @click="move('up', makeupData, scope.$index)"></el-button>
            <el-button 
              type="button" 
              icon="el-icon-sort-down" 
              :disabled="scope.$index == makeupData.length - 1" 
              @click="move('down', makeupData, scope.$index)"></el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template slot-scope="scope">
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
    <el-dialog title="添加整妆" :visible.sync="addVisible" width="35%">
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
  import { moveAction } from '@/components/common/common'
  export default {
    name: 'makeup',
    data(){
      return{
        searchName: '', // 搜索品牌商名称
        makeupData: [], // 品牌商列表数据
        total: 0, // 品牌商列表总数
        idx: -1, // 当前操作标记
        addVisible: false, // 编辑框状态
        delVisible: false, // 删除框状态
      }
    },
    created() {
      this.getMakeupData()
    },
    methods: {
      getMakeupData(){
        this.makeupData = [
          { id: 1, makeupName: '圣诞妆', coverImg: '/src/assets/login-bg.jpg' },
          { id: 2, makeupName: '我是一个整妆名', coverImg: '/src/assets/login-bg.jpg' },
          { id: 3, makeupName: '哇哇哇哇', coverImg: '/src/assets/login-bg.jpg' },
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
      move(type, arr, idx){
        moveAction(type, arr, idx)
      },
      saveOperation(){

      },
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

<style scoped>
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
  .del-dialog-cnt{
    font-size: 16px;
    text-align: center
  }
  .table{
    width: 100%;
    font-size: 14px;
  }
  .red{
    color: #ff0000;
  }
</style>