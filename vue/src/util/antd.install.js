import { Button, Form, Input, ConfigProvider, Layout } from 'ant-design-vue'

export default {
    install(app) {
        app.use(Button)
            .use(Form)
            .use(Input)
            .use(ConfigProvider)
            .use(Layout)
    }
}