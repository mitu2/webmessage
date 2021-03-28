import { createApp } from 'vue'
import App from './App'
import router from './router'
import request from "@/util/request";
import store from './store'
import antd from './util/antd.install'
import webSocket from "@/util/webSocket";

createApp(App)
    .use(store)
    .use(router)
    .use(antd)
    .use(request)
    .use(webSocket)
    .mount('#app')
