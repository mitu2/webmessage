<template>
  <a-config-provider :get-popup-container="getPopupContainer">
    <router-view/>
  </a-config-provider>
</template>
<script>
import { message } from "ant-design-vue";
import { mapState } from "vuex";

const urls = [ '/login', '/register' ];

export default {
  name: 'App',
  watch: {
    '$route': {
      handler(to) {
        const { isLogin, goLink } = this;

        if (urls.indexOf(to.path) === -1) {
          if (!isLogin) {
            goLink('/login', '您未登录自动为您跳转登录页面');
          }
        } else {
          if (isLogin) {
            goLink('/', '您已经登录, 如果访问请先退出登录');
          }
        }
      }
    },
    isLogin: {
      immediate: true,
      handler(nVal) {
       this.$wsocket[nVal ? 'create' : 'close']();
      }
    }
  },
  computed: {
    ...mapState([ 'isLogin' ])
  },
  methods: {
    goLink(link, text) {
      this.$router.push(link);
      message.error({ content: text, key: 'GO_LINK', duration: 2 });
    },
    getPopupContainer(el, dialogContext) {
      if (dialogContext) {
        return dialogContext.getDialogWrap();
      } else {
        return document.body;
      }
    },
  },
  mounted() {
    /** this.$wsocket.watchStatus((nVal) => {
      if (nVal !== 'OK' && this.isLogin) {
        this.$wsocket.create();
      }
    }) **/
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
