<template>
  <a-config-provider :get-popup-container="getPopupContainer">
    <router-view/>
  </a-config-provider>
</template>
<script>
import { message } from "ant-design-vue";

export default {
  name: 'App',
  watch: {
    '$route': {
      immutable: true,
      handler(nVal) {
        const { state: { isLogin } } = this.$store;
        const urls = ['/login', '/register'];
        if (urls.indexOf(nVal) !== -1 && !isLogin) {
          this.$router.push('/login');
          message.error({ content: '您未登录自动为您跳转登录页面', key: 'NOT_LOGIN', duration: 2 });
        }
      }
    }
  },
  methods: {
    getPopupContainer(el, dialogContext) {
      if (dialogContext) {
        return dialogContext.getDialogWrap();
      } else {
        return document.body;
      }
    },
  }
}
</script>
<style>
* {
  margin: 0;
  padding: 0;
}

body, #app {
  height: 100%;
  width: 100%;
}

#app {
  background-image: url("./assets/background.jpg");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  font-family: "Microsoft YaHei", sans-serif;
  position: relative;
  color: white;
}

</style>
