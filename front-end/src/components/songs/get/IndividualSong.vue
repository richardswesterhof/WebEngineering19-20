<template>
  <section>
    <h2 class=is-subpage-title>Browse Songs By ID</h2>

    <label><b>Enter ID</b></label>
    <b-field grouped style="width:16em; margin-left: auto; margin-right:auto;">
      <b-input v-model="songId"
               placeholder="song ID..."
               type="number"
               min="1">
      </b-input>
      <b-button class="button is-primary" @click="getSongById">retrieve</b-button>
    </b-field>

    <b-table :data="song ? [song] : [{}]" class="is-table custom-centered">
      <template slot-scope="props">
        <b-table-column field="id" label="ID" width="40" numeric>
          {{ props.row.songid }}
        </b-table-column>

        <b-table-column field="title" label="Title">
          <!-- prevent the text from being too long on one line -->
          <div style="max-width: 15em; word-wrap: break-word;">
            {{ props.row.title }}
          </div>
        </b-table-column>

        <b-table-column field="artistName" label="Artist Name">
          <!-- prevent the text from being too long on one line -->
          <div style="max-width: 25em; word-wrap: break-word;">
            {{ props.row.artistName }}
          </div>
        </b-table-column>

        <b-table-column field="duration" label="Duration" width="40" numeric>
          {{ props.row.duration }}
        </b-table-column>

        <b-table-column field="year" label="Year" width="40" numeric>
          {{ props.row.year }}
        </b-table-column>
      </template>
    </b-table>

    {{song === '' ? 'No song found with id = ' + requestedId + ' :(' : ''}}
  </section>
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
