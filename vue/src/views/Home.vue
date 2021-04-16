<template>
  <div class="home">
    <use-style-layout />
  </div>
</template>

<script>

import UseStyleLayout from "@/components/layout/UseStyleLayout";
export default {
  name: 'Home',
  components: {
    UseStyleLayout
  },
}
</script>

<style scoped>
.home {
  position: absolute;
  margin: auto;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  width: 1080px;
  height: 700px;
}
</style>