<template>
    <div class="wrapper" id="backgroundWrapper">
        <div class="login-container">
            <div class="header-info-box">
                <h1 class="main-title">“辽望”大模型—矿山安全开采智能决策系统</h1>

                <div class="logo-group">
                    <div class="logo-item">
                        <div class="partner-logo-wrapper">
                            <img src="../assets/鑫丰logo.png"  alt="鑫丰矿业Logo" class="actual-logo-img">
                        </div>
                        <p class="partner-name">辽宁鑫丰矿业（集团）矿山工程有限公司</p>
                    </div>
                    <!-- 分隔线 -->
                     <div class="divider"></div>
                    <!-- 辽宁大学 -->
                     <div class="logo-item">
                        <div class="partner-logo-wrapper">
                            <img src="../assets/辽宁大学logo.jpg" alt="辽宁大学Logo" class="actual-logo-img">
                        </div>
                        <p class="partner-name">辽宁大学</p>
                    </div>
                </div>
            </div>

            <div class="login-box">
                <div class="login-title"><b>用户登录</b></div>
                
                <el-form :model="dto" :rules="rules" ref="loginForm">
                    <el-form-item prop="username">
                        <el-input 
                            size="medium" 
                            style="margin: 10px 0" 
                            prefix-icon="el-icon-user" 
                            placeholder="请输入用户名"
                            v-model="dto.username">
                        </el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input 
                            size="medium" 
                            style="margin: 10px 0" 
                            prefix-icon="el-icon-lock" 
                            show-password 
                            placeholder="请输入密码"
                            v-model="dto.password">
                        </el-input>
                    </el-form-item>
                    <el-form-item style="margin: 20px 0 10px; text-align: center">
                        <el-button 
                            type="primary" 
                            size="medium" 
                            class="login-button"
                            autocomplete="off" 
                            @click="login">
                            登 录
                        </el-button>
                        <el-button 
                            type="warning" 
                            size="medium" 
                            class="register-button"
                            autocomplete="off" 
                            @click="$router.push('/register')">
                            注 册
                        </el-button>
                    </el-form-item>
                </el-form>
            </div>
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
                        { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
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
                    if (valid) {  // 表单校验合法
                        this.request.post("/user/login", this.dto).then(res => {
                            if (res.code === '200') {
                                // 验证成功
                                localStorage.setItem("user", JSON.stringify(res.data));
                                this.$message.success("登录成功");
                                this.$router.push("/dashboard");
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

<style scoped>
/* 使用 scoped 确保样式只应用于当前组件 */
.wrapper {
    height: 100vh;
    background-image: linear-gradient(to bottom right, #004080 , #0a3a6b); /* 深色渐变背景，更贴合矿业/科技主题 */
    overflow: hidden;
    display: flex; /* 使用 Flexbox 居中整个登录容器 */
    justify-content: center;
    align-items: center;
}

/* 增加了整体容器宽度，不再显得小气 */
.login-container {
    width: 600px; 
    border-radius: 12px;
    padding: 30px;
    box-shadow: 0 10px 40px rgba(0, 0, 0, 0.3);
}

/* 顶部信息区域 */
.header-info-box {
    margin-bottom: 30px; /* 增加底部间距 */
    padding: 10px 0;
    text-align: center;
    color: #fff;
    background: transparent;
}

/* 放大主标题 */
.main-title {
    font-size: 26px;
    font-weight: 700;
    margin-bottom: 25px; 
    line-height: 1.5;
    text-shadow: 1px 1px 5px rgba(0, 0, 0, 0.6);
}

/* 合作单位 Logo 组 */
.logo-group {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 30px; /* 增加 Logo 组内部间距 */
    margin-top: 15px;
}

.logo-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 45%;
}

/* 新增的 Logo 容器，用于创建圆形边框和居中图片 */
.partner-logo-wrapper {
    width: 70px; 
    height: 70px;
    border-radius: 50%; /* 确保圆形 */
    box-shadow: 0 3px 8px rgba(0, 0, 0, 0.3);
    border: 4px solid #fff; /* 边框 */
    display: flex; /* 使用 Flexbox 居中内部图片 */
    justify-content: center;
    align-items: center;
    overflow: hidden; /* 隐藏可能溢出的部分，但图片本身不会被裁剪 */
    background-color: #fff; /* Logo背景色，避免图片透明区域显示背景色 */
    margin-bottom: 8px;
}

/* 实际的 Logo 图片，设置最大尺寸以适应容器，但不会被裁剪 */
.actual-logo-img {
    max-width: 100%; /* 图片最大宽度为容器的100% */
    max-height: 100%; /* 图片最大高度为容器的100% */
    display: block; /* 确保图片为块级元素，便于布局 */
    object-fit: contain;
}

/* 放大文字尺寸 */
.partner-name {
    font-size: 12px;
    color: rgba(255, 255, 255, 0.9);
    margin: 0;
    line-height: 1.3;
}

/* 增加分隔线高度 */
.divider {
    width: 1px;
    height: 70px;
    background-color: rgba(255, 255, 255, 0.7); 
}

/* 登录框样式 */
.login-box {
    background-color: #fff;
    width: 400px; /* 登录框宽度增加 */
    padding: 40px 30px; /* 增加内边距 */
    border-radius: 10px;
    box-shadow: 0 5px 20px rgba(0, 0, 0, 0.15);
    margin: 0 auto; 
}

/* 登录标题放大 */
.login-title {
    margin: 0 0 25px;
    text-align: center;
    font-size: 28px;
    color: #333;
    font-weight: 700;
}

/* 按钮美化 */
.login-button {
    width: 48%; /* 稍微增加宽度 */
    background-color: #0a3a6b;
    border-color: #0a3a6b;
    transition: background 0.3s;
}

.login-button:hover {
    background-color: #004080;
    border-color: #004080;
}

.register-button {
    width: 48%; /* 稍微增加宽度 */
    background-color: #f7a01a;
    border-color: #f7a01a;
    transition: background 0.3s;
}

.register-button:hover {
    background-color: #e08e0b;
    border-color: #e08e0b;
}

/* 响应式调整 */
@media (max-width: 600px) {
    .login-container {
        width: 90%;
        padding: 10px;
    }
    .login-box {
        width: 100%;
        padding: 20px 15px;
    }
    .main-title {
        font-size: 22px;
    }
    .partner-logo-wrapper {
        width: 60px;
        height: 60px;
    }
    .partner-name {
        font-size: 10px;
    }
    .login-title {
        font-size: 24px;
    }
}
</style>
