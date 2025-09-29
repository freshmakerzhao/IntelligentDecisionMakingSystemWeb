<template>
    <div style="line-height: 60px; display: flex">
        <div style="flex: 1;">
            <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
            <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
                <el-breadcrumb-item :to="'/level2/'">首页</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <el-dropdown style="width: 150px; cursor: pointer; text-align: right">
            <div style="display: inline-block">
                <span>{{user.username}}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            </div>
            <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
                <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                    <div @click="goPerson">
                        <!--点击退出后，跳转登录页面-->
                        <span>个人信息</span>
                    </div>
                </el-dropdown-item>
                <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                    <div @click="logout">
                        <!--点击退出后，跳转登录页面-->
                        <span>退出</span>
                    </div>
                </el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>

<script>
    export default {
        // 导入变量类型
        // manage传递过来的user
        props: {
            collapseBtnClass: String,
            user: Object
        },
        data(){
            return{
            }
        },
        // 导入方法
        methods:{
            collapse(){
                // collapse方法名传递时叫asideCollapse
                this.$emit("asideCollapse")
            },
            // 退出登录时，删除存储的用户信息，并跳转回登录页
            logout() {
                this.$router.push("/login");
                localStorage.removeItem("user");
                this.$message.success("退出成功");
            },
            goPerson(){
                this.$router.push("/level1/person");
            }
        },
    }
</script>

<style scoped>

</style>