<template>
    <el-container style="min-height: 100vh">
        <!-- @asideCollapse传递方法，冒号传递变量-->
            <el-container>
                <!-- 顶部大图 banner（在导航栏上方） -->
                <div class="top-banner">
                    <div class="banner-inner">
                        <h1 class="banner-title">矿山安全开采智能决策系统</h1>
                    </div>
                </div>
                <!-- 去掉导航栏两侧空隙，横向填满 -->
                <el-header class="dashboard-header-el" style="border-bottom: 1px solid #ccc; padding: 0;">
                    <div class="header-wrap">
                        <Header :collapseBtnClass="collapseBtnClass" @asideCollapse="collapse" :user="user"/>
                    </div>
                </el-header>
            <el-main>
                <!-- 表示当前页面的子路由在router-view中展示-->
                <router-view @refreshUser="getUser" />
            </el-main>
        </el-container>
    </el-container>
</template>

<script>

// 导入组件

import Header from "../../components/Display/Header";

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
        Header
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
            let userId = '';
            userId = JSON.parse(localStorage.getItem("user")).id;
            // 从后台获取到修改后的user数据，再传递给header
            this.request.get("/user/findOneById/" + userId).then(res => {
                this.user = res.data;
            })
        },
    }
}
</script>

<style>

    html,
    body,
    #app,
    .el-container {
        height: 100%;
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
        position: relative;
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

    /* 导航栏与 banner 的平滑过渡样式 */
    .dashboard-header-el {
        background: rgba(255,255,255,0.02); /* 轻微透明，和 banner 混合 */
        box-shadow: 0 -8px 20px rgba(0,0,0,0.12); /* 上方阴影，柔化衔接 */
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

