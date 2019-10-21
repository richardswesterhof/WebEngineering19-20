<template>
  <b-field grouped position="is-right">
    <b-dropdown v-model="pageSize" @input="$emit('page-size-change', pageSize)" aria-role="list" position="is-top-right" style="margin-right:3em;">
      <button class="button" slot="trigger">
        <span>page size: {{pageSize}}</span>
      </button>

      <template v-for="opt in pageSizeOptions">
        <b-dropdown-item aria-role="listitem" :value="opt" :key="opt">{{opt}}</b-dropdown-item>
      </template>

    </b-dropdown>

    <b-button @click="prevPage" :disabled="currentPage === 1" style="margin-right: 1em;">previous</b-button>
    <b-input v-model="currentPage" v-on:blur="$emit('page-change', currentPage)" type="number" min="1" style="max-width: 4em;"></b-input>
    <b-button @click="nextPage" :disabled="$props.dataLength === 0 || $props.dataLength < pageSize">next</b-button>
  </b-field>
</template>

<script>
  export default {
    name: "TablePagination",
    props: {
      dataLength: {
        required: true,
        type: Number,
      },
    },

    data() {
      return {
        pageSize: 5,
        currentPage: 1,

        pageSizeOptions: [5, 10, 15, 20, 25, 50],
      }
    },

    methods: {
      prevPage() {
        this.currentPage = Number(this.currentPage) - 1;
        this.$emit('page-change', this.currentPage);
      },

      nextPage() {
        this.currentPage = Number(this.currentPage) + 1;
        this.$emit('page-change', this.currentPage);
      },
    }
  }
</script>

<style scoped>

</style>
