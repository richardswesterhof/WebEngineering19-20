<template>
  <div>
    <FilterManager ref="filter-manager"
                   :available-filters="availableFilters"
                   v-on:requirements-met="refreshStats"
                   style="margin-bottom:0.5em;"
    ></FilterManager>

    <div class="separator-line"></div>

    <b-loading :active.sync="isLoading" :can-cancel="true"></b-loading>


    <b-table :data="stats ? [stats] : [{}]" style="max-width:40em; margin-left:auto; margin-right:auto;">
      <template slot-scope="props">
        <b-table-column field="id" label="ID" width="40" numeric>
          {{ stats ? requestedId : ''}}
        </b-table-column>

        <b-table-column field="mean" label="Mean" numeric>
          {{ props.row.mean }}
        </b-table-column>

        <b-table-column field="median" label="Median" numeric>
          {{ props.row.median }}
        </b-table-column>

        <b-table-column field="standardDeveation" label="Standard Deviation" numeric>
          {{ props.row.standardDeveation }}
        </b-table-column>
      </template>
    </b-table>

    {{stats === '' || stats === {} ? 'No artist found with id = ' + requestedId + ' :(' : ''}}
  </div>
</template>

<script>
  import api from "../../../api/api";
  import FilterManager from "../../FilterManager";

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

        artistId: null,
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
        if(!this.artistId || this.artistId < 1) return;
        this.isLoading = true;
        let filters = this.$refs['filter-manager'].filters;
        let artistid;
        let year;
        for(let i = 0; i < filters.length; i++) {
          if(filters[i].filterName === 'artistId') {
            artistid = filters[i].filterValue;
          }
          if(filters[i].filterName === 'year') {
            year = filters[i].filterValue;
          }
        }

        api.getArtistStats(artistid, year).then((response) => {
          this.requestedId = artistid;
          this.stats = response;
          this.isLoading = false;
        });
      }
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