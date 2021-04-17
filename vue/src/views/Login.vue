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

const POPUP_KEY = 'LOGIN_KEY';

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
      const {
        $message: {
          success, error, loading
        },
        form: {
          username, password, isRememberMe
        },
        $store, $router
      } = this;
      loading({ content: '发送请求中...', key: POPUP_KEY });
      login(username, password, isRememberMe)
          .then(() => {
            $store.dispatch('updateUserConfig')
                .then(cond => {
                  if (cond) {
                    success({ content: '登录成功, 正在跳转', key: POPUP_KEY, duration: 2 });
                    $router.push('/')
                  } else {
                    error({ content: '登录失败, 原因为: 获取用户信息失败', key: POPUP_KEY, duration: 2 });
                  }
                })
                .catch(err => {
                  error({ content: '登录失败, 原因为: ' + err, key: POPUP_KEY, duration: 2 });
                })
          })
          .catch(err => {
            console.log(err)
            error({ content: '登录失败, 原因为: ' + err, key: POPUP_KEY, duration: 2 });
          })
    },

  }
}
</script>

<style scoped>
@import url(./loginAndRegisterStyle.css);
</style>