<template>
  <div class="home-center">
<!--    <a-list item-layout="horizontal" :data-source="messages" class="list-height">-->
<!--      <template #renderItem="{ item }">-->
<!--        <a-list-item>-->
<!--          <a-list-item-meta-->
<!--              :description="item.data"-->
<!--          >-->
<!--            <template #title>-->
<!--              <a href="#">{{ item.sender.username }}</a>-->
<!--            </template>-->
<!--            <template #avatar>-->
<!--              <a-avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"/>-->
<!--            </template>-->
<!--          </a-list-item-meta>-->
<!--        </a-list-item>-->
<!--      </template>-->
<!--    </a-list>-->
    <div class="message" v-for="(msg, ind) of messages" :key="ind">
      <div class="sender-name">
        用户: {{ msg.sid }}
      </div>
      <div class="sender-content">
        内容: {{ msg.data }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "HomeCenter",
  computed: {
    messages() {
      return this.$wsocket.message;
    }
  }
}
</script>

<style scoped>
.home-center {
  color: black;
  padding-top: 15px;
  padding-left: 30px;
  padding-right: 20px;
}
::v-deep .ant-list-items {
  height: 460px !important;

}
</style>