<template>
  <div class="login">
    <div class="title" style="margin-top: 140px">
      登录页面
    </div>
    <a-form
        :model="form"
    >
      <a-form-item class="wm-input">
        <a-input v-model:value="form.username" placeholder="用户名" autocomplete="username">
          <template #prefix>
            <UserOutlined style="color: rgba(0, 0, 0, 0.25)"/>
          </template>
        </a-input>
      </a-form-item>
      <a-form-item class="wm-input">
        <a-input v-model:value="form.password" autocomplete="current-password" type="password" placeholder="密码">
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
import { login } from "@/util/request";
import { message } from "ant-design-vue";

export default {
  name: "Login",
  data() {
    return {
      form: {
        username: 'admin',    // 用户名
        password: 'admin',    // 密码
        isRememberMe: true    // 是否记住我 (默认启用, 暂无表单控制, 至于为甚吗? 懒)
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
      const key = 'LOGIN_KEY';
      message.loading({ content: '发送请求中...', key });
      const { username, password, isRememberMe } = this.form;
      login(username, password, isRememberMe)
          .then(() => {
            this.$store.dispatch('updateUserInfo')
                .then(bol => {
                  if (bol) {
                    message.success({ content: '登录成功, 正在跳转', key, duration: 2 });
                    setTimeout(() => {
                      this.$router.push('/')
                    }, 2000)
                  } else {
                    message.error({ content: '登录失败, 原因为: 获取用户信息失败', key, duration: 2 });
                  }
                })
                .catch(err => {
                  message.error({ content: '登录失败, 原因为: ' + err, key, duration: 2 });
                })
          })
          .catch(err => {
            console.log(err)
            message.error({ content: '登录失败, 原因为: ' + err, key, duration: 2 });
          })
    },

  }
}
</script>

<style scoped>
@import url(./loginAndRegisterStyle.css);
</style>