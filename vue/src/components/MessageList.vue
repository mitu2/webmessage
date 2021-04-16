<template>
  <div class="message-list">
    <div class="message-item" v-for="(message, ind) of messages" :key="ind">
      <a-row v-if="message.type === 'RECIPIENT'">
        <a-col :span="2">
          <div class="user-avatar">
            <a-avatar shape="square" size="large"/>
          </div>
        </a-col>
        <a-col :span="22">
          <div class="recipient">
            <p>{{ message.text }}</p>
          </div>
        </a-col>
      </a-row>
      <a-row v-else-if="message.type === 'SENDER'">
        <a-col :span="22">
          <div class="sender">
            <p>{{ message.text }}</p>
          </div>
        </a-col>
        <a-col :span="2">
          <div class="user-avatar">
            <a-avatar shape="square" size="large"/>
          </div>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "MessageList",
  data() {
    return {
      messages: [
        { type: 'SENDER', text: '发送的消息哦' },
        { type: 'RECIPIENT', text: '接受的消息哦!' },
        { type: 'RECIPIENT', text: '猜猜我是谁？' },
        { type: 'SENDER', text: '我管你是谁！' },
        { type: 'SENDER', text: 'WDNMD！' },
        { type: 'RECIPIENT', text: '我是哇哈哈哈哈哈哈哈哈哈哈' },
        { type: 'SENDER', text: 'Project 施工中！' },
        { type: 'RECIPIENT', text: 'Project 施工中' },
      ]
    }
  }
}
</script>

<style scoped>
.message-list {
  height: 60%;
  overflow-y: scroll;
}

.user-avatar {
  padding-top: 7px;
  padding-right: 8px;
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
}

.recipient:before, .sender:after { /*用伪类写出小三角形*/
  content: '';
  display: block;
  width: 0;
  height: 0;
  border: 16px solid transparent;
  position: absolute;
  top: 9px;
}

/*分别给左右两边的小三角形定位*/
.recipient:before {
  border-right: 16px solid #9EEA6A;
  left: -30px;
}

.sender:after {
  border-left: 16px solid #FC3;
  right: -30px;
}
</style>