<template>
<!--    背景-->
    <div class="wrapper" id="backgroundWrapper">
        <!--        中间窗口-->
        <div style="margin: 200px auto; background-color: #fff; width: 350px; height: 300px; padding: 20px; border-radius: 10px">
            <div style="margin: 20px 0; text-align: center; font-size: 25px ;"><b>登 录</b></div>
<!--            输入框-->
<!--            show-password 隐藏密码-->
<!--            :model json数据绑定-->
<!--            ref 表单校验绑定-->
<!--            prop 与rules中的名字对应-->
            <el-form :model="dto" :rules="rules" ref="loginForm">
                <el-form-item prop="username">
                    <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="dto.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="dto.password"></el-input>
                </el-form-item>
                <el-form-item style="margin: 10px 0; text-align: right">
                    <el-button type="primary" size="small"  autocomplete="off" @click="login">登录</el-button>
                    <!--                    点击注册跳转到注册页-->
                    <el-button type="warning" size="small"  autocomplete="off" @click="$router.push('/register')">注册</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Login",
        data() {
            return {
                role: false, // 登录身份,默认false user，true doctor
                // 输入框输入的数据存入dto对象，并制定相应规则
                dto: {},
                rules: {
                    username: [
                        // trigger: 'blur'失焦时触发
                        { required: true, message: '请输入用户名', trigger: 'blur' },
                        { min: 2, max: 10, message: '长度在 2 到 5 个字符', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                        { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
                    ],
                }
            }
        },
        created() {
            // 清空存储的登录信息
            localStorage.clear();
        },
        methods: {
             login() {
                // 根据rule判断是否合法，valid为true就是合法
                this.$refs['loginForm'].validate((valid) => {
                    if (valid) {  // 表单校验合法
                        this.request.post("/user/login", this.dto).then(res => {
                            if (res.code === '200') {
                                // 验证成功
                                localStorage.setItem("user", JSON.stringify(res.data));
                                this.$message.success("登录成功");
                                // 直接根据用户权限做跳转
                                this.$router.push("/level1");
                            } else {
                                // 验证失败
                                this.$message.error(res.msg);
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
        /*background-image: linear-gradient(to right bottom, #3F5EFB , #FC466B);*/
        overflow: hidden;
    }
</style>
