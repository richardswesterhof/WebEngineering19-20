<template>
  <div class="tile is-ancestor">
    <div class="tile is-3" style="display: block;">
      <b-menu style="margin-left: 2em; max-width:12.5em;">
        <b-menu-list label="Browse">
          <b-menu-item
            label="Browse all songs"
            :active="$props.subpage === 'all'"
            @click="toggleSubpage('all')">
          </b-menu-item>
          <b-menu-item
            label="Browse songs by ID"
            :active="$props.subpage === 'individual'"
            @click="toggleSubpage('individual')">
          </b-menu-item>
          <b-menu-item
            label="Browse songs by popularity"
            :active="$props.subpage === 'popular'"
            @click="toggleSubpage('popular')">
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
            @click="$router.push('/import')">
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
      <AllSongs v-show="$props.subpage === 'all'" ref="all"></AllSongs>
      <IndividualSong v-show="$props.subpage === 'individual'" ref="individual"></IndividualSong>
      <PopularitySongs v-show="$props.subpage === 'popular'" ref="popular"></PopularitySongs>

      <!-- post requests -->
      <AddSong v-show="$props.subpage === 'add'" ref="add"></AddSong>

      <!-- put requests -->
      <UpdateSong v-show="$props.subpage === 'replace'" ref="replace"></UpdateSong>
    </div>
  </div>
</template>

<script>
  import FilterManager from "../table-addons/FilterManager";
  import AllSongs from "./get/AllSongs";
  import IndividualSong from "./get/IndividualSong";
  import AddSong from "./post/AddSong";
  import UpdateSong from "./put/UpdateSong";
  import PopularitySongs from "./get/PopularitySongs";

  export default {
    name: "Songs",
    components: {PopularitySongs, UpdateSong, AddSong, IndividualSong, AllSongs, FilterManager},
    props: {
      subpage: {
        type: String,
        required: true,
      },
    },

    data() {
      return {
        dataContainingRefs: ['all', 'individual', 'popular'],
      }
    },

    beforeRouteEnter(to, from, next) {
      //beforeRouteEnter doesn't have access to 'this' yet due to its place in the lifecycle
      //to get around this we can use a callback as follows:
      let subpage = to.params.subpage;
      next(self => {
        if(!self.dataContainingRefs.includes(subpage)) {
          return;
        }

        self.refreshSongs(subpage);
      });
    },

    beforeRouteUpdate(to, from, next) {
      //if the page we are routing to doesn't contain data, it doesn't need to refresh
      let subpage = to.params.subpage;
      if(!this.dataContainingRefs.includes(subpage)) {
        next();
        return;
      }

      this.refreshSongs(subpage);

      //always call next in beforeRouteEnter, otherwise the actual re-routing will never happen
      next();
    },

    methods: {
      toggleSubpage(name) {
        //only re-route if we aren't there yet
        if(this.$route.params.subpage !== name) {
          this.$router.push('/songs/' + name);
        }
      },

      refreshSongs(subpage) {
        //refresh the song list, if possible
        if(this.$refs[subpage] && this.$refs[subpage].refreshSongs) {
          this.$refs[subpage].refreshSongs();
        }
        //if not give a warning
        else {
          console.warn('The following reference could not be found or did not have method refreshSongs: ' + subpage);
        }
      },
    },
  }
</script>

<style scoped>

</style>
