<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item><i class="el-icon-lx-location"/>用户管理</el-breadcrumb-item>
                <el-breadcrumb-item>角色信息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="select.roleCode" placeholder="角色编码" class="handle-input mr10"/>
                <el-input v-model="select.roleName" placeholder="角色名称" class="handle-input mr10"/>
                <el-button type="primary" icon="el-icon-search" @click="getRoleData">搜索</el-button>
                <!--<el-button type="primary" icon="delete" class="handle-del mr10" @click="delAll">批量删除</el-button>-->
                <div class="handle-box-right">
                    <el-button type="success" icon="el-icon-plus" @click="handle('roleAdd')">添加</el-button>
                </div>
            </div>
            <el-table :data="data" border class="table" ref="multipleTable" @selection-change="handleSelectionChange">
                <!--<el-table-column type="selection" width="55" align="center"/>-->
                <el-table-column prop="roleCode" label="角色编码"/>
                <el-table-column prop="roleName" label="角色名称"/>
                <el-table-column prop="status" label="状态" :formatter="formatter"/>
                <el-table-column prop="remark" label="备注"/>
                <el-table-column prop="createTime" label="创建时间" width="160"/>
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handle('roleEdit',scope.row,scope.$index)">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" @click="handleDelete(scope.$index)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <!--分页-->
            <div class="pagination">
                <el-pagination background @current-change="handleCurrentChange" @size-change="handleSizeChange"
                               :page-sizes="[5, 10, 50, 100]" :page-size="size" layout="total,sizes,prev,pager,next,jumper" :total=total>
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="35%">
            <el-form ref="roleForm" :rules="rules" :model="roleForm" label-width="100px">
                <el-form-item label="角色编码" prop="roleCode">
                    <el-input v-model="roleForm.roleCode" placeholder="角色编码"/>
                </el-form-item>
                <el-form-item label="角色名称" prop="roleName">
                    <el-input v-model="roleForm.roleName" placeholder="角色名称"/>
                </el-form-item>
                <el-form-item label="状态" prop="status">
                    <el-select v-model="roleForm.status" placeholder="状态" style="width: 100%">
                        <el-option key="0" label="正常" value="0"></el-option>
                        <el-option key="1" label="禁用" value="1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-input v-model="roleForm.remark" placeholder="备注"/>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="saveOperation(operationType,'roleForm')">确 定</el-button>
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
    import api from '@/components/common/api'
    export default {
        name: "RoleList",
        data() {
            return {
                roleData: [],
                page: 1,
                size: 10,
                multipleSelection: [],
                select:{
                    roleCode: '',
                    roleName: '',
                },
                del_list: [],
                editVisible: false,
                delVisible: false,
                roleForm: {
                    roleId:'',
                    roleCode: '',
                    roleName: '',
                    remark: '',
                    status: ''
                },
                rules:{
                    roleCode:[
                        {required: true, message: '请输入角色编码', trigger: 'blur'},
                        {min: 1, max: 64, message: '长度在1到64个字符', trigger: 'blur'}
                    ],
                    roleName:[
                        {required: true, message: '请输入角色名称', trigger: 'blur'},
                        {min: 1, max: 64, message: '长度在1到64个字符', trigger: 'blur'}
                    ],
                    remark:[
                        {required: false, message: '请输入备注信息', trigger: 'blur'},
                        {min: 1, max: 32, message: '长度在1到32个字符', trigger: 'blur'}
                    ],
                    status:[
                        {required: true, message: '请选择角色状态 0：正常  1：禁用', trigger: 'blur'}
                    ]
                },
                idx: -1,
                total: 0,
                roleIdArr: [],
                operationType:''
            }
        },
        computed: {
            data() {
                return this.roleData.filter(role => {
                    let is_del = false;
                    for (let i = 0; i < this.del_list.length; i++) {
                        if (role.roleId === this.del_list[i].roleId) {
                            is_del = true;
                            break;
                        }
                    }
                   if (!is_del) {
                        if (role.roleCode.indexOf(this.select.roleCode) > -1 &&
                            role.roleName.indexOf(this.select.roleName) > -1) {
                            return role;
                        }
                    }
                })
            }
        },
        created() {
            this.getRoleData();
        },
        methods: {
            handleSizeChange(val){
                this.size = val;
                this.getRoleData();
            },
            // 分页导航
            handleCurrentChange(val) {
                this.page = val;
                this.getRoleData();
            },
            //获取角色分页列表数据
            getRoleData() {
                api.listRolePage({
                    page: this.page,
                    size: this.size,
                    roleName: this.select.roleName,
                    roleCode: this.select.roleCode
                }).then((res) => {
                    this.total = parseInt(res.data.data.total);
                    this.page = res.data.data.pageNum;
                    this.roleData = res.data.data.list;
                })
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            formatter(row) {
                return row.status == 0 ? "正常":"禁用";
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            handle(type,row,index) {
                this.operationType = type;
                switch (type) {
                    case "roleAdd":
                        this.roleForm = {
                            roleId:'',
                            roleCode: '',
                            roleName: '',
                            status: '',
                            remark: ''
                        };
                        this.editVisible = true;
                        break;
                    case "roleEdit":
                        this.idx = index;
                        this.roleForm = {
                            roleId: row.roleId,
                            roleCode: row.roleCode,
                            roleName: row.roleName,
                            status: row.status,
                            remark: row.remark
                        };
                        this.editVisible = true;
                        break;
                }
            },
            // 保存编辑
            saveOperation(operationType,formName) {
                this.$message(operationType);
                if(operationType == 'roleAdd') {
                    this.$refs[formName].validate((valid) => {
                        if (valid) {
                            api.addRole(this.roleForm).then((res) => {
                                if (res.data.code == '00') {
                                    this.$message.success('添加成功');
                                    this.getRoleData();
                                    this.editVisible = false;
                                }else {
                                    this.$message.error('添加失败');
                                }
                            });
                        }else {
                            this.$message.error("验证失败！");
                            return false;
                        }
                    });
                }
                if(operationType == 'roleEdit') {
                    this.$refs[formName].validate((valid) => {
                        if (valid) {
                            this.$set(this.roleData, this.idx, Object.assign({}, this.roleData[this.idx], this.roleForm));
                            api.updateRole(this.roleForm).then((res) => {
                                if (res.data.code == '00') {
                                    this.$message.success('编辑成功');
                                    this.editVisible = false;
                                }else {
                                    this.$message.error('编辑失败');
                                }
                            });
                        }else {
                            this.$message.error("验证失败！");
                            return false;
                        }
                    });
                }
            },

            handleDelete(index) {
                this.idx = index;
                this.delVisible = true;
            },
            delAll() {
                const length = this.multipleSelection.length;
                let str = '';
                this.del_list = this.del_list.concat(this.multipleSelection);
                for (let i = 0; i < length; i++) {
                    str += this.multipleSelection[i].roleName + ' ';
                    this.roleIdArr.push(this.multipleSelection[i].roleId);
                }
                api.deleteRole({
                    roleIdArr: this.roleIdArr
                }).then((res) => {
                    if(res.data.code.equals('00')){
                        this.$message.error('删除了' + str);
                        this.multipleSelection = [];
                    }
                });
            },
            // 确定删除
            deleteRow(){
                const roleId = this.roleData[this.idx].roleId;
                this.roleData.splice(this.idx, 1);
                this.$util.delete('/role/deleteRole/'+roleId).then((res) => {
                    if (res.data.code == '00') {
                        this.$message.success('删除成功');
                    }else {
                        this.$message.success('删除失败');
                    }
                });
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
    .table{
        width: 100%;
        font-size: 14px;
    }
    .red{
        color: #ff0000;
    }
</style>

