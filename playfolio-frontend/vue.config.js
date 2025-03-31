const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 9091,
    proxy: 'https://api.igdb.com',
  }
})
