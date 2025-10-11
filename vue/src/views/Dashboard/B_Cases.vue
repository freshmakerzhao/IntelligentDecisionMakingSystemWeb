<template>
  <div class="case-studies-container">
    
    <!-- 面包屑导航区域 -->
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
    <p class="page-subtitle"></p>

    <!-- 第一行：4个案例卡片 (lg=6 占据 1/4 宽度) -->
    <el-row :gutter="30" class="case-cards-row">
      <el-col 
        :xs="24" :sm="12" :lg="6" 
        v-for="(category, index) in caseCategories.slice(0, 4)" 
        :key="'row1-' + index" 
        class="case-col"
      >
        <el-card shadow="hover" class="case-card">
          
          <!-- 栏目标题：拆分成中英文两行显示 -->
          <div slot="header" class="card-header two-line-header">
            <div class="title-wrapper">
              <span class="zh-title">{{ category.title_zh }}</span>
              <br/>
              <span class="en-subtitle">({{ category.title_en }})</span>
            </div>
          </div>
          
          <!-- 案例列表 -->
          <ul class="case-list">
            <li v-for="(caseItem, i) in category.cases" :key="i" class="case-item">
              <!-- 添加 title 属性，鼠标悬停时显示完整内容 -->
              <!-- 添加 case-title-link 类，用于应用省略号样式 -->
              <el-link 
                type="_blank" 
                :underline="false" 
                @click="viewCaseDetail(caseItem)"
                :title="caseItem.title"
                class="case-title-link"
              >
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

    <!-- 第二行：3个案例卡片 (lg=8 占据 1/3 宽度) -->
    <el-row :gutter="30" class="case-cards-row second-row">
      <el-col 
        :xs="24" :sm="12" :lg="8" 
        v-for="(category, index) in caseCategories.slice(4, 7)" 
        :key="'row2-' + index" 
        class="case-col"
      >
        <el-card shadow="hover" class="case-card">
          
          <!-- 栏目标题：拆分成中英文两行显示 -->
          <div slot="header" class="card-header two-line-header">
            <div class="title-wrapper">
              <span class="zh-title">{{ category.title_zh }}</span>
              <br/>
              <span class="en-subtitle">({{ category.title_en }})</span>
            </div>
          </div>
          
          <ul class="case-list">
            <li v-for="(caseItem, i) in category.cases" :key="i" class="case-item">
              <!-- 添加 title 属性，鼠标悬停时显示完整内容 -->
              <!-- 添加 case-title-link 类，用于应用省略号样式 -->
              <el-link 
                type="_blank" 
                :underline="false" 
                @click="viewCaseDetail(caseItem)"
                :title="caseItem.title"
                class="case-title-link"
              >
                {{ caseItem.title }}
              </el-link>
              <span class="case-date">{{ caseItem.date }}</span>
            </li>
          </ul>

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
      // 案例分类数据 (共 7 个)
      caseCategories: [
        // 1. 采煤 (Row 1, Col 1)
        {
          title_zh: '采煤',
          title_en: 'Coal Mining',
          cases: [
            { title: '铁煤集团内蒙古东林煤炭有限公司W1107综采工作面劈帮、拆除工程', date: '2025-01' },
            { title: '徐州矿务（集团）新疆天山煤业5102工作面回撒工程', date: '2024-11' },
            { title: '徐州矿务集团有限公司三河尖煤矿7441工作面撤除工程', date: '2024-09' },
            { title: '枣庄矿业（集团）付村煤业3上1007工作面设备转到3上1008工作面扩安工程', date: '2024-06' },
          ]
        },
        // 2. 掘进 (Roadway Excavation) (Row 1, Col 2)
        {
          title_zh: '掘进',
          title_en: 'Roadway Excavation',
          cases: [
            { title: '霍州煤电集团三交河矿2-603安装工程', date: '2024-12' },
            { title: '费州林华矿业有限公司20910采面设备回撤及2098采面设备安装工程', date: '2024-10' },
            { title: '铁煤集团东林煤炭公司W1106、105综采工作面安拆工程', date: '2024-08' },
            { title: '平凉新安煤业有限责任公司1203工作面拆除及5202工作面安装工程', date: '2024-05' },
          ]
        },
        // 3. 安装回撤 (Installation and Withdrawal) (Row 1, Col 3)
        {
          title_zh: '安装回撤',
          title_en: 'Installation/Withdrawal',
          cases: [
            { title: '宁夏煤业公司1118上106工作面至118上105工作面搬迁工程', date: '2025-02' },
            { title: '内蒙伊东集团扶贫煤矿6201回撤工程', date: '2024-11' },
            { title: '内蒙伊东集团孙家豪煤炭有限公司1602工作面设备回撤、1604工作面设备安装工程', date: '2024-09' },
            { title: '铁煤集团内蒙古东林煤炭有限公司西一106 综采工作面安装工程', date: '2024-07' },
          ]
        },
        // 4. 辅助运输 (Auxiliary Transportation) (Row 1, Col 4)
        {
          title_zh: '辅助运输',
          title_en: 'Auxiliary Transport',
          cases: [
            { title: '四川川煤集团太平矿3221-21综采工作面撤除工程', date: '2024-12' },
            { title: '神华集团黄白茨矿020901工作面安装工程', date: '2024-10' },
            { title: '霍州煤电集团庞庞塔煤矿9-705工作面回撒工程', date: '2024-08' },
            { title: '徐矿集团百贸沟煤业1505工作面回嫩工程2504工作面安装工程', date: '2024-05' },
          ]
        },
        // --- 第二行 3 个模块 ---
        {
          title_zh: '机电',
          title_en: 'Mechanical/Electrical',
          cases: [
            { title: '淮北矿业股份有限公司海孜煤矿422综采工作面拆除工程', date: '2025-01' },
            { title: '淮北矿业集团童亭煤矿8216综采工作面回撤工程', date: '2024-11' },
            { title: '冀中能源股份有限公司东庞矿21217工作面拆除回撤工程', date: '2024-09' },
            { title: '内蒙宝山宝马矿业公司1601工作面安装工程', date: '2024-06' },
          ]
        },
        // 6. 通风 (Ventilation) (Row 2, Col 2)
        {
          title_zh: '通风',
          title_en: 'Ventilation',
          cases: [
            { title: '内蒙牙克石五九煤炭公司415综采工作面回撤425综采工作面安装工程', date: '2024-12' },
            { title: '内蒙白音华海州露天煤业有限公司1203-2综采工作面回撒工程', date: '2024-10' },
            { title: '内蒙古通大煤业五牧场煤矿主井井简安装工程', date: '2024-07' },
            { title: '空气质量（CO, 粉尘）热点分布分析', date: '2024-05' },
          ]
        },
        // 7. 应急救援 (Emergency Rescue) (Row 2, Col 3)
        {
          title_zh: '应急救援',
          title_en: 'Emergency Rescue',
          cases: [
            { title: '灾害发生后人员精准定位与轨迹跟踪', date: '2025-02' },
            { title: '最优救援路线实时规划与模拟', date: '2024-11' },
            { title: '应急物资库存与调配智能决策', date: '2024-09' },
            { title: '灾情发展趋势预测与疏散评估', date: '2024-06' },
          ]
        },
      ]
    }
  },
  computed: {
    breadcrumbs() {
      // 模拟当前页面的路径。
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
      console.log('查看更多分类案例:', category.title_zh);
      this.$message.success(`正在加载 "${category.title_zh}" 的全部案例...`);
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
/* 特别为第二行添加额外的上边距 */
.case-cards-row.second-row {
    margin-top: 10px; /* 略微增加行间距 */
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
    color: #409EFF; /* 保持主题色 */
    padding: 10px 0;
}
.two-line-header {
    display: flex;
    align-items: center; /* 垂直居中对齐图标和文本块 */
}

.title-wrapper {
    display: inline-block; 
    line-height: 1.1; 
    padding: 5px 0;
}
.zh-title {
    font-size: 20px;
    font-weight: 700;
    color: #409EFF; /* 中文主标题颜色 */
    display: block;
}
.en-subtitle {
    font-size: 14px; /* 英文副标题较小 */
    font-weight: 500;
    color: #909399; /* 英文副标题颜色略暗 */
    display: block;
    margin-top: 2px;
}

.case-list {
    list-style: none;
    padding: 10px 0;
    margin: 0;
    flex-grow: 1; /* 占据中间所有空间 */
}

.case-item {
    display: flex;
    /* 确保标题和日期左右对齐 */
    justify-content: space-between; 
    align-items: center;
    padding: 10px 0;
    border-bottom: 1px dashed #EBEEF5;
    font-size: 14px;
}

.case-item:last-child {
    border-bottom: none;
}

/* --- 案例标题省略号和悬停提示 --- */
.case-title-link {
    /* 强制左对齐容器内的文本 */
    text-align: left; 
    
    /* Flex 布局属性：让链接占据剩余空间 */
    flex: 1;
    min-width: 0; /* 解决 flex 容器中的子元素溢出问题 */
    margin-right: 10px; /* 与日期分隔 */

    /* 截断属性 */
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}

/* 针对 el-link 内部的文本元素应用截断属性，并强制左对齐 */
.case-title-link >>> span {
    /* 确保 span 是一个块级元素，可以完全占据 el-link 的宽度 */
    display: block; 
    /* 强制内部span占据el-link的全部宽度 */
    width: 100%; 
    /* 再次强制内部文本左对齐 */
    text-align: left;
    
    /* 截断属性 */
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
/* --- 结束 案例标题省略号和悬停提示 --- */

.case-date {
    /* 确保日期不被挤压，保持固定宽度 */
    flex-shrink: 0; 
    color: #909399;
    font-size: 13px;
    margin-left: 5px;
}

.view-more-footer {
    padding: 15px 0 5px 0;
    text-align: right;
    border-top: 1px solid #EBEEF5;
}

/* 响应式调整 */
@media (max-width: 992px) {
    .page-title {
        font-size: 28px;
    }
    .page-subtitle {
        font-size: 15px;
    }
}
</style>
