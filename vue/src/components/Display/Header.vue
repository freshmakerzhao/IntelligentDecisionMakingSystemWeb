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
    <el-menu-item index="/dashboard/cases" class="menu-item-flex">
      <div class="menu-label">
        <div class="menu-cn">项目案例</div>
        <div class="menu-en">Case Studies</div>
      </div>
    </el-menu-item>
    <el-menu-item index="/dashboard/faq" class="menu-item-flex">
      <div class="menu-label">
        <div class="menu-cn">常见问题</div>
        <div class="menu-en">FAQ</div>
      </div>
    </el-menu-item>
    <el-menu-item index="/dashboard/model" class="menu-item-flex">
      <div class="menu-label">
        <div class="menu-cn">模型决策</div>
        <div class="menu-en">Model Decision</div>
      </div>
    </el-menu-item>
    <el-menu-item index="/dashboard/about" class="menu-item-flex">
      <div class="menu-label">
        <div class="menu-cn">团队介绍</div>
        <div class="menu-en">Team Introduction</div>
      </div>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: 'AppHeader',
  data() {
    return {
      // 默认 activeIndex 设置为路由路径，如果没有路由则使用默认值
      activeIndex: this.$route ? this.$route.path : '/dashboard/home'
    };
  },
  mounted() {
    // 确保首次加载时 activeIndex 正确
    if (this.$route) {
        this.activeIndex = this.$route.path;
    }
  },
  methods: {
    handleSelect(index) {
      this.activeIndex = index;
    }
  },
  watch: {
      // 监听路由变化来更新 activeIndex
      $route(to) {
          this.handleSelect(to.path)
      }
  }
}
</script>
 
<style scoped>

/* ==================================== */
/* 导航栏容器样式 */
/* ==================================== */
.full-width-menu {
  width: 100%;
  display: flex;
  margin-bottom: 12px;
  height: 72px; /* 桌面端高度 */
  border-bottom: none !important;
  /* 允许在小屏设备上横向滚动查看所有菜单项 */
  overflow: hidden; 
}

/* ==================================== */
/* 菜单项 item 样式 (桌面端) */
/* ==================================== */

/* 强制覆盖 Element UI 的默认行高和高度 */
.el-menu--horizontal > .el-menu-item {
  height: 72px !important; 
  line-height: normal !important; 
  padding: 0 15px; /* 调整水平间距 */
  display: flex !important;
  align-items: center; 
  justify-content: center; 
  
  flex: 1 1 0; /* 自动分配宽度，并允许缩小 */
  min-width: 100px; /* 最小宽度，防止过度压缩 */
  transition: all 0.3s ease; /* 添加过渡效果 */
}

/* 菜单项 hover/active 样式优化 */
.el-menu--horizontal > .el-menu-item:hover {
    background-color: #034b91 !important; /* 悬停颜色 */
}


/* ==================================== */
/* 两行标签样式 */
/* ==================================== */
.menu-label {
  display: flex;
  flex-direction: column; 
  align-items: center; 
  justify-content: center; 
  white-space: nowrap; /* 防止文本换行 */
}

.menu-cn {
  font-size: 24px;
  color: #fff;
  font-weight: 900;
  line-height: 1.2; 
}

.menu-en {
  font-size: 16px;
  color: rgba(255,255,255,0.85);
  font-weight: 900;
  margin-top: 3px; 
  line-height: 1.2;
}

/* 激活状态的高亮指示器 */
.el-menu--horizontal > .el-menu-item.is-active {
    border-bottom: 3px solid #ffd04b !important;
    background-color: #00305f !important; 
}

/* ==================================== */
/* 响应式布局 (Mobile/Tablet) */
/* ==================================== */
@media (max-width: 768px) {
    /* 768px 及以下设备 */

    .full-width-menu {
        height: 56px; /* 减小整体高度 */
        /* 关键：强制让内部菜单项保持水平排列，允许容器横向滚动 */
        overflow-x: auto;
        overflow-y: hidden;
        justify-content: flex-start; /* 菜单项从左侧开始排列 */
    }
    
    /* 菜单项高度和边距调整 */
    .el-menu--horizontal > .el-menu-item {
        height: 56px !important;
        padding: 0 10px; /* 减小左右边距 */
        min-width: 90px; /* 允许进一步压缩 */
        flex: 0 0 auto; /* 不再自动分配宽度，只占据内容所需的空间 */
    }

    /* 字体大小调整 */
    .menu-cn {
        font-size: 16px; /* 中文标题大幅缩小 */
        font-weight: 700;
    }

    .menu-en {
        font-size: 11px; /* 英文副标题缩小 */
        font-weight: 600;
        margin-top: 2px;
    }
}
</style>
