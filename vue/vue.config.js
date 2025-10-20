const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    allowedHosts: [
      '.natappfree.cc', // 允许所有 Natapp 免费域名
      '.natapp.cc',     // 允许所有 Natapp 域名
      'localhost',      // 确保本地访问仍然可用
      '127.0.0.1'       // 确保本地访问仍然可用
    ],
  },
})
