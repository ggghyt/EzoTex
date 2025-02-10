const { defineConfig } = require('@vue/cli-service')
const target = 'http://localhost:3000'; //이거 뭘로 바꾸지?
var path = require("path");


module.exports = defineConfig({
  transpileDependencies: true,
  outputDir : path.resolve("../server/public"),
  devServer : {
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