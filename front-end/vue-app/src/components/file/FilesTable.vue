<template>
  <div>
    <mdb-tbl>
      <mdb-tbl-head>
        <tr>
          <th>id</th>
          <th>user id</th>
          <th>folder id</th>
          <th>description</th>
          <th>real name</th>
          <th>encoded name</th>
          <th>date</th>
        </tr>
      </mdb-tbl-head>
      <mdb-tbl-body>
        <tr v-for="file in files">
          <th>{{ file.id }}</th>
          <th>{{ file.userId }}</th>
          <td>{{ file.folderId }}</td>
          <td>{{ file.description }}</td>
          <td>{{ file.realName }}</td>
          <td>{{ file.encodeName }}</td>
          <td>{{ file.date }}</td>
          <td>
            <button class="btn fas fa-pencil-alt" v-on:click="enableUpdatingTemplate()"></button>
          </td>
          <td>
            <button class="btn fas fa-trash-alt" v-on:click="enableDeletingTemplate(file.id)"></button>
          </td>
        </tr>
      </mdb-tbl-body>
    </mdb-tbl>
  </div>
</template>

<script>
  import {mapState, mapGetters, mapActions, mapMutations} from 'vuex'
  import {mdbTbl, mdbTblHead, mdbTblBody, mdbAlert} from 'mdbvue';

  export default {
    name: "FilesTable",
    components: {
      mdbTbl,
      mdbTblHead,
      mdbTblBody,
      mdbAlert
    },
    data() {
      return {

      };
    },
    computed: {
      ...mapState([
        'files',
        'pageFile',
        'size',
      ]),
    },
    methods: {
      ...mapActions([
        'retrieveFiles',
        'deleteFile'
      ]),
      ...mapMutations([
        'enableFileDelete',
        'setId',
        'disableTableFiles',
        'disablePagination',
        'enableApprove'
      ]),
      enableUpdatingTemplate() {

      },
      enableDeletingTemplate(id) {
        this.enableFileDelete();
        this.setId(id);
        this.disableTableFiles();
        this.disablePagination();
        this.enableApprove();
      }
    },
    mounted() {
      this.retrieveFiles();
    }
  }
</script>

<style scoped>

</style>
