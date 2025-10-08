<template>
  <div class="app-embed-container">
    
    <div class="breadcrumb-container">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item 
          v-for="(item, index) in breadcrumbs" 
          :key="index" 
          :to="item.path ? { path: item.path } : null"
        >
          {{ item.name }}
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <h1 class="page-title">{{ appName }}</h1>
    <!-- <p class="page-subtitle">{{ appDescription }}</p> -->

    <div class="dify-chat-area">
      <iframe
        src="http://localhost:8081/chatbot/r8HI34wxCkVQquXU"
        frameborder="0"
        allow="microphone"
        class="dify-iframe-embed"
      ></iframe>
    </div>

    <div style="height: 40px;"></div>
  </div>
</template>

<script>
export default {
  name: 'DifyChatApp',
  data() {
    return {
      // 假设这是从路由或 API 获取的应用信息
      appName: '智能知识库问答机器人 (Knoledge Bot)',
      // appDescription: '基于 Dify 驱动的智能聊天应用，用于解答企业内部文档和常见问题。',
      
      // *** 重点：Dify 应用的嵌入 URL ***
      // 假设您的 Dify WebApp 发布地址是这个：
      appEmbedUrl: 'http://your-dify-domain:8081/app/knowledge-bot/embed',
    }
  },
  computed: {
    /**
     * 动态生成面包屑数组。
     */
     breadcrumbs() {
      // 模拟当前页面的路径。
      const currentPath = this.$route ? this.$route.path : '/dashboard/model';

      let paths = [
        // 首页总是第一项，且可跳转
        { name: '首页 (Home)', path: '/dashboard/home' },
      ];

      // 根据当前路径判断并添加第二级导航
      if (currentPath.includes('/model')) {
        paths.push({ name: '模型决策 (Model Decision)', path: '/dashboard/model' });
      } 
      
      return paths;
    }
  },
  methods: {
  }
};
</script>
<style scoped>
/* 容器样式保持一致，从 faq-container 改为 app-embed-container */
.app-embed-container {
  padding: 30px;
  max-width: 1600px; 
  margin: 0 auto;
}

/* 面包屑容器样式保持一致 */
.breadcrumb-container {
    padding: 10px 0 20px 0;
    border-bottom: 1px solid #EBEEF5;
    margin-bottom: 20px;
}

/* 标题样式保持一致 */
.page-title {
  font-size: 32px;
  font-weight: 800;
  color: #0a3a6b;
  text-align: center;
  margin-bottom: 5px;
}

.page-subtitle {
    font-size: 16px;
    color: #909399;
    text-align: center;
    margin-bottom: 40px;
}

/* --- Dify 应用内容区域样式 --- */
.dify-chat-area {
  /* 确保应用区域有足够的空间 */
  min-height: 700px; 
  border: 1px solid #DCDFE6; 
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  overflow: hidden; 
  /* 关键：设置为 flex 容器或 block 容器，确保 iframe 充满 */
  display: flex; 
}

.dify-iframe-embed {
  /* 继承父容器的全部宽度和高度 */
  width: 100%;
  height: 100%;
  /* min-height 可以在这里设置，也可以在 template 的 iframe 属性上设置 */
  min-height: 700px;
  display: block;
}

/* 底部留白 */
div[style="height: 40px;"] {
    clear: both;
}
</style>
