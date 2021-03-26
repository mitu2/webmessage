<template>
  <div class="home-footer" @keyup.ctrl.enter="submit">
      <a-textarea
          v-model:value="senderMessage"
          placeholder="请输入您要发送的信息"
          :auto-size="{ minRows: 4, maxRows: 4 }"
      />
      <div style="text-align: right">
        <a-button @click="submit">
          发送
        </a-button>
      </div>
  </div>
</template>

<script>
export default {
  name: "HomeFooter",
  data() {
    return {
      senderMessage: ''
    }
  },
  methods: {
    submit() {
      this.$wsocket.sendObject({
        type: 'ALL_USER',
        data: this.senderMessage
      })
      this.senderMessage = '';
    }
  }
}
</script>

<style scoped>
.home-footer {
  height: 130px;
}
</style>