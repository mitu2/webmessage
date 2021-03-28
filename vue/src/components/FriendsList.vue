<template>
  <div v-for="(user, ind) of users" :key="ind">
    {{ user.username }}
  </div>
</template>

<script>
export default {
  name: "FriendsList",
  data() {
    return {
      users: []
    }
  },
  mounted() {
    if (this.$store.state.isLogin) {
      this.$http.get('/rest/users?size=100')
          .then(({ data }) => {
            this.users = data['_embedded'].userTables;
          });
    }
  }
}
</script>

<style scoped>

</style>