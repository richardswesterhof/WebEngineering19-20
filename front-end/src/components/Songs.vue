<template>
  <div>
    <FilterManager ref="filter-manager"
                   :available-filters="availableFilters"
                   v-on:filter-update="refreshSongs"
    ></FilterManager>

    <div class="separator-line"></div>

    <b-loading :active.sync="isLoading" :can-cancel="true"></b-loading>
    <template v-for="song in songs">
      <Song :songData="song"></Song>
    </template>

    <template v-if="!this.isLoading && songs.length === 0">
      <p>There are no songs matching those filters in the database :(</p>
      <p>Try making your filters more broad and make sure they don't contain any typos</p>
    </template>
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
          {displayName: 'title', value: 'title'},
          {displayName: 'artist id', value: 'artistId'},
          {displayName: 'artist name', value: 'artistName'},
          {displayName: 'year of release', value: 'year'},
          {displayName: 'genre', value: 'genre'},
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
