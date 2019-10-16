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
            label="Browse individual artists"
            :active="$props.subpage === 'individual'"
            @click="toggleSubpage('individual')">
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
            :active="$props.subpage === 'import'"
            @click="toggleSubpage('import')">
          </b-menu-item>
        </b-menu-list>

        <b-menu-list label="Replace artist">
          <b-menu-item
            label="Replace an artist"
            :active="$props.subpage === 'replace'"
            @click="toggleSubpage('replace')">
          </b-menu-item>
<!--          <b-menu-item-->
<!--            label="Import from csv file"-->
<!--            :active="$props.subpage === 'import'"-->
<!--            @click="toggleSubpage('import')">-->
<!--          </b-menu-item>-->
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

      <AllArtists :available-filters="availableFilters" v-show="$props.subpage === 'all'" ref="artistList"></AllArtists>
      <IndividualArtist v-show="$props.subpage === 'individual'"></IndividualArtist>

      <AddArtist v-show="$props.subpage === 'add'" v-on:artist-addition="invalidateArtistCache"></AddArtist>

    </div>
  </div>
</template>

<script>
  import FilterManager from "../FilterManager";
  import AllArtists from "./AllArtists";
  import IndividualArtist from "./IndividualArtist";
  import AddArtist from "./AddArtist";
  export default {
    name: "Artists",
    components: {AddArtist, IndividualArtist, AllArtists, FilterManager},

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
      }
    },

    beforeRouteUpdate(to, from, next) {
      //if we open the list of artists, refresh the artist list
      if(to.params.subpage === 'all') {
        this.$refs.artistList.refreshArtists();
      }
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

      invalidateArtistCache() {
        this.$refs['artistList'].invalidateCache();
      }
    },
  }
</script>

<style scoped>

</style>
