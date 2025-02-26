const { defineConfig } = require('@vue/cli-service')
const target = 'http://localhost:80';

module.exports = defineConfig({
  devServer : {
    port : 81,
    proxy : {
      '^/api' : {
        target,
        changeOrigin : true,
        ws : false,
        pathRewrite : { '^/api' :'/' }
      }
    }
  }
})