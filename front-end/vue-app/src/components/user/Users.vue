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

  const qs = require('query-string');

  export default {
    name: "Users",
    data() {
      return {
        fields: [
          'userId',
          'login',
          'password',
          'gender',
          'birthDate',
          'information',
          'delete'
        ],
        users: [],
      };
    },

    methods: {
      /* eslint-disable no-console */
      retrieveUsers() {
        const requestBody = {
          username: 'vlad',
          password: '256247',
          grant_type: 'password',
        };
        const config = {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization' : 'Bearer ' + $cookies.get('token'),
            'Access-Control-Allow-Origin': '*'
          }
        };
        http
          .get("/users", qs.stringify(requestBody), config)
          .then(response => {
            this.users = response.data; // JSON are parsed automatically.
            console.log(response.data);
          })
          .catch(e => {
            console.log(e);
          });
      },
      deleteUser(userId,
      ) {
        http
          .delete("/user/" + userId,
            // {'headers':
            //     {'Authorization': 'bearer b7f96453-7fd7-475d-87a0-58a9b13391ba'}
            // }
          )
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
      /* eslint-enable no-console */
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
