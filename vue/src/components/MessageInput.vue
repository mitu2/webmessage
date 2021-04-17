<template>
  <div class="message-input" @keydown.ctrl.enter="submit">
    <div class="message-input-content">
      <a-textarea
          id="message-input-textarea"
          v-model:value="text"
      />
      <div class="message-input-btn-submit">
        <a-button @click="submit">
          发送 (Ctrl + Enter)
        </a-button>
      </div>
    </div>

  </div>
</template>

<script>
import { mapMutations, mapState } from "vuex";

export default {
  name: "MessageInput",
  data() {
    return {
      text: ''
    }
  },
  computed: {
    ...mapState([ 'chatConfig', 'chatCache' ])
  },
  methods: {
    ...mapMutations([ 'initChatCache' ]),
    submit() {
      const { text, $message: { error } } = this;
      if (!text || text.replaceAll('\n', '').trim().length === 0) {
        error({ content: '请不要发送无效消息', key: 'SENDER_MESSAGE', duration: 3 })
        return;
      }
      const { id, type } = this.chatConfig;
      const key = type + '_' + id;
      this.initChatCache(key);
      const json = {
        rid: id,
        type,
        data: text,
        timestamp: Date.now()
      };
      this.chatCache[key].push(Object.assign({ chatType: 'Sender' }, json))
      console.log(this.chatCache)
      this.$wsocket.sendJSON(json);
      this.text = '';
    }
  }
}
</script>

<style scoped>
.message-input {
  border-top: 1px solid #E7E7E7;
  background: #FFFFFF;
  height: 28.5%;
}

.message-input-content {
  margin-top: 20px;
}

.message-input-btn-submit {
  position: absolute;
  bottom: 12px;
  right: 10px;
}

#message-input-textarea {
  padding: 15px;
  height: 135px;
  resize: none;
  border: none;
}
</style>