<template>
  <div class="container-fluid">
    <div class="row">
      <Approve v-if="enabledApprove"></Approve>
      <UpdateFile v-if="enabledFileUpdate"></UpdateFile>
      <FilesTable class="cols-8 offset-2" v-if="enabledTableFiles"></FilesTable>
      <Pagination class="col-6 offset-3" v-if="enabledPagination"></Pagination>
    </div>
  </div>
</template>

<script>
  import Vue from 'vue';
  import {mdbDatatable} from 'mdbvue';
  import Pagination from './Pagination';
  import FilesTable from './FilesTable';
  import Approve from '../aproving/Approve';
  import UpdateFile from './UpdateFile';
  import {mapState, mapGetters, mapActions, mapMutations} from 'vuex';

  export default {
    name: "files",
    components: {
      Pagination,
      FilesTable,
      Approve,
      UpdateFile,
      mdbDatatable
    },
    computed: {
      ...mapState([
        'enabledPagination',
        'enabledTableFiles',
        'enabledApprove',
        'enabledFileUpdate'
      ])
    }
  };
</script>

<style scoped>

</style>
