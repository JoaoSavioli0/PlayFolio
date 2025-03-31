<template>
  <div class="">
    <router-view />
  </div>
</template>

<style>
#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: white;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}

.text-glow-purple {
  text-shadow: 0px 0px 20px #fc8ffe, 0px 0px 10px #ea53ed;
}

.text-glow-white {
  text-shadow: 0px 0px 11px rgba(206, 206, 206, 0.7);
}

.box-glow-purple {
  -webkit-box-shadow: 0px 0px 17px 0px rgba(240, 126, 242, 0.54);
  -moz-box-shadow: 0px 0px 17px 0px rgba(240, 126, 242, 0.54);
  box-shadow: 0px 0px 17px 0px rgba(240, 126, 242, 0.54);
}
</style>
