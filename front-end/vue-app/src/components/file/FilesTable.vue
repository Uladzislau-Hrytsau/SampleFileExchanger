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
        </tr>
      </mdb-tbl-body>
    </mdb-tbl>
  </div>
</template>

<script>
  import {mapState, mapGetters, mapActions, mapMutations} from 'vuex'
  import {mdbTbl, mdbTblHead, mdbTblBody} from 'mdbvue';

  export default {
    name: "FilesTable",
    components: {
      mdbTbl,
      mdbTblHead,
      mdbTblBody
    },
    data() {
      return {};
    },
    computed: {
      ...mapState(['files', 'pageFile', 'size']),
    },
    methods: {
      ...mapActions(['retrieveFiles'])
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
