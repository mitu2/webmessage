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

  }
}
</script>

<style scoped>

</style>