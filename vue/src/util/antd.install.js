import { Button, Form, Input, ConfigProvider, Layout, List, Avatar, message, Row, Col } from 'ant-design-vue'

export default {
    install(Vue) {
        Vue.config.globalProperties.$message = message;
        Vue.use(Button)
            .use(Form)
            .use(Input)
            .use(ConfigProvider)
            .use(List)
            .use(Avatar)
            .use(Layout)
            .use(Row)
            .use(Col)
    }
}