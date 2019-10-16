<template>
  <div class="tile is-ancestor">
    <div class="tile is-3" style="display: block;">
      <b-menu style="margin-left: 2em; max-width:16em;">
        <b-menu-list label="Songs">
          <b-menu-item label="Browse all songs"></b-menu-item>
          <b-menu-item label="Browse individual songs"></b-menu-item>
        </b-menu-list>
      </b-menu>
    </div>


    <div class="tile is-6" style="display:inline-block; text-align: center;">
      <div>

        <FilterManager ref="filter-manager"
                       :available-filters="availableFilters"
                       v-on:filter-update="refreshSongs"
        ></FilterManager>

        <div class="separator-line"></div>

        <b-loading :active.sync="isLoading" :can-cancel="true"></b-loading>


        <b-table :data="songs" style="max-width:40em; margin-left:auto; margin-right:auto;">
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

        <template v-if="!this.isLoading && songs.length === 0">
          <p>There are no songs matching those filters in the database :(</p>
          <p>Try making your filters more broad and make sure they don't contain any typos</p>
        </template>

      </div>
    </div>
  </div>
</template>

<script>
  import api from "../api/api";
  import Song from "./Song";
  import FilterManager from "./FilterManager";

  export default {
    name: "Songs",
    components: {FilterManager, Song},
    data() {
      return {
        songs: [],
        isLoading: true,

        availableFilters: [
          {displayName: 'title', value: 'title', type: 'text'},
          {displayName: 'artist id', value: 'artistId', type: 'number'},
          {displayName: 'artist name', value: 'artistName', type: 'text'},
          {displayName: 'year of release', value: 'year', type: 'number'},
          {displayName: 'genre', value: 'genre', type: 'text'},
        ],
      }
    },

    mounted() {
      //console.log('i am called');
      this.refreshSongs();
    },

    methods: {
      refreshSongs() {
        this.isLoading = true;
        api.getSongs(this.$refs['filter-manager'].filters).then((response) => {
          this.songs = response;
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
