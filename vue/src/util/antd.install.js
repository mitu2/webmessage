import { Button, Form, Input } from 'ant-design-vue'

export default {
    install(app) {
        app.use(Button)
            .use(Form)
            .use(Input)
    }
}