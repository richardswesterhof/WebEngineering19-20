<template>
  <div>
    title: {{$props.songData.title}},
    by: {{$props.songData.artistName}},
    from: {{$props.songData.year}}
  </div>
</template>

<script>
  export default {
    name: "Song",

    props: {
      songData: {
        type: Object,
        required: true,
      },
    },
  }
</script>

<style scoped>

</style>
