<template>
  <div class="message-list">
    <div class="message-item" v-for="(message, ind) of chatData" :key="ind">
      <a-row v-if="message.chatType.toUpperCase() === 'RECIPIENT'">
        <a-col :span="2">
          <div class="user-avatar">
            <a-avatar :src="userCache[message.sid]?.avatar" shape="square" size="large"/>
          </div>
        </a-col>
        <a-col :span="22">
          <div style="color: #8B8C8C">
            {{ userCache[message.sid]?.username }}
          </div>
          <div class="recipient">
            <p>{{ message.data.text }}</p>
          </div>
        </a-col>
      </a-row>
      <a-row v-else-if="message.chatType.toUpperCase() === 'SENDER'">
        <a-col :span="22">
          <div style="text-align: right; color: #8B8C8C; padding-right: 20px;">
            {{ userCache[message.rid]?.username }}
          </div>
          <div class="sender">
            <p>{{ message.data.text }}</p>
          </div>
        </a-col>
        <a-col :span="2">
          <div class="user-avatar">
            <a-avatar :src="userCache[message.rid]?.avatar" shape="square" size="large"/>
          </div>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script>
import { mapActions, mapMutations, mapState } from "vuex";

export default {
  name: "MessageList",
  data() {
    return {}
  },
  computed: {
    ...mapState([ 'chatConfig', 'chatCache', 'userCache' ]),
    chatData() {
      const { id, type } = this.chatConfig;
      const key = type + '_' + id;
      this.initChatCache(key);
      return this.chatCache[key];
    }
  },
  watch: {
    chatData: {
      deep: true,
      handler(nVal) {
        const ids = new Set(nVal.map(it => it.rid || it.sid))
        ids.forEach(id => {
          if (!this.userCache[id]) {
            this.addUserCache(id)
          }
        })
      }
    }
  },
  methods: {
    ...mapMutations([ 'initChatCache' ]),
    ...mapActions([ 'addUserCache' ]),
  },
  mounted() {
    this._unf = this.$wsocket.addHandler('Broadcast', (msg) => {
      const key = 'Broadcast' + '_' + msg.data.cid;
      this.initChatCache(key);
      this.chatCache[key].push(msg);
    })
  },
  beforeUnmount() {
    this._unf = this._unf && this._unf()
  }
}
</script>

<style scoped>
.message-list {
  height: 60%;
  overflow-y: scroll;
  overflow-x: hidden;
}

.user-avatar {
  padding-top: 7px;
  padding-right: 8px;
  cursor: pointer;
}

.message-item {
  margin-top: 8px;
  padding-left: 20px;
}

.message-item + .message-item {
  margin-top: 15px;
}

.recipient, .sender {
  min-height: 50px;
  position: relative;
  left: 10px;
  display: table;
  text-align: center;
  border-radius: 7px;
  background-color: #9EEA6A;
}

.sender { /*使左右的对话框分开*/
  /*top: 20px;*/
  /*left: 150px;*/
  /* top: 20px; */
  float: right;
  margin-right: 40px;
  background-color: #FC3;
}

.recipient > p, .sender > p { /*使内容居中*/
  display: table-cell;
  vertical-align: middle;
  padding: 0 10px;
  word-wrap: break-word;
  overflow-wrap: break-word;
  word-break: break-word;
}

.recipient:before, .sender:after { /*用伪类写出小三角形*/
  content: '';
  display: block;
  width: 0;
  height: 0;
  border: 7px solid transparent;
  position: absolute;
  top: 0px;
}

/*分别给左右两边的小三角形定位*/
.recipient:before {
  border-right: 16px solid #9EEA6A;
  left: -19px;
}

.sender:after {
  border-left: 16px solid #FC3;
  right: -19px;
}
</style>