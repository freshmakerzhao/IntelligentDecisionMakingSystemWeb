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
      
      <!-- 左侧：实时监测数据图表 (桌面端占 16 份宽度，移动端占 24 份) -->
      <el-col :xs="24" :lg="16">
        <el-card class="chart-card" :body-style="{ padding: '20px' }">
          <div class="card-header">
            <span>甲烷浓度实时监测趋势 (Methane Concentration Trend)</span>
          </div>
          <!-- 模拟图表容器 -->
          <div class="chart-placeholder">
            <div class="mock-chart-line-container">
                <svg width="100%" height="100%" viewBox="0 0 400 150" preserveAspectRatio="none">
                    <polyline points="0,140 50,110 100,100 150,80 200,60 250,90 300,70 350,50 400,30"
                              fill="none" stroke="#409EFF" stroke-width="3" stroke-linecap="round"/>
                    <text x="360" y="30" fill="#E6A23C" font-size="12">↑ 0.8%</text>
                    <line x1="0" y1="140" x2="400" y2="140" stroke="#EBEEF5" stroke-width="1"/>
                </svg>
            </div>
          </div>
        </el-card>
      </el-col>

      <!-- 右侧：智能决策建议与健康度 (桌面端占 8 份宽度，移动端占 24 份) -->
      <el-col :xs="24" :lg="8">
        <el-card class="text-card" :body-style="{ padding: '20px' }">
          <div class="card-header">
            <span>实时风险与决策建议 (Risk & Decision Advice)</span>
          </div>
          <div class="text-content">
            <el-alert
                title="当前风险等级：低"
                type="success"
                description="系统评估井下环境稳定，无重大风险源触发，保持正常开采作业。"
                show-icon
                :closable="false"
                style="margin-bottom: 15px;"
            />
            <h4 style="color: #606266; margin-top: 10px;">关键设备健康度分析：</h4>
            <el-progress :percentage="92" :format="() => '通风系统'" color="#67C23A"></el-progress>
            <el-progress :percentage="85" :format="() => '提升设备'" color="#E6A23C"></el-progress>
            <el-progress :percentage="78" :format="() => '排水泵站'" color="#F56C6C"></el-progress>
            
            <el-button type="warning" size="medium" style="margin-top: 20px;" icon="el-icon-s-promotion">查看全部决策报告</el-button>
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
                    <el-collapse-item title="【重要】系统 $3.1$ 版本更新通知 - 优化预警算法" name="1">
                        <div>新版本对预警模型的灵敏度和准确性进行了提升，并增强了移动端访问的稳定性。</div>
                    </el-collapse-item>
                    <el-collapse-item title="高危区域视频监控接入指南" name="2">
                        <div>为保障开采安全，请各部门按照最新指南完成所有高危区域视频流的接入配置。</div>
                    </el-collapse-item>
                    <el-collapse-item title="平台年度数据备份与恢复演练通知" name="3">
                        <div>演练将于 $11$ 月 $10$ 日进行，请数据管理员提前准备相关资料。</div>
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
      // 模拟统计数据
      stats: [
        { label: '实时风险指数 (RRI)', value: '1.25', icon: 'el-icon-warning-outline' },
        { label: '累计安全天数 (CSD)', value: '689', icon: 'el-icon-magic-stick' },
        { label: '设备健康度 (EHS)', value: '88%', icon: 'el-icon-setting' },
        { label: '预警事件总数 (TEA)', value: '18', icon: 'el-icon-bell' }
      ],
      activeNames: ['1'] // 默认展开第一个折叠面板
    }
  },
};
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
  max-width: 1400px; /* 适当增加最大宽度以容纳更多信息 */
  margin: 0 auto;
}

.main-title {
  font-size: 34px;
  font-weight: 800;
  color: #0a3a6b;
  text-align: center;
  margin-bottom: 5px;
}

.subtitle {
    font-size: 16px;
    color: #909399;
    text-align: center;
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
    height: 300px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    background-color: #f5f7fa;
    border-radius: 8px;
    color: #909399;
    font-size: 14px;
    padding: 20px;
    position: relative;
}

/* 模拟折线图样式 */
.mock-chart-line-container {
    width: 95%;
    height: 100%;
    margin-top: 15px;
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
    .main-title {
        font-size: 24px;
    }
    .subtitle {
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
