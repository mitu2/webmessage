import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import antd from './util/antd.install'
import request from "@/util/request";

const app = createApp(App);
app.config.globalProperties.$axios = request;
app
    .use(store)
    .use(router)
    .use(antd)
    .mount('#app')
