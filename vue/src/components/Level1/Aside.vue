<template>
    <el-menu :default-openeds="['1']" style="min-height: 100%; overflow-x: hidden"
             background-color="rgb(48, 65, 86)"
             text-color="#fff"
             active-text-color="#ffd04b"
             :collapse-transition="false"
             :collapse="isCollapse"
             :default-active="activeIndex"
             :router="true"
    >
        <div style="height: 60px; line-height: 60px; text-align: center">
            <img src="../../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; right: 5px">
            <b style="color: white" v-show="logoTextShow">后台管理系统</b>
        </div>
        <el-menu-item index="/level1/user">
            <span slot="title">用户管理</span>
        </el-menu-item>
        <el-menu-item index="/level1/travel">
            <span slot="title">拼车出行</span>
        </el-menu-item>
        <el-menu-item index="/level1/schoolKeeping">
            <span slot="title">跑腿代步</span>
        </el-menu-item>
        <el-menu-item index="/level1/lostAndFound">
            <span slot="title">失物招领</span>
        </el-menu-item>
        <el-menu-item index="/level1/secondHand">
            <span slot="title">闲置买卖</span>
        </el-menu-item>
        <el-menu-item index="/level1/comment">
            <span slot="title">评论管理</span>
        </el-menu-item>
        <el-menu-item index="/level1/person">
            <span slot="title">个人中心</span>
        </el-menu-item>
    </el-menu>
</template>

<script>
    // 导入变量类型
    export default {
        props: {
            isCollapse: Boolean,
            logoTextShow: Boolean
        },
        data() {
            return {
                activeIndex: "/level1/user"
            }
        },
        // 正常情况下，菜单只有点击后才会触发，如果通过route.push的方式跳转则不回触发，此时对应element将不会高亮
        // 因此通过watch监控route ，只要有变化，就手动刷新 activeIndex
        methods: {
            handleSelect(index) {
                this.activeIndex = index;
            }
        },
        watch: {
            $route() {
                this.handleSelect(this.$route.path)
            }
        }
    }
</script>

<style scoped>

</style>
