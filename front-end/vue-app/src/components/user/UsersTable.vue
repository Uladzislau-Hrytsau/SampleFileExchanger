<template>
  <div>
    <mdb-tbl>
      <mdb-tbl-head>
        <tr>
          <th>id</th>
          <th>login</th>
          <th>password</th>
          <th>gender</th>
          <th>birth date</th>
          <th>information</th>
        </tr>
      </mdb-tbl-head>
      <mdb-tbl-body>
        <tr v-for="user in users">
          <th>{{ user.id }}</th>
          <th>{{ user.name }}</th>
          <td>{{ user.password }}</td>
          <td>{{ user.gender }}</td>
          <td>{{ user.birthDate }}</td>
          <td>{{ user.information }}</td>
        </tr>
      </mdb-tbl-body>
    </mdb-tbl>
  </div>
</template>

<script>
  import {mapState, mapMutations, mapActions} from 'vuex'
  import { mdbTbl, mdbTblHead, mdbTblBody} from 'mdbvue';
  export default {
    name: "UsersTable",
    components: {
      mdbTbl,
      mdbTblHead,
      mdbTblBody
    },
    data() {
      return {};
    },
    computed: {
      ...mapState(['users', 'page', 'size']),
    },
    methods: {
      ...mapActions(['retrieveUsers'])
    },
    mounted() {
      this.$store.dispatch('retrieveUsers').catch(error => {
        console.log(error)
      })
    }
  }
</script>

<style scoped>

</style>
