<template>
  <div class="brand">
    <div class="crumbs">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item><i class="el-icon-lx-location"/>小程序管理</el-breadcrumb-item>
        <el-breadcrumb-item>引用库</el-breadcrumb-item>
        <el-breadcrumb-item>品牌商</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-input v-model="searchName" placeholder="品牌商名称" class="handle-input"/>
        <el-button type="primary" icon="el-icon-search" @click="getBrandData">搜索</el-button>
        <!--<el-button type="primary" icon="delete" class="handle-del mr10" @click="delAll">批量删除</el-button>-->
        <div class="handle-box-right">
          <el-button type="success" icon="el-icon-plus" @click="handleEdit('add')">添加</el-button>
        </div>
      </div>
      
      <el-table :data="brandData" border class="table" ref="multipleTable">
        <!--<el-table-column type="selection" width="55" align="center"/>-->
        <el-table-column width="300" prop="brandName" label="品牌商名称"/>
        <el-table-column prop="fileId" label="LOGO">
          <template slot-scope="scope">
            <img :src="scope.row.filePath" width="300" height="200" class="logo"/>
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
      <el-form ref="brandForm" :rules="rules" :model="brandForm" label-width="100px">
        <el-form-item label="品牌商名称" prop="brandName">
          <el-input v-model="brandForm.brandName" placeholder="品牌商名称"/>
        </el-form-item>
        <el-form-item label="品牌LOGO" prop="fileId">
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
          <el-button type="primary" @click="saveOperation(operationType,'brandForm')">确 定</el-button>
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
    name: 'brand',
    data(){
      return{
        apiUrl: `${apiUrl}`,
        page: 1,
        size: 5,
        searchName: '', // 搜索品牌商名称
        brandData: [], // 品牌商列表数据
        total: 0, // 品牌商列表总数
        idx: -1, // 当前操作标记
        editVisible: false, // 编辑框状态
        delVisible: false, // 删除框状态
        modalTitle: '',
        brandForm: {
          brandName: '',
          fileId: ''
        },
        rules:{
          brandName:[
            {required: true, message: '请输入品牌商名称', trigger: 'blur'},
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
      this.getBrandData()
    },
    methods: {
      getBrandData(){
        api.getBrandList({
          page: this.page,
          size: this.size,
          brandName: this.searchName
        }).then(res => {
          if (res.data.code == "00") {
            this.total = parseInt(res.data.data.total)
            this.page = res.data.data.pageNum
            this.brandData = addDomainToList(res.data.data.list)
          }
        })
      },
      // 每页显示数目
      handleSizeChange(val){
        this.size = val;
        this.getBrandData();
      },
      // 分页导航
      handleCurrentChange(val){
        this.page = val;
        this.getBrandData();
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
              this.$message.success('图片上传成功！');
              this.brandForm.fileId = res.data.data
            }
          })
        }).catch((err) => {
          this.$message.success('图片上传失败！');
        });
      },
      beforeAvatarUpload(file) {
        const isJpgPng = file.type === 'image/jpeg' || file.type === 'image/png';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJpgPng) {
          this.$message.error('上传LOGO图片只能是 JPG、PNG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传LOGO图片大小不能超过 2MB!');
        }
        return isJpgPng && isLt2M;
      },
      // 新增 | 编辑
      handleEdit(type, row, index){
        this.operationType = type
        this.modalTitle = type == 'add' ? `添加品牌商` : `编辑`
        this.editVisible = true
        if(type == 'add'){
          this.brandForm = {
            brandName: '',
            fileId: ''
          }
          this.imageUrl = ''
        }else{
          this.brandForm = {
            brandId: row.brandId,
            brandName: row.brandName,
            fileId: row.fileId
          }
          this.imageUrl = row.filePath
        }
      },
      saveOperation(type, formName){
        if(type == 'add'){ // 添加
          this.$refs[formName].validate(valid => {
            if (valid) {
              this.$set(this.brandData, this.idx, this.brandForm);
              api.addBrand(this.brandForm).then(res => {
                if (res.data.code == "00") {
                  this.$message.success("品牌商添加成功");
                  this.getBrandData();
                  this.editVisible = false;
                } else {
                  this.$message.error("品牌商添加失败");
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
                this.brandData,
                this.idx,
                Object.assign({}, this.brandForm[this.idx], this.brandForm)
              );
              api.updateBrand(this.brandForm).then(res => {
                if (res.data.code == "00") {
                  this.$message.success("品牌商编辑成功");
                  this.getBrandData();
                  this.editVisible = false;
                } else {
                  this.$message.error("品牌商编辑失败");
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
        const brandId = this.brandData[this.idx].brandId;
        const fileId = this.brandData[this.idx].fileId;
        api.deleteBrand(brandId, fileId).then(res => {
          if (res.data.code == "00") {
            this.brandData.splice(this.idx, 1);
            this.$message.success("品牌商删除成功");
          } else {
            this.$message.error("品牌商删除失败");
          }
        })
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
  .brand{
    width: 100%;
    font-size: 14px;
  }
  .logo{
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
    object-fit: contain;
  }
</style>