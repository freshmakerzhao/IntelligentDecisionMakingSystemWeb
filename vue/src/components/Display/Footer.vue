<template>
  <el-footer class="app-footer">
    <div class="footer-container">
      
      <!-- 顶部区域：合作标题 -->
      <div class="footer-top collaboration-header">
        
        <!-- 左侧伙伴：鑫丰矿业 -->
        <div class="partner-item left-partner">
          <div class="logo-placeholder">
            <img 
              src="../../assets/鑫丰logo.png" 
              alt="鑫丰矿业 Logo" 
              class="logo-img" 
            />
          </div>
          <div class="main-text-group">
            <p class="main-title">辽宁鑫丰矿业（集团）矿山工程有限公司</p>
          </div>
        </div>

        <!-- 分隔符 -->
        <div class="separator">/</div>

        <!-- 右侧伙伴：辽宁大学 -->
        <div class="partner-item right-partner">
          <div class="main-text-group">
            <p class="main-title">辽宁大学</p>
          </div>
          <div class="logo-placeholder">
            <img 
              src="../../assets/辽宁大学logo.jpg" 
              alt="辽宁大学 Logo" 
              class="logo-img" 
            />
          </div>
        </div>
      </div> <!-- /footer-top -->

      <hr class="divider" />

      <!-- 中部区域：联系信息和右侧小图标 -->
      <div class="footer-middle">
        <div class="contact-info">
            <!-- 联系信息 -->
            <p>地址（Add）：辽宁省沈阳市皇姑区新乐街道崇山中路66号辽宁大学　邮编（Postcode）：110000</p>
            <p>电话（Tel）：18500316563　E-mail：dsupermaker@163.com</p>
        </div>
        <!-- 右下角的小图标占位符 ，使用 SVG 模拟 -->
        <div class="middle-icon-wrap">
            <svg width="40" height="40" viewBox="0 0 24 24" fill="#fceb5c" xmlns="http://www.w3.org/2000/svg">
                <circle cx="12" cy="12" r="10" stroke="#fff" stroke-width="1.5" fill="none"/>
                <path d="M12 16V12" stroke="#fff" stroke-width="1.5" stroke-linecap="round"/>
                <circle cx="12" cy="8" r="1" fill="#fff"/>
            </svg>
        </div>
      </div>

      <!-- 底部区域：版权信息 -->
      <div class="footer-bottom">
        <p>辽宁大学 版权所有</p>
        <p>中国沈阳 辽宁大学 Copyright by 辽宁大学 2025</p>
      </div>
    </div>
  </el-footer>
</template>

<script>
export default {
  name: 'AppFooter',
  data() {
    return {
      // 链接数据已移除
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

/* --- 顶部区域：合作标题样式 --- */
.footer-top.collaboration-header {
  display: flex;
  justify-content: center; /* 居中整个合作区域 */
  align-items: center; 
  gap: 32px;
  padding-bottom: 20px;
}

/* 移除原有的 logo-wrap 和 links-wrap 样式，使用新的 partner-item */
.partner-item {
  display: flex;
  align-items: center;
  flex-shrink: 0;
}

/* 左侧伙伴：Logo在左 */
.left-partner {
    gap: 12px;
}
/* 右侧伙伴：Logo在右 */
.right-partner {
    gap: 12px;
}

.logo-placeholder {
  /* 使用 logo-img 占位符 */
}

.logo-img {
  width: 60px; /* 调整 logo 大小 */
  height: auto;
  display: block;
  /* 增加Logo的视觉效果 */
  border-radius: 8px; 
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}

.main-text-group {
  display: flex;
  flex-direction: column;
}

.main-title {
  font-weight: 700;
  font-size: 18px; /* 合作标题增大 */
  margin: 0;
  line-height: 1.3;
  color: #fceb5c; /* 突出合作标题 */
}
/* 移除不再需要的 english-subtitle 样式 */
.english-subtitle {
    display: none;
}

.separator {
    font-size: 30px;
    font-weight: 800;
    color: rgba(255, 255, 255, 0.7);
    flex-shrink: 0;
}

/* 移除所有与 links-wrap 相关的样式 */
.links-wrap, .links-row, .links-item, .links-item .title, .links-item .subtitle, .links-item .header-title, .links-item .header-subtitle {
    display: none; 
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
    opacity: 0.9;
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
    /* 1024px 及以下，合作标题垂直堆叠 */
    .footer-top.collaboration-header { 
        flex-direction: column; 
        gap: 20px;
    }
    .separator {
        display: none; /* 移除垂直堆叠时的分隔符 */
    }
    .main-title {
        font-size: 16px;
    }
    
    .partner-item {
        /* 确保在垂直堆叠时居中 */
        justify-content: center;
    }
}

@media (max-width: 768px) {
    /* 手机端，联系信息也垂直堆叠 */
    .footer-middle {
        flex-direction: column;
        justify-content: center;
        margin-bottom: 15px;
        font-size: 13px; /* 字体略微缩小 */
    }
    .middle-icon-wrap {
        margin: 10px 0 0 0; /* 图标放在联系信息下方 */
    }
}
</style>
