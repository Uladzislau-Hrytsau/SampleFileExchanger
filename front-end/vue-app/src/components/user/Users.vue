<template>
  <div class="container">
    <div class="row">
      <UsersTable class="col-12"></UsersTable>
      <Pagination class="col-12"></Pagination>
    </div>
  </div>
</template>

<script>
  import Vue from 'vue'
  import {mdbDatatable} from 'mdbvue';
  import Pagination from './Pagination';
  import UsersTable from './UsersTable'

  export default {
    name: "Users",
    components: {
      Pagination,
      UsersTable,
      mdbDatatable
    },
    methods: {
      // retrieveUsers() {
      //   this.$store.dispatch('getUsers')
      //     .then(response => {
      //       this.users = response.data
      //     })
      //     .catch(error => {
      //       console.log(error)
      //     })
      // },
      // deleteUser(userId) {
      //   this.$store.dispatch('deleteUser', {
      //     userId: userId
      //   })
      //     .then(this.retrieveUsers())
      //     .catch(error => {
      //       console.log(error)
      //     })
      // },
      // updateUser(user) {
      //   this.$store.dispatch('saveUserInformation', {
      //     user: user
      //   })
      //     .catch(error => console.log(error));
      //   this.$router.push('/UpdateUser')
      // },
      retrieveUsersSize() {
        this.$store.dispatch('retrieveUsersSize')
          .catch(error => {
            console.log(error)
          });
      },
    },
    mounted() {
      this.retrieveUsersSize();
    }
  };
</script>

<style>

</style>
