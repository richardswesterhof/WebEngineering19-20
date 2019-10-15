<template>
  <div id="app">
    <Header ref="header"></Header>
    <Navigation ref="navigation"></Navigation>
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',

  components: {
    Header,
    Navigation,
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
