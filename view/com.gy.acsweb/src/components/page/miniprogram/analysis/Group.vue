<template>
  <div class="group">
    <div class="crumbs">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item><i class="el-icon-lx-location"/>小程序管理</el-breadcrumb-item>
        <el-breadcrumb-item>五官分析</el-breadcrumb-item>
        <el-breadcrumb-item>排列组合</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-input v-model="searchName" placeholder="请输入关键字" class="handle-input"/>
        <el-button type="primary" icon="el-icon-search" @click="getGroupData">搜索</el-button>
        <!--<el-button type="primary" icon="delete" class="handle-del mr10" @click="delAll">批量删除</el-button>-->
        <div class="handle-box-right">
          <el-button type="success" icon="el-icon-plus" @click="handleEdit('add')">添加</el-button>
        </div>
      </div>
      <el-table :data="groupData" border class="table" ref="multipleTable">
        <!--<el-table-column type="selection" width="55" align="center"/>-->
        <el-table-column type="index" width="50" label="序号" align="center"></el-table-column>
        <el-table-column width="100" prop="organ" label="器官" align="center"/>
        <el-table-column prop="feature" label="特征类型/特征值" align="center"/>
        <el-table-column width="200" prop="artName" label="器官文艺名" align="center"/>
        <el-table-column width="300" prop="slogan" label="器官Slogan" align="center"/>
        <el-table-column prop="summary" label="器官总结语" align="center"/>
        <el-table-column label="操作" width="180" align="center">
          <template slot-scope="scope">
            <el-button type="text" icon="el-icon-edit" @click="handleEdit('edit', scope.row, scope.$index)">编辑</el-button>
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
      <el-form ref="groupForm" :rules="rules" :model="groupForm" label-width="100px">
        <el-form-item label="选择器官" prop="organ">
          <el-select v-model="groupForm.organ" placeholder="请选择器官" class="organ-select">
            <el-option label="肤色" value="fuse"></el-option>
            <el-option label="眼睛" value="yanjing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="特征类型/值" prop="group">
          <el-cascader 
            v-for="(item, index) in featureList" :key="index"
            placeholder="请选择特征类型/特征值"
            :options="featureSelect"
            filterable
            class="feature-select-item"
            :disabled="!groupForm.organ"
          ></el-cascader>
          <div class="minus-plus">
            <span 
              :class="[!groupForm.organ || featureList.length > 7 ? 'add-feature-item' : 'add-feature-item add-feature-item-active']"
              @click="addFeatureItem('plus')"><i class="el-icon-plus"></i></span>
            <span 
              :class="[!groupForm.organ || featureList.length < 2 ? 'add-feature-item' : 'add-feature-item add-feature-item-active']"
              @click="addFeatureItem('minus')"><i class="el-icon-minus"></i></span>
          </div>
        </el-form-item>
        <el-form-item label="组合文艺名" prop="artName">
          <el-input v-model="groupForm.artName" placeholder="请输入组合文艺名" :disabled="!groupForm.organ"/>
        </el-form-item>
        <el-form-item label="组合Slogan" prop="slogan">
          <el-input v-model="groupForm.slogan" placeholder="请输入组合Slogan" :disabled="!groupForm.organ"/>
        </el-form-item>
        <el-form-item label="组合总结语" prop="summary">
          <el-input type="textarea" :rows="7" v-model="groupForm.summary" :disabled="!groupForm.organ"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveOperation(operationType,'groupForm')">确 定</el-button>
          <el-button @click="editVisible = false">取 消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
  import api from '@/components/common/api'
  export default {
    name: 'group',
    data(){
      return{
        featureList: [1],
        searchName: '', // 搜索关键字
        groupData: [], // 排列组合列表数据
        total: 0, // 排列组合列表总数
        editVisible: false, // 编辑框状态
        modalTitle: '',
        groupForm: {
          organ: '',
          group: '',
          artName: '',
          slogan: '',
          summary: '',
        },
        rules:{
          organ:[
            {required: true, message: '请选择器官', trigger: 'blur'}
          ],
          group:[
            {required: true, message: '请选择排列组合', trigger: 'blur'}
          ],
          artName:[
            {required: true, message: '请输入组合文艺名', trigger: 'blur'}
          ],
          slogan:[
            {required: true, message: '请输入组合slogan', trigger: 'blur'}
          ],
          summary:[
            {required: true, message: '请输入组合总结语', trigger: 'blur'}
          ]
        },
        featureSelect: [{
          value: 'yanpi',
          label: '眼皮',
          children: [{
            value: 'chaoda',
            label: '超大',
          },{
            value: 'shizhong',
            label: '适中'
          }]
        }],
      }
    },
    created() {
      this.getGroupData()
    },
    methods: {
      getGroupData(){
        this.groupData = [
          {
            'organ': '眼睛',
            'feature': '眼皮/超大，瞳孔/棕色，瞳距/宽，整体/大',
            'artName': '希腊众神之眼',
            'slogan': '谁的目光点燃了这个世界？',
            'summary': '真可谓牛逼的眼睛啊！'
          },
        ]
        this.total = this.groupData.length
      },
      // 每页显示数目
      handleSizeChange(){
        this.size = val;
        this.groupData();
      },
      // 分页导航
      handleCurrentChange(){
        this.page = val;
        this.groupData();
      },
      // 新增 | 编辑
      handleEdit(type, row, index){
        this.modalTitle = type == 'add' ? `添加` : `编辑`
        this.editVisible = true
        if(row){
          this.groupForm = {
            organ: row.organ,
            feature: row.feature,
            artName: row.artName,
            slogan: row.slogan,
            summary: row.summary
          }
          this.featureList.length = 1
        }else{
          this.groupForm = {
            organ: '',
            feature: '',
            artName: '',
            slogan: '',
            summary: ''
          }
        }
      },
      addFeatureItem(type){
        if(!this.groupForm.organ) return
        if(type == 'plus') {
          if(this.featureList.length > 7) return
          this.featureList.push(1)
        }else{
          if(this.featureList.length < 2) return
          this.featureList.pop()
        }
      },
      saveOperation(){

      },
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
  .organ-select{
    width: calc(50% - 5px);
  }
  .feature-select-item{
    width: calc(50% - 5px);
    margin-bottom: 10px;
  }
  .feature-select-item:nth-of-type(odd),
  .add-feature-item:nth-of-type(odd),
  .add-feature-item-active:nth-of-type(odd){
    margin-right: 10px;
  }
  .add-feature-item{
    vertical-align: top;
    margin-top: 1px;
    display: inline-block;
    width: calc(50% - 5px);
    height: 32px;
    border-radius: 4px;
    box-sizing: border-box;
    text-align: center;
    cursor: pointer;
    border: 1px solid #dcdfe6;
    background-color: #f5f7fa;
    color: #c0c4cc;
    font-size: 14px;
  }
  .add-feature-item-active{
    border-style: dashed;
    background-color: #fff;
    color: #c0c4cc;
  }
  .minus-plus{
    font-size: 0;
  }
</style>