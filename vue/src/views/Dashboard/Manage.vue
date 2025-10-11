<template>
    <el-container style="min-height: 100vh">
        <!-- 整个页面的外层容器 -->
        
        <!-- 内部容器：包含 Header, Main, Footer -->
        <el-container direction="vertical">
            <!-- 顶部大图 banner（在导航栏上方） -->
            <div class="top-banner">
                <div class="banner-inner">
                    <h1 class="banner-title">“辽望”大模型—矿山安全开采智能决策系统</h1>
                </div>
                
                <!-- 用户显示和退出功能区域 -->
                <div class="user-controls">
                    <el-dropdown trigger="click" @command="handleCommand">
                        <span class="el-dropdown-link user-info">
                            <i class="el-icon-user-solid user-icon"></i>
                            <!-- 显示用户名，如果未登录则显示“未登录用户” -->
                            {{ user.name || '未登录用户' }} 
                            <i class="el-icon-arrow-down el-icon--right"></i>
                        </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="profile">个人中心</el-dropdown-item>
                            <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </div>
            
            <!-- 头部导航栏 -->
            <el-header class="dashboard-header-el" style="border-bottom: 1px solid #ccc; padding: 0;">
                <div class="header-wrap">
                    <!-- Header 组件包含您的导航菜单 -->
                    <Header :collapseBtnClass="collapseBtnClass" @asideCollapse="collapse" :user="user"/>
                </div>
            </el-header>
            
            <!-- 主要内容区域 -->
            <el-main class="main-content">
                <!-- 表示当前页面的子路由在router-view中展示-->
                <router-view @refreshUser="getUser" />
            </el-main>
            
            <!-- 底部页脚 -->
            <AppFooter />
        </el-container>
    </el-container>
</template>

<script>
// 导入组件
import Header from "../../components/Display/Header";
import AppFooter from "../../components/Display/Footer";

export default {
    name: 'Manage',
    data() {
        return {
            user: {},
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false, // 默认菜单栏是展开的
            sideWidth: 200,
            logoTextShow: true
        }
    },
    // object对象，定义该页存在的组件
    components: {
        Header,
        AppFooter
    },
    created() {
        // 页面加载时从后天获取user信息，存入页面
        this.getUser();
    },
    methods: {
        collapse() {  // 点击收缩按钮触发
            this.isCollapse = !this.isCollapse;
            if (this.isCollapse) {  // 收缩
                this.sideWidth = 64; // 更改菜单栏宽度
                this.collapseBtnClass = 'el-icon-s-unfold'; // 更改图标
                this.logoTextShow = false;
            } else {   // 展开
                this.sideWidth = 200; // 更改菜单栏宽度
                this.collapseBtnClass = 'el-icon-s-fold'; // 更改图标
                this.logoTextShow = true;
            }
        },
        // 获取用户信息，此方法利用@refreshUser，使得相关联页面也可以调用
        getUser() {
            let userString = localStorage.getItem("user");
            if (userString) {
                try {
                    const parsedUser = JSON.parse(userString);
                    const userId = parsedUser.id;

                    this.user = { id: userId, name: parsedUser.username || "Admin" };

                    // 假设 this.request 存在, 如果不需要真实请求，请保持注释
                    /*
                    this.request.get("/user/findOneById/" + userId).then(res => {
                        this.user = res.data;
                    }).catch(err => {
                        console.error("Failed to fetch user:", err);
                    });
                    */
                } catch (e) {
                    console.error("Error parsing user from localStorage:", e);
                    localStorage.removeItem("user"); // 清除无效数据
                    this.user = {};
                }
            } else {
                this.user = {};
            }
        },
        
        // 处理下拉菜单命令
        handleCommand(command) {
            if (command === 'logout') {
                this.logout();
            } else if (command === 'profile') {
                // 占位
                this.$message.info("即将跳转到个人中心...");
            }
        },
        
        // 退出登录逻辑
        logout() {
            // 1. 清除本地存储中的用户信息
            localStorage.removeItem("user");
            this.$message.success("退出成功！已清除登录状态。");
            
            // 2. 刷新 user 状态
            this.user = {};
            
            // 3. 重定向到登录页面
            if (this.$router) {
                this.$router.push('/login');
            } else {
                console.warn("Router is not available. Logout successful.");
            }
        }
    }
}
</script>

<style>
/* 全局样式，确保根元素和 body 占满整个视口 */
html,
body,
#app {
    height: 100%;
    margin: 0;
    padding: 0;
}

/* 内部的 el-container (垂直方向)，确保它是一个 Flex 容器，且在内容不足时占满父容器 */
.el-container:nth-child(2) {
    display: flex;
    flex-direction: column;
    min-height: 100%; 
}

/* 使 el-main 占据所有剩余空间 */
.main-content {
    flex: 1 0 auto; 
    
    /* 移除默认 padding，让内容完全由子组件控制 */
    padding: 0 !important; 
    
    /* 确保内容本身没有内部滚动条，让页面的滚动条出现 */
    overflow: visible;
    /* 有一点距离更美观 */
    margin-top: 15px;
}

/* 确保 header 内部无额外间距，菜单横向填满 */
.header-wrap {
    width: 100%;
    padding: 0;
    margin: 0;
}

/* 顶部 banner 样式 */
.top-banner {
    width: 100%;
    height: 160px;
    background: linear-gradient(180deg, #0a3a6b, #1b6aa5);
    display: flex;
    align-items: center;
    justify-content: center;
    color: #fff;
    position: relative; /* 确保 user-controls 可以绝对定位 */
    z-index: 1;
}
/* 底部渐变阴影，用于与导航栏衔接 */
.top-banner::after {
    content: '';
    position: absolute;
    left: 0;
    right: 0;
    bottom: -16px;
    height: 32px;
    background: linear-gradient(to bottom, rgba(0,0,0,0.0), rgba(0,0,0,0.28));
    pointer-events: none;
    z-index: 0;
}

/* 用户控制区域样式 */
.user-controls {
    position: absolute;
    top: 25px; /* 距离顶部的距离 */
    right: 30px; /* 距离右侧的距离 */
    z-index: 10;
}

.user-info {
    cursor: pointer;
    color: #fff;
    font-size: 16px;
    font-weight: 500;
    transition: all 0.3s;
    padding: 8px 12px;
    border-radius: 5px;
    background-color: rgba(255, 255, 255, 0.1); /* 轻微背景 */
    display: flex;
    align-items: center;
}
.user-info:hover {
    background-color: rgba(255, 255, 255, 0.25);
}
.user-icon {
    font-size: 18px;
    margin-right: 5px;
}
.el-dropdown-link {
    /* 确保下拉菜单的链接能被识别为链接样式 */
    color: #fff; 
}


/* 导航栏与 banner 的平滑过渡样式 */
.dashboard-header-el {
    background: #003567; /* 使用导航栏的背景色覆盖原有的透明度 */
    box-shadow: 0 4px 10px rgba(0,0,0,0.1);
    position: relative;
    z-index: 2;
}
.banner-inner {
    text-align: center;
}
.banner-title {
    margin: 0;
    font-size: 36px;
    font-weight: 700;
    letter-spacing: 6px;
    color: #fff;
}
.banner-sub {
    margin: 6px 0 0 0;
    color: rgba(255,255,255,0.85);
}
</style>
