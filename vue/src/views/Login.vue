<template>
  <div class="login">
    <div class="title" style="margin-top: 140px">
      登录页面
    </div>
    <a-form
        :model="form"
    >
      <a-form-item class="wm-input">
        <a-input v-model:value="form.username" placeholder="用户名">
          <template #prefix>
            <UserOutlined style="color: rgba(0, 0, 0, 0.25)"/>
          </template>
        </a-input>
      </a-form-item>
      <a-form-item class="wm-input">
        <a-input v-model:value="form.password" type="password" placeholder="密码">
          <template #prefix>
            <LockOutlined style="color: rgba(0, 0, 0, 0.25)"/>
          </template>
        </a-input>
      </a-form-item>
      <a-form-item class="wm-input">

        <a-button
            type="primary"
            html-type="submit"
            @click="submit"
        >
          <div class="but" type="submit">登录</div>
        </a-button>
      </a-form-item>
    </a-form>
    <router-link to="/register">
      没有账号? 点击注册。
    </router-link>
  </div>
</template>

<script>
import { LockOutlined, UserOutlined } from '@ant-design/icons-vue';
import { login, register } from "@/util/request";
import { message } from "ant-design-vue";

export default {
  name: "Login",
  data() {
    return {
      form: {
        username: '',
        password: '',
      }
    }
  },
  components: {
    UserOutlined, LockOutlined
  },
  methods: {
    submit() {
      this.doLogin();
    },
    doLogin() {
      const key = 'LOGIN';
      message.loading({ content: '发送请求中...', key });
      const { username, password } = this.form;
      login(username, password)
          .then(() => {
            message.success({ content: '登录成功, 正在跳转', key, duration: 2 });
          })
          .catch(err => {
            message.error({ content: '登录失败, 原因为: ' + err, key, duration: 2 });
          })
    },
    doRegister() {
      const key = 'REGISTER';
      message.loading({ content: '发送请求中...', key });
      const { username, password, email } = this.form;
      register(username, password, email)
          .then(() => {
            message.success({ content: '注册成功, 返回登录', key, duration: 2 });
          })
          .catch(err => {
            message.error({ content: '登录失败, 原因为: ' + err, key, duration: 2 });

          })
    }
  }
}
</script>

<style scoped>
@import url(./loginAndRegisterStyle.css);
</style>