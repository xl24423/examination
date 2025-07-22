const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    // 或指定允许的域名（推荐）
    allowedHosts: [".natappfree.cc"],
    proxy: { // 添加代理，避免跨域
      '/api': {
        target: 'http://localhost:9090', // 后端本地地址
        changeOrigin: true,
        pathRewrite: { '^/api': '' }
      }
    }
  }
})

