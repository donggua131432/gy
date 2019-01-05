<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/' }"><i class="el-icon-lx-location"/>系统管理</el-breadcrumb-item>
                <el-breadcrumb-item>菜单管理表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <!--<div class="handle-box">
                <el-input v-model="select.menuName" placeholder="菜单名称" class="handle-input mr10"/>
                <el-select v-model="select.type" placeholder="资源类型" class="handle-input mr10">
                    <el-option key="0" label="--请选择--" value=""/>
                    <el-option key="1" label="菜单" value="1"/>
                    <el-option key="2" label="资源" value="2"/>
                    <el-option key="3" label="资源分类" value="3"/>
                </el-select>
                <el-button type="primary" icon="el-icon-search" @click="getMenuData">搜索</el-button>
            </div>-->
            <div class="handle-box-right">
                <el-button type="success" icon="el-icon-plus" @click="handle('menuAdd')">添加</el-button>
            </div>
            <el-table :data="menuData" border class="table" :row-style="showTr" ref="menuTable">
                <el-table-column v-for="(column, index) in columns" :key="column.dataIndex" :label="column.text" :width="column.width">
                    <template slot-scope="scope">
                        <span v-if="spaceIconShow(index)" v-for="(space, levelIndex) in scope.row._level" class="ms-tree-space"></span>
                        <span v-if="toggleIconShow(index,scope.row)" @click="toggle(scope.$index)">
                            <i v-if="!scope.row._expanded" class="el-icon-caret-right" aria-hidden="true"></i>
                            <i v-if="scope.row._expanded" class="el-icon-caret-bottom" aria-hidden="true"></i>
                        </span>
                        <span v-else-if="index===0" class="ms-tree-space"></span>
                        <span v-if="column.dataIndex == 'type'">{{formatterType(scope.row)}}</span>
                        <span v-else-if="column.dataIndex == 'status'">{{formatter(scope.row)}}</span>
                        <span v-else>{{scope.row[column.dataIndex]}}</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handle('menuEdit',scope.row,scope.$index)">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" @click="handleDelete(scope.$index)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="35%">
            <el-form ref="menuForm" :rules="rules" :model="menuForm" label-width="100px" style="padding-right:20px" label-suffix="：">
                <el-form-item label="菜单名称" prop="menuName">
                    <el-input v-model="menuForm.menuName" placeholder="菜单名称"/>
                </el-form-item>
                <el-form-item label="资源编码" prop="menuCode">
                    <el-input v-model="menuForm.menuCode" placeholder="资源编码"></el-input>
                </el-form-item>
                <el-form-item label="父菜单">
                    <el-cascader placeholder="试试搜索：资源父菜单" :options="options" :props="props"
                                 v-model="defaultParent" @change="handleParentIdChange" filterable change-on-select/>
                </el-form-item>
                <el-form-item label="url" prop="url">
                    <el-input v-model="menuForm.url" placeholder="url"/>
                </el-form-item>
                <el-form-item label="资源类型" prop="type">
                    <el-select v-model="menuForm.type" filterable placeholder="请选择">
                        <el-option v-for="item in menuTypes"
                            :key="item.itemValue"
                            :label="item.itemName"
                            :value="item.itemValue">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="请求方法" prop="method">
                    <el-input v-model="menuForm.method" placeholder="请输入请求方法GET、POST、PUT、DELETE、PATCH"></el-input>
                </el-form-item>
                <el-form-item label="资源图标" prop="menuIco">
                    <el-input v-model="menuForm.menuIco" placeholder="资源图标"/>
                </el-form-item>
                <el-form-item label="资源状态" prop="status">
                    <el-select v-model="menuForm.status" filterable placeholder="请选择">
                        <el-option v-for="item in status"
                                   :key="item.itemValue"
                                   :label="item.itemName"
                                   :value="item.itemValue">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="排序" prop="sort">
                    <el-input v-model.number="menuForm.sort" type="number" placeholder="排序"/>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="saveOperation(operationType,'menuForm')">确 定</el-button>
                    <el-button @click="editVisible = false">取 消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <!-- 删除提示框 -->
        <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="deleteRow">确 定</el-button>
                <el-button @click="delVisible = false">取 消</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import dataTranslate from '../../common/dataTranslate'
    export default {
        name: 'MenuMgr',
        data() {
            return {
                menuData: [],
                page: 1,
                size: 10,
                multipleSelection: [],
                del_list: [],
                editVisible: false,
                delVisible:false,
                menuForm: {
                    menuId:'',
                    menuCode: '',
                    menuName: '',
                    parentId: '',
                    url: '',
                    type: '',
                    method: '',
                    menuIco: '',
                    status: '',
                    sort: '',
                    createTime:''
                },
                rules:{
                    menuCode:[
                        {required: true, message: '请输入资源编码', trigger: 'blur'},
                        {min: 1, max: 32, message: '长度在1到32个字符'}
                    ],
                    menuName:[
                        {required: true, message: '请输入资源名称', trigger: 'blur'},
                        {min: 1, max: 64, message: '长度在1到64个字符'}
                    ],
                    parentId:[
                        {required: true, message: '请选择父菜单', trigger: 'blur'}
                    ],
                    url:[
                        {min: 1, max: 128, message: '长度在1到128个字符'}
                    ],
                    type:[
                        {required: true, message: '请选择菜单类型', trigger: 'change'}
                    ],
                    method:[
                        {min: 1, max: 10, message: '长度在1到10个字符'}
                    ],
                    menuIco:[
                        {message: '请选择菜单图标', trigger: 'change'},
                        {min: 1, max: 32, message: '长度在1到32个字符'}
                    ],
                    status:[
                        {required: true, message: '请选择资源状态', trigger: 'change'},
                        {required: true, message: '请选择访问方式 GET、POST、PUT、DELETE、PATCH', trigger: 'blur'}
                    ],
                    menuIco:[
                        {required: false, message: '请输入菜单图标', trigger: 'blur'},
                        {min: 1, max: 32, message: '长度在1到32个字符', trigger: 'blur'}
                    ],
                    status:[
                        {required: true, message: '请输入资源状态 0:正常 1:禁用', trigger: 'blur'},
                    ],
                    sort:[
                        {required: true, message: '不能为空', trigger: 'blur'},
                        {type: 'number', message: '必须为数字值'}
                    ]
                },
                idx: -1,
                total: 0,
                menuIdArr:[],
                operationType:'',
                options:[],
                defaultParent:["0"],
                props: {
                    label : "menuName",
                    value : "menuId",
                    children : "children"
                },
                menuTypes: [],
                status:[],
                columns: [
                    {text: '菜单名称',  dataIndex: 'menuName'},
                    {text: '资源编码',  dataIndex: 'menuCode'},
                    {text: 'url地址',  dataIndex: 'url'},
                    {text: '类型',  dataIndex: 'type'},
                    {text: '访问方式',  dataIndex: 'method'},
                    {text: '菜单图标',  dataIndex: 'menuIco', width:'150'},
                    {text: '状态',  dataIndex: 'status', width:'100'},
                    {text: '排序',  dataIndex: 'sort', width:'100'},
                    {text: '创建时间',  dataIndex: 'createTime', width:'160'}
                ]
            }
        },
        created() {
            this.getMenuTree();
            //设置数据字典的值
            this.$util.getDictItems("menu_type").then((res) => {
                this.menuTypes = res;
            });

            this.$util.getDictItems("status").then((res) => {
                this.status = res;
            });
        },
        methods: {
            getMenuTree(){
                this.$util.get('/menu/listAllMenu').then((res) => {
                    if (res.data.code == '00') {
                        this.menuData = dataTranslate.treeToArray(res.data.data, null, null, true);
                        //声明根节点
                        this.options = [];
                        const rootNode = {
                            menuName : "根节点",
                            menuId : "0",
                            children : null
                        };
                        this.options.push(rootNode);
                        this.options = this.options.concat(res.data.data);
                    }
                })
            },
            handleParentIdChange(val){
                //获取最后一级选项值得value作为父ID
                const parentId = val[val.length-1];
                this.menuForm.parentId = parentId;
            },
            formatter(row) {
                return this.$util.getDictItemName(this.status, row.status);
            },
            formatterType(row){
                return this.$util.getDictItemName(this.menuTypes, row.type);
            },
            handle(type,row,index) {
                this.operationType = type;
                switch(type) {
                    case "menuAdd":
                        this.menuForm = {
                            menuId:'',
                            menuCode: '',
                            menuName: '',
                            parentId: '0',
                            url: '',
                            type: '',
                            method: '',
                            menuIco: '',
                            status: '',
                            sort: '',
                            createTime: ''
                        };
                        this.defaultParent=["0"];
                        this.editVisible = true;
                        break;
                    case "menuEdit":
                        this.idx = index;
                        this.menuForm = {
                            menuId:row.menuId,
                            menuCode: row.menuCode,
                            menuName: row.menuName,
                            parentId: row.parentId,
                            url: row.url,
                            type: row.type,
                            method: row.method,
                            menuIco: row.menuIco,
                            status: row.status,
                            sort: row.sort*1,
                            createTime: row.createTime
                        };
                        //设置级联选择框默认值
                        this.$util.get('/menu/listMenuParentId/'+row.parentId).then((res) => {
                            const parentIds = res.data.data;
                            if(parentIds.length>0){
                                this.defaultParent=res.data.data;
                            }else{
                                this.defaultParent=[row.parentId];
                            }
                        });
                        this.editVisible = true;
                        break;
                }
            },
            // 保存编辑
            saveOperation(operationType,formName) {
                this.$message(operationType);
                if (operationType == 'menuAdd') {
                    this.$refs[formName].validate((valid) => {
                        if (valid) {
                            this.$util.post('/menu/addMenu',this.menuForm).then((res) => {
                                if (res.data.code == '00') {
                                    this.$message.success('添加成功');
                                    this.getMenuTree();
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

                if(operationType == 'menuEdit') {
                    this.$refs[formName].validate((valid) => {
                        if (valid) {
                            this.$set(this.menuData, this.idx, Object.assign({}, this.menuData[this.idx], this.menuForm));
                            this.$util.put('/menu/updateMenu',this.menuForm).then((res) => {
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
            handleDelete(index){
                this.idx = index;
                this.delVisible = true;
            },
            // 确定删除
            deleteRow(){
                const menuId = this.menuData[this.idx].menuId;
                this.menuData.splice(this.idx, 1);
                this.$util.delete('/menu/deleteMenu/'+menuId).then((res) => {
                    if (res.data.code == '00') {
                        this.$message.success('删除成功');
                    }else {
                        this.$message.success('删除失败');
                    }
                });
                this.delVisible = false;
            },

            // 显示行
            showTr: function (row) {
                const rowData = row.row;
                let show = true;
                //判断是否存在_parent属性
                if(rowData.hasOwnProperty('_parent')){
                    show = (rowData._parent._expanded && rowData._parent._show);
                }else{
                    show = true;
                }
                rowData._show = show;
                return show ? '' : 'display:none;'
            },
            // 展开下级
            toggle: function (trIndex) {
                let record = this.menuData[trIndex];
                record._expanded = !record._expanded;
            },
            // 显示层级关系的空格和图标
            spaceIconShow(index) {
                if (index == 0) {
                    return true;
                }
                return false;
            },
            // 点击展开和关闭的时候，图标的切换
            toggleIconShow(index, record) {
                if (index == 0 && record.children && record.children.length > 0) {
                    return true
                }
                return false
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

    .ms-tree-space {
        position: relative;
        top: 1px;
        display: inline-block;
        font-family: 'Glyphicons Halflings';
        font-style: normal;
        font-weight: 400;
        line-height: 1;
        width: 18px;
        height: 14px;
    }
    .ms-tree-space::before {
        content: ""
    }
    table td {
        line-height: 26px;
    }
</style>
