<template>
  <div class="container-fluid">
    <div class="row">
      <Approve v-if="enabledApprove"></Approve>
      <UpdateUser v-if="enabledUserUpdate"></UpdateUser>
      <UsersTable class="col-8 offset-2" v-if="enabledTableUsers"></UsersTable>
      <Pagination class="col-6 offset-3" v-if="enabledPagination"></Pagination>
    </div>
  </div>
</template>

<script>
  import Vue from 'vue'
  import {mdbDatatable} from 'mdbvue';
  import Pagination from './Pagination';
  import UsersTable from './UsersTable'
  import Approve from '../aproving/Approve'
  import UpdateUser from './UpdateUser'
  import {mapState, mapGetters, mapActions, mapMutations} from 'vuex'

  import 'bootstrap-css-only/css/bootstrap.min.css';
  import 'mdbvue/build/css/mdb.css';
  import VueMaterial from 'vue-material'
  import 'vue-material/dist/vue-material.min.css'

  export default {
    name: "Users",
    components: {
      Pagination,
      UsersTable,
      mdbDatatable,
      Approve,
      UpdateUser,
    },
    computed: {
      ...mapState([
        'enabledPagination',
        'enabledTableUsers',
        'enabledApprove',
        'enabledUserUpdate'
      ])
    }
  };
</script>
<style>

</style>
