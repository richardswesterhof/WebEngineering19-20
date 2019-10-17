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
        title: null,
        duration: null,
        year: null,
      }
    },

    methods: {
      submitSong() {
        if(!(this.artistId && this.title && this.duration && this.year)) {
          this.$buefy.toast.open({message: 'please fill in all fields', type: 'is-primary'});
          return;
        }
        this.isLoading = true;
        api.postSong(this.artistId, this.title, this.duration, this.year).then((response) => {
          if(response.status === 201) {
            this.$buefy.toast.open({
              message: 'song is now in the database',
              type: 'is-success',
            });
            this.clearFields();
          }
          else {
            this.$buefy.toast.open({message: 'request failed with status code: ' + response.status, type: 'is-danger'});
          }
          this.isLoading = false;
        });
      },

      clearFields() {
        this.artistId = null;
        this.title = null;
        this.duration = null;
        this.year = null;
      }
    },
  }
</script>

<style scoped>

</style>
