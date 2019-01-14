<template>
  <div id="app">
    <nav>
      <router-link class="btn btn-primary" to="/">main</router-link>
      <router-link class="btn btn-primary" to="/Users">get all users</router-link>
      <router-link class="btn btn-primary" to="/CreateUser">Creat user</router-link>
      <router-link class="btn btn-primary" to="/DeleteUser">delete user</router-link>
      <router-link class="btn btn-primary" to="/UpdateUser">update user</router-link>
      <router-link class="btn btn-primary" to="/Files">get all files</router-link>
    </nav>
    <router-view/>
  </div>
</template>

<script>
  export default {
    name: 'App'
  }
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }
</style>
