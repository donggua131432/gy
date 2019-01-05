<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/' }"><i class="el-icon-lx-location"/>系统管理</el-breadcrumb-item>
                <el-breadcrumb-item>数据字典管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="select.dictCode" placeholder="字典编码" class="handle-input mr10"/>
                <el-input v-model="select.dictName" placeholder="字典名称" class="handle-input mr10"/>
                <el-button type="primary" icon="el-icon-search" @click="getDictData">搜索</el-button>
            </div>
            <div style="overflow: auto">
                <div style="width: 49%; float: left">
                    <div class="handle-box" style="padding-bottom: 20px;">
                        <span style="float: left; font-size: 16px; font-weight: bolder">数据字典</span>
                        <div style="float: right;">
                            <el-button type="success" icon="el-icon-plus" @click="handle('dictAdd')">添加</el-button>
                        </div>
                    </div>
                    <el-table ref="dictTable" :data="data" highlight-current-row @current-change="handleCurrentTable" border class="table">
                        <el-table-column prop="dictName" label="字典名称"/>
                        <el-table-column prop="dictCode" label="字典编码"/>
                        <!--<el-table-column prop="createTime" label="添加时间"/>-->
                        <el-table-column label="操作" width="150" align="center">
                            <template slot-scope="scope">
                                <!--<el-button type="text" icon="el-icon-edit" @click="handle('dictEdit',scope.row)">编辑</el-button>-->
                                <el-button type="text" icon="el-icon-delete" class="red" @click.stop="handleDelete('dictDel',scope.$index)">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <!--分页-->
                    <el-pagination background @current-change="handleCurrentChange" @size-change="handleSizeChange"
                                   :page-sizes="[10, 20, 50, 100]" :page-size="10" layout="total, sizes,prev, pager, next, jumper" :total=total>
                    </el-pagination>
                </div>
                <div style="width: 49%; margin-left:2%; float: left">
                    <div class="handle-box" style="padding-bottom: 20px;">
                        <span style="float: left; font-size: 16px; font-weight: bolder">数据字典值</span>
                        <div style="float: right;">
                            <el-button type="success" icon="el-icon-plus" @click="handle('itemAdd')">添加</el-button>
                        </div>
                    </div>
                    <el-table :data="dictItemData" border class="table">
                        <el-table-column prop="itemName" label="字典值名称"/>
                        <el-table-column prop="itemValue" label="字典值"/>
                        <el-table-column prop="itemSort" label="字典值排序" sortable/>
                     <!--   <el-table-column prop="createTime" label="添加时间"/>-->
                        <el-table-column label="操作" width="150" align="center">
                            <template slot-scope="scope">
                                <el-button type="text" icon="el-icon-edit" @click.stop="handle('itemEdit',scope.row,scope.$index)">编辑</el-button>
                                <el-button type="text" icon="el-icon-delete" class="red" @click.stop="handleDelete('itemDel',scope.$index)">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </div>
        </div>

        <!-- 字典添加/编辑弹出框 -->
        <el-dialog title="数据字典" :visible.sync="editVisible" width="35%">
            <el-form ref="dictForm" :rules="rules" :model="dictForm" label-width="100px" style="padding-right:20px" label-suffix="：">
                <el-form-item label="字典名称" prop="dictName">
                    <el-input v-model="dictForm.dictName" placehoder="字典名称"/>
                </el-form-item>
                <el-form-item label="字典编码" prop="dictCode">
                    <el-input v-model="dictForm.dictCode" placeholder="字典编码"/>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="saveOperation(operationType,'dictForm')">确 定</el-button>
                    <el-button @click="editVisible = false">取 消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <!-- 字典值添加/编辑弹出框 -->
        <el-dialog title="数据字典值" :visible.sync="editItemVisible" width="35%">
            <el-form ref="dictItemForm" :rules="rules" :model="dictItemForm" label-width="110px" style="padding-right:20px" label-suffix="：">
                <el-form-item label="字典值名称" prop="itemName">
                    <el-input v-model="dictItemForm.itemName" placeholder="字典值名称"/>
                </el-form-item>
                <el-form-item label="字典值" prop="itemValue">
                    <el-input v-model="dictItemForm.itemValue" placeholder="字典值"/>
                </el-form-item>
                <el-form-item label="字典值排序" prop="itemSort">
                    <el-input v-model.number="dictItemForm.itemSort" type="number" placeholder="字典值排序"/>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="saveOperation(operationType,'dictItemForm')">确 定</el-button>
                    <el-button @click="editItemVisible = false">取 消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <!-- 删除提示框 -->
        <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？此删除将删除此数据字典及其关联的数据字典值</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteRow(operationType)">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                dictData: [],
                dictItemData:[],
                page:1,
                size:10,
                multipleSelection: [],
                select: {
                    dictCode:'',
                    dictName:''
                },
                del_list: [],
                editVisible: false,
                editItemVisible:false,
                delVisible:false,
                dictForm: {
                    dictId:'',
                    dictCode: '',
                    dictName: '',
                    createTime:''
                },
                dictItemForm: {
                    itemId:'',
                    dictId:'',
                    itemName: '',
                    itemValue: '',
                    itemSort: 0,
                    createTime:''
                },
                rules:{
                    dictCode:[
                        {required: true, message: '请输入字典编码', trigger: 'blur'},
                        {min: 1, max: 64, message: '长度在1到64个字符'}
                    ],
                    dictName:[
                        {required: true, message: '请输入字典名称', trigger: 'blur'},
                        {min: 1, max: 64, message: '长度在1到64个字符'}
                    ],
                    itemName:[
                        {required: true, message: '请输入字典值名称', trigger: 'blur'},
                        {min: 1, max: 64, message: '长度在1到64个字符'}
                    ],
                    itemValue:[
                        {required: true, message: '请输入字典值', trigger: 'blur'},
                        {min: 1, max: 16, message: '长度在1到2个字符'}
                    ],
                    itemSort:[
                        {required : true, message: '请输入字典值排序', trigger: 'blur'},
                        {type: 'number', message: '必须为数字值'}
                    ]
                },
                idx: -1,
                total: 0,
                dictIdArr:[],
                operationType:'',
                currentRow:null
            }
        },
        created(){
          this.getDictData();

            /*bus.$emit("name","fanglin");*/
        },
        computed:{
            data() {
                return this.dictData.filter((dict) => {
                    let is_del = false;
                    for (let i = 0; i < this.del_list.length; i++) {
                        if (dict.dictId === this.del_list[i].dictId) {
                            is_del = true;
                            break;
                        }
                    }
                    if (!is_del) {
                        if (dict.dictName.indexOf(this.select.dictName) > -1 &&
                            dict.dictCode.indexOf(this.select.dictCode) > -1) {
                            return dict;
                        }
                    }
                })
            }
        },
        methods: {
            getDictData() {
                this.$util.post('/dict/listAllDictPage', {
                    page: this.page,
                    size: this.size,
                    dictCode: this.select.dictCode,
                    dictName: this.select.dictName
                }).then((res) => {
                    if (res.data.code == '00') {
                        this.total = parseInt(res.data.data.total);
                        this.page = res.data.data.pageNum;
                        this.dictData = res.data.data.list;
                        this.$refs.dictTable.setCurrentRow(this.dictData[0]);
                    }
                });
            },
            handleCurrentTable(row) {
                if (row != null) {
                    this.currentRow = row;
                    this.dictItemForm.dictId = row.dictId;
                    //this.$message("我被选中去查询字典值信息！");
                    this.$util.get('/dictItem/getDictItem/' + row.dictId, {}).then((res) => {
                        if (res.data.code == '00') {
                            this.dictItemData = res.data.data;
                        }
                    });
                }
            },
            handleSizeChange(val) {
                this.size = val;
                this.getDictData();
            },
            // 分页导航
            handleCurrentChange(val) {
                this.page = val;
                this.getDictData();
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            handle(type, row,index) {
                this.operationType = type;
                switch (type) {
                    case "dictAdd":
                        this.dictForm.dictId = '';
                        this.dictForm.dictCode = '';
                        this.dictForm.dictName = '';
                        this.dictForm.createTime = '';
                        this.editVisible = true;
                        break;
                    case "dictEdit":
                        this.dictForm = {
                            dictId: row.dictId,
                            dictCode: row.dictCode,
                            dictName: row.dictName,
                            createTime: row.createTime
                        };
                        this.editVisible = true;
                        break;
                    case "itemAdd":
                        this.dictItemForm.itemName = '';
                        this.dictItemForm.itemValue = '';
                        this.dictItemForm.itemSort = 0;
                        this.editItemVisible = true;
                        break;
                    case "itemEdit":
                        this.idx = index;
                        this.dictItemForm = {
                            itemId: row.itemId,
                            dictId: row.dictId,
                            itemName: row.itemName,
                            itemValue: row.itemValue,
                            itemSort: row.itemSort
                        };
                        this.editItemVisible = true;
                        break;
                }
            },
            // 保存编辑
            saveOperation(operationType, formName) {
                if (operationType == 'dictAdd') {
                    this.$refs[formName].validate((valid) => {
                        if (valid) {
                             this.$set(this.dictData, this.idx, this.dictForm);
                             this.$util.post('/dict/addDict', this.dictForm).then((res) => {
                                 if (res.data.code == '00') {
                                     this.$message.success('添加成功');
                                     this.getDictData();
                                     this.editVisible = false;
                                 } else {
                                     this.$message.error('添加失败');
                                 }
                             });
                        } else {
                            this.$message.error("验证失败！");
                            return false;
                        }
                    });
                }

                if (operationType == 'dictEdit') {
                    this.$refs[formName].validate((valid) => {
                        if (valid) {
                            this.$set(this.dictData, this.idx, Object.assign({}, this.dictData[this.idx], this.dictForm));
                            this.$util.put('/dict/updateDict', this.dictForm).then((res) => {
                                if (res.data.code == '00') {
                                    this.$message.success('编辑成功');
                                    this.editVisible = false;
                                } else {
                                    this.$message.error('编辑失败');
                                }
                            });
                        } else {
                            this.$message.error("验证失败！");
                            return false;
                        }
                    });
                }

                if (operationType == 'itemAdd') {
                    this.$refs[formName].validate((valid) => {
                        if (valid) {
                            this.$util.post('/dictItem/addDictItem', this.dictItemForm).then((res) => {
                                if (res.data.code == '00') {
                                    this.handleCurrentTable(this.currentRow);
                                    this.$message.success('添加成功');
                                    this.editItemVisible = false;
                                } else {
                                    this.$message.error('添加失败');
                                }
                            });
                        } else {
                            this.$message.error("验证失败！");
                            return false;
                        }
                    });
                }

                if (operationType == 'itemEdit') {
                    this.$refs[formName].validate((valid) => {
                        if (valid) {
                            this.$set(this.dictItemData, this.idx, Object.assign({}, this.dictItemData[this.idx], this.dictItemForm));
                            this.$util.put('/dictItem/updateDictItem', this.dictItemForm).then((res) => {
                                if (res.data.code == '00') {
                                    this.handleCurrentTable(this.currentRow);
                                    this.$message.success('编辑成功');
                                    this.editItemVisible = false;
                                } else {
                                    this.$message.error('编辑失败');
                                }
                            });
                        } else {
                            this.$message.error("验证失败！");
                            return false;
                        }
                    });
                }
            },

            handleDelete(type, index) {
                this.idx = index;
                this.operationType = type;
                switch (type) {
                    case "dictDel":
                        this.delVisible = true;
                        break;
                    case "itemDel":
                        this.delVisible = true;
                        break;
                }
            },
            // 确定删除
            deleteRow(operationType) {
                if (operationType == 'dictDel') {
                    const delDictId = this.dictData[this.idx].dictId;
                    this.dictData.splice(this.idx, 1);
                    this.$util.delete('/dict/deleteDict/' + delDictId).then((res) => {
                        if (res.data.code == '00') {
                            this.dictItemData = [];
                            this.$message.success('数据字典删除成功');
                        } else {
                            this.$message.error('数据字典删除失败');
                        }
                    });
                } else {
                    const itemId = this.dictItemData[this.idx].itemId;
                    this.dictItemData.splice(this.idx, 1);
                    this.$util.delete('/dictItem/deleteDictItem/' + itemId).then((res) => {
                        if (res.data.code == '00') {
                            this.$message.success('删除成功');
                        } else {
                            this.$message.error('删除失败');
                        }
                    });
                }
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
