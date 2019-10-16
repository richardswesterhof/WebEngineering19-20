<template>
  <div>
    <label><b>Add a filter</b></label>
    <b-field grouped style="width: 36em; margin-left: auto; margin-right: auto;">
      <b-dropdown v-model="selectedFilter" @input="updateFieldType">
        <button class="button" slot="trigger">
          <span>{{selectedFilter ? selectedFilter.displayName : '(select filter)'}}</span>
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
      <b-input v-model="filterValue"
               placeholder="filter value..."
               ref="filterValueInput"
               expanded>
      </b-input>
      <b-button class="button is-primary" @click="addFilter">add filter</b-button>
    </b-field>


    <b-taglist style="width:36em; margin-left: auto; margin-right:auto;">
      <template v-for="filter in filters">
        <b-tag type="is-primary" closable @close="removeFilter(filter)">
          {{Object.keys(filter)[0]}} = {{filter[Object.keys(filter)[0]]}}</b-tag>
      </template>
    </b-taglist>
  </div>
</template>

<script>
  export default {
    name: "FilterManager",

    props: {
      availableFilters: {
        type: Array,
        required: true,
      },
    },

    data() {
      return {
        selectedFilter: null,
        filters: [],
        filterValue: '',
      }
    },

    methods: {
      addFilter() {
        if(!this.selectedFilter){
          this.$buefy.toast.open({
            message: 'filter could not be added: please select a filter first',
            type: 'is-danger',
          });
          return;
        }
        let newFilter = {};
        newFilter[this.selectedFilter.value] = this.filterValue;
        for(let i = 0; i < this.filters.length; i++) {
          if(Object.keys(this.filters[i])[0] === this.selectedFilter.displayName) {
            this.filters.splice(i, 1);
            i--;
          }
        }
        this.filters.push(newFilter);
        this.filterValue = '';
        this.$emit('filter-update');
      },

      removeFilter(filter) {
        this.filters.splice(this.filters.indexOf(filter), 1);
        this.$emit('filter-update');
      },

      updateFieldType() {
        this.filterValue = '';
        this.$refs['filterValueInput'].$refs.input.setAttribute('type', this.selectedFilter.type);
      },
    }
  }
</script>

<style scoped>

</style>
