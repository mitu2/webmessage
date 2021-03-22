<template>
  <div class="register">
    <div class="title" style="margin-top: 70px">
      注册页面
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
        <a-input v-model:value="form.email" placeholder="邮箱">
          <template #prefix>
            <LockOutlined style="color: rgba(0, 0, 0, 0.25)"/>
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
        <a-input v-model:value="form.repeatPassword" type="password" placeholder="重复密码">
          <template #prefix>
            <LockOutlined style="color: rgba(0, 0, 0, 0.25)"/>
          </template>
        </a-input>
      </a-form-item>
      <a-form-item class="wm-input">
        <a-button
            type="primary"
            html-type="submit"
        >
          <div class="but" type="submit">注册</div>
        </a-button>
      </a-form-item>
    </a-form>
    <router-link to="/login">
      以有账号? 点击登录。
    </router-link>
  </div>
</template>

<script>
import { LockOutlined, UserOutlined } from '@ant-design/icons-vue';

export default {
  name: "Register",
  data() {
    return {
      form: {
        username: '',
        password: '',
        email: '',
        repeatPassword: ''
      }
    }
  },
  components: {
    UserOutlined, LockOutlined
  },
}
</script>

<style scoped>
@import url(./loginAndRegisterStyle.css);
</style>