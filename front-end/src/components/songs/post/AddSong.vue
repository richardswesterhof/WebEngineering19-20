<template>
  <section>
    <b-loading :active.sync="isLoading" :can-cancel="true"></b-loading>


    <b-field label="Artist ID" horizontal style="width: 36em; margin: 1.5em auto 0.5em;">
      <b-input v-model="artistId" type="number" min="1" placeholder="artist id...">
      </b-input>
    </b-field>

    <b-field label="Title" horizontal style="width: 36em; margin: 0.5em auto 0.5em;">
      <b-input v-model="title" placeholder="title...">
      </b-input>
    </b-field>

    <b-field label="Duration" horizontal style="width: 36em; margin: 0.5em auto 0.5em;">
      <b-input v-model="duration" type="number" min="0" placeholder="duration in seconds...">
      </b-input>
    </b-field>

    <b-field label="Year" horizontal style="width: 36em; margin: 0.5em auto 0.5em;">
      <b-input v-model="year" type="number" min="0" placeholder="year...">
      </b-input>
    </b-field>

    <b-button class="button is-primary" @click="submitSong" style="margin-left: 29em;">submit song</b-button>
  </section>
</template>

<script>
  import api from "../../../api/api";

  export default {
    name: "AddSong",

    data() {
      return {
        isLoading: false,

        artistId: null,
        title: '',
        duration: null,
        year: null,
      }
    },

    methods: {
      submitSong() {
        this.isLoading = true;
        api.postSong(this.artistId, this.title, this.duration, this.year).then((response) => {
          console.log(response);
          this.isLoading = false;
          this.$buefy.toast.open({
            message: 'song is now in the database',
            type: 'is-success',
          });
        });
      },
    },
  }
</script>

<style scoped>

</style>
