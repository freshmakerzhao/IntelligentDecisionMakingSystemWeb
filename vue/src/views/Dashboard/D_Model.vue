<template>
  <div class="app-embed-container">
    
    <!-- 面包屑导航区域 -->
    <div class="breadcrumb-container">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item 
          v-for="(item, index) in breadcrumbs" 
          :key="index" 
          :to="item.path ? { path: item.path } : null"
          @click.native.prevent="handleBreadcrumbClick(item, index)"
        >
          {{ item.name }}
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <!-- 主内容区：气泡图 或 AI 问答 -->
    <div class="model-decision-content">

      <!-- 1. 气泡图 UI / 手机按钮列表 UI -->
      <div v-if="currentView === 'bubbles'" class="bubble-visualization">
        
        <!-- 桌面/平板视图 (气泡图) -->
        <div v-if="!isMobile" class="bubble-field-container">
          
          <div 
            v-for="domain in domains" 
            :key="domain.name" 
            class="bubble" 
            :class="[domain.class, domain.amplitudeClass, domain.class !== 'center-bubble' ? 'floating-bubble' : '']"
            :style="{ 
                width: domain.size + 'px', 
                height: domain.size + 'px', 
                backgroundColor: domain.color,
                // 动态设置动画时长和延迟，实现非同步效果
                animationDuration: domain.duration, 
                animationDelay: domain.delay 
            }"
            @click="selectDomain(domain.name)"
          >
            <div class="bubble-content">
              <span class="bubble-cn">{{ domain.name }}</span>
              <span class="bubble-en">{{ domain.en }}</span>
            </div>
          </div>

        </div>
        
        <!-- 手机视图 (按钮列表) -->
        <div v-else class="domain-button-list">
             <button 
                v-for="domain in subDomains" 
                :key="domain.name"
                class="domain-button"
                :style="{ backgroundColor: domain.color }"
                @click="selectDomain(domain.name)"
            >
                <i v-if="domain.icon" :class="domain.icon" class="button-icon"></i>
                <span class="button-cn">{{ domain.name }}</span>
                <span class="button-en">{{ domain.en }}</span>
            </button>
        </div>

      </div>

      <!-- 2. AI 问答机器人 UI (Chat Iframe) -->
      <div v-else class="dify-chat-area">
        <iframe
          :src="appEmbedUrl"
          frameborder="0"
          allow="microphone"
          class="dify-iframe-embed"
        ></iframe>
      </div>
    </div>
    
    <div style="height: 40px;"></div>
  </div>
</template>

<script>

const ROUTE_MAPPING = {
  HOME: { name: '首页 (Home)', path: '/dashboard/home' },
  MODEL_DECISION: { name: '模型决策 (Model Decision)', path: '/dashboard/model' },
};

export default {
  name: 'ModelDecision',
  data() {
    // 辅助函数：生成随机动画值
    // 生成随机时长 (2s 到 5s)
    const generateRandomDuration = () => `${(Math.random() * 3 + 2).toFixed(1)}s`;
    // 生成随机延迟 (0s 到 -8s，确保动画一开始就处于不同阶段)
    const generateRandomDelay = () => `-${(Math.random() * 8).toFixed(1)}s`;
    // 生成随机振幅类名
    const generateRandomAmplitudeClass = () => {
        const amplitudes = ['pulse-sm', 'pulse-md', 'pulse-lg'];
        return amplitudes[Math.floor(Math.random() * amplitudes.length)];
    };
    
    const domainsData = [
        // 中心大模型 (不可点击切换)
        { name: '大模型', en: 'LLM Core', size: 280, color: '#1B6AA5', class: 'center-bubble' },
        // 周围七个子领域 (点击后切换到聊天模式)
        { name: '采煤', en: 'Coal Mining', size: 190, color: '#909399', class: 'pos-1' , duration: generateRandomDuration(), delay: generateRandomDelay(), amplitudeClass: generateRandomAmplitudeClass() },
        { name: '掘进', en: 'Roadway Excavation', size: 150, color: '#F56C6C', class: 'pos-2' , duration: generateRandomDuration(), delay: generateRandomDelay(), amplitudeClass: generateRandomAmplitudeClass() },
        { name: '安装回撤', en: 'Equipment Installation & Retreat', size: 160, color: '#E6A23C', class: 'pos-3' , duration: generateRandomDuration(), delay: generateRandomDelay(), amplitudeClass: generateRandomAmplitudeClass() },
        { name: '辅助运输', en: 'Auxiliary Transportation', size: 140, color: '#E44D26', class: 'pos-4' , duration: generateRandomDuration(), delay: generateRandomDelay(), amplitudeClass: generateRandomAmplitudeClass() },
        { name: '机电', en: 'Mechanical & Electrical', size: 160, color: '#67C23A', class: 'pos-5', duration: generateRandomDuration(), delay: generateRandomDelay(), amplitudeClass: generateRandomAmplitudeClass() },
        { name: '通风', en: 'Ventilation', size: 150, color: '#6F42C1', class: 'pos-6' , duration: generateRandomDuration(), delay: generateRandomDelay(), amplitudeClass: generateRandomAmplitudeClass() },
        { name: '应急救援', en: 'Emergency Rescue', size: 140, color: '#409EFF', class: 'pos-7', duration: generateRandomDuration(), delay: generateRandomDelay(), amplitudeClass: generateRandomAmplitudeClass() },
      ];

    return {
      // 控制视图状态: 'bubbles' 或 'chat'
      currentView: 'bubbles', 
      // 存储当前选中的领域名称，用于更准确地更新面包屑
      selectedDomainName: '', 
      
      // *** Dify 应用的嵌入 URL，用于演示，请替换为您的实际地址 ***
      appEmbedUrl: 'http://localhost:8081/chatbot/GPM7svwlg3Pa5sMn',
      
      // 气泡数据：1大 + 7小
      domains: domainsData,
      // 手机端按钮列表数据 (排除大模型)
      subDomains: domainsData.filter(d => d.name !== '大模型'),
      // 新增：响应式属性，判断是否为手机视图
      isMobile: false, 
    }
  },
  mounted() {
    this.checkMobile();
    // 监听窗口尺寸变化
    window.addEventListener('resize', this.checkMobile);
  },
  beforeDestroy() {
    // 移除监听器，避免内存泄漏
    window.removeEventListener('resize', this.checkMobile);
  },
  watch: {
    // 监听路由路径变化
    '$route.path': {
      handler(newPath, oldPath) {
        // 确保当路由回到模型决策主页时，如果当前不是气泡视图，则重置视图。
        if (newPath === ROUTE_MAPPING.MODEL_DECISION.path && this.currentView !== 'bubbles') {
          this.resetToBubbles();
        }
      },
      immediate: true // 组件创建时立即执行一次
    },
  },
  computed: {
    /**
     * 动态生成面包屑数组。
     */
    breadcrumbs() {
      // computed 属性不需要手动接收参数或赋值给 data，只需返回结果
      const currentPath = this.$route ? this.$route.path : ROUTE_MAPPING.MODEL_DECISION.path;
      let paths = [
        ROUTE_MAPPING.HOME, // 总是以首页开始
      ];

      // 1. 定义 '模型决策' 这一级的对象，强制 path 为空
      const decisionLink = { 
        name: ROUTE_MAPPING.MODEL_DECISION.name,
        // 【关键修改】始终将 path 设置为空。
        // 这将阻止 <el-breadcrumb-item> 自动触发路由导航，从而消除 NavigationDuplicated 警告。
        // 我们完全依赖 handleBreadcrumbClick 来处理点击事件。
        path: '' 
      };

      // 根据当前路径和视图状态生成面包屑
      if (currentPath.includes(ROUTE_MAPPING.MODEL_DECISION.path)) {
        
        // 总是添加 '模型决策' 这一级
        paths.push(decisionLink); 
        
        if (this.currentView === 'chat' && this.selectedDomainName) {
            // 聊天视图下，增加第三级路径 (xxx 机器人)
            // 最后一级总是不可点击
            paths.push({ name: `${this.selectedDomainName} 机器人`, path: '' }); 
        }
      } 
      
      // 直接返回计算好的数组
      return paths;
    }
  },
  methods: {
    /**
     * 检查当前是否为移动端尺寸 (<= 768px)
     */
    checkMobile() {
      this.isMobile = window.innerWidth <= 768;
    },
    /**
     * 点击气泡/按钮时触发，切换到聊天界面
     * @param {string} domainName - 选中的领域名称
     */
    selectDomain(domainName) {
      if (domainName !== '大模型') {
        this.currentView = 'chat';
        this.selectedDomainName = domainName; // 存储选中的领域名称
        this.$message.success(`已进入 ${domainName} 智能决策模式，请开始提问。`);
        console.log(this.currentView)
      } else {
        this.$message.info('请点击周围的子领域气泡以进入对应的决策模式。');
      }
    },
    /**
     * 重置视图，回到气泡图界面
     */
    resetToBubbles() {
      this.currentView = 'bubbles';
      this.selectedDomainName = '';
      console.log('视图已重置为气泡图。');
    },
    /**
     * 处理面包屑点击事件，防止重复导航错误。
     * @param {Object} item - 面包屑项数据
     * @param {number} index - 面包屑项索引
     */
    handleBreadcrumbClick(item, index) {
      console.log("进来了");
      // 检查：是否是 '模型决策' (index 1) 且当前处于 'chat' 视图
      if (index === 1 && this.currentView === 'chat') {
        // 目标：从 chat 视图返回 bubbles 视图，执行内部状态重置
        console.log("进来了2");
        this.resetToBubbles();
        return;
      } 
      
      // 检查：是否是 '首页' (index 0) 或其他需要路由跳转的项
      // 并且路径与当前路径不相同，避免不必要的跳转
      if (item.path && item.path !== this.$route.path) {
          // 手动执行路由跳转
          this.$router.push({ path: item.path }).catch(err => {
              // 捕获并忽略 NavigationDuplicated 错误
              if (err.name !== 'NavigationDuplicated') {
                  console.error('Router navigation error:', err);
              }
          });
      }
      // 如果 item.path 为空（最后一级）或路径与当前路径相同，则不做任何操作。
    },
  }
};
</script>
<style scoped>

/* 容器样式 */
.app-embed-container {
  padding: 30px;
  max-width: 1600px; 
  margin: 0 auto;
}

/* 面包屑容器样式 */
.breadcrumb-container {
    padding: 10px 0 20px 0;
    border-bottom: 1px solid #EBEEF5;
    margin-bottom: 20px;
}

/* 标题样式 */
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

/* ================================================== */
/* 1a. 气泡图 UI 样式 (桌面/平板) */
/* ================================================== */

/* 气泡可视化容器 */
.bubble-visualization {
    display: flex;
    justify-content: center;
    align-items: flex-start; 
    min-height: 700px;
}

/* 气泡场容器 (用于定位) */
.bubble-field-container {
    position: relative;
    width: 80%;
    height: 800px;
    margin: 0 auto; 
    transform: scale(0.9); /* 稍微缩小以适应中等屏幕 */
    /* 允许内容在移动端收缩 */
    max-width: 100%; 
    max-height: 100%;
}

/* 单个气泡基础样式 */
.bubble {
    border-radius: 50%;
    position: absolute;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
    color: #fff;
    cursor: pointer;
    /* 基础过渡效果，用于 hover */
    transition: transform 0.3s ease, box-shadow 0.3s ease, z-index 0s 0.3s;
    box-shadow: 0 8px 15px rgba(0, 0, 0, 0.2);
    z-index: 10;
    font-family: 'Inter', sans-serif;
}

.bubble:hover {
    transform: scale(1.1);
    box-shadow: 0 12px 20px rgba(0, 0, 0, 0.3);
    z-index: 20; 
}

.bubble-content {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 10px;
    user-select: none; /* 防止气泡文本被选中 */
}

.bubble-cn {
    font-size: 18px;
    font-weight: bold;
    line-height: 1.2;
    margin-top: 5px;
}

.bubble-en {
    font-size: 12px;
    opacity: 0.8;
}

.bubble-icon {
    font-size: 40px;
    line-height: 1;
}

/* 中心大模型气泡 */
.center-bubble {
    top: 250px;
    left: 500px;
    font-size: 24px;
    z-index: 15;
    cursor: default; /* 不可点击切换 */
}

/* 悬停时避免动画冲突 */
.center-bubble:hover {
    transform: none; 
}

/* 小气泡的呼吸动画 - 三种振幅 */
/* 小振幅 */
@keyframes pulse-sm {
    0% { transform: scale(1); }
    50% { transform: scale(1.02); } 
    100% { transform: scale(1); }
}
/* 中振幅 */
@keyframes pulse-md {
    0% { transform: scale(1); }
    50% { transform: scale(1.10); }
    100% { transform: scale(1); }
}
/* 大振幅 */
@keyframes pulse-lg {
    0% { transform: scale(1); }
    50% { transform: scale(1.16); }
    100% { transform: scale(1); }
}

/* 应用于所有非中心气泡 */
.floating-bubble {
    /* 统一设置动画属性，name, duration, delay 由内联 style 和 amplitude class 覆盖 */
    animation-timing-function: ease-in-out;
    animation-iteration-count: infinite;
}

/* 应用不同振幅的动画名称 */
.floating-bubble.pulse-sm { animation-name: pulse-sm; }
.floating-bubble.pulse-md { animation-name: pulse-md; }
.floating-bubble.pulse-lg { animation-name: pulse-lg; }

/* 悬停效果：停止呼吸动画，并应用更明显的放大效果 */
.floating-bubble:hover {
    transform: scale(1.55); 
}


/* 7个小气泡的不规则定位 */
/* --------------------------------------------------- */
.pos-1 { top: 390px; left: 100px; }
.pos-2 { top: 590px; left: 360px;}
.pos-3 { top: 560px; left: 740px; }
.pos-4 { top: 320px; left:820px; }
.pos-5 { top: 90px; left: 940px; }
.pos-6 { top: 50px; left: 580px; }
.pos-7 { top: 145px; left: 280px; }
/* --------------------------------------------------- */

/* ================================================== */
/* 1b. 手机端按钮列表 UI 样式 (max-width: 768px 时显示) */
/* ================================================== */
.domain-button-list {
    display: flex;
    flex-direction: column;
    gap: 15px;
    width: 90%;
    max-width: 500px;
    margin: 40px auto;
    padding: 10px;
}

.domain-button {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    padding: 15px 25px;
    border: none;
    border-radius: 12px;
    color: #fff;
    cursor: pointer;
    font-family: 'Inter', sans-serif;
    transition: transform 0.2s ease, box-shadow 0.2s ease;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
    text-align: left;
}

.domain-button:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 15px rgba(0, 0, 0, 0.3);
}

.button-icon {
    font-size: 30px;
    margin-right: 15px;
}

.button-cn {
    font-size: 18px;
    font-weight: bold;
    flex-grow: 1; /* CN text takes up space */
}

.button-en {
    font-size: 14px;
    opacity: 0.8;
}

/* ================================================== */
/* 2. AI 问答机器人 UI 样式 */
/* ================================================== */
.dify-chat-area {
  min-height: 700px; 
  border: 1px solid #DCDFE6; 
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  overflow: hidden; 
  display: flex; 
}

.dify-iframe-embed {
  width: 100%;
  height: 100%;
  min-height: 700px;
  display: block;
}

/* ================================================== */
/* 响应式调整 (仅针对气泡容器缩放，按钮列表结构已通过 v-if/v-else 切换) */
/* ================================================== */
@media (max-width: 1024px) {
    /* 1024px 以下，平板视图，气泡稍微缩小 */
    .bubble-field-container {
        width: 700px;
        height: 700px;
        transform: scale(0.85);
    }
}
@media (max-width: 768px) {
    .bubble-visualization {
        /* 手机视图下，气泡图容器不再需要最小高度或特定缩放，因为内容是按钮列表 */
        min-height: auto; 
        padding-top: 20px;
    }
    /* 气泡相关样式在 isMobile 模式下不生效 */
}
</style>
