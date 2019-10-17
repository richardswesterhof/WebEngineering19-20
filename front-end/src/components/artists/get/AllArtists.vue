<template>
  <div>
    <FilterManager ref="filter-manager"
                   :available-filters="availableFilters"
                   v-on:requirements-met="refreshArtists('-f')"
                   style="margin-bottom:0.5em;"
    ></FilterManager>


    <div class="separator-line"></div>

    <b-loading :active.sync="isLoading" :can-cancel="true"></b-loading>


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
</template>

<script>
  import api from "../../../api/api";
  import FilterManager from "../../FilterManager";

  export default {
    name: "AllArtists",
    components: {FilterManager},

    props: {
      availableFilters: {
        type: Array,
        required: true,
      },
    },

    data() {
      return {
        cacheValid: true,

        artists: [],
        isLoading: true,
      }
    },

    mounted() {
      this.refreshArtists('-f');
    },

    methods: {
      refreshArtists(force) {
        if(this.cacheValid && force !== '-f') return;
        this.isLoading = true;
        api.getArtists(this.$refs['filter-manager'].filters).then((response) => {
          this.artists = response;
          this.isLoading= false;
          this.cacheValid = true;
        });
      },

      invalidateCache() {
        this.cacheValid = false;
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
