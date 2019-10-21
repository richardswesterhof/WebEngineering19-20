<template>
  <section>
    <h2 class=is-subpage-title>Browse Artists By Popularity</h2>

    <div class="separator-line"></div>

    <b-loading :active.sync="isLoading" :can-cancel="true"></b-loading>


    <b-table
      :data="artists"
      class="is-table custom-centered">
      <template slot-scope="props">
        <b-table-column field="name" label="Name">
          <!-- prevent the text from being too long on one line -->
          <div style="max-width: 20em; word-wrap: break-word;">
            {{ props.row.name }}
          </div>
        </b-table-column>

        <b-table-column field="term" label="Genre">
          <!-- prevent the text from being too long on one line -->
          <div style="max-width: 10em; word-wrap: break-word;">
            {{ props.row.term }}
          </div>
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

      <template v-if="!this.isLoading" slot="empty">
        <div class="has-text-centered">
          <p>There are no artists matching those filters in the database :(</p>
          <p>Try making your filters more broad and make sure they don't contain any typos</p>
        </div>
      </template>
    </b-table>
  </section>
</template>

<script>
  import api from "../../../api/api";
  import TablePagination from "../../table-addons/TablePagination";

  export default {
    name: "PopularityArtists",
    components: {TablePagination},
    data() {
      return {
        isLoading: false,
        artists: [],
      }
    },

    methods: {
      refreshArtists() {
        this.isLoading = true;
        let tableFilters = [
          {filterName: 'pageSize', filterValue: this.$refs['table-pagination'].pageSize, displayName: 'page size'},
          {filterName: 'pageRank', filterValue: this.$refs['table-pagination'].currentPage - 1, displayName: 'page number'},
        ];
        api.getArtistsByPopularity(tableFilters).then((response) => {
          if(response.status === 200) {
            this.artists = response.data;
          }
          else {
            this.$buefy.toast.open({message: 'request failed with status code: ' + (response.status ? response.status : 'unknown status'), type: 'is-danger'});
            console.error(response);
          }
          this.isLoading = false;
        });
      },
    },
  }
</script>

<style scoped>

</style>
