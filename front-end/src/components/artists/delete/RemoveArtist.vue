<template>
  <section>
    <h2 class=is-subpage-title>Delete Artist</h2>

    <b-loading :active.sync="isLoading" :can-cancel="true"></b-loading>


    <b-field label="Artist ID" horizontal style="width: 40em; margin: 1.5em auto 0.5em;">
      <b-input v-model="artistid" type="number" min="1" placeholder="artist id...">
      </b-input>
    </b-field>

    <b-button class="button is-primary is-submit-button" @click="deleteArtist">delete artist</b-button>
  </section>
</template>

<script>
  import api from "../../../api/api";

  export default {
    name: "RemoveArtist",

    data() {
      return {
        isLoading: false,
        artistid: null,
      }
    },

    methods: {
      deleteArtist() {
        if(!this.artistid) {
          this.$buefy.toast.open({message: 'please enter an id', type: 'is-primary'});
          return;
        }
        this.isLoading = true;
        api.deleteArtist(this.artistid).then((response) => {
          if(response.status === 204) {
            this.$buefy.toast.open({message: 'artist was deleted successfully', type: 'is-success'});
            this.clearFields();
          }
          else {
            this.$buefy.toast.open({message: 'request failed with status code: ' + response.status})
          }
          this.isLoading = false;
        });
      },

      clearFields() {
        this.artistid = null;
      }
    },
  }
</script>

<style scoped>

</style>
