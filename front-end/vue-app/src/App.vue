<template>
  <div id="app">
    <b-navbar toggleable="lg" type="dark" variant="dark">
      <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>
      <b-navbar-brand to="/">FileExchanger</b-navbar-brand>
      <b-collapse is-nav id="nav_collapse">
        <b-navbar-nav>
          <b-nav-item v-show="loggedIn && hasRoleUser && hasRoleAdmin" :to="{ name : 'Users' }">users</b-nav-item>
          <b-nav-item v-show="loggedIn && hasRoleAdmin" :to="{ name : 'Files' }">files</b-nav-item>
          <b-nav-item v-show="loggedIn && (hasRoleUser || hasRoleAdmin)" :to="{ name : 'Structure' }">data</b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav class="ml-auto">
          <b-nav-item v-show="!loggedIn" :to="{ name : 'Registration' }">sing up</b-nav-item>
          <b-nav-item v-show="!loggedIn" :to="{ name : 'Authorization' }">sing in</b-nav-item>
          <b-nav-item-dropdown right v-show="loggedIn && (hasRoleUser || hasRoleAdmin)">
            <template slot="button-content"><em>User</em></template>
            <b-dropdown-item >Profile</b-dropdown-item>
            <b-dropdown-item :to="{ name : 'Logout' }">Log out</b-dropdown-item>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <router-view/>
  </div>
</template>

<script>
  import 'bootstrap-css-only/css/bootstrap.min.css';
  import 'mdbvue/build/css/mdb.css';
  import VueMaterial from 'vue-material'
  import 'vue-material/dist/vue-material.min.css'
  import {mapState, mapGetters, mapActions, mapMutations} from 'vuex'

  export default {
    name: 'App',

    computed: {
      ...mapGetters([
        'loggedIn',
        'hasRoleUser',
        'hasRoleAdmin'
      ]),
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
</style>
