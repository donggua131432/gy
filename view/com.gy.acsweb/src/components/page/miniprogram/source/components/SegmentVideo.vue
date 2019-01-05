<template>
  <div class="seg-video">
    <el-form :model="videoForm" :rules="rules" ref="videoForm" label-width="100px" class="video-form" :disabled="!editStatus">
      <div class="edit-wrap">
        <div class="form-left">
          <el-form-item label="视频文件" prop="Video">
            <el-upload 
              class="avatar-uploader" 
              :action="uploadUrl" 
              :show-file-list="false" 
              :on-success="handleVideoSuccess" 
              :before-upload="beforeUploadVideo" 
              :on-progress="uploadVideoProcess">
                <video 
                  v-if="videoForm.Video !='' && videoFlag == false" 
                  :src="videoForm.Video" 
                  class="avatar" 
                  controls="controls">您的浏览器不支持视频播放</video>
                <i 
                  v-else-if="videoForm.Video =='' && videoFlag == false" 
                  class="el-icon-plus avatar-uploader-icon"></i>
                <el-progress 
                  v-if="videoFlag == true" 
                  type="circle" 
                  :percentage="videoUploadPercent"
                  style="margin-top: 115px"></el-progress>
            </el-upload>
            <P class="upload-video-tips">请保证视频格式正确，且不超过10M</P>
          </el-form-item>
        </div>
        <div class="form-right">
          <el-form-item label="分段信息" prop="Segments">
            <!-- 开头 -->
            <div class="seg-item">
              <div class="seg-item-title">开头</div>
              <div class="seg-item-info">
                <el-col :span="12">
                  <el-form-item label="结束时间点">
                    <el-time-picker
                      v-model="videoForm.begin.endtime"
                      :picker-options="{ selectableRange: '00:00:00 - 00:59:59' }"
                      placeholder="结束时间点"
                      style="width:100%">
                    </el-time-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="步骤名" class="seg-item-info">
                    <el-input v-model="videoForm.begin.segName" placeholder="步骤名"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="试妆包" prop="begin.makeupPkg">
                    <el-select v-model="videoForm.begin.makeupPkg" placeholder="请选择试妆包">
                      <el-option label="试妆包1" value="makeupPkg1"></el-option>
                      <el-option label="试妆包2" value="makeupPkg2"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </div>
            </div>
            <!-- 自定义分段 -->
            <div class="seg-item user-define-item" v-for="(item, index) in defineSegment" :key="index">
              <div class="seg-item-title">自定义分段{{index + 1}}
                <el-button class="del-seg-btn" type="text" icon="el-icon-delete" @click="deleteCurSegment(index)">删除</el-button>
              </div>
              <div class="seg-item-info">
                <el-col :span="12">
                  <el-form-item label="结束时间点">
                    <el-time-picker
                      v-model="item.endtime"
                      :picker-options="{ selectableRange: '00:00:00 - 00:59:59' }"
                      placeholder="结束时间点"
                      style="width:100%"></el-time-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="步骤名" class="seg-item-info">
                    <el-input v-model="item.segName" placeholder="步骤名"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="试妆包" prop="begin.makeupPkg">
                    <el-select v-model="item.makeupPkg" placeholder="请选择试妆包">
                      <el-option label="试妆包1" value="makeupPkg1"></el-option>
                      <el-option label="试妆包2" value="makeupPkg2"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </div>
            </div>
            <el-button class="add-segment" @click="addDefineSegment">添加一个自定义分段</el-button>
            <!-- 结尾 -->
            <div class="seg-item">
              <div class="seg-item-title">结尾</div>
              <div class="seg-item-info">
                <el-col :span="12">
                  <el-form-item label="结束时间点">
                    <el-time-picker
                      v-model="videoForm.end.endtime"
                      :picker-options="{ selectableRange: '00:00:00 - 00:59:59' }"
                      placeholder="结束时间点"
                      style="width:100%"></el-time-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="步骤名" class="seg-item-info">
                    <el-input v-model="videoForm.end.segName" placeholder="步骤名"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="试妆包" prop="end.makeupPkg">
                    <el-select v-model="videoForm.end.makeupPkg" placeholder="请选择试妆包">
                      <el-option label="试妆包1" value="makeupPkg1"></el-option>
                      <el-option label="试妆包2" value="makeupPkg2"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </div>
            </div>
          </el-form-item>
        </div>
      </div>
      <el-form-item >
        <el-button type="primary" @click="submitForm('videoForm')">保存</el-button>
      </el-form-item>
    </el-form>

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
    name: 'campaign',
    props: {
      editStatus: Boolean
    },
    data() {
      return {
        uploadUrl: '',
        videoFlag: false,
        videoUploadPercent: 0,
        defineSegment: [
          {endtime: new Date(0,0), segName: '', makeupPkg: []}
        ],
        videoForm: {
          Video: '',
          Segments: '',
          begin: {
            endtime: new Date(0,0),
            segName: '',
            makeupPkg: []
          },
          end: {
            endtime: new Date(0,0),
            segName: '',
            makeupPkg: []
          }
        },
        rules: {
          Video: [
            { required: true, message: '请上传视频文件' },
          ],
          Segments: [
            { required: true, message: '请填写分段信息' },
          ],
        },
        idx: -1, // 当前操作标记
        delVisible: false,
      };
    },
    methods: {
      uploadVideoProcess(event, file, fileList){
        this.videoFlag = true;
        this.videoUploadPercent = parseInt(file.percentage.toFixed(0));
      },
      handleVideoSuccess(res, file) {
        this.videoFlag = false;
        this.videoUploadPercent = 0;
        if(res.status == 200){
            this.videoForm.videoUploadId = res.data.uploadId;
            this.videoForm.Video = res.data.uploadUrl;
        }else{
            this.$message.error('视频上传失败，请重新上传！');
        }
      },
      beforeUploadVideo(file) {
        const isLt10M = file.size / 1024 / 1024  < 10;
        if (['video/mp4', 'video/ogg', 'video/flv','video/avi','video/wmv','video/rmvb'].indexOf(file.type) == -1) {
            this.$message.error('请上传正确的视频格式');
            return false;
        }
        if (!isLt10M) {
            this.$message.error('上传视频大小不能超过10MB哦!');
            return false;
        }
      },
      // 添加一个自定义分段
      addDefineSegment(){
        this.defineSegment.push({endtime: new Date(0,0), segName: '', makeupPkg: []})
      },
      deleteCurSegment(idx){
        this.delVisible = true
        this.idx = idx
      },
      // 确定删除
      deleteRow(){
        this.defineSegment.splice(this.idx, 1);
        this.delVisible = false;
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

<style lang="scss">
  .seg-video{
    .video-form{
      padding-top: 10px;
    }
    .edit-wrap{
      overflow: hidden;
    }
    .form-left{
      width: 42%;
      height: 360px;
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
      width: 358px;
      height: 358px;
      line-height: 358px;
      text-align: center;
    }
    .avatar {
      width: 100%;
      height: 100%;
      object-fit: contain;
    }
    .el-upload--text{
      height: 360px;
    }
    .upload-video-tips{
      color: #aaa;
      font-size: 12px;
    }
    .form-right{
      width: 54%;
      float: left;
    }
    .seg-item{
      border-radius: 6px;
      border: 1px dashed #d9d9d9;
      margin-bottom: 20px;
    }
    .seg-item:last-of-type{
      margin-bottom: 0;
    }
    .seg-item-title{
      padding-left: 18px;
      padding-right: 18px;
      background-color: #f9f9f9;
      margin-bottom: 18px;
      overflow: hidden;
    }
    .seg-item-info{
      padding-right: 9px;
      overflow: hidden;
    }
    .add-segment{
      width: 100%;
      height: 32px;
      border: 1px dashed #409EFF;
      color: #409EFF;
      margin-bottom: 20px;
      border-radius: 6px;
      text-align: center;
      font-size: 14px;
      cursor: pointer;
    }
    .user-define-item .seg-item-title{
      color: #409EFF;
    }
    .delete-define-item{
      padding-right: 9px;
      text-align: right;
    }
    .del-seg-btn{
      color: #f56c6c;
      float: right;
    }
  }
  
</style>