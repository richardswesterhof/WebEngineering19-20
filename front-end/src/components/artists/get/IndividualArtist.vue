<template>
  <section>
    <h2 class=is-subpage-title>Browse Artists By ID</h2>

    <label><b>Enter ID</b></label>
    <b-field grouped style="width:16em; margin-left: auto; margin-right:auto;">
      <b-input v-model="artistId"
               placeholder="artist ID..."
               type="number"
               min="1">
      </b-input>
      <b-button class="button is-primary" @click="getArtistById">retrieve</b-button>
    </b-field>

    <div class="separator-line"></div>

    <b-loading :active.sync="isLoading" :can-cancel="true"></b-loading>


    <b-table :data="artist ? [artist] : [{}]" class="is-table custom-centered">
      <template slot-scope="props">
        <b-table-column field="id" label="ID" width="40" numeric>
          {{ props.row.id }}
        </b-table-column>

        <b-table-column field="name" label="Name">
          <!-- prevent the text from being too long on one line -->
          <div style="max-width: 25em; word-wrap: break-word;">
            {{ props.row.name }}
          </div>
        </b-table-column>

        <b-table-column field="term" label="Genre">
          <!-- prevent the text from being too long on one line -->
          <div style="max-width: 15em; word-wrap: break-word;">
            {{ props.row.term }}
          </div>
        </b-table-column>
      </template>
    </b-table>

    {{artist === '' ? 'No artist found with id = ' + requestedId + ' :(' : ''}}
  </section>
</template>

<script>
  import api from "../../../api/api";

  export default {
    name: "IndividualArtist",

    data() {
      return {
        isLoading: false,

        artistId: null,
        requestedId: -1,
        artist: null,
      }
    },

    methods: {
      refreshArtists() {
        this.getArtistById()
      },

      getArtistById() {
        if(!this.artistId || this.artistId < 1) return;
        this.isLoading = true;
        this.requestedId = this.artistId;
        api.getArtist(this.artistId).then((response) => {
          if(response.status === 200) {
            this.artist = response.data;
          }
          else {
            this.$buefy.toast.open({message: 'request failed with status code: ' + (response.status ? response.status : 'unknown status'), type: 'is-danger'});
            console.error(response);
          }
          this.isLoading = false;
        });
      },
    },
  }
</script>

<style scoped>

</style>