<template>
  <div class="team-container">
    
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

    <h1 class="page-title">团队介绍 (Team Introduction)</h1>
    <p class="page-subtitle">简介</p>

    <!-- 实验室简介内容卡片 -->
    <el-row :gutter="30" class="intro-row">
      <el-col :span="24">
        <el-card shadow="always" class="intro-card">
          <!-- 内容区域 -->
          <div class="intro-content">
            <p class="intro-paragraph">
              “辽望”大模型—矿山安全开采智能决策系统由辽宁大学与辽宁鑫丰矿业（集团）有限公司共同开发，是深度合作的重大产学研成果。
            </p>
            <p class="intro-paragraph">
              辽宁鑫丰矿业（集团）有限公司，作为一家集产品研发、设备制造、工程服务、煤矿托管于一体的创新型高新技术企业，是本项目的实践主体和数据来源核心。集团拥有 800 余名专业人才，通过了 ISO 质量管理体系认证，荣获“辽宁省级企业技术中心”等多项荣誉，更在全国有轨安装领域位居榜首。鑫丰集团为联合团队提供了 15 年积累的海量、真实、高质量的煤矿工业数据，以及对煤炭行业安全管理和设备运行的深刻理解，确保了平台决策结果的工程可行性与实际应用价值。
            </p>
            <p class="intro-paragraph last-paragraph">
              辽宁大学作为国家“ 211 工程”重点建设院校，依托其在应用经济学、统计学、信息科学与技术等学科的优势，为“智能决策平台”的内核提供了坚实的理论基础和前沿算法支持。团队成功将复杂的煤矿场景问题转化为精确的决策优化模型，设计和构建了系统的核心算法、风险评估体系和数据可视化框架，确保平台具备高精度、高稳定性的数据处理和决策能力。
            </p>
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
  name: 'TeamIntroduction',
  data() {
    return {
      // Data is minimal as content is static
    }
  },
  computed: {
    /**
     * 根据当前路由路径 (this.$route.path) 动态生成面包屑数组。
     */
    breadcrumbs() {
      // 模拟当前页面的路径。
      const currentPath = this.$route ? this.$route.path : '/dashboard/team';

      let paths = [
        // 首页总是第一项，且可跳转
        { name: '首页 (Home)', path: '/dashboard/home' },
      ];

      // 根据当前路径判断并添加第二级导航
      if (currentPath.includes('/about')) {
        // 团队介绍页面
        paths.push({ name: '团队介绍 (Team Introduction)', path: '/dashboard/team' });
      } 
      
      return paths;
    }
  },
  methods: {
    // No specific methods needed for this static page for now
  }
};
</script>

<style scoped>
.team-container {
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

.intro-card {
    border-radius: 10px;
}

/* 覆盖 el-card header 样式 */
.intro-card >>> .el-card__header {
    border-bottom: 2px solid #EBEEF5;
}

.intro-content {
    padding: 10px 20px;
    line-height: 1.8; /* 提高行高，增强可读性 */
}

.intro-paragraph {
    font-size: 16px;
    color: #303133;
    text-indent: 2em; /* 首行缩进 */
    margin-bottom: 15px;
    text-align: justify; /* 文本两端对齐 */
}

.intro-paragraph.last-paragraph {
    margin-bottom: 0;
}
</style>
