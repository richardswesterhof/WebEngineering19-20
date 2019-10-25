<template>
  <section>
    <h2 class=is-subpage-title>Replace Song</h2>

    <b-field class="is-input is-first" label="Song ID" horizontal>
      <b-input v-model="songId" type="Number" min="1" placeholder="song id..."></b-input>
    </b-field>

    <b-field class="is-input" label="Artist ID" horizontal>
      <b-input v-model="artistId" type="number" min="1" placeholder="artist id...">
      </b-input>
    </b-field>

    <b-field class="is-input" label="Title" horizontal>
      <b-input v-model="title" placeholder="title...">
      </b-input>
    </b-field>

    <b-field class="is-input" label="Duration" horizontal>
      <b-input v-model="duration" type="number" min="0" placeholder="duration in seconds...">
      </b-input>
    </b-field>

    <b-field class="is-input" label="Year" horizontal>
      <b-input v-model="year" type="number" min="0" placeholder="year...">
      </b-input>
    </b-field>

    <b-field class="is-input" label="Hotness" horizontal>
      <b-input v-model="hotness" placeholder="hotness..." type="number" step="0.001" min="0" max="1">
      </b-input>
    </b-field>

    <b-button class="button is-primary is-submit-button" @click="updateSong">replace song</b-button>

    <b-button v-show="assignedId" class="button is-primary is-medium" @click="checkSong(assignedId)">Check out updated song</b-button>
  </section>
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
        hotness: null,

        assignedId: null,
      }
    },

    methods: {
      updateSong() {
        if(!(this.songId && this.artistId && this.title && this.duration && this.year && this.hotness)) {
          this.$buefy.toast.open({message: 'please fill in all fields', type: 'is-primary'});
          return;
        }
        this.isLoading = true;
        api.putSong(this.songId, this.artistId, this.title, this.duration, this.year, this.hotness).then((response) => {
          if(response.status === 200) {
            this.$buefy.toast.open({
              message: 'successfully updated song with id: ' + response.data.songid,
              type: 'is-success',
            });
            this.clearFields();
            this.assignedId = response.data.songid;
          }
          else {
            this.$buefy.toast.open({message: 'request failed with status code: ' + (response.status ? response.status : 'unknown status'), type: 'is-danger'});
            console.error(response);
          }
        });
        this.isLoading = false;
      },

      clearFields() {
        this.songId = null;
        this.artistId = null;
        this.title = null;
        this.duration = null;
        this.year = null;
        this.hotness = null;
      },

      checkSong(songid) {
        this.$emit('check-song', songid);
      },
    },
  }
</script>

<style scoped>

</style>
