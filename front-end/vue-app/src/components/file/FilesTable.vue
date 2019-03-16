<template>
  <div v-if="approved">
    <mdb-alert color="primary">
      <button class="btn" v-on:click="deleteAfterApprove(id)">approve</button>
      <button class="btn" v-on:click="cancelApprove">cancel</button>
    </mdb-alert>
  </div>
  <div v-else>
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
            <button class="btn fas fa-pencil-alt"></button>
          </td>
          <td>
            <button class="btn fas fa-trash-alt" v-on:click="approve(file.id)"></button>
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
        approved: false,
        id: '',
      };
    },
    computed: {
      ...mapState(['files', 'pageFile', 'size']),
    },
    methods: {
      ...mapActions(['retrieveFiles', 'deleteFile']),
      approve(id) {
        this.approved = true;
        this.id = id
      },
      cancelApprove() {
        this.approved = false;
      },
      async deleteAfterApprove(id) {
        await this.deleteFile(id);
        this.approved = false;
        await this.retrieveFiles();
      },
    },
    mounted() {
      this.$store.dispatch('retrieveFiles').catch(error => {
        console.log(error)
      })
    }
  }
</script>

<style scoped>

</style>
