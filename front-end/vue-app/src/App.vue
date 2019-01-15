<template>
  <div id="app">
    <b-navbar toggleable="md" type="dark" variant="dark">
      <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>
      <b-navbar-brand to="/">FileExchanger</b-navbar-brand>
      <b-collapse is-nav id="nav_collapse">
        <b-navbar-nav>
          <b-nav-item to="/Users">get all users</b-nav-item>
          <b-nav-item to="/CreateUser">creat user</b-nav-item>
          <b-nav-item to="/UpdateUser">update user</b-nav-item>
          <b-nav-item to="/Files">get all files</b-nav-item>
          <b-nav-item to="/CreateFile">creat file</b-nav-item>
          <b-nav-item to="/UpdateFile">update file</b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
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
    color: #000000;
    margin-top: auto;
  }
</style>
