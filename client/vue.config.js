const { defineConfig } = require('@vue/cli-service')
const target = 'http://localhost:80';
var path = require("path");


module.exports = defineConfig({
  transpileDependencies: true,
  outputDir : path.resolve("../server/public"),
  devServer : {
    port : 80,
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

