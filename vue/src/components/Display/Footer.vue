<template>
  <el-footer class="app-footer">
    <div class="footer-container">
      
      <!-- 顶部区域：Logo + 主标题 + 链接导航 -->
      <div class="footer-top">
        
        <!-- 左侧部分：Logo 和主标题文本 -->
        <div class="logo-wrap">
          <!-- 暂时用logo占位符进行占位 -->
          <img 
            src="https://placehold.co/60x60/ffffff/000000?text=LOGO" 
            alt="Logo" 
            class="logo-img" 
          />
          <!-- Logo旁的信息 -->
          <div class="main-text-group">
            <p class="main-title">中文标题xxxxx</p>
            <p class="english-subtitle">english subtitle</p>
          </div>
        </div>
        
        <!-- 右侧部分：链接项目 -->
        <div class="links-wrap">
          <div class="links-row">
            <div class="links-item" v-for="(link, idx) in links" :key="idx">
              <!-- 根据索引判断是否是“友情链接”标题，应用特殊样式 -->
              <div class="title">{{ link.title }}</div>
              <div class="subtitle">{{ link.subtitle }}</div>
            </div>
          </div>
        </div>
      </div>

      <hr class="divider" />

      <!-- 中部区域：联系信息和右侧小图标 -->
      <div class="footer-middle">
        <div class="contact-info">
            <!-- 联系信息 -->
            <p>地址（Add）：辽宁省沈阳市皇姑区新乐街道崇山中路66号辽宁大学　邮编（Postcode）：110000</p>
            <p>电话（Tel）：1234-123456　E-mail：test@test.com</p>
        </div>
        <!-- 右下角的小图标占位符 ，使用 SVG 模拟 -->
        <div class="middle-icon-wrap">
             <svg width="40" height="40" viewBox="0 0 24 24" fill="#6ba7e1" xmlns="http://www.w3.org/2000/svg">
                <circle cx="12" cy="12" r="10" stroke="#fff" stroke-width="1.5" fill="none"/>
                <path d="M12 16V12" stroke="#fff" stroke-width="1.5" stroke-linecap="round"/>
                <circle cx="12" cy="8" r="1" fill="#fff"/>
            </svg>
        </div>
      </div>

      <!-- 底部区域：版权信息 -->
      <div class="footer-bottom">
        <p>xxxxxxxxxxxxxxxxxxxxxxxxxxxx 版权所有</p>
        <p>Copyright © 2020 zhao All Rights Reserved</p>
        <!-- <p>Copyright © 2020  All Rights Reserved</p> -->
      </div>
    </div>
  </el-footer>
</template>

<script>
export default {
  name: 'AppFooter',
  data() {
    return {
      // 链接数据
      links: [
        { title: '中文title1', subtitle: 'english subtitle1' },
        { title: '中文title2', subtitle: 'english subtitle2' },
        { title: '中文title3', subtitle: 'english subtitle3' },
        { title: '中文title4', subtitle: 'english subtitle4' },
        { title: '中文title5', subtitle: 'english subtitle5' },
        { title: '中文title6', subtitle: 'english subtitle6' }
      ]
    }
  }
}
</script>

<style scoped>
/* 页脚主容器样式 */
.app-footer {
  background-color: #003567;
  color: #fff;
  padding: 40px 20px 20px;
  /* El-footer 默认有高度限制，这里确保内容能完整显示 */
  height: auto !important; 
}
.footer-container {
  max-width: 1200px;
  margin: 0 auto;
}

/* --- 顶部区域样式 (Logo + 链接) --- */
.footer-top {
  display: flex;
  align-items: center; 
  gap: 32px;
  padding-bottom: 20px;
}

/* Logo 和 主标题文本组合 */
.logo-wrap {
  display: flex;
  align-items: center;
  gap: 12px;
  flex-shrink: 0; /* 防止左侧 logo 组被压缩 */
  width: 300px; /* 设定一个合适的宽度，避免链接部分过于拥挤 */
}
.logo-img {
  width: 60px; /* 调整 logo 大小 */
  height: auto;
  display: block;
}
.main-text-group {
  display: flex;
  flex-direction: column;
}
.main-title {
  font-weight: 700;
  font-size: 15px;
  margin: 0;
  line-height: 1.3;
}
.english-subtitle {
  font-size: 11px;
  color: rgba(255, 255, 255, 0.8);
  margin: 0;
  line-height: 1.3;
}

/* 底部links */
.links-wrap {
  flex: 1; /* 占据顶部区域剩余所有空间 */
  min-width: 0; 
}
.links-row {
  display: flex;
  justify-content: space-between; /* 均匀分散所有链接项 */
  align-items: flex-start; 
  flex-wrap: nowrap; /* 默认不换行，保持单行布局 */
}
.links-item {
  flex: 1; /* 每个链接项平分 links-wrap 的空间 */
  text-align: center;
  min-width: 90px;
  padding: 0 5px;
}
.links-item .title {
  font-weight: 700;
  font-size: 16px;
  color: #fff;
  margin-bottom: 4px;
}
.links-item .subtitle {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.7);
  line-height: 1.2;
}

/* 特殊样式，高亮效果 */
.links-item .header-title {
    font-size: 18px; 
    font-weight: 800;
    color: #fceb5c; /* 亮黄色 */
}
.links-item .header-subtitle {
    font-size: 14px;
    color: #fceb5c;
}

/* --- 分割线样式 --- */
.divider {
  border: none;
  border-top: 1px solid rgba(255, 255, 255, 0.2); 
  margin: 10px 0 20px 0;
}

/* --- 中部区域样式 (联系信息) --- */
.footer-middle {
  display: flex;
  justify-content: center; /* 整体居中 */
  align-items: center;
  text-align: center;
  color: rgba(255, 255, 255, 0.95);
  font-size: 14px;
  margin-bottom: 20px;
}
.footer-middle p {
    margin: 4px 0;
}
/* 右侧小图标 */
.middle-icon-wrap {
    margin-left: 30px; 
    flex-shrink: 0;
    opacity: 0.8;
}

/* --- 底部区域样式 (版权) --- */
.footer-bottom {
  text-align: center;
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);
  line-height: 1.6;
}
.footer-bottom p {
    margin: 0;
}


/* --- 响应式布局 --- */
@media (max-width: 1024px) {
    /* 1024px 及以下，顶部内容垂直堆叠 */
    .footer-top { 
        flex-direction: column; 
        gap: 20px;
    }
    .logo-wrap {
        width: 100%;
        justify-content: center; /* 居中 logo 和文字 */
    }
    .links-wrap {
        width: 100%;
    }
    .links-row { 
        justify-content: space-around; /* 链接项之间保持间距 */
        flex-wrap: wrap; /* 允许链接换行 */
        gap: 16px 20px; 
    }
    .links-item {
        min-width: 100px; 
        flex: 0 0 auto; 
    }
}

@media (max-width: 768px) {
    /* 手机端，联系信息也垂直堆叠 */
    .footer-middle {
        flex-direction: column;
        justify-content: center;
        margin-bottom: 15px;
    }
    .middle-icon-wrap {
        margin: 10px 0 0 0; /* 图标放在联系信息下方 */
    }
}
</style>
