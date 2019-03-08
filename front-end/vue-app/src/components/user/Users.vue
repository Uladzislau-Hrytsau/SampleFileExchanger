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
        <td>
          <button class="btn btn-dark" @click="updateUser(user)">update</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>

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
          .catch(error => {
            console.log(error)
          })
      },
      deleteUser(userId) {
        this.$store.dispatch('deleteUser', {
          userId: userId
        })
          .then(response => {
            this.retrieveUsers();
          })
          .catch(error => {
            console.log(error)
          })
      },
      updateUser(user) {
        this.$store.dispatch('saveUserInformation', {
          user: user
        })
        this.$router.push('/UpdateUser')
      }
    },
    mounted() {
      this.retrieveUsers();
    }
  };
</script>

<style>

</style>
