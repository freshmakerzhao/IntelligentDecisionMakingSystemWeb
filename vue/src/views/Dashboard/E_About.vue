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
    <p class="page-subtitle">国家矿山安全重点实验室简介</p>

    <!-- 实验室简介内容卡片 -->
    <el-row :gutter="30" class="intro-row">
      <el-col :span="24">
        <el-card shadow="always" class="intro-card">
          
          <!-- 栏目标题，突出显示 -->
          <div slot="header" class="card-header-title">
            <i class="el-icon-school"></i> 实验室简介 (Laboratory Introduction)
          </div>
          
          <!-- 内容区域 -->
          <div class="intro-content">
            <p class="intro-paragraph">
              煤矿冲击地压机理与防控技术国家矿山安全监察局重点实验室以辽宁大学为牵头单位，依托辽宁大学、辽宁工程技术大学、中国中煤能源集团有限公司、煤炭科学技术研究院有限公司4家单位共同建设，实验室于2024年经国家矿山安全监察局批准成立，是国家矿山安全监察局批准建设的第二批重点实验室之一。
            </p>
            <p class="intro-paragraph">
              实验室实行依托单位领导下的主任负责制，设立学术委员会作为实验室的学术指导机构。实验室的研究方向包括：冲击地压灾害岩体力学基础理论研究、冲击地压灾害微震电荷监测大数据关键技术与装备、本煤层自解放保护层开采防突关键技术、采场全域协同自适应液压支护防治冲击地压动力灾害关键技术与装备、冲击地压复合动力灾害一体化预测与防治关键技术与装备、冲击地压煤层智能化开采关键技术与装备。
            </p>
            <p class="intro-paragraph last-paragraph">
              冲击地压监测预警实验室面向世界科技前沿和国家防灾减灾重大需求，以“问题是创新的起点，也是创新的动力源”为核心理念，坚持社会实现场景真实问题，紧密结合矿山安全监测预警难题，应用大数据、人工智能、云计算等高新技术，突破矿震、微震、地音、应力、电荷和钻屑六大监测方法基础理论、设备研发与现场应用关键技术创新，为煤矿冲击地压监测预警提供解决方案，切实提升我国煤矿冲击地压监测预警水平，有效预防和遏制冲击地压事故。
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

.card-header-title {
    font-size: 24px;
    font-weight: 700;
    color: #0a3a6b; /* 使用深蓝色突出标题 */
}

.card-header-title i {
    margin-right: 10px;
    font-size: 26px;
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
