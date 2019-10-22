<template>
  <section>
    <h2 class=is-subpage-title>Browse Artists</h2>

    <FilterManager ref="filter-manager"
                   :available-filters="availableFilters"
                   v-on:requirements-met="filterChanged"
                   style="margin-bottom:0.5em;"
    ></FilterManager>


    <div class="separator-line"></div>

    <b-loading :active.sync="isLoading" :can-cancel="true"></b-loading>


    <b-table :data="artists" class="is-table custom-centered">
      <template slot-scope="props">
        <b-table-column field="id" label="ID" width="40" numeric>
          {{ props.row.id }}
        </b-table-column>

        <b-table-column field="name" label="Name">
          <!-- prevent the text from being too long on one line -->
          <div style="max-width: 20em; word-wrap: break-word;">
            {{ props.row.name }}
          </div>
        </b-table-column>

        <b-table-column field="term" label="Genre">
          <!-- prevent the text from being too long on one line -->
          <div style="max-width: 15em; word-wrap: break-word;">
            {{ props.row.term }}
          </div>
        </b-table-column>

        <b-table-column label="Statistics">
          <b-button @click="$emit('check-stats', props.row.id)">check statistics</b-button>
        </b-table-column>
      </template>

      <template slot="footer">
        <TablePagination
          :data-length="artists ? artists.length : 0"
          v-on:page-size-change="refreshArtists"
          v-on:page-change="refreshArtists"
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
            <p>There are no artists matching those filters in the database :(</p>
            <p>Try making your filters more broad and make sure they don't contain any typos</p>
          </div>
        </template>
      </template>
    </b-table>
  </section>
</template>

<script>
  import api from "../../../api/api";
  import FilterManager from "../../table-addons/FilterManager";
  import TablePagination from "../../table-addons/TablePagination";

  export default {
    name: "AllArtists",
    components: {FilterManager, TablePagination},

    props: {
      availableFilters: {
        type: Array,
        required: true,
      },
    },

    data() {
      return {
        artists: [],
        isLoading: true,
      }
    },

    methods: {
      refreshArtists() {
        this.isLoading = true;
        let tableFilters = [
          {filterName: 'pageSize', filterValue: this.$refs['table-pagination'].pageSize, displayName: 'page size'},
          {filterName: 'pageRank', filterValue: this.$refs['table-pagination'].currentPage - 1, displayName: 'page number'},
        ];
        let filters = this.$refs['filter-manager'].filters.concat(tableFilters);
        api.getArtists(filters).then((response) => {
          if(response.status === 200) {
            this.artists = response.data;
          }
          else {
            this.$buefy.toast.open({message: 'request failed with status code: ' + (response.status ? response.status : 'unknown status'), type: 'is-danger'});
            console.error(response);
          }
          this.isLoading= false;
        });
      },

      filterChanged() {
        this.$refs['table-pagination'].currentPage = 1;
        this.refreshArtists();
      },
    },
  }
</script>

<style scoped>

</style>
