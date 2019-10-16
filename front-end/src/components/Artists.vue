<template>
  <div>
    <FilterManager ref="filter-manager"
                   :available-filters="availableFilters"
                   v-on:filter-update="refreshArtists"
    ></FilterManager>


    <div class="separator-line"></div>

    <template v-for="artist in artists">
      <Artist :artist-data="artist"></Artist>
    </template>


    <template v-if="!this.isLoading && artists.length === 0">
      <p>There are no artists matching those filters in the database :(</p>
      <p>Try making your filters more broad and make sure they don't contain any typos</p>
    </template>

  </div>
</template>

<script>
  import FilterManager from "./FilterManager";
  import api from "../api/api";
  import Artist from "./Artist";
  export default {
    name: "Artists",
    components: {Artist, FilterManager},

    data() {
      return {
        artists: [],
        isLoading: true,

        availableFilters: [
          {displayName: 'name', value: 'name'},
          {displayName: 'genre', value: 'genre'},
        ],
      }
    },

    mounted() {
      this.refreshArtists();
    },

    methods: {
      refreshArtists() {
        this.isLoading = true;
        api.getArtists(this.$refs['filter-manager'].filters).then((response) => {
          this.artists = response;
        this.isLoading= false;
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
