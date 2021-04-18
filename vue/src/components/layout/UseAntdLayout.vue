<template>
  <a-layout style="height: 100%">
    <a-layout-sider>
      <home-sider/>
    </a-layout-sider>
    <a-layout>
      <a-layout-header>Header</a-layout-header>
      <a-layout-content>
        <home-center />
      </a-layout-content>
      <a-layout-footer>
        <home-footer/>
      </a-layout-footer>
    </a-layout>
  </a-layout>
</template>

<script>
import HomeSider from "@/components/layout/HomeSlider";
import HomeFooter from "@/components/layout/HomeFooter";
import HomeCenter from "@/components/layout/HomeCenter";

export default {
  name: "UseAntdLayout",
  components: { HomeCenter, HomeFooter, HomeSider }
}
</script>

<style scoped>

</style>