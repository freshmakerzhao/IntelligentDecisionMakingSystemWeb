<template>
<!--    el-card卡片样式-->
    <el-card style="width: 500px;">
        <el-form label-width="80px" size="small" :rules="rules" ref="dataForm" :model="dataForm" >
            <el-form-item label="用户名">
                <el-input v-model="dataForm.username" disabled autocomplete="off"></el-input>
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
            <el-form-item label="身份证号" >
                <el-input v-model="dataForm.idcard" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="save">确 定</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<script>
    export default {
        data(){
            return{
                dataForm: {},
                user: {},
                companyLoc: '', // 单位选项
                companyList: '', // 单位数据
                rules: {
                    age: [
                        {
                            validator: (rule, value, callback) => {
                                if(value == null || value == ""){
                                    callback();
                                } else if (Math.floor(value) != value) {
                                    callback('请输入数字值');
                                } else if (!(value >= 0 && value <= 150)){
                                    callback('年龄应该在0至150之间');
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
                    ]
                }
            }
        },
        created() {
            this.getUser().then(res=>{
                this.dataForm = res;
            })
        },
        methods: {
            // 获取用户信息，异步操作，等待获取到用户信息后再加载
            async getUser(){
                this.user = JSON.parse(localStorage.getItem("user"));
                return (await this.request.get("/user/findOneById/" + this.user.id)).data;
            },
            // 点击确认修改后，触发，并更新浏览器存储的用户信息
            save() {
                this.$refs["dataForm"].validate((valid) => {
                    if (valid) {
                        this.request.post("/user/post", this.dataForm).then(res => {
                            if (res.code === '200') {
                                this.$message.success("保存成功");
                                // 触发父级Manage的refreshUser方法，让Manage获取新的user数据
                                this.$emit("refreshUser");

                                // 更新浏览器缓存
                                this.getUser().then(res => {
                                    res.token = JSON.parse(localStorage.getItem("user")).token;
                                    localStorage.setItem("user",JSON.stringify(res));
                                })
                            } else {
                                this.$message.error("保存失败");
                            }
                        })
                    }else{
                        this.$message.error("校验失败");
                    }
                })
            },
        }
    }
</script>

<style>

</style>
