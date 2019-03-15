<template>
  <div class="container-fluid">
    <div class="row">
      <UsersTable class="col-8 offset-2"></UsersTable>
      <Pagination class="col-6 offset-3"></Pagination>
    </div>
  </div>
</template>

<script>
  import Vue from 'vue'
  import {mdbDatatable} from 'mdbvue';
  import Pagination from './Pagination';
  import UsersTable from './UsersTable'
  import {mapState, mapMutations, mapActions} from 'vuex'

  export default {
    name: "Users",
    components: {
      Pagination,
      UsersTable,
      mdbDatatable,
    },
  };
</script>
<style>

</style>
