const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 9091,
    proxy: {
      '/v4': {
        target: 'https://api.igdb.com',
        changeOrigin: true,
        secure: false,
        pathRewrite: { '^/v4': '/v4' }
      }
    }
  }
})
