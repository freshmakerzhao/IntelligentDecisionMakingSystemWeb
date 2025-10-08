import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";

Vue.use(VueRouter);

// 路由常量，方便维护
const ROUTE_LOGIN = '/login';
const ROUTE_ROOT = '/';

const routes = [
  {
    // 超级管理
    path: '/administrator',
    component: () => import('../views/Administrator/Manage.vue'),
    redirect: "/administrator/user",
    children:[
      { path: 'user', name: '用户管理', component: () => import('../views/Administrator/Administrator_A_User')},
      { path: 'travel', name: '拼车出行', component: () => import('../views/Administrator/Administrator_B_Travel')},
      { path: 'schoolKeeping', name: '跑腿代步', component: () => import('../views/Administrator/Administrator_C_SchoolKeeping')},
      { path: 'lostAndFound', name: '失物招领', component: () => import('../views/Administrator/Administrator_D_LostAndFound')},
      { path: 'secondHand', name: '闲置买卖', component: () => import('../views/Administrator/Administrator_E_SecondHand')},
      { path: 'comment', name: '评论管理', component: () => import('../views/Administrator/Administrator_F_Comment')},
      { path: 'person', name: '个人信息', component: () => import('../views/Administrator/Administrator_G_Person')}
    ]
  },
  {
    // 测试
    path: '/dashboard',
    component: () => import('../views/Dashboard/Manage.vue'),
    redirect: "/dashboard/home",
    children:[
      { path: 'home', name: '主页', component: () => import('../views/Dashboard/A_Home')},
      { path: 'cases', name: '案例分析', component: () => import('../views/Dashboard/B_Cases')},
      { path: 'faq', name: '常见问题', component: () => import('../views/Dashboard/C_Faq')},
      { path: 'model', name: '模型决策', component: () => import('../views/Dashboard/D_Model')},
      { path: 'about', name: '团队介绍', component: () => import('../views/Dashboard/E_About')}
    ]
  },
  {
    path: ROUTE_LOGIN,
    name: 'Login',
    component: () => import('../views/All_Login.vue'),
    meta: { skipAuth: true } // 跳过认证检查
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/All_Register.vue'),
    meta: { skipAuth: true } // 跳过认证检查
  },
  // 增加 404 捕获路由
  {
      path: '*',
      name: '404',
      component: { template: '<div>404 Page Not Found</div>' },
      meta: { skipAuth: true }
  }
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

// 路由守卫
router.beforeEach((to, from, next) => {
    localStorage.setItem("currentPathName", to.name); // 设置当前的路由名称，为了在Header组件中去使用
    if (store && store.commit) {
        store.commit("setPath"); // 触发store的数据更新
    }
    // 判断是否是公共页面（标记了 skipAuth 的页面）
    const isPublic = to.matched.some(record => record.meta.skipAuth);

    if (isPublic) {
        next(); // 公共页面，直接放行
        return;
    }

    // 尝试从 localStorage 获取用户信息
    const userJson = localStorage.getItem("user");
    let hasToken = false;

    if (userJson) {
        try {
            const user = JSON.parse(userJson);
            // 检查 token 是否存在
            hasToken = !!user.token; 
        } catch(e) {
            // JSON 解析失败，视为未登录
            localStorage.removeItem("user");
            hasToken = false;
        }
    }
    // 如果未登录
    if (!hasToken) {
        if (to.path !== '/login') {
            Vue.prototype.$message.warning("请先登录才能访问此页面");
            next({
                path: '/login',
                query: { redirect: to.fullPath } // 带上目标路径
            });
            return;
        }
    }
    next();
});

export default router
