<template>
  <div class="container-fluid">
    <div class="row">
      <FilesTable class="cols-8 offset-2"></FilesTable>
      <Pagination class="col-6 offset-3"></Pagination>
    </div>
  </div>
</template>

<script>
  import Vue from 'vue'
  import {mdbDatatable} from 'mdbvue';
  import Pagination from './Pagination'
  import FilesTable from './FilesTable'
  import {mapState, mapGetters, mapActions, mapMutations} from 'vuex'

  export default {
    name: "files",
    components: {
      Pagination,
      FilesTable,
      mdbDatatable
    }
  };
</script>

<style scoped>

</style>
