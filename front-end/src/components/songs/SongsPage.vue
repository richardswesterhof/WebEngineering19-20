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

        <b-menu-list label="Add song">
          <b-menu-item
            label="Add one song"
            :active="$props.subpage === 'add'"
            @click="toggleSubpage('add')">
          </b-menu-item>
          <b-menu-item
            label="Import from csv file"
            :active="$props.subpage === 'import'"
            @click="toggleSubpage('import')">
          </b-menu-item>
        </b-menu-list>

        <b-menu-list label="Replace song">
          <b-menu-item
            label="Replace a song"
            :active="$props.subpage === 'replace'"
            @click="toggleSubpage('replace')">
          </b-menu-item>
<!--          <b-menu-item-->
<!--            label="Import from csv file"-->
<!--            :active="$props.subpage === 'import'"-->
<!--            @click="toggleSubpage('import')">-->
<!--          </b-menu-item>-->
        </b-menu-list>
      </b-menu>
    </div>


    <div class="tile is-6" style="display:inline-block; text-align: center;">
      <AllSongs :available-filters="availableFilters" v-show="$props.subpage === 'all'" ref="songList"></AllSongs>
      <IndividualSong v-show="$props.subpage === 'individual'"></IndividualSong>

      <AddSong v-show="$props.subpage === 'add'" v-on:song-addition="invalidateSongCache"></AddSong>
    </div>
  </div>
</template>

<script>
  import FilterManager from "../FilterManager";
  import AllSongs from "./get/AllSongs";
  import IndividualSong from "./get/IndividualSong";
  import AddSong from "./post/AddSong";

  export default {
    name: "Songs",
    components: {AddSong, IndividualSong, AllSongs, FilterManager},
    props: {
      subpage: {
        type: String,
        required: true,
      },
    },

    data() {
      return {
        cacheValid: true,
        availableFilters: [
          {displayName: 'title', value: 'title', type: 'text'},
          {displayName: 'artist id', value: 'artistId', type: 'number'},
          {displayName: 'artist name', value: 'artistName', type: 'text'},
          {displayName: 'year of release', value: 'year', type: 'number'},
          {displayName: 'genre', value: 'genre', type: 'text'},
        ],
      }
    },

    beforeRouteUpdate(to, from, next) {
      //if we open the list of songs, refresh the song list
      if(to.params.subpage === 'all') {
        this.$refs.songList.refreshSongs();
      }
      //always call next in beforeRouteUpdate, otherwise the actual re-routing will never happen
      next();
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

      invalidateSongCache() {
        this.$refs['songList'].invalidateCache();
      },
    },
  }
</script>

<style scoped>

</style>
