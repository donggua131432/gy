<template>
  <div class="campaign">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="base-info-form" :disabled="!editStatus">
      <div class="edit-wrap">
        <div class="form-left">
          <el-form-item label="外链文字" prop="outLinkText">
            <el-input v-model="ruleForm.originName" placeholder="请输入外链文字" class="single-item"></el-input>
          </el-form-item>
          <el-form-item label="外链URL" prop="outLinkURL">
            <el-input v-model="ruleForm.enterName" placeholder="请输入外链URL" class="single-item"></el-input>
          </el-form-item>
          <el-form-item label="标题" prop="title">
            <el-input v-model="ruleForm.enterName" placeholder="请输入标题" class="single-item"></el-input>
          </el-form-item>
          <el-form-item label="封面图" prop="coverImg">
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
          <el-form-item label="文章">
            <div class="article-item" v-for="item in articleSeg" :key="item.sort">
              <div class="article-item-oper">{{item.flag == 'article' ? '正文' : item.flag == 'image' ? '图片' : '高级跑马灯'}}
                <div class="oper-btns">
                  <el-button v-if="item.flag == 'article'" 
                             size="mini" type="primary" plain @click="changeTextStatus('strong')">强调</el-button>
                  <el-button v-if="item.flag == 'article'"
                             size="mini" type="warning" plain @click="changeTextStatus('quote')">引用</el-button>
                  <el-button class="up-item-btn" type="text" 
                             @click="move('up', articleSeg, item.sort)"
                             :disabled="item.sort == 0"><i class="el-icon-back"></i>上移</el-button>
                  <el-button class="down-item-btn" type="text" 
                             @click="move('down', articleSeg, item.sort)"
                             :disabled="item.sort == articleSeg.length - 1"><i class="el-icon-back"></i>下移</el-button>
                  <el-button class="del-item-btn" type="text" icon="el-icon-delete" @click="deleteCur(item.sort, 'seg')">删除</el-button>
                </div>
              </div>
              <!-- 正文 -->
              <div v-if="item.flag == 'article'" class="article-item-content">
                <!-- <el-input type="textarea" rows="7" class="mainContent" :value="ruleForm.content"></el-input> -->
                <textarea rows="7" class="mainContent" :value="ruleForm.content" :readonly="!editStatus"></textarea>
              </div>
              <!-- 图片 -->
              <div v-else-if="item.flag == 'image'" class="article-item-content">
                <el-upload
                  class="avatar-uploader"
                  action="https://jsonplaceholder.typicode.com/posts/"
                  :show-file-list="false"
                  :on-success="handleAvatarSuccess"
                  :before-upload="beforeAvatarUpload">
                  <img v-if="imageUrl" :src="imageUrl" class="avatar">
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
              </div>
              <!-- 高级跑马灯 -->
              <div v-else class="article-item-content lamp-content">
                <div class="lamp-img-item" v-for="it in item.lampImgArr" :key="it.sort">
                  <img :src="it.img" alt="">
                  <span class="lamp-img-item-oper">
                    <el-button 
                      type="text"
                      @click="move('left', item.lampImgArr, it.sort)"
                      :disabled="it.sort == 0"
                      :class="{'colorWhite' : it.sort != 0}"><i class="el-icon-back"></i> 左移</el-button>
                    <el-button
                      type="text"
                      @click="move('right', item.lampImgArr, it.sort)"
                      :disabled="it.sort == item.lampImgArr.length - 1"
                      :class="{'colorWhite' : it.sort != item.lampImgArr.length - 1}"><i class="el-icon-back"></i> 右移</el-button>
                    <el-button 
                      type="text" 
                      class="colorWhite"
                      @click="deleteCur(it.sort, 'lamp')"><i class="el-icon-delete"></i> 删除</el-button>
                  </span>
                </div>
                <el-upload
                  class="avatar-uploader"
                  action="https://jsonplaceholder.typicode.com/posts/"
                  :show-file-list="false"
                  :on-success="lampSuccess"
                  :before-upload="lampUpload">
                  <i class="el-icon-plus avatar-uploader-icon"></i>
                  <div class="mask" @click="interceptUpload" :data-index="item.sort"></div>
                </el-upload>
              </div>
            </div>
            <!-- 添加 -->
            <el-popover
              placement="top"
              width="53%"
              v-model="popVisible"
              trigger="click">
              <div>
                <el-button @click="articleSegChange('article')">正文</el-button>
                <el-button @click="articleSegChange('image')">图片</el-button>
                <el-button @click="articleSegChange('lamp')">高级跑马灯</el-button>
              </div>
              <el-button class="add-paragraph" slot="reference" icon="el-icon-plus"></el-button>
            </el-popover>
          </el-form-item>
        </div>
      </div>
      <el-form-item >
        <el-button type="primary" @click="submitForm('ruleForm')">保存</el-button>
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
  import { moveAction } from '@/components/common/common'
  export default {
    name: 'campaign',
    props: {
      editStatus: Boolean
    },
    data() {
      return {
        imageUrl: '',
        modelCover: '',
        articleSeg: [
          {
            flag: 'article', 
            articleTxt: '',
            sort: 0
          },
          {
            flag: 'image', 
            img: '',
            sort: 1,
          },
          {
            flag: 'lamp', 
            lampImgArr: [],
            sort: 2
          },
        ],
        popVisible: false,
        delVisible: false,
        ruleForm: {
          outLinkText: '',
          outLinkURL: '',
          title: '',
          coverImg: '',
          content: '',
        },
        rules: {
          outLinkText: [
            { required: true, message: '请输入外链文字', trigger: 'blur' },
          ],
          outLinkURL: [
            { required: true, message: '请输入外链URL', trigger: 'blur' },
          ],
          title: [
            { required: true, message: '请输入标题',},
          ],
          coverImg: [
            { required: true, message: '请上传封面图', trigger: 'blur' },
          ],
          content: [
            { required: true, message: '请输入文章内容' },
          ]
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
      lampSuccess(res, file, list) {
        let obj = {'img': URL.createObjectURL(file.raw), sort: list.length - 1}
        this.articleSeg[this.idx].lampImgArr.push(obj)
      },
      lampUpload(file) {
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
      interceptUpload(e){
        this.idx = e.srcElement.dataset.index
      },
      // 强调
      changeTextStatus(type){
        let $content = this.$el.querySelectorAll('.mainContent')[0];
        let txt = window.getSelection ? window.getSelection() : document.selection.createRange().text
        let str = type == 'strong' ? `<strong>${txt}</strong>` : `<quote>${txt}</quote>`
        this.changeSelectedText($content, str)
      },
      changeSelectedText(obj, str) {
        if (window.getSelection) {
          obj.setRangeText(str);
          obj.selectionStart += str.length;
          obj.focus()
        } else if (document.selection) {
          obj.focus();
          var sel = document.selection.createRange();
          sel.text = str;
        }
        this.ruleForm.content = obj.value
      },
      articleSegChange(seg){
        let obj = { flag: seg, sort: this.articleSeg.length}
        let idx = 0
        switch(seg){
          case 'article':
            obj = {'articleTxt': '', ...obj};
            break;
          case 'image':
            obj = {'img': '', ...obj};
            break;
          case 'lamp':
            obj = {'lampImgArr': [], ...obj};
            idx = this.articleSeg.length
            break;
        }
        this.articleSeg.push(obj)
        this.popVisible = !this.popVisible
      },
      move(type, arr, idx){
        moveAction(type, arr, idx)
      },
      // 删除当前
      deleteCur(idx, type){
        this.delVisible = true
        this.idx = {
          type: type,
          idx: idx
        }
      },
      // 确定删除
      deleteRow(){
        if(this.idx.type == 'seg'){
          this.articleSeg.splice(this.idx.idx, 1);
        }else{
          this.articleSeg[2].lampImgArr.splice(this.idx.idx, 1);
        }
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

<style>
  .campaign .base-info-form{
    padding-top: 10px;
  }
  .campaign .edit-wrap{
    overflow: hidden;
  }
  .campaign .form-left{
    width: 42%;
    float: left;
  }
  .campaign .form-right{
    width: 380px;
    float: left;
  }
  .campaign .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .mask{
    width: 100%;
    height: 100%;
    position: absolute;
    left: 0; right: 0;
    top: 0; bottom: 0;
    z-index: 10;
    background-color: transparent;
  }
  .campaign .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .campaign .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .campaign .avatar {
    width: 100%;
    height: 100%;
    object-fit: contain;
  }
  .campaign .single-item{
    width: 360px;
  }
  .campaign .form-right{
    width: 54%;
    float: left;
  }
  .campaign .article-item{
    border-radius: 6px;
    border: 1px dashed #d9d9d9;
    margin-bottom: 20px;
  }
  .campaign .article-item:last-of-type{
    margin-bottom: 0;
  }
  .campaign .article-item-oper{
    padding-left: 18px;
    padding-right: 18px;
    background-color: #f9f9f9;
    overflow: hidden;
  }
  .campaign .add-segment{
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
  .campaign .user-define-item .seg-item-title{
    color: #409EFF;
  }
  .campaign .delete-define-item{
    padding-right: 9px;
    text-align: right;
  }
  .campaign .oper-btns{
    float: right;
  }
  .campaign .up-item-btn i{
    transform: rotate(90deg);
    margin-left: 20px;
  }
  .campaign .down-item-btn i{
    transform: rotate(-90deg);
  }
  .campaign .del-item-btn{
    color: #f56c6c;
  }
  .campaign .el-textarea__inner{
    border: 0;
  }
  .campaign .article-item-content .avatar-uploader{
    width: 100%;
  }
  .campaign .article-item-content .el-upload{
    width: 100%;
    display: block;
    border: 0;
  }
  .campaign .lamp-content{
    padding: 0 18px 18px;
    line-height: 1;
    font-size: 0;
  }
  .campaign .lamp-content .lamp-img-item{
    width: 150px;
    height: 150px;
    overflow: hidden;
    margin-right: 18px;
    margin-top: 18px;
    display: inline-block;
    border: 1px solid #c0ccda;
    border-radius: 6px;
    vertical-align: top;
    position: relative;
  }
  .campaign .lamp-content .lamp-img-item img{
    width: 100%;
    height: 100%;
    border-radius: 6px;
    object-fit: cover;
    cursor: pointer;
  }
  .campaign .lamp-content .lamp-img-item .lamp-img-item-oper{
    opacity: 0;
    padding-top: 7px;
    position: absolute;
    left: 0; right: 0;
    top: 0; bottom: 0;
    background-color: rgba(0,0,0,.7);
    transition: opacity .3s ease-in-out; 
    z-index: 10;
  }
  .campaign .lamp-content .lamp-img-item .lamp-img-item-oper:hover{
    opacity: 1;
    transition: opacity .3s ease-in-out; 
  }
  .campaign .lamp-content .lamp-img-item .lamp-img-item-oper>*{
    display: block;
    margin: 10px auto;
  }
  .campaign .lamp-content .lamp-img-item .lamp-img-item-oper>*:nth-of-type(2) i{
    transform: rotate(180deg);
  }
  .campaign .lamp-content .avatar-uploader{
    margin-top: 18px;
    display: inline-block;
    width: 150px;
    height: 150px;
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
  }
  .campaign .lamp-content .avatar-uploader .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 150px;
    height: 150px;
    line-height: 150px;
    text-align: center;
  }
  .campaign .lamp-content .el-upload{
    width: 150px;
    height: 150px;
    display: block;
    border: 0;
  }
  .campaign .add-paragraph{
    margin-top: 18px;
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
  .campaign .mainContent{
    display: block;
    width: 100%;
    border: none;
    resize: none;
    box-sizing: border-box;
    padding: 15px 18px;
    outline: none;
  }
  .campaign .colorWhite{
    color: #fff;
  }
</style>