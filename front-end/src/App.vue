<template>
  <div id="app">
    <Navigation ref="navigation"></Navigation>
    <router-view style="text-align:left"/>

    <div class="spacer"></div>

    <Footer class="footer" ref="footer"></Footer>
  </div>
</template>

<script>
import Navigation from './components/page-addons/Navigation.vue'
import Footer from "./components/page-addons/Footer";


export default {
  name: 'App',

  components: {
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

  margin-top: 2em;
  display: flex;
  flex-direction: column;
  min-height: 89vh;
}

.spacer {
  flex: 1 0 auto;
}

.footer {
  margin-top:1em;
  height: 2em;
}
</style>


<style lang="scss">
  // Import Bulma's core
  @import "~bulma/sass/utilities/_all";

  // Set your colors
  $primary: #267bad;
  $primary-invert: findColorInvert($primary);
  $twitter: #4099FF;
  $twitter-invert: findColorInvert($twitter);

  // Setup $colors to use as bulma classes (e.g. 'is-twitter')
  $colors: (
    "white": ($white, $black),
    "black": ($black, $white),
    "light": ($light, $light-invert),
    "dark": ($dark, $dark-invert),
    "primary": ($primary, $primary-invert),
    "info": ($info, $info-invert),
    "success": ($success, $success-invert),
    "warning": ($warning, $warning-invert),
    "danger": ($danger, $danger-invert),
    "twitter": ($twitter, $twitter-invert)
  );

  // Links
  $link: $primary;
  $link-invert: $primary-invert;
  $link-focus-border: $primary;


  @import "~bulma";
  @import "~buefy/src/scss/buefy";
  @import "~@fortawesome/fontawesome-free/scss/fontawesome.scss";
</style>
