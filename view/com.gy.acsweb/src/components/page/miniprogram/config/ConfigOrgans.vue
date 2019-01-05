<template>
  <div class="table">
    <div class="crumbs">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item><i class="el-icon-lx-location" />小程序管理</el-breadcrumb-item>
        <el-breadcrumb-item>配置</el-breadcrumb-item>
        <el-breadcrumb-item>器官配置</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div style="overflow: auto">
        <div style="width: 32%; float: left">
          <div class="handle-box" style="padding-bottom: 20px;">
            <span style="float: left; font-size: 16px; font-weight: bolder">器官</span>
            <div style="float: right;margin-bottom: 10px;">
              <el-button type="success" icon="el-icon-plus" @click="handle('organAdd')">添加</el-button>
            </div>
          </div>
          <el-table ref="organTable" :data="organData" highlight-current-row @current-change="handleCurOrganTable" border class="table">
            <el-table-column prop="organName" label="器官名称"/>
            <el-table-column label="操作" width="150" align="center">
              <template slot-scope="scope">
                <el-button type="text" icon="el-icon-edit" @click.stop="handle('organEdit',scope.row)">编辑</el-button>
                <el-button type="text" icon="el-icon-delete" class="red" @click.stop="handleDelete('organDel',scope.$index)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div style="width: 32%; margin-left:2%; float: left">
          <div class="handle-box" style="padding-bottom: 20px;">
            <span style="float: left; font-size: 16px; font-weight: bolder">特征类型</span>
            <div style="float: right;margin-bottom: 10px;">
              <el-button type="success" icon="el-icon-plus" @click="handle('featureAdd')">添加</el-button>
            </div>
          </div>
          <el-table ref="featureTable" :data="featureData" highlight-current-row @current-change="handleCurFeatureTable" border class="table">
            <el-table-column prop="featureTypeName" label="特征类型" />
            <!-- <el-table-column prop="createTime" label="添加时间"/> -->
            <el-table-column label="操作" width="150" align="center">
              <template slot-scope="scope">
                <el-button type="text" icon="el-icon-edit" @click.stop="handle('featureEdit',scope.row,scope.$index)">编辑</el-button>
                <el-button type="text" icon="el-icon-delete" class="red" @click.stop="handleDelete('featureDel',scope.$index)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div style="width: 32%; margin-left:2%; float: left">
          <div class="handle-box" style="padding-bottom: 20px;">
            <span style="float: left; font-size: 16px; font-weight: bolder">特征值</span>
            <div style="float: right;margin-bottom: 10px;">
              <el-button type="success" icon="el-icon-plus" @click="handle('valueAdd')">添加</el-button>
            </div>
          </div>
          <el-table :data="valueData" border class="table">
            <el-table-column prop="featureName" label="特征值"/>
            <!-- <el-table-column prop="createTime" label="添加时间"/> -->
            <el-table-column label="操作" width="150" align="center">
              <template slot-scope="scope">
                <el-button type="text" icon="el-icon-edit" @click.stop="handle('valueEdit',scope.row,scope.$index)">编辑</el-button>
                <el-button type="text" icon="el-icon-delete" class="red" @click.stop="handleDelete('valueDel',scope.$index)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
    </div>

    <!-- 器官添加/编辑弹出框 -->
    <el-dialog :title="organEditTitle" :visible.sync="editOrganVisible" width="35%">
      <el-form
        ref="organForm"
        :rules="rules"
        :model="organForm"
        label-width="100px"
        style="padding-right:20px"
        label-suffix="：">
        <el-form-item label="器官名称" prop="organName">
          <el-input v-model="organForm.organName" placeholder="请输入器官名称"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveOperation(operationType,'organForm')">确 定</el-button>
          <el-button @click="editOrganVisible = false">取 消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 字典值添加/编辑弹出框 -->
    <el-dialog :title="featureEditTitle" :visible.sync="editFeatureVisible" width="35%">
      <el-form ref="featureForm" 
        :rules="rules" 
        :model="featureForm" 
        label-width="110px" style="padding-right:20px" label-suffix="：">
        <el-form-item label="特征类型" prop="featureTypeName">
          <el-input v-model="featureForm.featureTypeName" placeholder="请输入特征类型"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveOperation(operationType,'featureForm')">确 定</el-button>
          <el-button @click="editFeatureVisible = false">取 消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 字典值添加/编辑弹出框 -->
    <el-dialog :title="valueEditTitle" :visible.sync="editValueVisible" width="35%">
      <el-form ref="valueForm" 
        :rules="rules" 
        :model="valueForm" 
        label-width="110px" style="padding-right:20px" label-suffix="：">
        <el-form-item label="特征值" prop="featureName">
          <el-input v-model="valueForm.featureName" placeholder="请输入特征值"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveOperation(operationType,'valueForm')">确 定</el-button>
          <el-button @click="editValueVisible = false">取 消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 删除提示框 -->
    <el-dialog title="提示" :visible.sync="delVisible" width="500px" center>
      <div class="del-dialog-cnt" v-html="delTitle"></div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="delVisible = false">取 消</el-button>
        <el-button type="primary" @click="deleteRow(operationType)">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import api from "@/components/common/api";
export default {
  data() {
    return {
      organEditTitle: "添加器官",
      featureEditTitle: "添加特征类型",
      valueEditTitle: "添加特征值",
      organData: [],
      featureData: [],
      valueData: [],
      editOrganVisible: false,
      editFeatureVisible: false,
      editValueVisible: false,
      delVisible: false,
      delTitle: '',
      organForm: {
        organId: "",
        organName: ""
      },
      featureForm: {
        organId: "",
        featureTypeId: "",
        featureTypeName: "",
      },
      valueForm: {
        organId: "",
        featureTypeId: "",
        featureId: "",
        featureName: "",
      },
      rules: {
        organName: [
          { required: true, message: "请输入器官名称", trigger: "blur" },
          { min: 1, max: 64, message: "长度在1到64个字符", trigger: "blur" }
        ],
        featureTypeName: [
          { required: true, message: "请输入特征类型", trigger: "blur" },
          { min: 1, max: 64, message: "长度在1到64个字符", trigger: "blur" }
        ],
        featureName: [
          { required: true, message: "请输入特征值", trigger: "blur" },
          { min: 1, max: 64, message: "长度在1到64个字符", trigger: "blur" }
        ],
      },
      idx: -1,
      operationType: "",
      currentRow: null,
    };
  },
  created() {
    this.getorganData();
    /*bus.$emit("name","fanglin");*/
  },
  methods: {
    getorganData() {
      api.getOrganList().then(res => {
        if (res.data.code == "00") {
          this.organData = res.data.data;
          this.$refs.organTable.setCurrentRow(this.organData[0]);
        }
      })
    },
    handleCurOrganTable(row) {
      if (row != null) {
        this.currentRow = row;
        this.organForm.organId = row.organId;
        api.getFeatureList({
          organId: row.organId, 
        }).then(res => {
          if (res.data.code == "00") {
            if(res.data.data.list.length > 0){
              this.featureData = res.data.data.list;
              this.$refs.featureTable.setCurrentRow(this.featureData[0]);
            }else{
              this.featureData = []
              this.valueData = []
            }
          }
        })
      }
    },
    handleCurFeatureTable(row){
      if (row != null) {
        this.currentRow = row;
        this.featureForm = {
          organId: row.organId,
          featureTypeId: row.featureTypeId,
          featureTypeName: row.featureTypeName
        }
        api.getValueList({
          featureTypeId: row.featureTypeId,
        }).then(res => {
          if (res.data.code == "00") {
            this.valueData = res.data.data.list;
          }
        })
      }
    },
    handle(type, row, index) {
      this.operationType = type;
      switch (type) {
        case "organAdd":
          this.organEditTitle = "添加器官";
          this.organForm.organId = "";
          this.organForm.organName = "";
          this.editOrganVisible = true;
          break;
        case "organEdit":
          this.organEditTitle = "编辑器官";
          this.organForm = {
            organId: row.organId,
            organName: row.organName
          };
          this.editOrganVisible = true;
          break;
        case "featureAdd":
          this.featureEditTitle = "添加特征类型";
          this.featureForm.featureTypeId = "";
          this.featureForm.featureTypeName = "";
          this.editFeatureVisible = true;
          break;
        case "featureEdit":
          this.featureEditTitle = "编辑特征类型";
          this.idx = index;
          this.featureForm = {
            organId: row.organId,
            featureTypeId: row.featureTypeId,
            featureTypeName: row.featureTypeName,
          };
          this.editFeatureVisible = true;
          break;
        case "valueAdd":
          this.valueEditTitle = "添加特征值";
          this.valueForm.featureId = "";
          this.valueForm.featureName = "";
          this.valueForm.featureTypeId = this.currentRow.featureTypeId;
          this.editValueVisible = true;
          break;
        case "valueEdit":
          this.valueEditTitle = "编辑特征值";
          this.idx = index;
          this.valueForm = {
            organId: row.organId,
            featureTypeId: row.featureTypeId,
            featureId: row.featureId,
            featureName: row.featureName,
          }
          this.editValueVisible = true;
          break;
      }
    },
    // 保存编辑
    saveOperation(operationType, formName) {
      switch(operationType){
        case 'organAdd':
          this.$refs[formName].validate(valid => {
            if (valid) {
              this.$set(this.organData, this.idx, this.organForm);
              api.addOrgan(this.organForm).then(res => {
                if (res.data.code == "00") {
                  this.$message.success("器官添加成功");
                  this.getorganData();
                  this.editOrganVisible = false;
                } else {
                  this.$message.error("器官添加失败");
                }
              });
            } else {
              this.$message.error("验证失败！");
              return false;
            }
          })
          break;
        case 'organEdit':
          this.$refs[formName].validate(valid => {
            if (valid) {
              this.$set(
                this.organData,
                this.idx,
                Object.assign({}, this.organData[this.idx], this.organForm)
              );
              api.updateOrgan(this.organForm).then(res => {
                if (res.data.code == "00") {
                  this.$message.success("器官编辑成功");
                  this.getorganData();
                  this.editOrganVisible = false;
                } else {
                  this.$message.error("器官编辑失败");
                }
              });
            } else {
              this.$message.error("验证失败！");
              return false;
            }
          });
          break;
        case 'featureAdd':
          this.$refs[formName].validate(valid => {
            if (valid) {
              api.addFeature(this.featureForm).then(res => {
                if (res.data.code == "00") {
                  this.handleCurOrganTable(this.currentRow);
                  this.$message.success("特征类型添加成功");
                  this.editFeatureVisible = false;
                } else {
                  this.$message.error("特征类型添加失败");
                }
              });
            } else {
              this.$message.error("验证失败！");
              return false;
            }
          });
          break;
        case 'featureEdit':
          this.$refs[formName].validate(valid => {
            if (valid) {
              this.$set(
                this.featureData,
                this.idx,
                Object.assign({}, this.featureData[this.idx], this.featureForm)
              );
              api.updateFeature(this.featureForm).then(res => {
                if (res.data.code == "00") {
                  this.handleCurOrganTable(this.currentRow);
                  this.$message.success("特征类型编辑成功");
                  this.editFeatureVisible = false;
                } else {
                  this.$message.error("特征类型编辑失败");
                }
              })
            } else {
              this.$message.error("验证失败！");
              return false;
            }
          })
          break;
        case 'valueAdd':
          this.$refs[formName].validate(valid => {
            if (valid) {
              api.addValue(this.valueForm).then(res => {
                if (res.data.code == "00") {
                  this.handleCurFeatureTable(this.currentRow);
                  this.$message.success("特征值添加成功");
                  this.editValueVisible = false;
                } else {
                  this.$message.error("特征值添加失败");
                }
              });
            } else {
              this.$message.error("验证失败！");
              return false;
            }
          });
          break;
        case 'valueEdit':
          this.$refs[formName].validate(valid => {
            if (valid) {
              this.$set(
                this.valueData,
                this.idx,
                Object.assign({}, this.valueData[this.idx], this.valueForm)
              );
              api.updateValue(this.valueForm)
                .then(res => {
                  if (res.data.code == "00") {
                    this.handleCurFeatureTable(this.currentRow);
                    this.$message.success("特征类型值编辑成功");
                    this.editValueVisible = false;
                  } else {
                    this.$message.error("特征类型值编辑失败");
                  }
                });
            } else {
              this.$message.error("验证失败！");
              return false;
            }
          })
          break;
      }
    },

    handleDelete(type, index) {
      this.idx = index;
      this.operationType = type;
      switch (type) {
        case "organDel":
          this.delTitle = '此删除将删除此器官分类及其关联的特征类型和特征值</br>删除不可恢复，是否确定删除？'
          break;
        case "featureDel":
          this.delTitle = '此删除将删除此特征类型及其关联的特征值</br>删除不可恢复，是否确定删除？'
          break;
        case "valueDel":
          this.delTitle = '删除不可恢复，是否确定删除？'
          break;
      }
      this.delVisible = true;
    },
    // 确定删除
    deleteRow(operationType) {
      switch(operationType){
        case 'organDel':
          let delorganId = this.organData[this.idx].organId;
          api.deleteOrgan(delorganId).then(res => {
            if (res.data.code == "00") {
              this.organData.splice(this.idx, 1);
              this.featureData = [];
              this.valueData = [];
              this.$message.success("器官删除成功");
            } else {
              this.$message.error("器官删除失败");
            }
          })
          break;
        case 'featureDel':
          let featureTypeId = this.featureData[this.idx].featureTypeId;
          api.deleteFeature(featureTypeId).then(res => {
            if (res.data.code == "00") {
              this.featureData.splice(this.idx, 1)
              this.valueData = []
              this.$message.success("特征类型删除成功")
            } else {
              this.$message.error("特征类型删除失败")
            }
          })
          break;
        case 'valueDel':
          let featureId = this.valueData[this.idx].featureId;
          api.deleteValue(featureId).then(res => {
            if (res.data.code == "00") {
              this.valueData.splice(this.idx, 1)
              this.$message.success("特征类型值删除成功")
            } else {
              this.$message.error("特征类型值删除失败")
            }
          })
          break;
      }
      this.delVisible = false;
    }
  }
}
</script>

<style scoped>
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
.table {
  width: 100%;
  font-size: 14px;
}
.red {
  color: #ff0000;
}
</style>
