<template>
    <div>
        <div style="margin: 10px 0">
            <!-- 搜索框 suffix-icon内嵌图标 primary蓝色按钮 v-model绑定参数-->
            <el-input style="width: 200px" placeholder="请输入昵称" suffix-icon="el-icon-search" v-model="nickname"></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>
        </div>

        <!--        tableData存储表格数据-->
        <!--        header-cell-class-name设置固定表格样式-->
        <!--        selection-change选择时触发-->
        <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'">
            <el-table-column type="expand">
                <template slot-scope="props">
                    <el-form label-position="left" inline class="demo-table-expand">
                        <el-form-item label="年龄：">
                            <span>{{ props.row.age ? props.row.age : "/"}}</span>
                        </el-form-item>
                        <el-form-item label="性别：">
                            <span>{{ props.row.sex ? props.row.sex : "/"}}</span>
                        </el-form-item>
                        <el-form-item label="手机号：">
                            <span>{{ props.row.phone ? props.row.phone : "/"}}</span>
                        </el-form-item>
                    </el-form>
                </template>
            </el-table-column>
            <el-table-column prop="id" label="ID" width="80"></el-table-column>
            <el-table-column prop="nickname" label="昵称" :formatter="formatEmpty"></el-table-column>
            <el-table-column prop="name" label="姓名" :formatter="formatEmpty"></el-table-column>
            <el-table-column prop="phone" label="手机号" :formatter="formatEmpty"></el-table-column>
            <el-table-column prop="idcard" label="身份证号" :formatter="formatEmpty"></el-table-column>
            <el-table-column label="操作"  width="200" align="center">
                <template slot-scope="scope">
                    <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
                </template>
            </el-table-column>
        </el-table>
        <!--              底部页码等信息-->
        <!--              @size-change="handleSizeChange" 动态绑定，获取页面显示条数-->
        <!--              @current-change="handleCurrentChange" 动态绑定，获取页面所在页数-->
        <!--              :current-page="pageNum" 动态显示当前页-->
        <!--              :page-sizes="[5, 10, 15, 20]" 设置每页显示个数-->
        <!--              :page-size="10" 默认显示个数-->
        <!--              layout="total, sizes, prev, pager, next, jumper" 从左到右顺序-->
        <!--              :total="400" 总个数-->
        <div style="padding: 10px 0">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="pageNum"
                    :page-sizes="[2, 5, 10, 20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>

        <!--        编辑数据弹窗-->
        <!--        dialogFormVisible判断是否展示-->
        <!--        form存储新增数据-->
        <!--        save 执行新增-->

        <el-dialog title="信息修改" :visible.sync="dialogFormVisible" @close="resetRules">
            <el-form label-width="80px" size="small" :rules="rules" ref="dataForm" :model="dataForm" >
                <el-form-item label="用户名">
                    <el-input disabled v-model="dataForm.nickname" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="dataForm.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="年龄" prop="age">
                    <el-input v-model="dataForm.age" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="sex">
                    <el-input v-model="dataForm.sex" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="手机号" prop="phone">
                    <el-input v-model="dataForm.phone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="身份证号">
                    <el-input v-model="dataForm.idcard" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        data(){
            return{
                tableData: [],
                total: 0,
                pageNum: 1, // 当前页，默认为1
                pageSize: 5, // 每页显示数量，默认5
                nickname: '', // 搜索
                dialogFormVisible: false, // 新增弹窗，默认不展示
                multipleSelection: [],
                dataForm: {}, // 新增数据，默认为空
                rules: {
                    age: [
                        {
                            validator: (rule, value, callback) => {
                                if (value == null || value == ""){
                                    callback();
                                }else if (Math.floor(value) != value) {
                                    callback('请输入数字值');
                                } else if (!(value >= 0 && value <= 150)){
                                    callback('年龄应该在0至150之间');
                                } else {
                                    callback();
                                }
                            },
                            trigger: 'blur'
                        },
                    ],
                    sex: [
                        {
                            validator: (rule, value, callback) => {
                                if (value !== "男" && value !== "女")  {
                                    callback('请输入 “男” 或 “女”');
                                } else {
                                    callback();
                                }
                            },
                            trigger: 'blur'
                        }
                    ],
                    phone: [
                        {
                            type: 'string',
                            pattern:/^((0\d{2,3}-\d{7,8})|(1[34578]\d{9}))$/,
                            message: "手机号格式错误",
                            trigger: 'blur'},
                    ],
                    name: [
                        {
                            type: 'string',
                            min: 2,
                            max: 12,
                            message: "长度在2到12个字符",
                            trigger: 'blur'
                        }
                    ],
                }
            }
        },
        created() { // 加载时触发
            this.load();
        },
        methods:{
            // 从后台获取数据，并在页面中赋值
            async load() {
                this.companyLoc = '';
                // 获取用户
                await this.request.get("/user/page", {
                    // 对应后台参数名称，将页面默认信息传到后台，获取表中数据
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        nickname: this.nickname,
                        rid: 2,
                    }
                }).then(res => {
                    // 获取到数据后，request打包，返回前台
                    this.tableData = res.data.records;
                    this.total = res.data.total;
                });
            },
            // 点击确认后，触发
            save() {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        this.request.post("/user/post", this.dataForm).then(res => {
                            if (res.data) {
                                this.$message.success("保存成功");
                                this.dialogFormVisible = false;
                                this.load();
                            } else {
                                this.$message.error(res.msg);
                            }
                        })
                    }
                })
            },
            // 编辑信息时，弹出新增窗口
            handleEdit(row) {
                this.dataForm = Object.assign({},row); //  将row拷贝到空对象中 解决没点确定数据改变的问题
                this.dialogFormVisible = true;
            },
            // 重置搜索框
            reset() {
                this.nickname = "";
                // 重置搜索框后，重新加载表格
                this.load();
            },
            //  改变当前页码
            handleCurrentChange(pageNum) {
                this.pageNum = pageNum;
                this.load();
            },
            //  改变当前每页容量
            handleSizeChange(pageSize) {
                this.pageSize = pageSize;
                this.load();
            },
            // 格式化空内容
            formatEmpty(row, column) {
                // 获取单元格数据
                let temp = row[column.property];
                if(temp == null) {
                    return "/";
                }else{
                    return temp;
                }
            },
            // 清空报错提示
            resetRules(){
                this.$refs.dataForm.resetFields()
            },
        }
    }

</script>

<style>
    .headerBg {
        background: #eee!important;
    }

    /*行展开css*/
    .demo-table-expand {
        font-size: 0;
    }
    .demo-table-expand label {
        width: 90px;
        color: #99a9bf;
    }
    .demo-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
</style>