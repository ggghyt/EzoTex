const { defineConfig } = require('@vue/cli-service')
const target = 'http://localhost:80';
var path = require("path");
const NodePolyfillPlugin = require("node-polyfill-webpack-plugin");

module.exports = defineConfig({
  transpileDependencies: true,
  resolve: {
    fallback: {
      process: require.resolve("process/browser")
    }
  },
  lintOnSave: false,
  configureWebpack: {
    plugins: [new NodePolyfillPlugin()],
    optimization: {
      splitChunks: {
        chunks: "all",
      },
    },
  },
  outputDir : path.resolve("../server/public"),
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
