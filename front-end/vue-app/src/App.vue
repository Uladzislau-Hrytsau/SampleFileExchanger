<template>
  <div id="app">
    <b-navbar toggleable="md" type="dark" variant="dark">
      <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>
      <b-navbar-brand to="/">FileExchanger</b-navbar-brand>
      <b-collapse is-nav id="nav_collapse">
        <b-navbar-nav>
          <b-nav-item :to="{ name : 'Users' }">get all users</b-nav-item>
          <b-nav-item :to="{ name : 'UpdateUser'}">update user</b-nav-item>
          <b-nav-item :to="{ name : 'Files' }">get all files</b-nav-item>
          <b-nav-item :to="{ name : 'CreateFile' }">creat file</b-nav-item>
          <b-nav-item :to="{ name : 'UpdateFile' }">update file</b-nav-item>
          <!--<b-nav-item v-show="$cookies.get('token') == null" class="registration" to="/Registration">sing up</b-nav-item>-->
          <b-nav-item class="registration" :to="{ name : 'Registration' }">sing up</b-nav-item>
          <!--<b-nav-item v-show="$cookies.get('token') == null" class="authorization" to="/Authorization">sing in</b-nav-item>-->
          <b-nav-item class="authorization" :to="{ name : 'Authorization' }">sing in</b-nav-item>
          <!--<b-nav-item v-show="!($cookies.get('token') == null)" @click="logout" to="/">logout</b-nav-item>-->
          <!--<b-nav-item @click="logout" :to="{ name : 'Index' }">logout</b-nav-item>-->
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <router-view/>
  </div>
</template>

<script>
  import 'bootstrap-css-only/css/bootstrap.min.css';
  import 'mdbvue/build/css/mdb.css';

  export default {
    name: 'App',

    computed: {
      loggedIn() {
        return this.$store.getters.loggedIn
      }
    }
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

  .registration {
    position: center;
  }
</style>
