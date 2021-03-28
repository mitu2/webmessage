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
import { message } from "ant-design-vue";

export default {
  name: "HomeFooter",
  data() {
    return {
      senderMessage: ''
    }
  },
  methods: {
    submit() {
      const senderMessage = this.senderMessage;
      if (!senderMessage || senderMessage.replaceAll('\n', '').trim().length === 0) {
        message.error({ content: '不要发空滴消息啊', key: 'SENDER_MESSAGE', duration: 3 })
        return;
      }

      this.$wsocket.sendObject({
        type: 'ALL_USER',
        data: senderMessage
      });
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