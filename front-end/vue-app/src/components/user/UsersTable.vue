<template>
  <div>
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
        <th>{{ user.id }}</th>
        <th>{{ user.name }}</th>
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
    name: "UsersTable",
    data() {
      return {
        users: '',
      };
    },
    methods: {
      retrieveUsers() {
        this.$store.dispatch('getUsers', {
          page: this.$store.getters.currentPagination,
          size: this.$store.getters.recordAmount,
        })
          .then(response => {
            this.users = response.data
          })
          .catch(error => {
            console.log(error)
          })
      }
    },
    mounted() {
      this.retrieveUsers();
    }
  }
</script>

<style scoped>

</style>
