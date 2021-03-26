import { Button, Form, Input, ConfigProvider, Layout, List, Avatar } from 'ant-design-vue'

export default {
    install(app) {
        app.use(Button)
            .use(Form)
            .use(Input)
            .use(ConfigProvider)
            .use(List)
            .use(Avatar)
            .use(Layout)
    }
}