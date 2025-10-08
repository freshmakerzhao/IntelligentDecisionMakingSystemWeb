<template>
  <div class="faq-container">
    
    <!-- 面包屑导航区域 (动态渲染) -->
    <div class="breadcrumb-container">
      <el-breadcrumb separator="/">
        <!-- 使用 v-for 动态渲染面包屑项 -->
        <el-breadcrumb-item 
          v-for="(item, index) in breadcrumbs" 
          :key="index" 
          :to="item.path ? { path: item.path } : null"
        >
          {{ item.name }}
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <h1 class="page-title">常见问题与解答 (Frequently Asked Questions)</h1>
    <p class="page-subtitle">关于系统部署、功能和维护的常见疑问解答。</p>

    <!-- FAQ 卡片区域，四列布局 -->
    <el-row :gutter="30" class="faq-cards-row">
      
      <!-- 循环生成四个 FAQ 栏目 -->
      <el-col :xs="24" :sm="12" :lg="6" v-for="(category, index) in faqCategories" :key="index" class="faq-col">
        <el-card shadow="hover" class="faq-card">
          
          <!-- 栏目标题，突出显示 -->
          <div slot="header" class="card-header">
            <i :class="category.icon"></i>
            <span>{{ category.title }}</span>
          </div>
          
          <!-- 问题列表 -->
          <ul class="faq-list">
            <li v-for="(question, i) in category.questions" :key="i" class="faq-item">
              <!-- 使用 el-link 实现可点击的问题标题，模拟查看答案 -->
              <el-link type="primary" :underline="false" @click="showAnswer(question)">
                {{ question.title }}
              </el-link>
            </li>
          </ul>

          <!-- 查看更多按钮 -->
          <div class="view-more-footer">
            <el-button type="text" @click="viewMore(category)">
              查看更多问题 <i class="el-icon-arrow-right"></i>
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 底部留白 -->
    <div style="height: 40px;"></div>
  </div>
</template>

<script>
export default {
  name: 'FAQ',
  data() {
    return {
      // 常见问题分类数据
      faqCategories: [
        {
          title: '系统概览与功能',
          icon: 'el-icon-info',
          questions: [
            { title: '智能决策系统是什么？', answer: '它是一个基于AI和大数据技术的平台，用于实时分析矿山安全数据，并为安全开采提供智能预警和决策建议。' },
            { title: '系统主要功能有哪些？', answer: '包括实时监测、风险评估、应急预案生成、预测性维护和历史数据分析。' },
            { title: '系统支持哪些类型的矿山？', answer: '主要支持煤矿、金属矿和非金属矿的地下开采场景。' },
            { title: '系统与传统系统的区别？', answer: '最大的区别在于其预测性和决策能力，而非仅仅是数据记录和报警。' },
          ]
        },
        {
          title: '技术要求与部署',
          icon: 'el-icon-setting',
          questions: [
            { title: '部署需要多长时间？', answer: '根据矿山规模和现有基础设施，通常需要4到8周。' },
            { title: '需要哪些硬件支持？', answer: '需要高性能服务器进行AI模型训练和数据存储，以及兼容的传感器网络。' },
            { title: '是否支持云端部署？', answer: '支持。我们提供私有云和混合云的部署方案以满足不同安全需求。' },
            { title: '数据安全如何保障？', answer: '系统采用多层加密和访问控制，确保所有矿山数据安全隔离。' },
          ]
        },
        {
          title: '数据与模型维护',
          icon: 'el-icon-cpu',
          questions: [
            { title: '系统如何处理数据缺失？', answer: '使用先进的插值算法和模型平滑技术，确保监测连续性。' },
            { title: 'AI 模型多久更新一次？', answer: '模型会根据新采集的数据进行季度微调，重大升级每年进行一次。' },
            { title: '是否可以接入旧的传感器？', answer: '可以，系统设计了灵活的API和数据适配层，支持主流协议和旧设备。' },
            { title: '预测准确率如何？', answer: '核心风险预测的准确率在95%以上，具体取决于数据质量。' },
          ]
        },
        {
          title: '服务与支持',
          icon: 'el-icon-service',
          questions: [
            { title: '系统提供哪些售后服务？', answer: '提供7x24小时远程支持、定期现场巡检和操作培训服务。' },
            { title: '如何进行人员培训？', answer: '提供线上视频教程、现场实操指导和认证课程。' },
            { title: '故障响应时间是多久？', answer: '一级故障（系统宕机）响应时间不超过15分钟。' },
            { title: '我可以定制功能吗？', answer: '可以，我们提供个性化定制服务，以满足矿山特定的安全需求。' },
          ]
        },
      ]
    }
  },
  computed: {
    /**
     * 根据当前路由路径 (this.$route.path) 动态生成面包屑数组。
     */
    breadcrumbs() {
      // 模拟当前页面的路径。
      const currentPath = this.$route ? this.$route.path : '/dashboard/faq';

      let paths = [
        // 首页总是第一项，且可跳转
        { name: '首页 (Home)', path: '/dashboard/home' },
      ];

      // 根据当前路径判断并添加第二级导航
      if (currentPath.includes('/faq')) {
        // 常见问题页面
        paths.push({ name: '常见问题 (FAQ)', path: '/dashboard/faq' });
      } 
      
      return paths;
    }
  },
  methods: {
    // 模拟显示问题答案，使用 Element UI 的消息提示
    showAnswer(question) {
      console.log('查看答案:', question.title);
      // 注意：这里使用 this.$alert 可以在 iframe 环境中显示模态框
      this.$alert(question.answer, `【${question.title}】的解答`, {
        confirmButtonText: '确定',
        type: 'info'
      });
    },
    // 模拟跳转到更多问题列表
    viewMore(category) {
      console.log('查看更多分类问题:', category.title);
      this.$message.success(`正在加载 "${category.title}" 的更多问题...`);
    }
  }
};
</script>

<style scoped>
/* 容器样式保持一致 */
.faq-container {
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

.faq-cards-row {
    margin-left: -15px !important;
    margin-right: -15px !important;
}

.faq-col {
    margin-bottom: 30px;
}

.faq-card {
    border-radius: 10px;
    height: 100%; /* 确保卡片等高 */
    display: flex;
    flex-direction: column;
}

/* 覆盖 el-card body，使其能够垂直 flex */
.faq-card >>> .el-card__body {
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    padding: 0 20px 0 20px; /* 移除垂直 padding，保留水平 padding */
}

.card-header {
    font-size: 20px;
    font-weight: 700;
    color: #409EFF; /* 使用主题色突出标题 */
    padding: 10px 0;
}

.card-header i {
    margin-right: 10px;
    font-size: 22px;
}

.faq-list {
    list-style: none;
    padding: 10px 0;
    margin: 0;
    flex-grow: 1; /* 占据中间所有空间 */
}

.faq-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 0;
    border-bottom: 1px dashed #EBEEF5;
    font-size: 14px;
}

.faq-item:last-child {
    border-bottom: none;
}

/* 这里我们不需要日期，但保持 flex 结构 */
.faq-item .el-link {
    font-weight: 500;
    color: #303133; /* 正常问题颜色 */
}

.faq-item .el-link:hover {
    color: #409EFF; /* 悬停颜色 */
}

.view-more-footer {
    padding: 15px 0 5px 0;
    text-align: right;
    border-top: 1px solid #EBEEF5;
}
</style>
