<template>
  <div class="friend-list">
    <div class="friend-item" v-for="(friend, ind) of friends" :key="ind">
      <div class="user-avatar">
        <a-avatar shape="square" size="large" />
      </div>
      <div class="user-name">{{ friend.name }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "FriendList",
  data() {
    return {
      friends: [
        { name: '天国基佬 （404）' },
        { name: '天国基佬 （405）' },
        { name: '天国基佬 （406）' }
      ]
    }
  },
  mounted() {

  }
}
</script>

<style scoped>
.friend-list {
  margin-top: 20px;
  color: black;
  font-size: 16px;
}
.friend-item {
  height: 65px;
  padding-left: 15px;
}
.user-avatar {
  padding-top: 7px;
  padding-right: 8px;
  float: left;
}
.user-name {
  margin-top: 10px;
  font-weight: 500;
  float: left;
}
</style>