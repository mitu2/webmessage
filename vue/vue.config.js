const isProdMode = process.env.NODE_ENV === "production";

module.exports = {
    publicPath: "/",
    // 打包存放地址
    outputDir: "../src/main/html",
    // 静态文件存放地址
    assetsDir: "static",
    // 首页存放地址
    indexPath: "templates/vx.html",

}