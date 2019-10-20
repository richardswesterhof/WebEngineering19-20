<template>
  <b-navbar fixed-top shadow style="background-color: #fafafa">
    <template slot="brand">
      <b-navbar-item tag="router-link" :to="'/'">
        <img
          src="../../assets/shamzamLogo2.png"
          alt="Homepage"
        >
      </b-navbar-item>
    </template>
    <template slot="start">
      <b-navbar-item tag="router-link" :to="'/artists'">
        Artists
      </b-navbar-item>


      <b-navbar-item tag="router-link" :to="'/songs'">
        Songs
      </b-navbar-item>


      <b-navbar-item tag="router-link" :to="'/import'">
        Import Data
      </b-navbar-item>


    </template>
  </b-navbar>
</template>

<script>
  export default {
    name: "Navigation",
  }
</script>

<style scoped>

</style>
