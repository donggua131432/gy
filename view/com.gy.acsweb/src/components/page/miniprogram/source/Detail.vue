<template>
  <div class="table">
    <div class="crumbs">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item><i class="el-icon-lx-location"/>小程序管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/source/source'}">整妆库</el-breadcrumb-item>
        <el-breadcrumb-item>{{makeupTypeName}}</el-breadcrumb-item>
        <el-breadcrumb-item>{{currentTab}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <el-tabs v-model="activeName" @tab-click="handleClick" class="tabs-wrap">
        <el-tab-pane label="小程序" name="miniprogram">
          <div class="left-tabs">
            <el-menu default-active="1" class="el-menu-vertical-demo" :collapse="false">
              <el-menu-item index="1" @click="changeRightView('baseInfo')">
                <span slot="title">基本信息</span>
              </el-menu-item>
              <el-menu-item index="2" @click="changeRightView('segmentVideo')">
                <span slot="title">互动视频</span>
              </el-menu-item>
              <el-menu-item index="3" @click="changeRightView('campaign')">
                <span slot="title">Campaign</span>
              </el-menu-item>
            </el-menu>
          </div>
          <div class="right-content">
            <component :is="curRightView" keep-alive :editStatus="type == 'edit'"></component>
          </div>
        </el-tab-pane>
        <el-tab-pane label="五官分析匹配" name="match">
          <el-row class="oper-btns">
            <el-button 
              type="success" icon="el-icon-plus" :disabled="type == 'check'"
              @click="handleEdit('add')">添加</el-button>
            <!-- <el-button type="primary" icon="el-icon-edit" :disabled="type == 'check'">批量编辑</el-button>
            <el-button type="danger" icon="el-icon-delete" :disabled="type == 'check'">批量删除</el-button> -->
          </el-row>
          <el-table :data="matchData" border class="table" ref="multipleTable">
            <el-table-column type="selection" width="55" align="center"/>
            <el-table-column type="index" label="序号" width="55" align="center"/>
            <el-table-column prop="organ" label="器官" align="center"/>
            <el-table-column prop="feature" label="特征类型" align="center"/>
            <el-table-column prop="value" label="特征值" align="center"/>
            <el-table-column prop="score" label="评分" align="center">
              <template slot-scope="scope">
                <i v-for="(item, index) in scope.row.score" :key="index" class="el-icon-star-on icon-star"></i>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="180" align="center">
              <template slot-scope="scope">
                <el-button 
                  type="text" 
                  icon="el-icon-edit" 
                  :disabled="type == 'check'"
                  @click="handleEdit('edit', scope.row, scope.$index)">编辑</el-button>
                <el-button 
                  type="text" 
                  icon="el-icon-delete" 
                  :class="{red: type == 'edit'}" 
                  :disabled="type == 'check'"
                  @click="handleDelete(scope.$index)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </div>

    <!-- 编辑弹出框 -->
    <el-dialog :title="modalTitle" :visible.sync="editVisible" width="30%">
      <el-form ref="matchForm" :rules="matchRules" :model="matchForm" label-width="100px">
        <el-form-item label="选择组合" prop="group">
          <el-cascader 
            v-for="(item, index) in featureList" :key="index"
            placeholder="请选择组合"
            :options="featureSelect"
            filterable
            class="feature-select-item"
          ></el-cascader>
        </el-form-item>
        <el-form-item label="评分" prop="score">
          <span class="edit-stars" v-for="(s, i) in 3" :key="i" @click="changeStarStatus(s)" :title="`${s}星`">
            <i :class="s <= star ? 'el-icon-star-on gold-star':'el-icon-star-on'"></i>
          </span>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveOperation(operationType,'matchForm')">确 定</el-button>
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
  import BaseInfo from './components/BaseInfo'
  import SegmentVideo from './components/SegmentVideo'
  import Campaign from './components/Campaign'
  export default {
    components:{
      'baseInfo': BaseInfo,
      'segmentVideo': SegmentVideo,
      'campaign': Campaign
    },
    data(){
      return {
        type: this.$route.params.type,
        makeupTypeName: '整妆详情',
        currentTab: '小程序',
        curRightView: 'baseInfo',
        activeName: 'miniprogram',
        matchData: [
          {organ: '眼睛', feature: '眼皮', value: '欧式双眼皮', score: [1,1]},
          {organ: '眼睛', feature: '瞳距', value: '宽', score: [1]},
        ],
        editVisible: false,
        delVisible: false,
        idx: -1,
        modalTitle: '添加',
        star: 1,
        matchForm: [],
        rules: [],
        featureList: [1],
        featureSelect: [{
          value: 'yanjing',
          label: '眼睛',
          children: [{
            value: 'yanpi',
            label: '眼皮',
            children: [{
              value: 'shuangyan',
              label: '欧式双眼皮',
            },{
              value: 'danyanpi',
              label: '单眼皮'
            }]
          }]
        }],
        matchForm: {
          group: '',
          score: ''
        },
        matchRules:{
          group:[
            {required: true, message: '请选择组合', trigger: 'blur'}
          ],
          score:[
            {required: true, message: '请选评分'}
          ],
        },
      }
    },
    created() {
      this.getGroupData()
      let id = this.$route.params.id
      !id ? this.makeupTypeName = '创建整妆' : this.makeupTypeName = '整妆详情'
    },
    mounted() {
      this.$nextTick(() => {
        if(this.type == 'check') this.$message('上线状态不能编辑哦！');
      })
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
      handleClick(tab, event) {
        this.currentTab = tab.label
      },
      changeRightView(view){
        this.curRightView = view;
      },
      // 新增 | 编辑
      handleEdit(type, row, index){
        this.modalTitle = type == 'add' ? `添加` : `编辑`
        this.editVisible = true
        if(row){
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
      addFeatureItem(type){
        if(!this.matchForm.organ) return
        if(type == 'plus') {
          if(this.featureList.length > 7) return
          this.featureList.push(1)
        }else{
          if(this.featureList.length < 2) return
          this.featureList.pop()
        }
      },
      changeStarStatus(s){
        this.star = s
      },
      // 确定删除
      deleteRow(){
        const id = this.matchData[this.idx].id;
        this.matchData.splice(this.idx, 1);
        this.delVisible = false;
      },
    }
  }
</script>

<style scoped>
  .container{
    padding-top: 10px;
  }
  .tabs-wrap{
    overflow: hidden;
  }
  .left-tabs{
    width: 120px;
    float: left;
  }
  .right-content{
    float: right;
    width: calc(100% - 150px);
    height: auto;
    box-sizing: border-box;
  }
  .table{
    margin-top: 10px;
  }
  .table .icon-star{
    font-size: 14px;
    margin: 0 2px;
    color: gold;
  }
  .oper-btns{
    margin-bottom: 20px;
    text-align: right;
  }
  .red{
    color: #ff0000;
  }
  .edit-stars{
    cursor: pointer;
  }
  .edit-stars i{
    font-size: 16px;
    margin: 0 2px;
  }
  .edit-stars i.gold-star{
    color: gold;
  }
</style>