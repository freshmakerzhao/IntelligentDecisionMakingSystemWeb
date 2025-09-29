import axios from 'axios'
import ElementUI from 'element-ui'
// 导入 Vue Router 实例，用于强制跳转
import router from '../router'

const request = axios.create({
    // 请求前缀
    baseURL: 'http://localhost:9090',
    timeout: 5000
})

// ======================= 请求拦截器 =======================
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    try {
        const userJson = localStorage.getItem("user");
        if (userJson) {
            const user = JSON.parse(userJson);
            // 确保 user 存在且 token 存在
            if (user && user.token) {
                config.headers['token'] = user.token; 
            }
        }
    } catch (e) {
        console.error("解析用户 Token 失败:", e);
        // 即使解析失败，也不影响请求发送，后端拦截器会处理 Token 缺失
    }
    return config
}, error => {
    // 很少用到，用于请求前的配置或网络错误
    return Promise.reject(error)
});

// ======================= 响应拦截器 =======================

// 在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }

        // 兼容服务端返回的字符串数据
        if (typeof res === 'string' && res.trim() !== '') {
            try {
                 res = JSON.parse(res);
            } catch (e) {
                // 如果解析失败，可能是纯文本或其他非JSON数据，直接返回原始值
                return response;
            }
        }

        // 统一处理 CODE_401 (认证失败/Token过期)
        if (res && res.code === '401') {
            const currentPath = router.currentRoute.fullPath;
            
            // 避免在登录页时重复跳转
            if (currentPath !== '/login') {
                ElementUI.Message.error(res.msg || "登录状态已过期，请重新登录");
                
                // 强制执行跳转
                localStorage.removeItem("user"); // 清除本地无效 Token
                router.push({
                    path: '/login',
                    query: { redirect: currentPath } // 登录后尝试返回原页面
                });
            }
            
            // 返回一个被拒绝的 Promise，阻止业务代码继续执行
            // 避免前端在 401 发生后还尝试处理返回数据
            return Promise.reject(res);
        }

        return res;
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)


export default request

