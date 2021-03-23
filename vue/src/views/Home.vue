<template>
  <div class="home">
    <use-antd-layout/>
  </div>
</template>

<script>
import UseAntdLayout from "@/components/layout/UseAntdLayout";

export default {
  name: 'Home',
  components: {
    UseAntdLayout
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