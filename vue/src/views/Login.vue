<template>
  <div class="login">
    <div>
      登录
    </div>
    <a-form
        :model="form"
    >
      <a-form-item>
        <div>
          用户名
        </div>
        <a-input v-model:value="form.username">
          <template #prefix>
            <UserOutlined style="color: rgba(0, 0, 0, 0.25)"/>
          </template>
        </a-input>
      </a-form-item>
      <a-form-item>
        <div>
          密码
        </div>
        <a-input v-model:value="form.password" type="password">
          <template #prefix>
            <LockOutlined style="color: rgba(0, 0, 0, 0.25)"/>
          </template>
        </a-input>
      </a-form-item>
      <a-form-item>
        <a-button
            type="primary"
            html-type="submit"
            @click="submit"
            :disabled="form.username === '' || form.password === ''"
        >
          Log in
        </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import { UserOutlined, LockOutlined } from '@ant-design/icons-vue';
import { login } from "@/util/request";

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
      console.log(this.form)
      const { username, password } = this.form;
      login(username, password)
          .then(() => {

          })
          .catch(err => {
            console.log(err)
          })
    }
  }
}
</script>

<style scoped>
.login {
  position: absolute;
  margin: auto;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  background: rgba(29, 29, 31, 0.72);
  backdrop-filter: saturate(180%) blur(20px);
  width: 1000px;
  height: 750px;
}
</style>