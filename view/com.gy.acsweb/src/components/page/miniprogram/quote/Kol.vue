<template>
  <div class="kol">
    <div class="crumbs">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item><i class="el-icon-lx-location"/>小程序管理</el-breadcrumb-item>
        <el-breadcrumb-item>引用库</el-breadcrumb-item>
        <el-breadcrumb-item>KOL</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-input v-model="searchName" placeholder="KOL名称" class="handle-input"/>
        <el-button type="primary" icon="el-icon-search" @click="getKolData">搜索</el-button>
        <!--<el-button type="primary" icon="delete" class="handle-del mr10" @click="delAll">批量删除</el-button>-->
        <div class="handle-box-right">
          <el-button type="success" icon="el-icon-plus" @click="handleEdit('add')">添加</el-button>
        </div>
      </div>
      
      <el-table :data="kolData" border class="table" ref="multipleTable">
        <!--<el-table-column type="selection" width="55" align="center"/>-->
        <el-table-column width="300" prop="kolName" label="KOL名称"/>
        <el-table-column prop="fileId" label="LOGO">
          <template slot-scope="scope">
            <img :src="scope.row.filePath" width="80" height="80" class="potrait"/>
          </template>
        </el-table-column>
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
      <el-form ref="kolForm" :rules="rules" :model="kolForm" label-width="100px">
        <el-form-item label="KOL名称" prop="kolName">
          <el-input v-model="kolForm.kolName" placeholder="KOL名称"/>
        </el-form-item>
        <el-form-item label="头像" prop="fileId">
          <el-upload
            class="avatar-uploader"
            action=""
            :http-request="uploadImageFile"
            accept="image/*"
            :show-file-list="false"
            :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveOperation(operationType,'kolForm')">确 定</el-button>
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
  import api from "@/components/common/api"
  import { addDomainToList } from '@/components/common/common'
  export default {
    name: 'kol',
    data(){
      return{
        apiUrl: `${apiUrl}`,
        page: 1,
        size: 5,
        searchName: '', // 搜索KOL名称
        kolData: [], // KOL列表数据
        total: 0, // KOL列表总数
        idx: -1, // 当前操作标记
        editVisible: false, // 编辑框状态
        delVisible: false, // 删除框状态
        modalTitle: '',
        kolForm: {
          kolName: '',
          fileId: ''
        },
        rules:{
          kolName:[
            {required: true, message: '请输入KOL名称', trigger: 'blur'},
            {min: 1, max: 64, message: '长度在1到64个字符', trigger: 'blur'}
          ],
          fileId:[
            {required: true, message: '请上传品牌LOGO'}
          ]
        },
        imageUrl: '',
        operationType: '',
      }
    },
    created() {
      this.getKolData()
    },
    methods: {
      getKolData(){
        api.getKolList({
          page: this.page,
          size: this.size,
          kolName: this.searchName
        }).then(res => {
          if (res.data.code == "00") {
            this.total = parseInt(res.data.data.total)
            this.page = res.data.data.pageNum
            this.kolData = addDomainToList(res.data.data.list)
          }
        })
      },
      // 每页显示数目
      handleSizeChange(val){
        this.size = val;
        this.getKolData();
      },
      // 分页导航
      handleCurrentChange(val){
        this.page = val;
        this.getKolData();
      },
      uploadImageFile(content){
        const file = content.file, client = this.$util.ossClient();
        const fileName = file.name.substr(0,file.name.lastIndexOf(".")),
              fileSuffix = file.name.substr(file.name.lastIndexOf(".") + 1),
              fileType = file.type,
              fileSize = Math.ceil(file.size/1024),
              filePath = `${this.$util.getUploadOssUrl()}${this.$util.uuid()}.${fileSuffix}`;
        client.put(filePath, file, {
          progress: (percentage) => {
            this.percentage = percentage; //上传的进度
          }
        }).then(res => {
          this.imageUrl = res.url
          this.$api.addSysFileInfo({
            fileName: fileName,
            fileSuffix: fileSuffix,
            fileSize: fileSize,
            filePath: filePath,
            playTime: 0,
          }).then((res) => {
            if(res.data.code == "00"){
              this.$message.success('头像上传成功！');
              this.kolForm.fileId = res.data.data
            }
          })
        }).catch((err) => {
          this.$message.success('头像上传失败！');
        });
      },
      beforeAvatarUpload(file) {
        const isJpgPng = file.type === 'image/jpeg' || file.type === 'image/png';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJpgPng) {
          this.$message.error('上传头像图片只能是 JPG、PNG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJpgPng && isLt2M;
      },
      // 新增 | 编辑
      handleEdit(type, row, index){
        this.operationType = type
        this.modalTitle = type == 'add' ? `添加KOL` : `编辑`
        this.editVisible = true
        if(type == 'add'){
          this.kolForm = {
            kolName: '',
            fileId: ''
          }
          this.imageUrl = ''
        }else{
          this.kolForm = {
            kolId: row.kolId,
            kolName: row.kolName,
            fileId: row.fileId
          }
          this.imageUrl = row.filePath
        }
      },
      saveOperation(type, formName){
        if(type == 'add'){ // 添加
          this.$refs[formName].validate(valid => {
            if (valid) {
              this.$set(this.kolData, this.idx, this.kolForm);
              api.addKol(this.kolForm).then(res => {
                if (res.data.code == "00") {
                  this.$message.success("KOL添加成功");
                  this.getKolData();
                  this.editVisible = false;
                } else {
                  this.$message.error("KOL添加失败");
                }
              });
            } else {
              this.$message.error("验证失败！");
              return false;
            }
          })
        }else{ // 删除
          this.$refs[formName].validate(valid => {
            if (valid) {
              this.$set(
                this.kolData,
                this.idx,
                Object.assign({}, this.kolForm[this.idx], this.kolForm)
              );
              api.updateKol(this.kolForm).then(res => {
                if (res.data.code == "00") {
                  this.$message.success("KOL编辑成功");
                  this.getKolData();
                  this.editVisible = false;
                } else {
                  this.$message.error("KOL编辑失败");
                }
              });
            } else {
              this.$message.error("验证失败！");
              return false;
            }
          })
        }
      },
      handleDelete(index){
        this.idx = index;
        this.delVisible = true;
      },
      // 确定删除
      deleteRow(){
        const kolId = this.kolData[this.idx].kolId;
        const fileId = this.kolData[this.idx].fileId;
        api.deleteKol(kolId, fileId).then(res => {
          if (res.data.code == "00") {
            this.kolData.splice(this.idx, 1);
            this.$message.success("KOL删除成功");
          } else {
            this.$message.error("KOL删除失败");
          }
        })
        this.delVisible = false;
      }
    }
  }
</script>

<style lang="scss">
  .kol{
    width: 100%;
    font-size: 14px;
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
    .potrait{
      object-fit: cover;
    }
    .red{
      color: #ff0000;
    }
    .avatar-uploader .el-upload {
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
      border-color: #409EFF;
    }
    .avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      line-height: 178px;
      text-align: center;
    }
    .avatar {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
    .el-upload--text{
      width: 180px;
    }
  }
</style>