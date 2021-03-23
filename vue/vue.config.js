// eslint-disable-next-line no-unused-vars
const isProdMode = process.env.NODE_ENV === "production";

module.exports = {
    publicPath: "/",
    // 打包存放地址
    outputDir: "../src/main/resources/html",
    // 静态文件存放地址
    assetsDir: "static",
    indexPath: 'templates/index.html',
    // pages: {
    //     // 首页存放配置
    //     'main': {
    //         // page 的入口
    //         entry: 'src/main.js',
    //         // 模板来源
    //         template: 'public/index.html',
    //         title: 'Project WebMessage',
    //         filename: 'templates/index.html',
    //         // 在这个页面中包含的块，默认情况下会包含
    //         // 提取出来的通用 chunk 和 vendor chunk。
    //         chunks: ['chunk-vendors', 'chunk-common', 'index']
    //     },
    // },
    productionSourceMap: false,
    devServer: {
        proxy: 'http://localhost:8080',
        port: 8090
    },
}