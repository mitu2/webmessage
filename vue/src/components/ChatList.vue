<template>
  <div class="chat-list">
    <div v-for="(chat, ind) of chats"
         class="chat-item"
         :class="{ 'current-chat': select === ind }"
         :key="ind"
         @click="select = ind">
      <div class="user-avatar">
        <a-avatar :src="chat.icon" shape="square" size="large"/>
      </div>
      <div class="user-name">{{ chat.name }}</div>
    </div>
  </div>
</template>

<script>

import HaiImg from '@/assets/hai.gif';
import { mapMutations } from "vuex";


export default {
  name: "ChatList",
  data() {
    return {
      select: 0,
    }
  },
  watch: {
    select: {
      immediate: true,
      handler(nVal) {
        this.modifyChat(this.chats[nVal]);
      }
    }
  },
  computed: {
    chats() {
      return [
        { name: '测试群聊', type: 'Broadcast', id: -1, icon: HaiImg },
        { name: '测试群聊2', type: 'Broadcast', id: -2, icon: HaiImg },
        { name: '测试群聊3', type: 'Broadcast', id: -3, icon: HaiImg },
      ]
    }
  },
  methods: {
    ...mapMutations([ 'modifyChat' ])
  },
  mounted() {

  }
}
</script>

<style scoped>
.current-chat {
  background: #C8C6C6;
}

.chat-list {
  margin-top: 20px;
  color: black;
  font-size: 16px;
}

.chat-item {
  height: 65px;
  padding-left: 15px;
}

.user-avatar {
  margin-top: 7px;
  margin-right: 8px;
  float: left;
  border: 1px solid #E7E7E7;
}

.user-name {
  margin-top: 10px;
  font-weight: 500;
  float: left;
}
</style>