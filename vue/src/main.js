import { createApp } from 'vue'
import App from './App'
import router from './router'
import request from "@/util/request";
import store from './store'
import antd from './util/antd.install'

const app = createApp(App);
app
    .use(store)
    .use(router)
    .use(antd)
    .use(request)
    .mount('#app')
