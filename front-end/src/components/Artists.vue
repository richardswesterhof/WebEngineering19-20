<template>
  <div class="tile is-ancestor">
    <div class="tile is-3" style="display: block;">
      <b-menu style="margin-left: 2em;">
        <b-menu-list label="Artists">
          <b-menu-item label="Browse all artists"></b-menu-item>
          <b-menu-item label="Browse individual artists"></b-menu-item>
        </b-menu-list>
      </b-menu>
    </div>

    <div class="tile is-6" style="display:inline-block; text-align: center;">
      <FilterManager ref="filter-manager"
                     :available-filters="availableFilters"
                     v-on:filter-update="refreshArtists"
      ></FilterManager>


      <div class="separator-line"></div>

      <b-table :data="artists" style="max-width:40em; margin-left:auto; margin-right:auto;">
        <template slot-scope="props">
          <b-table-column field="id" label="ID" width="40" numeric>
            {{ props.row.id }}
          </b-table-column>

          <b-table-column field="name" label="Name">
            {{ props.row.name }}
          </b-table-column>

          <b-table-column field="term" label="Genre">
            {{ props.row.term }}
          </b-table-column>
        </template>
      </b-table>


      <template v-if="!this.isLoading && artists.length === 0">
        <p>There are no artists matching those filters in the database :(</p>
        <p>Try making your filters more broad and make sure they don't contain any typos</p>
      </template>
    </div>
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
          {displayName: 'name', value: 'name', type: 'text'},
          {displayName: 'genre', value: 'genre', type: 'number'},
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
