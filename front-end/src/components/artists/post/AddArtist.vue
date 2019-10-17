<template>
  <section>
    <b-loading :active.sync="isLoading" :can-cancel="true"></b-loading>


    <b-field label="Name" horizontal style="width: 36em; margin: 1.5em auto 0.5em;">
      <b-input v-model="name" placeholder="name...">
      </b-input>
    </b-field>

    <b-field label="Genre" horizontal style="width: 36em; margin: 0.5em auto 0.5em;">
      <b-input v-model="terms" placeholder="genre...">
      </b-input>
    </b-field>

    <b-button class="button is-primary" @click="submitArtist" style="margin-left: 29em;">submit artist</b-button>
  </section>
</template>

<script>
  import api from "../../../api/api";

  export default {
    name: "AddArtist",

    data() {
      return {
        isLoading: false,

        name: '',
        terms: '',
      }
    },

    methods: {
      submitArtist() {
        this.isLoading = true;
        api.postArtist(this.name, this.terms).then((response) => {
          console.log(response);
          this.isLoading = false;
          this.$buefy.toast.open({
            message: 'artist is now in the database',
            type: 'is-success',
          });
          this.$emit('artist-addition');
        });
      },
    },
  }
</script>

<style scoped>

</style>
