const { defineConfig } = require('@vue/cli-service');

module.exports = defineConfig({
  devServer : {
    port : 8081,    
    proxy : {
       '^/api' : {
          target : 'http://localhost:80',
          changeOrigin : true,
          pathRewrite : { '^/api' : '/' },
          ws : false 
       }
    }
 }
})