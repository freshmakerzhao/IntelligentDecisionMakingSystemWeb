<template>
<!--    背景-->
    <div class="wrapper">
        <!--        中间窗口-->
        <div style="margin: 150px auto; background-color: #fff; width: 350px;   padding: 20px; border-radius: 10px">
            <div style="margin: 20px 0; text-align: center; font-size: 25px ;"><b>注 册</b></div>
<!--            输入框-->
<!--            show-password 隐藏密码-->
<!--            :model json数据绑定-->
<!--            ref 表单校验绑定-->
<!--            prop 与rules中的名字对应-->
            <el-form :model="user" :rules="rules" ref="userForm">
                <el-form-item prop="username">
                    <el-input placeholder="请输入用户名" size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input placeholder="请输入密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
                </el-form-item>
                <el-form-item prop="confirmPassword">
                    <el-input placeholder="请确认密码"size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="user.confirmPassword"></el-input>
                </el-form-item>
                <el-form-item style="margin-top: 20px; text-align: right">
                    <el-button type="primary" size="small" autocomplete="off" @click="login">注册</el-button>
                    <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/login')">返回登录</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Register",
        data() {
            return {
                // 输入框输入的数据存入user对象，并制定相应规则
                user: {},
                rules: {
                    username: [
                        // trigger: 'blur'失焦时触发
                        { required: true, message: '请输入用户名', trigger: 'blur' },
                        { min: 3, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                        { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
                    ],
                    confirmPassword: [
                        { required: true, message: '请再次输入密码', trigger: 'blur' },
                        { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
                    ],
                }
            }
        },
        methods: {
            login() {
                // 根据rule判断是否合法，valid为true就是合法
                this.$refs['userForm'].validate((valid) => {
                    if (valid) {  // 表单校验合法
                        // 判断两次密码输入是否一致
                        if (this.user.password !== this.user.confirmPassword) {
                            this.$message.error("两次输入的密码不一致");
                            return false
                        }
                        // 如果一致，跳转后台进行注册，传递user数据
                        this.request.post("/user/register", this.user).then(res => {
                            if(res.code === '200') {
                                this.$message.success("注册成功")
                                this.$router.push("/login");
                            } else {
                                this.$message.error(res.msg)
                            }
                        })
                    }
                });
            }
        }
    }
</script>

<style>
    .wrapper {
        /*高度填充100%*/
        height: 100vh;
        /*渐变色背景*/
        background-image: linear-gradient(to bottom right, #FC466B , #3F5EFB);
        overflow: hidden;
    }
</style>
