<template>
  <div class="tile is-ancestor">
    <div class="tile is-3" style="display: block;">
      <b-menu style="margin-left: 2em; max-width:12.5em;">
        <b-menu-list label="Browse">
          <b-menu-item
            label="Browse all artists"
            :active="$props.subpage === 'all'"
            @click="toggleSubpage('all')">
          </b-menu-item>
          <b-menu-item
            label="Browse artists by ID"
            :active="$props.subpage === 'individual'"
            @click="toggleSubpage('individual')">
          </b-menu-item>
          <b-menu-item
            label="Browse artist statistics"
            :active="$props.subpage === 'stats'"
            @click="toggleSubpage('stats')">
          </b-menu-item>
          <b-menu-item
            label="Browse artists by popularity"
            :active="$props.subpage === 'popular'"
            @click="toggleSubpage('popular')">
          </b-menu-item>
        </b-menu-list>

        <b-menu-list label="Add artist">
          <b-menu-item
            label="Add one artist"
            :active="$props.subpage === 'add'"
            @click="toggleSubpage('add')">
          </b-menu-item>
          <b-menu-item
            label="Import from csv file"
            @click="$router.push('/import')">
          </b-menu-item>
        </b-menu-list>

        <b-menu-list label="Replace artist">
          <b-menu-item
            label="Replace an artist"
            :active="$props.subpage === 'replace'"
            @click="toggleSubpage('replace')">
          </b-menu-item>
        </b-menu-list>

        <b-menu-list label="Remove artist">
          <b-menu-item
            label="Remove an artist"
            :active="$props.subpage === 'remove'"
            @click="toggleSubpage('remove')">
          </b-menu-item>

        </b-menu-list>
      </b-menu>
    </div>

    <div class="tile is-6" style="display:inline-block; text-align: center;">

      <!-- get requests -->
      <AllArtists :available-filters="availableFilters" v-show="$props.subpage === 'all'" ref="all" v-on:check-stats="checkStats($event)"></AllArtists>
      <IndividualArtist v-show="$props.subpage === 'individual'" ref="individual"></IndividualArtist>
      <ArtistStatistics v-show="$props.subpage === 'stats'" ref="stats"></ArtistStatistics>
      <PopularityArtists v-show="$props.subpage === 'popular'" ref="popular"></PopularityArtists>

      <!-- post requests -->
      <AddArtist v-show="$props.subpage === 'add'" ref="add"></AddArtist>

      <!-- put requests -->
      <UpdateArtist v-show="$props.subpage === 'replace'" ref="replace"></UpdateArtist>

      <!-- delete requests -->
      <RemoveArtist v-show="$props.subpage === 'remove'" ref="remove"></RemoveArtist>

    </div>
  </div>
</template>

<script>
  import FilterManager from "../table-addons/FilterManager";
  import AllArtists from "./get/AllArtists";
  import IndividualArtist from "./get/IndividualArtist";
  import AddArtist from "./post/AddArtist";
  import ArtistStatistics from "./get/ArtistStatistics";
  import UpdateArtist from "./put/UpdateArtist";
  import RemoveArtist from "./delete/RemoveArtist";
  import PopularityArtists from "./get/PopularityArtists";
  export default {
    name: "Artists",
    components: {
      PopularityArtists,
      RemoveArtist, UpdateArtist, ArtistStatistics, AddArtist, IndividualArtist, AllArtists, FilterManager},

    props: {
      subpage: {
        type: String,
        required: true,
      }
    },

    data() {
      return {
        availableFilters: [
          {displayName: 'name', value: 'artistName', type: 'text'},
          {displayName: 'genre', value: 'genre', type: 'text'},
        ],

        dataContainingRefs: ['all', 'individual', 'stats', 'popular'],
      }
    },

    beforeRouteEnter(to, from, next) {
      let subpage = to.params.subpage;
      //beforeRouteEnter doesn't have access to 'this' yet due to its place in the lifecycle
      //to get around this we can use a callback as follows:
      next(self => {
        if(!self.dataContainingRefs.includes(subpage)) {
          return;
        }

        self.refreshArtists(subpage);
      });
    },

    beforeRouteUpdate(to, from, next) {
      //if the page we are routing to doesn't contain data, it doesn't need to refresh
      let subpage = to.params.subpage;
      if(!this.dataContainingRefs.includes(subpage)) {
        next();
        return;
      }

      this.refreshArtists(subpage);

      //always call next in beforeRouteUpdate, otherwise the actual re-routing will never happen
      next();
    },

    methods : {
      toggleSubpage(name) {
        //if we are already there, do nothing
        if(this.$route.params.subpage === name) {
          return;
        }
        //otherwise we can re-route to the new component
        else {
          this.$router.push('/artists/' + name);
        }
      },

      refreshArtists(subpage) {
        //refresh the artist list, if possible
        if(this.$refs[subpage] && this.$refs[subpage].refreshArtists) {
          this.$refs[subpage].refreshArtists();
        }
        //if not give a warning
        else {
          console.warn('The following reference could not be found or did not have method refreshArtists: ' + subpage);
        }
      },

      //due to queries not working well when vue serves cached pages, we do this to force an update
      checkStats(id) {
        this.$refs['stats'].checkArtist(id);
        this.$router.push('/artists/stats');
      },
    },
  }
</script>

<style scoped>

</style>
