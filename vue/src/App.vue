<template>
  <a-config-provider :get-popup-container="getPopupContainer">
    <router-view/>
  </a-config-provider>
</template>
<script>
import { mapGetters } from "vuex";
import { watch } from "vue";

export default {
  name: 'App',
  watch: {
    isGuest: {
      immediate: true,
      handler(nVal) {
        this.$wsocket[!nVal ? 'create' : 'close']();
      }
    }
  },
  computed: {
    ...mapGetters([ 'isGuest' ])
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
    watch(this.$wsocket.status, () => {
      if (!(this.$wsocket.isOk || this.isGuest)) {
        this.$message.warning({ content: '服务器已断开, 正在自动重连。。。', key: 'RETURN' })
        this.$wsocket.create();
      }
    });
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
  background-image: url("./assets/bc.jpg");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  font-family: "Microsoft YaHei", sans-serif;
  position: relative;
  color: white;
}

</style>
