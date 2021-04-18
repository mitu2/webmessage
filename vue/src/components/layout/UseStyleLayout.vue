<template>
  <div class="layout-main">
    <div class="layout-buttons inner">
      <home-buttons/>
    </div>
    <div class="layout-list inner">
      <home-slider/>
    </div>
    <div class="layout-content inner">
      <home-center/>
    </div>
  </div>
</template>

<script>
import HomeCenter from "@/components/layout/HomeCenter";
import HomeSlider from "@/components/layout/HomeSlider";
import HomeButtons from "@/components/layout/HomeButtons";

export default {
  name: "UseStyleLayout",
  components: { HomeButtons, HomeSlider, HomeCenter }
}
</script>

<style scoped>
.layout-main {
  background: rgba(29, 29, 31, 0.72);
  backdrop-filter: saturate(180%) blur(20px);
  width: 100%;
  height: 100%;
}

.layout-buttons {
  width: 8%;
  height: 100%;
}

.layout-list {
  width: 25%;
  height: 100%;
  background: #ede9e7;
}

.layout-content {
  width: 67%;
  height: 100%;
  background: #f4f4f4;
}

.inner {
  float: left;
}
</style>