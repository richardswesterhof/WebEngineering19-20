<template>
  <div>
    name: {{artistData.name}},
    genre: {{artistData.terms}}
  </div>
</template>

<script>
  export default {
    name: "Artist",

    props: {
      artistData: {
        type: Object,
        required: true,
      }
    }
  }
</script>

<style scoped>

</style>
