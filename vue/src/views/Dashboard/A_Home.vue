<template>
  <div class="dashboard-container">
    <!-- 安全指标概览 (KPIs 四宫格) -->
    <el-row :gutter="20" class="data-overview">
      <el-col :xs="12" :sm="6" v-for="(stat, index) in stats" :key="index">
        <el-card shadow="hover" class="stat-card" :class="`stat-card-${index}`" :body-style="{ padding: '20px' }">
          <div class="stat-value">{{ stat.value }}</div>
          <div class="stat-label">{{ stat.label }}</div>
          <div class="stat-icon"><i :class="stat.icon"></i></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 主内容区：实时监测与风险分析 -->
    <el-row :gutter="20" class="main-content-row">
      
      <!-- 左侧：累计案例统计图表 (桌面端占 16 份宽度，移动端占 24 份) -->
      <el-col :xs="24" :lg="16">
        <el-card class="chart-card" :body-style="{ padding: '20px' }">
          <div class="card-header">
            <span>累计案例数 (Cumulative Cases)</span>
          </div>
          
          <!-- 柱状图容器 -->
          <div class="bar-chart-container">
              <!-- 循环渲染 7 个领域的累计案例 -->
              <div v-for="(data, index) in cumulativeCaseData" :key="index" class="bar-item">
                  <div class="bar-label">{{ data.name }}</div>
                  <div class="bar-wrapper">
                      <!-- 柱子：宽度根据案例数占最大案例数的比例计算 -->
                      <div 
                          class="bar" 
                          :style="{ 
                              width: (data.cases / maxCases) * 100 + '%', 
                              backgroundColor: data.color 
                          }"
                      ></div>
                      <!-- 案例数字 -->
                      <span class="case-count">{{ data.cases }}</span>
                  </div>
              </div>
          </div>
        </el-card>
      </el-col>

      
      <!-- 右侧：关键设备健康度 (桌面端占 8 份宽度，移动端占 24 份) -->
      <el-col :xs="24" :lg="8">
        <el-card class="text-card" :body-style="{ padding: '20px' }">
          <div class="card-header">
            <span>关键设备健康度 (Health Status)</span>
          </div>
          <div class="text-content">
            <!-- 仅保留设备健康度进度条，并使用循环渲染 -->
            <div v-for="(item, index) in equipmentHealth" :key="index" class="health-item-wrapper">
                <el-row>
                  <el-col :span="4"><div class="health-item-label">{{ item.name }}</div></el-col>
                  <el-col :span="20"><el-progress 
                    class="health-item-progress"
                    :percentage="item.percentage" 
                    :color="item.color" 
                    :stroke-width="12"
                ></el-progress></el-col>
                </el-row>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <!-- 最新公告与动态区域 -->
    <el-row :gutter="20" class="news-section-row">
        <el-col :span="24">
            <el-card class="news-card" :body-style="{ padding: '20px' }">
                <div class="card-header">
                    <span>系统公告与技术支持 (System Announcements)</span>
                </div>
                <el-collapse v-model="activeNames" accordion>
                    <el-collapse-item title="【重要】系统 3.1 版本更新通知 - 优化预警算法" name="1">
                        <div>新版本对预警模型的灵敏度和准确性进行了提升，并增强了移动端访问的稳定性。</div>
                    </el-collapse-item>
                    <el-collapse-item title="高危区域视频监控接入指南" name="2">
                        <div>为保障开采安全，请各部门按照最新指南完成所有高危区域视频流的接入配置。</div>
                    </el-collapse-item>
                    <el-collapse-item title="平台年度数据备份与恢复演练通知" name="3">
                        <div>演练将于 11 月 10 日进行，请数据管理员提前准备相关资料。</div>
                    </el-collapse-item>
                </el-collapse>
            </el-card>
        </el-col>
    </el-row>

    <!-- 确保内容底部有一定的间距 -->
    <div class="bottom-spacer"></div>

  </div>
</template>

<script>
export default {
  name: 'Dashboard',
  data() {
    return {
      stats: [
        { label: '总案例数量 (Total Cases)', value: '1,500', icon: 'el-icon-s-order' },
        { label: '总煤矿数量 (Total Mines)', value: '345', icon: 'el-icon-office-building' },
        { label: '平均设备健康度 (Avg. Health)', value: '88%', icon: 'el-icon-setting' },
        { label: '累计安全天数 (Safety Days)', value: '689', icon: 'el-icon-magic-stick' }
      ],
      activeNames: ['1'], // 默认展开第一个折叠面板
      
      // 关键设备健康度数据 (已根据您的最新请求扩充)
      equipmentHealth: [
        { name: '采煤', percentage: 92, color: '#909399' },
        { name: '掘进', percentage: 85, color: '#F56C6C' },
        { name: '安装回撤', percentage: 78, color: '#E6A23C' },
        { name: '辅助运输', percentage: 92, color: '#E44D26' },
        { name: '机电', percentage: 85, color: '#67C23A' },
        { name: '通风', percentage: 95, color: '#6F42C1' },
        { name: '应急救援', percentage: 88, color: '#409EFF' }
      ]
    }
  },
  computed: {
    /**
     * 计算累计案例数据，用于柱状图。
     * 数据固定用于演示。
     */
    cumulativeCaseData() {
        // 定义 7 个领域及其模拟案例数据
        const rawData = [
            { name: '采煤', color: '#909399', cases: 350 },
            { name: '掘进', color: '#F56C6C', cases: 280 },
            { name: '安装回撤', color: '#E6A23C', cases: 420 },
            { name: '辅助运输', color: '#E44D26', cases: 120 },
            { name: '机电', color: '#67C23A', cases: 180 },
            { name: '通风', color: '#6F42C1', cases: 90 },
            { name: '应急救援', color: '#409EFF', cases: 60 },
        ];
        return rawData;
    },
    
    /**
     * 获取最大案例数，用于计算柱状图的百分比宽度。
     */
    maxCases() {
        // 确保数据不为空
        if (this.cumulativeCaseData.length === 0) return 1;
        return Math.max(...this.cumulativeCaseData.map(d => d.cases));
    }
  }
};
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
  max-width: 1600px; /* 适当增加最大宽度以容纳更多信息 */
  margin: 0 auto;
  font-family: 'Inter', sans-serif;
}

.page-title {
  font-size: 30px;
  font-weight: 700;
  color: #303133;
  margin-bottom: 5px;
}

.page-subtitle {
    font-size: 16px;
    color: #909399;
    margin-bottom: 30px;
}

/* --- 数据概览卡片样式 --- */
.data-overview {
    margin-bottom: 30px;
}
.stat-card {
    border-radius: 12px;
    text-align: left;
    position: relative;
    overflow: hidden;
    transition: all 0.3s ease;
}
.stat-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}

.stat-card-0 { border-left: 5px solid #F56C6C; } /* 红色 - 风险 */
.stat-card-1 { border-left: 5px solid #67C23A; } /* 绿色 - 安全天数 */
.stat-card-2 { border-left: 5px solid #409EFF; } /* 蓝色 - 设备 */
.stat-card-3 { border-left: 5px solid #E6A23C; } /* 黄色 - 预警 */


.stat-value {
    font-size: 36px;
    font-weight: 900;
    color: #303133;
    line-height: 1.2;
}
.stat-label {
    font-size: 14px;
    color: #909399;
    margin-top: 5px;
}
.stat-icon {
    position: absolute;
    top: 20px;
    right: 20px;
    font-size: 48px;
    color: rgba(0, 0, 0, 0.05);
}

/* --- 主内容分栏样式 --- */
.main-content-row {
    margin-bottom: 30px;
}
.chart-card, .text-card {
    border-radius: 12px;
    min-height: 420px; /* 统一高度，保持美观 */
}

.card-header {
    font-size: 18px;
    font-weight: 700;
    color: #303133;
    padding-bottom: 15px;
    margin-bottom: 15px;
    border-bottom: 1px solid #EBEEF5;
}

/* 图表占位符样式 */
.chart-placeholder {
    /* 移除旧的占位符背景和居中，让图表容器控制 */
    font-size: 14px;
    padding: 20px 0; 
    position: relative;
    height: auto;
    min-height: 350px;
}

/* --- 累计案例柱状图样式 --- */
.bar-chart-container {
  display: flex;
  flex-direction: column;
  gap: 15px; /* 增加间距 */
  padding: 10px 0;
  min-height: 300px; /* 确保图表区域有足够的空间 */
  justify-content: center; /* 垂直居中 */
}

.bar-item {
  display: flex;
  align-items: center;
  font-size: 13px;
  color: #606266;
}

.bar-label {
  /* 领域名称的固定宽度 */
  width: 130px; 
  flex-shrink: 0;
  text-align: right;
  padding-right: 20px;
  font-weight: 500;
}

.bar-wrapper {
  flex-grow: 1;
  display: flex;
  align-items: center;
  /* 限制最大宽度，防止条形图过长 */
  max-width: calc(100% - 150px); 
}

.bar {
  height: 22px; /* 增加高度 */
  border-radius: 4px;
  /* 增加过渡动画，使 bar 长度变化更平滑 */
  transition: width 1s cubic-bezier(0.25, 0.8, 0.5, 1); 
  margin-right: 15px;
  box-shadow: 0 1px 3px rgba(0,0,0,0.2);
}

.case-count {
  font-weight: bold;
  color: #303133;
  min-width: 30px;
  font-size: 14px;
}

/* --- 关键设备健康度样式 --- */
.health-item-wrapper {
    margin-bottom: 27px; /* 增加每个项目之间的间距 */
}

.health-item-label {
    font-size: 13px;
    color: #606266;
}

/* --- 文字介绍/公告样式 --- */
.news-section-row {
    margin-bottom: 30px;
}
.news-card {
    border-radius: 12px;
}
.el-collapse-item div {
    line-height: 1.6;
    color: #606266;
    font-size: 14px;
}

/* 底部留白，确保底部内容不会紧贴页脚 */
.bottom-spacer {
    height: 10px;
}


/* 响应式调整 */
@media (max-width: 992px) {
    .dashboard-container {
      padding: 10px;
    }
    /* 修正：使用实际的标题类名 */
    .page-title {
      font-size: 24px;
    }
    /* 修正：使用实际的副标题类名 */
    .page-subtitle {
      font-size: 14px;
    }
    .chart-card {
      margin-bottom: 20px; 
    }
    .stat-value {
      font-size: 28px;
    }
    .stat-icon {
      font-size: 36px;
  }
}
</style>

