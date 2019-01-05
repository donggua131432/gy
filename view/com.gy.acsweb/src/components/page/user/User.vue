<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/' }"><i class="el-icon-lx-location"/>用户管理</el-breadcrumb-item>
                <el-breadcrumb-item>用户管理表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="select.userName" placeholder="请输入用户名" class="handle-input mr10"/>
                <el-select v-model="select.deptId" placeholder="请输入用户所在部门" class="handle-input mr10">
                    <el-option key="0" label="--请选择--" value=""/>
                    <el-option key="1" label="技术部" value="1"/>
                    <el-option key="2" label="设计部" value="2"/>
                    <el-option key="3" label="财务部" value="3"/>
                </el-select>
                <el-button type="primary" icon="el-icon-search" @click="getUserData">搜索</el-button>
                <div class="handle-box-right">
                    <el-button type="success" icon="el-icon-plus" @click="handle('userAdd')">添加</el-button>
                </div>
            </div>
            <el-table :data="data" border class="table" ref="userTable" @selectionChange="handleSelectionChange" >
                <!--<el-table-column type="selection" width="55" align="center"/>-->
                <el-table-column prop="userName" label="用户名"/>
                <el-table-column prop="email" label="邮箱"/>
                <el-table-column prop="mobile" label="手机"/>
                <el-table-column prop="status" label="状态" :formatter="formatter" v-bind:width="100"/>
                <el-table-column label="操作" width="180" align="center" v-bind:width="300">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" @click="reSetVisiblePrompt(scope.$index)">重置密码</el-button>
                        <el-button type="text" icon="el-icon-edit" @click="handle('userEdit',scope.row,scope.$index)">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" @click="handleDelete(scope.$index)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <!--分页-->
            <div class="pagination">
                <el-pagination background @current-change="handleCurrentChange" @size-change="handleSizeChange"
                               :page-sizes="[10,20,50,100]" :page-size="10" layout="total,sizes,prev,pager,next,jumper" :total="total">
                </el-pagination>
            </div>
        </div>
        <!--编辑和添加弹出框-->
        <el-dialog title="编辑" :visible.sync="editVisible" width="35%">
            <el-form ref="userForm" :rules="rules" :model="userForm" label-width="100px" style="padding-right:20px" label-suffix=": ">
                <el-form-item label="用户名" prop="userName">
                    <el-input v-model="userForm.userName" />
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="userForm.email" />
                </el-form-item>
                <el-form-item label="手机" prop="mobile">
                    <el-input v-model.number="userForm.mobile" />
                </el-form-item>
                <el-form-item label="状态" prop="status" >
                    <el-select v-model="userForm.status" placeholder="状态" style="width: 100%">
                        <el-option key="0" label="正常" value="0"></el-option>
                        <el-option key="1" label="禁用" value="1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="saveOperation(operationType,'userForm')">确 定</el-button>
                    <el-button @click="editVisible = false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <!--删除提示框-->
        <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确认删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="deleteRow">确定</el-button>
                <el-button @click="delVisible = false">取消</el-button>
            </span>
        </el-dialog>
        <!--重置密码提示框-->
        <el-dialog title="提示" :visible.sync="reSetVisible" width="300px" center>
            <div class="del-dialog-cnt">是否确定重置此用户密码？</div>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="reSetPassword">确定</el-button>
                <el-button @click="reSetVisible = false">取消</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import api from '@/components/common/api'
    export default {
        name: "User",
        data(){
            return {
                userData:[],
                page: 1,
                size: 10,
                total: 0,
                select:{
                    userName:'',
                    deptId:''
                },
                multipleSelection:[],
                userForm:{
                    userId:'',
                    userName:'',
                    password:'',
                    salt:'',
                    email:'',
                    mobile:'',
                    status:'',
                    deptId:'',
                    token:'',
                    createTime:'',
                    updateUserId:'',
                    updateUserName:'',
                    updateTime:''
                },
                editVisible:false,
                delVisible:false,
                reSetVisible:false,
                rules:{
                    userName:[
                        { required: true, message: '请输入用户名', trigger: 'blur' },
                        { min: 1, max: 64, message: '长度在 1 到 64 个字符', trigger: 'blur' }
                    ],
                    email:[
                        { required: true, message: '请输入邮箱地址', trigger: 'blur' },
                        { pattern:/^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/, message: '邮箱格式不正确', trigger: 'blur' }
                    ],
                    mobile: [
                        { type:"number", required: true,message:'请输入手机号', trigger: 'blur'},
                        { pattern:/^0{0,1}(13[0-9]|15[7-9]153|156|18[7-9])[0-9]{8}$/, message: '手机号格式不正确', trigger:'blur'}
                    ],
                    depetId:[
                        { required: true, message: '请输入部门ID', trigger: 'blur' },
                        { min: 1, max: 32, message: '长度在 1 到 32 个字符', trigger: 'blur' }
                    ],
                    updateUserId:[
                        { required: true, message: '请输入操作人ID', trigger: 'blur' },
                        { min: 1, max: 32, message: '长度在 1 到 32 个字符', trigger: 'blur' }
                    ],
                    updateUserName:[
                        { required: true, message: '请输入操作人姓名', trigger: 'blur' },
                        { min: 1, max: 64, message: '长度在 1 到 64 个字符', trigger: 'blur' }
                    ]
                },
                del_list: [],
                operationType:'',
                idx:-1
            }
        },
        computed:{
            data() {
                return this.userData.filter((user) => {
                    let is_del = false;
                    for (let i = 0; i < this.del_list.length; i++) {
                        if (user.userId === this.del_list[i].userId) {
                            is_del = true;
                            break;
                        }
                    }
                    if (!is_del) {
                        if (user.userName.indexOf(this.select.userName) > -1 &&
                            user.deptId.indexOf(this.select.deptId) > -1) {
                            return user;
                        }
                    }
                })
            }
        },
        created() {
            this.getUserData();
        },
        methods: {
            getUserData(){
                api.getUserList({
                    page:this.page,
                    size:this.size,
                    userName:this.select.userName,
                    deptId:this.select.deptId
                }).then((res) => {
                    console.log((res));
                    if (res.data.code == '00') {
                        this.total = parseInt(res.data.data.total);
                        this.page = res.data.data.pageNum;
                        this.userData = res.data.data.list;
                        this.$refs.userTable.setCurrentRow(this.userData[0]);
                    }
                })
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            handleSizeChange(val) {
                this.size = val;
                this.getUserData();
            },
            handleCurrentChange(val) {
                this.page = val;
                this.getUserData();
            },
            formatter(row) {
                return row.status == 0 ? "正常":"禁用";
            },
            handle(type,row,index) {
                this.operationType = type;
                switch(type) {
                    case "userAdd":
                        this.userForm = {
                            userId:'',
                            userName:'',
                            password:'',
                            salt:'',
                            email:'',
                            mobile:'',
                            status:'',
                            deptId:'',
                            token:'',
                            createTime:'',
                            updateUserId:'',
                            updateUserName:'',
                            updateTime:''
                        };
                        this.editVisible = true;
                        break;
                    case "userEdit":
                        this.idx = index;
                        this.userForm = {
                            userId:row.userId,
                            userName:row.userName,
                            password:row.password,
                            email:row.email,
                            mobile:row.mobile,
                            status:row.status,
                            deptId:row.deptId,
                            token:row.token,
                            createTime:row.createTime,
                            updateUserId:row.updateUserId,
                            updateUserName:row.updateUserName,
                            updateTime:row.updateTime
                        };
                        this.editVisible = true;
                        break;
                }
            },
            saveOperation(operationType,formName) {
                this.$message(operationType);
                if (operationType == 'userAdd') {
                    this.$refs[formName].validate((valid) =>{
                        if (valid) {
                            api.addUser(this.userForm).then((res) =>{
                                if (res.data.code == '00') {
                                    this.$message.success("添加成功");
                                    this.getUserData();
                                    this.editVisible = false;
                                }else {
                                    this.$message.error("添加失败");
                                }
                            });
                        }else {
                            this.$message.error("验证失败");
                            return false;
                        }
                    });
                }
                if (operationType == 'userEdit') {
                    this.$refs[formName].validate((valid) =>{
                        if (valid) {
                            this.$set(this.userData, this.idx, Object.assign({},this.userData[this.ids], this.userForm));
                            api.updateUser(this.userForm).then((res) => {
                                if (res.data.code == '00') {
                                    this.$message.success("编辑成功");
                                    this.editVisible = false;
                                }else {
                                    this.$message.error("编辑失败");
                                }
                            });
                        }else {
                            this.$message.error("验证失败");
                            return false;
                        }
                    });
                }
            },
            handleDelete(index) {
                this.idx = index;
                this.delVisible = true;
            },
            deleteRow() {
                const userId = this.userData[this.idx].userId;
                this.userData.splice(this.idx,1);
                this.$util.delete("/user/deleteUser/"+userId).then((res) =>{
                    if (res.data.code == '00') {
                        this.$message.success("删除成功");
                    }else {
                        this.$message.error("删除失败");
                    }
                });
                this.delVisible = false;
            },
            reSetVisiblePrompt(index) {
                this.idx = index;
                this.reSetVisible = true;
            },
            reSetPassword() {
                const userId = this.userData[this.idx].userId;
                this.$util.put("/user/reSetPassword/"+userId).then((res) => {
                    if (res.data.code == '00') {
                        this.$message.success("重置成功");
                        this.reSetVisible = false;
                    }else {
                        this.$message.error("重置失败");
                    }
                });
                this.reSetVisible = false;
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

    .handle-select {
        width: 120px;
    }

    .handle-input {
        width: 300px;
        display: inline-block;
    }
    .del-dialog-cnt{
        font-size: 16px;
        text-align: center
    }
    .table{
        width: 100%;
        font-size: 14px;
    }
    .red{
        color: #ff0000;
    }
</style>
