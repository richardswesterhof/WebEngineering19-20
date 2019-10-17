<template>
  <div>
    <label><b>Enter ID</b></label>
    <b-field grouped style="width:16em; margin-left: auto; margin-right:auto;">
      <b-input v-model="songId"
               placeholder="song ID..."
               type="number"
               min="1">
      </b-input>
      <b-button class="button is-primary" @click="getSongById">retrieve</b-button>
    </b-field>

    <b-table :data="song ? [song] : [{}]" style="max-width:40em; margin-left:auto; margin-right:auto;">
      <template slot-scope="props">
        <b-table-column field="id" label="ID" width="40" numeric>
          {{ props.row.songid }}
        </b-table-column>

        <b-table-column field="title" label="Title">
          {{ props.row.title }}
        </b-table-column>

        <b-table-column field="artistName" label="Artist Name">
          {{ props.row.artistName }}
        </b-table-column>
      </template>
    </b-table>

    {{song === '' ? 'No song found with id = ' + requestedId + ' :(' : ''}}
  </div>
</template>

<script>
  import api from "../../../api/api";

  export default {
    name: "IndividualSong",

    data() {
      return {
        isLoading: false,

        songId: null,
        requestedId: -1,
        song: null,
      }
    },

    methods: {
      //provides an alias for getSongById
      refreshSongs() {
        this.getSongById();
      },

      getSongById() {
        if(!this.songId || this.songId < 1) return;
        this.isLoading = true;
        this.requestedId = this.songId;
        api.getSong(this.songId).then((response) => {
          if(response.status === 200) {
            this.song = response.data;
          }
          else {
            this.$buefy.toast.open({message: 'request failed with status code: ' + response.status, type: 'is-danger'});
          }
          this.isLoading = false;
        });
      },
    },
  }
</script>

<style scoped>

</style>
