<template>
  <div class="container mt-5">
    <table class="table table-bordered table-hover">
      <thead>
      <tr>
        <th>userId</th>
        <th>login</th>
        <th>password</th>
        <th>gender</th>
        <th>birthDate</th>
        <th>information</th>
        <th></th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in users">
        <th>{{ user.userId }}</th>
        <th>{{ user.login }}</th>
        <td>{{ user.password }}</td>
        <td>{{ user.gender }}</td>
        <td>{{ user.birthDate }}</td>
        <td>{{ user.information }}</td>
        <td>
          <button class="btn btn-dark" @click="deleteUser(user.userId)">delete</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
  import http from "../../http-common";

  export default {
    name: "Users",
    data() {
      return {
        users: [],
      };
    },

    methods: {
      retrieveUsers() {
        this.$store.dispatch('getUsers')
          .then(response => {
            this.users = response.data
            console.log(this.users)
          })
      },
      deleteUser(userId) {
        http
          .delete("/user/" + userId)
          .then(response => {
            console.log(response.data);
            this.$router.push('/Users');
            this.retrieveUsers();
          })
          .catch(e => {
            this.validation = false;
            this.response.push(e.response.data.message);
            console.log(e);
          });
      },
    },
    mounted() {
      this.retrieveUsers();
    }
  };
</script>

<style>
  .list {
    text-align: left;
    max-width: 450px;
    margin: auto;
  }
</style>
