<template>
  <div>
    <label><b>Add a filter</b></label>
    <b-field grouped style="width: 36em; margin-left: auto; margin-right: auto;">
      <b-dropdown v-model="selectedFilter">
        <button class="button" slot="trigger">
          <span>{{selectedFilter ? selectedFilter.displayName : '(select filter)'}}</span>
          <b-icon icon="menu-down"></b-icon>
        </button>
        <template v-for="filter in availableFilters">
          <b-dropdown-item
            aria-role="listitem"
            :key="filter.displayName"
            :value="filter">
              {{filter.displayName}}</b-dropdown-item>
        </template>
      </b-dropdown>
      <span style="width: 2em; margin-top:0.5em;">=</span>
      <b-input v-model="filterValue" expanded></b-input>
      <b-button class="button is-primary" @click="addFilter">add filter</b-button>
    </b-field>

    <b-taglist style="width:36em; margin-left: auto; margin-right:auto;">
      <template v-for="filter in filters">
        <b-tag type="is-info" closable @close="removeFilter(filter)">
          {{Object.keys(filter)[0]}} = {{filter[Object.keys(filter)[0]]}}</b-tag>
      </template>
    </b-taglist>


    <div class="separator-line"></div>


    <b-loading :active.sync="isLoading" :can-cancel="true"></b-loading>
    <template v-for="song in songs">
      <Song :songData="song"></Song>
    </template>
    <p v-if="!this.isLoading && songs.length === 0">There are no songs in the database :(</p>
  </div>
</template>

<script>
  import api from "../api/api";
  import Song from "./Song";

  export default {
    name: "Songs",
    components: {Song},
    data() {
      return {
        availableFilters: [
          {displayName: 'title', value: 'title'},
          {displayName: 'artist id', value: 'artistId'},
          {displayName: 'artist name', value: 'artistName'},
          {displayName: 'year of release', value: 'year'},
          {displayName: 'genre', value: 'genre'},
        ],
        selectedFilter: null,
        filters: [],
        filterValue: '',
        songs: [],
        isLoading: true,
      }
    },

    mounted() {
      //console.log('i am called');
      this.refreshSongs();
    },

    methods: {
      refreshSongs() {
        this.isLoading = true;
        api.getSongs(this.filters).then((response) => {
          this.songs = response;
          this.isLoading = false;
        });
      },


      addFilter() {
        if(!this.selectedFilter) return;
        let newFilter = {};
        newFilter[this.selectedFilter.value] = this.filterValue;
        for(let i = 0; i < this.filters.length; i++) {
          if(Object.keys(this.filters[i])[0] === this.selectedFilter.displayName) {
            this.filters.splice(i, 1);
            i--;
          }
        }
        this.filters.push(newFilter);
        this.refreshSongs();
      },

      removeFilter(filter) {
        this.filters.splice(this.filters.indexOf(filter), 1);
        this.refreshSongs();
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
