<template>
  <section>
    <h2 class=is-subpage-title>Browse Artists By Popularity</h2>

    <FilterManager ref="filter-manager"
                   :available-filters="availableFilters"
                   v-on:requirements-met="refreshArtists()"
                   style="margin-bottom:0.5em;">
    </FilterManager>


    <div class="separator-line"></div>

    <b-loading :active.sync="isLoading" :can-cancel="true"></b-loading>


    <b-table :data="artists" class="is-table custom-centered">
      <template slot-scope="props">
        <b-table-column field="name" label="Name">
          {{ props.row.name }}
        </b-table-column>

        <b-table-column field="terms" label="Genre">
          {{ props.row.terms }}
        </b-table-column>
      </template>
    </b-table>

    <template v-if="!this.isLoading && artists.length === 0">
      <p>There are no artists matching those filters in the database :(</p>
      <p>Try making your filters more broad and make sure they don't contain any typos</p>
    </template>
  </section>
</template>

<script>
  import api from "../../../api/api";
  import FilterManager from "../../FilterManager";

  export default {
    name: "PopularityArtists",
    components: {FilterManager},

    data() {
      return {
        isLoading: false,
        artists: [],

        availableFilters: [
          {displayName: 'page size', value: 'pageSize', type: 'number', required: false},
          {displayName: 'page rank', value: 'pageRank', type: 'number', required: false},
        ],
      }
    },

    methods: {
      refreshArtists() {
        this.isLoading = true;
        api.getArtistsByPopularity(this.$refs['filter-manager'].filters).then((response) => {
          if(response.status === 200) {
            this.artists = response.data;
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
