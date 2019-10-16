<template>
  <div class="tile is-ancestor">
    <div class="tile is-3" style="display: block;">
      <b-menu style="margin-left: 2em; max-width:12.5em;">
        <b-menu-list label="Artists">
          <b-menu-item
            label="Browse all artists"
            :active="$props.subpage === 'all'"
            @click="toggleSubpage('all')">
          </b-menu-item>
          <b-menu-item
            label="Browse individual artists" :active="$props.subpage === 'individual'" @click="toggleSubpage('individual')"></b-menu-item>
        </b-menu-list>
      </b-menu>
    </div>

    <div class="tile is-6" style="display:inline-block; text-align: center;">

      <AllArtists :available-filters="availableFilters" v-show="$props.subpage === 'all'"></AllArtists>
      <IndividualArtist v-show="$props.subpage === 'individual'"></IndividualArtist>

    </div>
  </div>
</template>

<script>
  import FilterManager from "../FilterManager";
  import AllArtists from "./AllArtists";
  import IndividualArtist from "./IndividualArtist";
  export default {
    name: "Artists",
    components: {IndividualArtist, AllArtists, FilterManager},

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
    },
  }
</script>

<style scoped>

</style>
