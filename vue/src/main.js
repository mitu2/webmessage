import { createApp } from 'vue'
import App from './App'
import router from './router'
import store from './store'
import antd from './util/antd.install'
import request from "@/util/request";

const app = createApp(App);
app
    .use(store)
    .use(router)
    .use(antd)
    .use(request)
    .mount('#app')
