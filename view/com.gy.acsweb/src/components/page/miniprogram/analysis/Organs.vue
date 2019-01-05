<template>
  <div class="organ">
    <div class="crumbs">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item><i class="el-icon-lx-location"/>小程序管理</el-breadcrumb-item>
        <el-breadcrumb-item>五官分析</el-breadcrumb-item>
        <el-breadcrumb-item>五官编辑</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-input v-model="searchName" placeholder="请输入关键字" class="handle-input"/>
        <el-button type="primary" icon="el-icon-search" @click="getOrganData">搜索</el-button>
        <!--<el-button type="primary" icon="delete" class="handle-del mr10" @click="delAll">批量删除</el-button>-->
        <div class="handle-box-right">
          <el-button type="success" icon="el-icon-plus" @click="handleEdit('add')">添加</el-button>
        </div>
      </div>
      <el-table :data="organData" border class="table" ref="multipleTable">
        <!--<el-table-column type="selection" width="55" align="center"/>-->
        <el-table-column type="index" width="50" label="序号" align="center"></el-table-column>
        <el-table-column width="200" prop="levelOne" label="一级分类" align="center"/>
        <el-table-column width="200" prop="levelTwo" label="二级分类" align="center"/>
        <el-table-column width="200" prop="organName" label="特征名称" align="center"/>
        <el-table-column prop="desc" label="特征文案" align="center"/>
        <el-table-column label="操作" width="180" align="center">
          <template slot-scope="scope">
            <el-button type="text" icon="el-icon-edit" @click="handleEdit('edit', scope.row, scope.$index)">编辑</el-button>
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

    <!-- 编辑弹出框 -->
    <el-dialog :title="modalTitle" :visible.sync="editVisible" width="35%">
      <el-form ref="organForm" :rules="rules" :model="organForm" label-width="100px">
        <el-form-item label="一级分类" prop="levelOne">
          <el-select v-model="levelOneValue" placeholder="请选择一级分类">
            <el-option
              v-for="item in levelOne"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="二级分类" prop="levelTwo">
          <el-select v-model="levelTwoValue" placeholder="请选择一级分类">
            <el-option
              v-for="item in levelTwo"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="特征名称" prop="organName">
          <el-input v-model="organForm.organName" placeholder="请输入特征名称"/>
        </el-form-item>
        <el-form-item label="特征文案" prop="desc">
          <el-input type="textarea" :rows="7" v-model="organForm.desc"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveOperation(operationType, 'organForm')">确 定</el-button>
          <el-button @click="editVisible = false">取 消</el-button>
        </el-form-item>
      </el-form>
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
    name: 'organ',
    data(){
      return{
        searchName: '', // 搜索品牌商名称
        organData: [], // 品牌商列表数据
        total: 0, // 品牌商列表总数
        idx: -1, // 当前操作标记
        editVisible: false, // 编辑框状态
        delVisible: false, // 删除框状态
        modalTitle: '',
        levelOne: [{
          value: 'fuse',
          label: '肤色'
        }, {
          value: 'yanjing',
          label: '眼睛'
        }],
        levelOneValue: '',
        levelTwo: [{
          value: 'fuse',
          label: '肤色'
        }, {
          value: 'mei',
          label: '眉间距'
        }],
        levelTwoValue: '',
        organForm: {
          organName: '',
          levelOne: '',
          levelTwo: '',
          desc: '',
        },
        rules:{
          levelOne:[
            {required: true, message: '请输入或选择一级分类', trigger: 'blur'}
          ],
          levelTwo:[
            {required: true, message: '请输入或选择二级分类', trigger: 'blur'}
          ],
          organName:[
            {required: true, message: '请输入特征名称', trigger: 'blur'},
            {min: 1, max: 64, message: '长度在1到64个字符', trigger: 'blur'}
          ],
          desc: [
            {required: true, message: '请输入特征文案', trigger: 'blur' }
          ]
        },
        organSelect: [
          { 
            value: 'fuse', 
            label: '肤色', 
            children: [{
              value: 'fuse',
              label: '肤色'
            }]
          },{ 
            value: 'yanjing', 
            label: '眼睛', 
            children: [{
              value: 'yanpi',
              label: '眼皮'
            },{
              value: 'mei',
              label: '眉间距'
            }]
          }
        ]
      }
    },
    created() {
      this.getOrganData()
    },
    methods: {
      getOrganData(){
        this.organData = [
          {levelOne: '肤色', levelTwo: '肤色', organName: '肤色黝黑', desc: '你的肤色超级黑，我要叫你黑猴，你是不是要打我？'},
        ]
        this.total = this.organData.length
      },
      // 每页显示数目
      handleSizeChange(){
        this.size = val;
        this.organData();
      },
      // 分页导航
      handleCurrentChange(){
        this.page = val;
        this.organData();
      },
      // 新增 | 编辑
      handleEdit(type, row, index){
        this.modalTitle = type == 'add' ? `添加` : `编辑`
        this.editVisible = true
        if(row){
          console.log(row)
          this.organForm = {
            levelOne: '',
            levelTwo: '',
            organName: row.organName,
            desc: row.desc
          }
        }else{
          this.organForm = {
            levelOne: '',
            levelTwo: '',
            organName: '',
            desc: ''
          }
        }
      },
      saveOperation(){

      },
      handleDelete(index){
        this.idx = index;
        this.delVisible = true;
      },
      // 确定删除
      deleteRow(){
        const id = this.organData[this.idx].id;
        this.organData.splice(this.idx, 1);
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
  .organ{
    width: 100%;
    font-size: 14px;
  }
  .select-organ{
    margin-left: 30px;
  }
  .red{
    color: #ff0000;
  }
</style>