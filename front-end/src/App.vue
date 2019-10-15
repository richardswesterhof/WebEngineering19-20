<template>
  <div id="app">
    <Header ref="header"></Header>
    <Navigation ref="navigation"></Navigation>
    <router-view/>

    <Footer ref="footer"></Footer>
  </div>
</template>

<script>
import Header from './components/Header.vue'
import Navigation from './components/Navigation.vue'
import Footer from "./components/Footer";


export default {
  name: 'App',

  components: {
    Header,
    Navigation,
    Footer,
  },
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
