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
        </b-menu-list>
      </b-menu>
    </div>


    <div class="tile is-6" style="display:inline-block; text-align: center;">

      <!-- get requests -->
      <AllSongs :available-filters="availableFilters" v-show="$props.subpage === 'all'" ref="all"></AllSongs>
      <IndividualSong v-show="$props.subpage === 'individual'" ref="individual"></IndividualSong>

      <!-- post requests -->
      <AddSong v-show="$props.subpage === 'add'" ref="add"></AddSong>

      <!-- put requests -->
      <UpdateSong v-show="$props.subpage === 'replace'" ref="replace"></UpdateSong>
    </div>
  </div>
</template>

<script>
  import FilterManager from "../FilterManager";
  import AllSongs from "./get/AllSongs";
  import IndividualSong from "./get/IndividualSong";
  import AddSong from "./post/AddSong";
  import UpdateSong from "./put/UpdateSong";

  export default {
    name: "Songs",
    components: {UpdateSong, AddSong, IndividualSong, AllSongs, FilterManager},
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

        dataContainingRefs: ['all', 'individual'],
      }
    },

    beforeRouteUpdate(to, from, next) {
      //if the page we are routing to doesn't contain data, it doesn't need to refresh
      let subpage = to.params.subpage;
      if(!this.dataContainingRefs.includes(subpage)) {
        next();
        return;
      }

      //refresh the song list, if possible
      if(this.$refs[subpage] && this.$refs[subpage].refreshSongs) {
        this.$refs[subpage].refreshSongs();
      }
      //if not give a warning
      else {
        console.warn('The following reference could not be found or did not have method refreshSongs: ' + subpage);
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
    },
  }
</script>

<style scoped>

</style>
