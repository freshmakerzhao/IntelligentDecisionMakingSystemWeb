<template>
  <div class="case-studies-container">
    
    <!-- 面包屑导航区域 (已改为动态渲染) -->
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

    <h1 class="page-title">优秀项目案例介绍 (Featured Case Studies)</h1>
    <p class="page-subtitle">智能决策系统在不同矿区的应用成果展示。</p>

    <!-- 案例卡片区域，四列布局 -->
    <el-row :gutter="30" class="case-cards-row">
      
      <!-- 循环生成四个案例栏目 -->
      <!-- lg=6 表示在大屏幕上占 1/4 宽度，sm=12 表示在中小屏幕上占 1/2，xs=24 表示在手机端占满 -->
      <el-col :xs="24" :sm="12" :lg="6" v-for="(category, index) in caseCategories" :key="index" class="case-col">
        <el-card shadow="hover" class="case-card">
          
          <!-- 栏目标题，突出显示 -->
          <div slot="header" class="card-header">
            <i :class="category.icon"></i>
            <span>{{ category.title }}</span>
          </div>
          
          <!-- 案例列表 -->
          <ul class="case-list">
            <li v-for="(caseItem, i) in category.cases" :key="i" class="case-item">
              <!-- 使用 el-link 实现可点击的标题 -->
              <el-link type="_blank" :underline="false" @click="viewCaseDetail(caseItem)">
                {{ caseItem.title }}
              </el-link>
              <span class="case-date">{{ caseItem.date }}</span>
            </li>
          </ul>

          <!-- 查看更多按钮 -->
          <div class="view-more-footer">
            <el-button type="text" @click="viewMore(category)">
              显示更多案例 <i class="el-icon-arrow-right"></i>
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
  name: 'CaseStudies',
  data() {
    return {
      // 案例分类数据
      caseCategories: [
        {
          title: '瓦斯防治与监测',
          icon: 'el-icon-wind-power',
          cases: [
            { title: '高瓦斯矿井抽采优化项目', date: '2024-11' },
            { title: '5G 融合实时监测平台应用', date: '2024-09' },
            { title: '掘进面瓦斯异常预测与预警', date: '2024-07' },
            { title: '区域防突措施智能评估', date: '2023-12' },
          ]
        },
        {
          title: '地压与支护优化',
          icon: 'el-icon-place',
          cases: [
            { title: '深部开采地压显现规律分析', date: '2025-01' },
            { title: '液压支架姿态智能调控系统', date: '2024-10' },
            { title: '采动影响下巷道稳定性控制', date: '2024-06' },
            { title: '复合锚杆支护参数设计案例', date: '2023-11' },
          ]
        },
        {
          title: '水害防治与排水',
          icon: 'el-icon-water-and-fire',
          cases: [
            { title: '突水水源智能识别及定位', date: '2024-12' },
            { title: '高精度水文地质建模应用', date: '2024-08' },
            { title: '井下大功率排水泵站集群调度', date: '2024-05' },
            { title: '采空区积水状态监测与评估', date: '2023-10' },
          ]
        },
        {
          title: '综合安全管理',
          icon: 'el-icon-s-operation',
          cases: [
            { title: '人员精准定位与轨迹跟踪', date: '2025-02' },
            { title: '关键设备全生命周期预测维护', date: '2024-11' },
            { title: '协同决策与应急指挥平台', date: '2024-09' },
            { title: 'AI 驱动的违规行为识别系统', date: '2024-04' },
          ]
        },
      ]
    }
  },
  computed: {
    /**
     * 根据当前路由路径 (this.$route.path) 动态生成面包屑数组。
     * 在实际应用中，通常会遍历 this.$route.matched 数组，并从路由的 meta 字段中提取 breadcrumbName。
     */
    breadcrumbs() {
      // 模拟当前页面的路径。在实际应用中，this.$route.path 会自动反映当前 URL。
      const currentPath = this.$route ? this.$route.path : '/dashboard/cases';

      let paths = [
        // 首页总是第一项，且可跳转
        { name: '首页 (Home)', path: '/dashboard/home' },
      ];

      // 根据当前路径判断并添加第二级导航
      if (currentPath.includes('/cases')) {
        // 项目案例页面
        paths.push({ name: '项目案例 (Case Studies)', path: '/dashboard/cases' });
        // 当前页面的标题作为最后一项，通常不可跳转 (path: '')
      } 
      // 您可以根据需要继续添加其他路径的判断逻辑，例如 /dashboard/faq, /dashboard/model 等
      /*
      else if (currentPath.includes('/faq')) {
          paths.push({ name: '常见问题 (FAQ)', path: '/dashboard/faq' });
      }
      */
      
      return paths;
    }
  },
  methods: {
    // 模拟跳转到案例详情
    viewCaseDetail(caseItem) {
      console.log('查看案例详情:', caseItem.title);
      this.$message.info(`即将跳转到 "${caseItem.title}" 详情页`);
    },
    // 模拟跳转到更多案例列表
    viewMore(category) {
      console.log('查看更多分类案例:', category.title);
      this.$message.success(`正在加载 "${category.title}" 的全部案例...`);
    }
  }
};
</script>

<style scoped>
.case-studies-container {
  padding: 30px;
  max-width: 1600px; 
  margin: 0 auto;
}

/* 新增面包屑容器样式 */
.breadcrumb-container {
    padding: 10px 0 20px 0;
    border-bottom: 1px solid #EBEEF5;
    margin-bottom: 20px;
}

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

.case-cards-row {
    margin-left: -15px !important;
    margin-right: -15px !important;
}

.case-col {
    margin-bottom: 30px;
}

.case-card {
    border-radius: 10px;
    height: 100%; /* 确保卡片等高 */
    display: flex;
    flex-direction: column;
}

/* 覆盖 el-card body，使其能够垂直 flex */
.case-card >>> .el-card__body {
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

.case-list {
    list-style: none;
    padding: 10px 0;
    margin: 0;
    flex-grow: 1; /* 占据中间所有空间 */
}

.case-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 0;
    border-bottom: 1px dashed #EBEEF5;
    font-size: 14px;
}

.case-item:last-child {
    border-bottom: none;
}

.case-date {
    color: #909399;
}

.view-more-footer {
    padding: 15px 0 5px 0;
    text-align: right;
    border-top: 1px solid #EBEEF5;
}
</style>
