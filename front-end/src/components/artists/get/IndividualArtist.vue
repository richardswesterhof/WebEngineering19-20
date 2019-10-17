<template>
  <div>
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


    <b-table :data="artist ? [artist] : [{}]" style="max-width:40em; margin-left:auto; margin-right:auto;">
      <template slot-scope="props">
        <b-table-column field="id" label="ID" width="40" numeric>
          {{ props.row.id }}
        </b-table-column>

        <b-table-column field="name" label="Name">
          {{ props.row.name }}
        </b-table-column>

        <b-table-column field="genre" label="Genre">
          {{ props.row.term }}
        </b-table-column>
      </template>
    </b-table>

    {{artist === '' ? 'No artist found with id = ' + requestedId + ' :(' : ''}}
  </div>
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
      getArtistById() {
        this.isLoading = true;
        this.requestedId = this.artistId;
        api.getArtist(this.artistId).then((response) => {
          this.artist = response;
          this.isLoading = false;
        });
      },
    },
  }
</script>

<style scoped>
  .separator-line {
    width: 36em;
    border-bottom: 2px solid darkgray;
    margin-left: auto;
    margin-right: auto;
  }
</style>
