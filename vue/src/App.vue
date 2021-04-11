<template>
  <a-config-provider :get-popup-container="getPopupContainer">
    <router-view/>
  </a-config-provider>
</template>
<script>
import { mapState } from "vuex";

export default {
  name: 'App',
  watch: {
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
