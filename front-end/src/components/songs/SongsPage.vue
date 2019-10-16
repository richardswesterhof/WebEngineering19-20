<template>
  <div class="tile is-ancestor">
    <div class="tile is-3" style="display: block;">
      <b-menu style="margin-left: 2em; max-width:12.5em;">
        <b-menu-list label="Songs">
          <b-menu-item
            label="Browse all songs"
            :active="$props.subpage === 'all'"
            @click="toggleSubpage('all')">
          </b-menu-item>
          <b-menu-item
            label="Browse individual songs"
            :active="$props.subpage === 'individual'"
            @click="toggleSubpage('individual')">
          </b-menu-item>
        </b-menu-list>
      </b-menu>
    </div>


    <div class="tile is-6" style="display:inline-block; text-align: center;">
      <AllSongs :available-filters="availableFilters" v-show="$props.subpage === 'all'"></AllSongs>
      <IndividualSong v-show="$props.subpage === 'individual'"></IndividualSong>
    </div>
  </div>
</template>

<script>
  import FilterManager from "../FilterManager";
  import AllSongs from "./AllSongs";
  import IndividualSong from "./IndividualSong";

  export default {
    name: "Songs",
    components: {IndividualSong, AllSongs, FilterManager},
    props: {
      subpage: {
        type: String,
        required: true,
      },
    },

    data() {
      return {
        availableFilters: [
          {displayName: 'title', value: 'title', type: 'text'},
          {displayName: 'artist id', value: 'artistId', type: 'number'},
          {displayName: 'artist name', value: 'artistName', type: 'text'},
          {displayName: 'year of release', value: 'year', type: 'number'},
          {displayName: 'genre', value: 'genre', type: 'text'},
        ],
      }
    },

    methods: {
      toggleSubpage(name) {
        //if we are already there, do nothing
        if(this.$route.params.subpage === name) {
          return;
        }
        //otherwise we can re-route to the new component
        else {
          this.$router.push('/songs/' + name);
        }
      },
    },
  }
</script>

<style scoped>

</style>
