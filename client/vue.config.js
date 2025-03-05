const { defineConfig } = require('@vue/cli-service');

module.exports = defineConfig({

   //assetsDir: "vueProject",

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