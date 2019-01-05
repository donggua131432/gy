<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">后台管理系统</div>
            <el-form :model="userForm" :rules="rules" ref="userForm" label-width="0px" class="ms-content">
                <el-form-item prop="username">
                    <el-input v-model="userForm.username" placeholder="username">
                        <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" placeholder="password" v-model="userForm.password" @keyup.enter.native="submitForm('userForm')">
                        <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm('userForm')">登录</el-button>
                </div>
                <p class="login-tips">Tips : 请输入正确的用户名和密码。</p>
            </el-form>
        </div>
    </div>
</template>

<script>
    import api from '@/components/common/api'
    export default {
        data: function(){
            return {
                userForm: {
                    username: 'admin',
                    password: '123123'
                },
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        //1：获取动态秘钥
                        api.getDynamicSecretKey().then((res) => {
                            var tokenKey = res.data.data.tokenKey, userKey = res.data.data.userKey;
                            //2:发起登陆获取jwtToken
                            if (res.data.code == '00') {
                                //AES前端加密
                                var userFormAES = this.$util.encrypt(this.userForm.password,tokenKey);
                                api.login({
                                    appId:this.userForm.username,
                                    timestamp:Date.parse(new Date()),
                                    password:userFormAES,
                                    userKey:userKey
                                }).then((res) => {
                                    if (res.data.code == '00') {
                                        //3:将jwtToken缓存到本地客户端
                                        const user = res.data.data;
                                        localStorage.setItem('Authorization',user.token);
                                        localStorage.setItem('AppId',user.userId);
                                        localStorage.setItem('ms_username',user.userName);
                                        localStorage.setItem('user',user);
                                        this.$router.push('/');
                                    }else {
                                        this.$message.error(res.data.msg);
                                    }
                                });
                            }else{
                                this.$message.error(res.data.msg);
                            }
                        });
                    } else {
                        this.$message.error('验证失败');
                        return false;
                    }
                });
            }
        }
    }
</script>

<style scoped>
    .login-wrap{
        position: relative;
        width:100%;
        height:100%;
        background-image: url(../../assets/login-bg.jpg);
        background-size: 100%;
    }
    .ms-title{
        width:100%;
        line-height: 50px;
        text-align: center;
        font-size:20px;
        color: #fff;
        border-bottom: 1px solid #ddd;
    }
    .ms-login{
        position: absolute;
        left:50%;
        top:50%;
        width:350px;
        margin:-190px 0 0 -175px;
        border-radius: 5px;
        background: rgba(255,255,255, 0.3);
        overflow: hidden;
    }
    .ms-content{
        padding: 30px 30px;
    }
    .login-btn{
        text-align: center;
    }
    .login-btn button{
        width:100%;
        height:36px;
        margin-bottom: 10px;
    }
    .login-tips{
        font-size:12px;
        line-height:30px;
        color:#fff;
    }
</style>
