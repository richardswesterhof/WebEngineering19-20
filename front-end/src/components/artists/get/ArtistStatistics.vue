<template>
  <section>
    <h2 class=is-subpage-title>Browse Artist Statistics</h2>

    <FilterManager ref="filter-manager"
                   :available-filters="availableFilters"
                   v-on:requirements-met="filterChanged"
                   style="margin-bottom:0.5em;"
    ></FilterManager>

    <div class="separator-line"></div>

    <b-loading :active.sync="isLoading" :can-cancel="true"></b-loading>


    <b-table :data="stats ? [stats] : [{}]" class="is-table custom-centered">
      <template slot-scope="props">
        <b-table-column field="id" label="ID" width="40" numeric>
          {{ stats ? requestedId : ''}}
        </b-table-column>

        <b-table-column field="mean" label="Mean" numeric>
          {{ stats ? props.row.mean.toFixed(3) : null }}
        </b-table-column>

        <b-table-column field="median" label="Median" numeric>
          {{ stats ? props.row.median.toFixed(3) : null }}
        </b-table-column>

        <b-table-column field="standardDeviation" label="Standard Deviation" numeric>
          {{ stats ? props.row.standardDeviation.toFixed(3) : null }}
        </b-table-column>
      </template>
    </b-table>

    {{stats === '' || stats === {} ? 'No artist found with id = ' + requestedId + ' :(' : ''}}
  </section>
</template>

<script>
  import api from "../../../api/api";
  import FilterManager from "../../table-addons/FilterManager";

  export default {
    name: "ArtistStatistics",
    components: {FilterManager},

    data() {
      return {
        isLoading: false,

        availableFilters: [
          {displayName: 'artist id', value: 'artistId', type: 'number', required: true},
          {displayName: 'year of release', value: 'year', type: 'number', required: false},
        ],

        requestedId: -1,
        stats: null,
      }
    },

    methods: {
      //provides and alias for refreshStats
      refreshArtists() {
        this.refreshStats();
      },

      refreshStats() {
        let filters = this.$refs['filter-manager'].filters;
        let artistid = null;
        let year = null;
        for(let i = 0; i < filters.length; i++) {
          if(filters[i].filterName === 'artistId') {
            artistid = filters[i].filterValue;
          }
          if(filters[i].filterName === 'year') {
            year = filters[i].filterValue;
          }
        }

        if(!artistid || artistid < 1) return;
        this.isLoading = true;

        api.getArtistStats(artistid, year).then((response) => {
          if(response.status === 200) {
            this.requestedId = artistid;
            this.stats = response.data;
          }
          else {
            this.$buefy.toast.open({message: 'request failed with status code: ' + (response.status ? response.status : 'unknown status'), type: 'is-danger'});
            console.error(response);
          }
        });
        this.isLoading = false;
      },

      filterChanged() {
        this.refreshStats();
      },

      checkArtist(artistid) {
        if(!artistid) return;
        let idFilter = {value: 'artistId', filterValue: artistid, displayName: 'artist id'};
        this.$refs['filter-manager'].addFilter(idFilter);
      }
    },
  }
</script>

<style scoped>

</style>
