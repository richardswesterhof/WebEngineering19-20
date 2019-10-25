<template>
  <div class="prettier custom-centered">
    <h1>Oops, that page doesn't exist :(</h1>
    <p @click="goBack" style="cursor: pointer; color: var(--primaryColor)">Click here to go back to the previous page</p>
  </div>
</template>

<script>
  export default {
    name: "NotFoundPage",

    methods: {
      goBack() {
        this.$router.go(-1);
      },
    },
  }
</script>

<style scoped>
.prettier {
  text-align: center !important;
}
</style>
