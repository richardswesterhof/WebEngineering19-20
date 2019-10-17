<template>
  <div>
    <b-field label="Artist ID" horizontal style="width: 36em; margin: 1.5em auto 0.5em;">
      <b-input v-model="artistId" type="number" min="1" placeholder="artist id...">
      </b-input>
    </b-field>

    <b-field label="Name" horizontal style="width: 36em; margin: 0.5em auto 0.5em;">
      <b-input v-model="name" placeholder="name...">
      </b-input>
    </b-field>

    <b-field label="Genre" horizontal style="width: 36em; margin: 0.5em auto 0.5em;">
      <b-input v-model="terms" placeholder="genre...">
      </b-input>
    </b-field>

    <b-button class="button is-primary" @click="updateArtist" style="margin-left: 29em;">replace artist</b-button>
  </div>
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
            this.$buefy.toast.open({message: 'request failed with status code: ' + response.status});
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
