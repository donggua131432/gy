<template>
  <div>
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="base-info-form" :disabled="!editStatus">
      <div class="edit-wrap">
        <div class="form-left">
          <el-form-item label="整妆原名" prop="originName">
            <el-input v-model="ruleForm.originName" placeholder="请选择整妆原名" class="single-item"></el-input>
          </el-form-item>
          <el-form-item label="入口名" prop="enterName">
            <el-input v-model="ruleForm.enterName" placeholder="请选择入口名" class="single-item"></el-input>
          </el-form-item>
          <el-form-item label="入口图" prop="enterImg">
            <el-upload
              class="avatar-uploader"
              action="https://jsonplaceholder.typicode.com/posts/"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
              <img v-if="imageUrl" :src="imageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
        </div>
        <div class="form-right">
          <el-form-item label="选择品牌商" prop="brand">
            <el-select v-model="ruleForm.brand" placeholder="请选择品牌商" class="single-item">
              <el-option label="品牌1" value="pinpai1"></el-option>
              <el-option label="品牌2" value="pinpai2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="模特封面图" prop="modelCover">
            <el-upload
              class="avatar-uploader"
              action="https://jsonplaceholder.typicode.com/posts/"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
              <img v-if="modelCover" :src="modelCover" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="选择KOL" prop="kol">
            <el-select v-model="ruleForm.kol" placeholder="请选择KOL" class="single-item">
              <el-option label="KOL1" value="kol1"></el-option>
              <el-option label="KOL2" value="kol2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="封面底色" prop="bgColor">
            <el-input v-model="ruleForm.bgColor" class="single-item" placeholder="请输入封面底色"></el-input>
          </el-form-item>
          <el-form-item label="封面标题" prop="title">
            <el-input v-model="ruleForm.title" class="single-item" placeholder="请输入封面标题"></el-input>
          </el-form-item>
          <el-form-item label="标题色号" prop="color">
            <el-input v-model="ruleForm.color" class="single-item" placeholder="请输入标题色号"></el-input>
          </el-form-item>
        </div>
      </div>
      <el-form-item >
        <el-button type="primary" @click="submitForm('ruleForm')">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  export default {
    name: 'campaign',
    props: {
      editStatus: Boolean
    },
    data() {
      return {
        imageUrl: '',
        modelCover: '',
        ruleForm: {
          originName: '',
          enterName: '',
          enterImg: '',
          brand: '',
          modelCover: '',
          kol: '',
          bgColor: '',
          title: '',
          color: '',
        },
        rules: {
          originName: [
            { required: true, message: '请输入整妆原名', trigger: 'blur' },
          ],
          enterName: [
            { required: true, message: '请输入入口名', trigger: 'blur' },
          ],
          enterImg: [
            { required: true, message: '请输上传入口图',},
          ],
          brand: [
            { required: true, message: '请选择品牌商', trigger: 'blur' },
          ],
          modelCover: [
            { required: true, message: '请上传模特封面',},
          ],
          kol: [
            { required: true, message: '请选择KOL', trigger: 'blur' },
          ],
          bgColor: [
            { required: true, message: '请输入封面底色', trigger: 'blur' },
          ],
          title: [
            { required: true, message: '请输入封面标题', trigger: 'blur' },
          ],
          color: [
            { required: true, message: '请输入标题色号', trigger: 'blur' },
          ],
        }
      };
    },
    methods: {
      handleAvatarSuccess(res, file) {
        this.imageUrl = URL.createObjectURL(file.raw);
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('submit!');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      }
    }
  }
</script>

<style scoped>
  .base-info-form{
    padding-top: 10px;
  }
  .edit-wrap{
    overflow: hidden;
  }
  .form-left{
    width: 42%;
    float: left;
  }
  .form-right{
    width: 380px;
    float: left;
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
  .single-item{
    width: 360px;
  }
</style>