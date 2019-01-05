<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 表单</el-breadcrumb-item>
                <el-breadcrumb-item>文件上传</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="content-title">上传视频</div>
            <el-upload class="upload-demo" drag action="/uploadFile" :http-request="uploadFile" accept="video/mp4" :limit="1" :before-upload="onBeforeUploadVideo">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em><br>只能上传mp4文件，且不超过20M</div>
            </el-upload>

            <div class="content-title">上传视频到阿里云OSS</div>
            <el-upload class="upload-demo" drag action="" :http-request="uploadFileOss" accept="video/mp4" :limit="1" :before-upload="onBeforeUploadVideo">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">{{percentage}}将文件拖到此处，或<em>点击上传</em><br>只能上传mp4文件，且不超过20M</div>
            </el-upload>
            <div class="content-title">
                <video :src="ossFileUrl" controls preload="auto" ref="ossFilePlayer" :width="300" :height="200"></video>
            </div>

            <div class="content-title">上传图片</div>
            <el-upload class="upload-demo" drag action="/uploadFile" :http-request="uploadFile" accept="image/jpeg" :limit="1" :before-upload="onBeforeUploadImg">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em><br>只能上传jpg文件，且不超过2M</div>
            </el-upload>

            <div class="content-title">支持裁剪</div>
            <div class="crop-demo">
                <img :src="cropImg" class="pre-img">
                <div class="crop-demo-btn">选择图片
                    <input class="crop-input" type="file" name="image" accept="image/*" @change="setImage"/>
                </div>
            </div>
            <el-dialog title="裁剪图片" :visible.sync="dialogVisible" width="30%">
                <vue-cropper ref='cropper' :src="imgSrc" :ready="cropImage" :zoom="cropImage" :cropmove="cropImage" style="width:100%;height:300px;"></vue-cropper>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="cancelCrop">取 消</el-button>
                    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import VueCropper  from 'vue-cropperjs';
    import fs from 'fs';
    export default {
        name: 'upload',
        data: function(){
            return {
                defaultSrc: './static/img/img.jpg',
                fileList: [],
                imgSrc: '',
                cropImg: '',
                dialogVisible: false,
                percentage: '',
                ossFileUrl: ''
            }
        },
        components: {
            VueCropper
        },
        methods:{
            uploadFile(content){
                let formData = new FormData();
                formData.append("file", content.file);

                this.$util.post(content.action,formData).then(res =>{
                    console.log(res);
                }).catch(error => {
                    console.log(error);
                });
            },

            uploadFileOss(content){
                const file = content.file, client = this.$util.ossClient();
                const fileName = file.name.substr(0,file.name.lastIndexOf(".")),
                    fileSuffix = file.name.substr(file.name.lastIndexOf(".")+1),
                    fileSize = Math.ceil(file.size/1024),
                    filePath = this.$util.getUploadOssUrl()+this.$util.uuid()+"."+fileSuffix;

                client.put(filePath, file, {
                    progress: (percentage) => {
                        //上传的进度
                        this.percentage = percentage;
                    }
                }).then((res) => {
                    this.ossFileUrl = res.url;
                    setTimeout(() => {
                        const playTime = Math.ceil(this.$refs.ossFilePlayer.duration);
                        this.$api.addSysFileInfo({
                            "fileName":fileName,
                            "fileSuffix":fileSuffix,
                            "playTime":playTime,
                            "fileSize":fileSize,
                            "filePath":filePath,
                        }).then((res) => {
                            if(res.data.code == "00"){
                                this.$message.success(res.data.msg);
                                console.log(res.data.data);
                            }
                        })
                    },1000);
                }).catch((err) => {
                    this.$message.success('文件上传失败！');
                });
            },

            setImage(e){
                const file = e.target.files[0];
                if (!file.type.includes('image/')) {
                    return;
                }
                const reader = new FileReader();
                reader.onload = (event) => {
                    this.dialogVisible = true;
                    this.imgSrc = event.target.result;
                    this.$refs.cropper && this.$refs.cropper.replace(event.target.result);
                };
                reader.readAsDataURL(file);
            },
            cropImage () {
                this.cropImg = this.$refs.cropper.getCroppedCanvas().toDataURL();
            },
            cancelCrop(){
                this.dialogVisible = false;
                this.cropImg = this.defaultSrc;
            },
            handleError(){
                this.$notify.error({
                    title: '上传失败',
                    message: '图片上传接口上传失败，可更改为自己的服务器接口'
                });
            },
            onBeforeUploadVideo(file){
                const isVideo = file.type === 'video/mp4', isSize = file.size / 1024 / 1024 < 20;
                if (!isVideo) this.$message.error('上传视频只能是MP4格式!');
                if (!isSize) this.$message.error('上传视频大小不能超过20MB!');
                return isVideo && isSize;
            },
            onBeforeUploadImg(file){
                const isImage = file.type === 'image/jpeg',  isSize = file.size / 1024 / 1024 < 2;
                if (!isImage) this.$message.error('上传视频只能是jpg格式!');
                if (!isSize) this.$message.error('上传视频大小不能超过2MB!');
                return isImage && isSize;
            }
        },
        created(){
            this.cropImg = this.defaultSrc;
        }
    }
</script>

<style scoped>
    .content-title{
        font-weight: 400;
        line-height: 50px;
        margin: 10px 0;
        font-size: 22px;
        color: #1f2f3d;
    }
    .pre-img{
        width: 100px;
        height: 100px;
        background: #f8f8f8;
        border: 1px solid #eee;
        border-radius: 5px;
    }
    .crop-demo{
        display: flex;
        align-items: flex-end;
    }
    .crop-demo-btn{
        position: relative;
        width: 100px;
        height: 40px;
        line-height: 40px;
        padding: 0 20px;
        margin-left: 30px;
        background-color: #409eff;
        color: #fff;
        font-size: 14px;
        border-radius: 4px;
        box-sizing: border-box;
    }
    .crop-input{
        position: absolute;
        width: 100px;
        height: 40px;
        left: 0;
        top: 0;
        opacity: 0;
        cursor: pointer;
    }
</style>
