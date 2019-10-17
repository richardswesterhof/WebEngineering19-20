<template>
  <div>
    <FilterManager ref="filter-manager"
                   :available-filters="availableFilters"
                   v-on:requirements-met="refreshSongs()"
                   style="margin-bottom:0.5em;"
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
</template>

<script>
  import api from "../../../api/api";
  import FilterManager from "../../FilterManager";

  export default {
    name: "AllSongs",
    components: {FilterManager},

    props: {
      availableFilters: {
        type: Array,
        required: true,
      }
    },

    data() {
      return {
        songs: [],
        isLoading: true,
      }
    },

    mounted() {
      this.refreshSongs();
    },

    methods: {
      refreshSongs() {
        this.isLoading = true;
        api.getSongs(this.$refs['filter-manager'].filters).then((response) => {
          if(response.status === 200) {
            this.songs = response.data;
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
  .separator-line {
    width: 36em;
    border-bottom: 2px solid darkgray;
    margin-left: auto;
    margin-right: auto;
  }
</style>
