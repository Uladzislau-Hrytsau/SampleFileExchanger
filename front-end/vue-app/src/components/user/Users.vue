<template>
  <div class="container-fluid">
    <!--<div>-->
      <!--<mdb-container>-->
        <!--<mdb-btn color="default" class="mb-3" @click="confirm = true">lanuch modal</mdb-btn>-->

        <!--<mdb-modal v-if="confirm" @close="confirm = false" size="sm" class="text-center" danger>-->
          <!--<mdb-modal-header center :close="false">-->
            <!--<p class="heading">Are you sure?</p>-->
          <!--</mdb-modal-header>-->
          <!--<mdb-modal-body>-->
            <!--<mdb-icon icon="times" size="4x" class="animated rotateIn"/>-->
          <!--</mdb-modal-body>-->
          <!--<mdb-modal-footer center>-->
            <!--<mdb-btn outline="danger" @click="confirm = false">Yes</mdb-btn>-->
            <!--<mdb-btn color="danger" @click="confirm = false">No</mdb-btn>-->
          <!--</mdb-modal-footer>-->
        <!--</mdb-modal>-->
      <!--</mdb-container>-->
    <!--</div>-->
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
  import {
    mdbDatatable,
    mdbBtn,
    mdbContainer,
    mdbModal,
    mdbModalHeader,
    mdbModalBody,
    mdbModalFooter,
    mdbIcon
  } from 'mdbvue';
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
      mdbBtn,
      mdbContainer,
      mdbModal,
      mdbModalHeader,
      mdbModalBody,
      mdbModalFooter,
      mdbIcon
    },
    data() {
      return {
        confirm: false
      };
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
