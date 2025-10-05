<template>
  <el-container style="min-height: 100vh">
      <!--  sideWidth动态设置菜单栏宽度    -->
    <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246); box-shadow: 2px 0 6px rgba(0, 21, 41, 0.35);">
<!--        通过路由实现-->
<!--        组件传值,将isCollapse和logoTextShow传到Aside.vue-->
        <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow" />
    </el-aside>

<!--      @asideCollapse传递方法，冒号传递变量-->
    <el-container>
        <el-header style="border-bottom: 1px solid #ccc;">
          <Header :collapseBtnClass="collapseBtnClass" @asideCollapse="collapse" :user="user"/>
      </el-header>

      <el-main>
<!--          表示当前页面的子路由在router-view中展示-->
          <router-view @refreshUser="getUser" />
      </el-main>
    </el-container>
  </el-container>
</template>

<script>

// 导入组件

import Aside from "../../components/Administrator/Aside";
import Header from "../../components/Administrator/Header";

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
        Aside,
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
</style>

