<template>
  <div class="user-title">
    <div class="user-name">
      天国基佬 （404）
    </div>
    <div class="not_use_btn">
        - □ ⅹ
    </div>
  </div>
<!--  <div class="message" v-for="(msg, ind) of messages" :key="ind">-->
<!--    <div class="sender-name">-->
<!--      用户: {{ msg.sid }}-->
<!--    </div>-->
<!--    <div class="sender-content">-->
<!--      内容: {{ msg.data }}-->
<!--    </div>-->
<!--  </div>-->
  <message-list />
  <message-input />
</template>

<script>
import MessageList from "@/components/MessageList";
import MessageInput from "@/components/MessageInput";
export default {
  name: "HomeCenter",
  components: { MessageInput, MessageList },
  computed: {
    messages() {
      return this.$wsocket.messages.value;
    }
  }
}
</script>

<style scoped>
.user-title {
  user-select: none;
  position: relative;
  height: 10%;
  width: 100%;
  border-bottom: 1px solid #E7E7E7;
}
.user-name {
  height: 30px;
  width: 400px;
  position: absolute;
  margin: auto;
  top: 0;
  left: 30px;
  bottom: 0;
  color: black;
  font-weight: 400;
  font-family: "Microsoft YaHei", sans-serif;
  font-size: 16px;
  line-height: 30px;
}
.not_use_btn {
  position: absolute;
  right: 5px;
  top: 0;
  font-size: 16px;
  color: #9D9D9D;
  font-weight: bolder;
  letter-spacing: 18px;
}
</style>