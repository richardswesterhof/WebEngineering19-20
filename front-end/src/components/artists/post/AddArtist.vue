<template>
  <section>
    <h2 class=is-subpage-title>Add Artist</h2>

    <b-loading :active.sync="isLoading" :can-cancel="true"></b-loading>


    <b-field class="is-input is-first" label="Name" horizontal>
      <b-input v-model="name" placeholder="name...">
      </b-input>
    </b-field>

    <b-field class="is-input" label="Genre" horizontal>
      <b-input v-model="terms" placeholder="genre...">
      </b-input>
    </b-field>

    <b-field class="is-input" label="Hotness" horizontal>
      <b-input v-model="hotness" placeholder="hotness..." type="number" step="0.001" min="0" max="1">
      </b-input>
    </b-field>

    <b-button class="button is-primary is-submit-button" @click="submitArtist">submit artist</b-button>

    <b-button v-show="assignedId" class="button is-primary is-medium" @click="checkArtist(assignedId)">Check out new artist</b-button>
  </section>
</template>

<script>
  import api from "../../../api/api";

  export default {
    name: "AddArtist",

    data() {
      return {
        isLoading: false,

        name: null,
        terms: null,
        hotness: null,

        assignedId: null,
      }
    },

    methods: {
      submitArtist() {
        if(!(this.name && this.terms && this.hotness)) {
          this.$buefy.toast.open({message: 'please fill in all fields', type: 'is-primary'});
          return;
        }
        this.isLoading = true;
        api.postArtist(this.name, this.terms, this.hotness).then((response) => {
          if(response.status === 201) {
            this.$buefy.toast.open({
              message: 'artist is now in the database, it has been assigned id: ' + response.data.artistid,
              type: 'is-success',
            });
            this.clearFields();
            this.assignedId = response.data.artistid;
          }
          else {
            this.$buefy.toast.open({message: 'request failed with status code: ' + (response.status ? response.status : 'unknown status'), type: 'is-danger'});
            console.error(response);
          }
        });
        this.isLoading = false;
      },

      clearFields() {
        this.name = null;
        this.terms = null;
        this.hotness = null;
      },

      checkArtist(artistid) {
        this.$emit('check-artist', artistid);
      },
    },
  }
</script>

<style scoped>

</style>
