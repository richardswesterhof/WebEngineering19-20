<template>
  <section>
    <h2 class="is-subpage-title">Add Song</h2>

    <b-loading :active.sync="isLoading" :can-cancel="true"></b-loading>


    <b-field class="is-input is-first" label="Artist ID" horizontal>
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
      <b-input v-model="hotness" type="number" min="0" max="1" step="0.001" placeholder="hotness...">
      </b-input>
    </b-field>

    <b-button class="button is-primary is-submit-button" @click="submitSong">submit song</b-button>

    <b-button v-show="assignedId" class="button is-primary is-medium" @click="checkSong(assignedId)">Check out new song</b-button>
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
        hotness: null,

        assignedId: null,
      }
    },

    methods: {
      submitSong() {
        if(!(this.artistId && this.title && this.duration && this.year)) {
          this.$buefy.toast.open({message: 'please fill in all fields', type: 'is-primary'});
          return;
        }
        this.isLoading = true;
        api.postSong(this.artistId, this.title, this.duration, this.year, this.hotness).then((response) => {
          if(response.status === 201) {
            this.$buefy.toast.open({
              message: 'song is now in the database, it has been assigned id: ' + response.data.songid,
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
