<template>
  <section>
    <h2 class=is-subpage-title>Replace Artist</h2>

    <b-field class="is-input is-first" label="Artist ID" horizontal>
      <b-input v-model="artistId" type="number" min="1" placeholder="artist id...">
      </b-input>
    </b-field>

    <b-field class="is-input" label="Name" horizontal>
      <b-input v-model="name" placeholder="name...">
      </b-input>
    </b-field>

    <b-field class="is-input" label="Genre" horizontal>
      <b-input v-model="terms" placeholder="genre...">
      </b-input>
    </b-field>

    <b-button class="button is-primary is-submit-button" @click="updateArtist">replace artist</b-button>
  </section>
</template>

<script>
  import api from "../../../api/api";

  export default {
    name: "UpdateArtist",

    data() {
      return {
        isLoading: false,

        artistId: null,
        name: null,
        terms: null,
      }
    },

    methods: {
      updateArtist() {
        if(!(this.artistId && this.name && this.terms)) {
          this.$buefy.toast.open({message: 'please fill in all fields', type: 'is-primary'});
          return;
        }
        this.isLoading = true;
        api.putArtist(this.artistId, this.name, this.terms).then((response) => {
          if(response.status === 200) {
            this.$buefy.toast.open({
              message: 'artist was successfully updated',
              type: 'is-success',
            });
            this.clearFields();
          }
          else {
            this.$buefy.toast.open({message: 'request failed with status code: ' + (response.status ? response.status : 'unknown status'), type: 'is-danger'});
            console.error(response);
          }
          this.isLoading = false;
        });
      },

      clearFields() {
        this.artistId = null;
        this.name = null;
        this.terms = null;
      },
    },
  }
</script>

<style scoped>

</style>
