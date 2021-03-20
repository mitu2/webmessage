<template>
  <div id="nav">
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link>
  </div>
  <router-view/>
</template>

<style>
* {
  margin: 0;
  padding: 0;
}
body, #app {
  height: 100%;
  width: 100%;
}
#app {
  background-image: url("./assets/background.jpg");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  font-family: "Microsoft YaHei", sans-serif;
  position: relative;
  color: white;
}

</style>
