<template>
    <div>
      <b-field label="Song ID" horizontal style="width: 36em; margin: 1.5em auto 0.5em;">
        <b-input v-model="songId" type="Number" min="1" placeholder="song id..."></b-input>
      </b-field>

      <b-field label="Artist ID" horizontal style="width: 36em; margin: 0.5em auto 0.5em;">
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

      <b-button class="button is-primary" @click="updateSong" style="margin-left: 29em;">replace song</b-button>
    </div>
</template>

<script>
  import api from "../../../api/api";

  export default {
    name: "UpdateSong",

    data() {
      return {
        isLoading: false,

        songId: null,
        artistId: null,
        title: null,
        duration: null,
        year: null,
      }
    },

    methods: {
      updateSong() {
        if(!(this.songId && this.artistId && this.title && this.duration && this.year)) {
          this.$buefy.toast.open({message: 'please fill in all fields', type: 'is-primary'});
          return;
        }
        this.isLoading = true;
        api.putSong(this.songId, this.artistId, this.title, this.duration, this.year).then((response) => {
          if(response.status === 200) {
            this.$buefy.toast.open({
              message: 'song was successfully updated',
              type: 'is-success',
            });
            this.clearFields();
          }
          else {
            this.$buefy.toast.open({message: 'request failed with status code: ' + response.status});
          }
          this.isLoading = false;
        });
      },

      clearFields() {
        this.songId = null;
        this.artistId = null;
        this.title = null;
        this.duration = null;
        this.year = null;
      },
    },
  }
</script>

<style scoped>

</style>
