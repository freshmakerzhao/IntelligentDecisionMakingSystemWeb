<template>
  <!-- 顶部导航栏 -->
  <el-menu
    class="el-menu-demo full-width-menu"
    mode="horizontal"
    background-color="#003567"
    text-color="#fff"
    active-text-color="#ffd04b"
    :collapse-transition="false"
    :default-active="activeIndex"
    :router="true"
  >
    <el-menu-item index="/dashboard/home" class="menu-item-flex">
      <div class="menu-label">
        <div class="menu-cn">首页</div>
        <div class="menu-en">Home</div>
      </div>
    </el-menu-item>
    <el-menu-item index="/dashboard/home" class="menu-item-flex">
      <div class="menu-label">
        <div class="menu-cn">团队介绍</div>
        <div class="menu-en">About Us</div>
      </div>
    </el-menu-item>
    <el-menu-item index="/dashboard/home" class="menu-item-flex">
      <div class="menu-label">
        <div class="menu-cn">预测系统</div>
        <div class="menu-en">Prediction System</div>
      </div>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  data() {
    return {
      activeIndex: '/dashboard/home'
    };
  },
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
.full-width-menu {
  width: 100%;
  display: flex;
  margin-bottom: 12px; /* 在 header 下方增加间隔 */
  /* Fix: set explicit navbar height and center items so labels don't overflow */
  height: 72px;
  align-items: center;
}
.full-width-menu > li {
  flex: 1 1 0;
  justify-content: center;
  height: 100%;
}
.menu-item-flex {
  display: flex !important;
  justify-content: center;
  align-items: center;
  /* reduce vertical padding so content stays inside navbar */
  padding: 0 10px;
  height: 100%;
}
/* 两行标签的样式 */
.menu-label {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.menu-cn {
  font-size: 18px;
  color: #fff;
  font-weight: 700;
  line-height: 1;
}
.menu-en {
  font-size: 12px;
  color: rgba(255,255,255,0.85);
  margin-top: 4px;
}
</style>