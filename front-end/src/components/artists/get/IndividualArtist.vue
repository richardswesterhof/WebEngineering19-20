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
        <b-table-column field="artistid" label="ID" width="40" numeric>
          {{ props.row.artistid }}
        </b-table-column>

        <b-table-column field="name" label="Name">
          <!-- prevent the text from being too long on one line -->
          <div style="max-width: 25em; word-wrap: break-word;">
            {{ props.row.name }}
          </div>
        </b-table-column>

        <b-table-column field="terms" label="Genre">
          <!-- prevent the text from being too long on one line -->
          <div style="max-width: 15em; word-wrap: break-word;">
            {{ props.row.terms }}
          </div>
        </b-table-column>

        <b-table-column field="hotness" label="Hotness" width="20" numeric>
          {{ artist ? props.row.hotness.toFixed(3) : null }}
        </b-table-column>
      </template>
    </b-table>

    {{artist === '' ? 'No artist found with id = ' + requestedId + ' :(' : ''}}




    <div v-show="artist && artist.songs" id="artistsSongs" style="margin-top: 2em;">

      <h3 class="is-subpage-subtitle">{{artist ? artist.name : ''}}'s songs</h3>

      <b-table :data="songSubSet" class="is-table custom-centered">
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
            <div style="max-width: 20em; word-wrap: break-word;">
              {{ props.row.artistName }}
            </div>
          </b-table-column>

          <b-table-column field="duration" label="Duration" width="40" numeric>
            {{ props.row.duration.toFixed(3) }}
          </b-table-column>

          <b-table-column field="year" label="Year" width="40" numeric>
            {{ props.row.year }}
          </b-table-column>
        </template>

        <template slot="footer">
          <TablePagination
            :data-length="songSubSet ? songSubSet.length : 0"
            v-on:page-size-change="changeSongSubSet"
            v-on:page-change="changeSongSubSet"
            ref="table-pagination">
          </TablePagination>
        </template>

        <template slot="empty">
          <template v-if="$refs && $refs['table-pagination'] ? $refs['table-pagination'].currentPage > 1 : false" class="has-text-centered">
            <div class="has-text-centered">
              <p>Oops, it looks like that was the last page</p>
            </div>
          </template>

          <template v-else-if="!this.isLoading">
            <div class="has-text-centered">
              It looks like {{artist ? artist.name : 'this artist'}} doesn't have any songs in the database
              <br>
              Try adding a song to their name at the <router-link to="/songs/add"><a>add song page</a></router-link>
            </div>
          </template>
        </template>
      </b-table>
    </div>


  </section>
</template>

<script>
  import api from "../../../api/api";
  import TablePagination from "../../table-addons/TablePagination";

  export default {
    name: "IndividualArtist",
    components: {TablePagination},

    data() {
      return {
        isLoading: false,

        artistId: null,
        requestedId: -1,
        artist: null,

        songPage: 1,
        songSubSet: [],
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
            this.changeSongSubSet();
          }
          else {
            this.$buefy.toast.open({message: 'request failed with status code: ' + (response.status ? response.status : 'unknown status'), type: 'is-danger'});
            console.error(response);
          }
        });
        this.isLoading = false;
      },

      changeSongSubSet() {
        let tp = this.$refs['table-pagination'];
        this.songSubSet = this.artist.songs.slice((tp.currentPage - 1) * tp.pageSize, tp.currentPage * tp.pageSize);
      },

      checkArtist(artistid) {
        if(!artistid) return;
        this.artistId = artistid;
        this.getArtistById();
      },
    },
  }
</script>

<style scoped>

</style>
