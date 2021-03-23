<template>
  <a-layout style="height: 100%">
    <a-layout-sider>
      <home-sider/>
    </a-layout-sider>
    <a-layout>
      <a-layout-header>Header</a-layout-header>
      <a-layout-content>Content</a-layout-content>
      <a-layout-footer>Footer</a-layout-footer>
    </a-layout>
  </a-layout>
</template>

<script>
import HomeSider from "@/components/layout/HomeSider";

export default {
  name: "UseAntdLayout",
  components: { HomeSider }
}
</script>

<style scoped>

</style>