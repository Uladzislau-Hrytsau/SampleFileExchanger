// import Vue from 'vue'
import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)
axios.defaults.baseURL = 'http://localhost:8088'

const qs = require('query-string');

export const store = new Vuex.Store({

  state: {
    token: localStorage.getItem('access_token') || null,
    username: '',
    users: [],
    files: [],
    role_user: '',
    role_admin: '',
  },

  getters: {
    loggedIn(state) {
      return state.token !== null
    },
    hasRoleUser(state) {
      return state.role_user !== null
    },
    hasRoleAdmin(state) {
      return state.role_admin !== null
    },
  },

  mutations: {
    retrieveToken(state, token) {
      state.token = token
    },
    destroyToken(state) {
      state.token = null
    },
    retrieveUserRoles(state, userRole) {
      if (userRole.includes('ROLE_USER')) {
        state.role_user = true
      }
      if (userRole.includes('ROLE_ADMIN')) {
        state.role_admin = true
      }
    },
    destroyUserRoles(state) {
      state.userRole = null
    },
    getUsers(state, users) {
      state.users = users
    },
    getFiles(state, files) {
      state.files = files;
    }
  },

  actions: {

     retrieveToken(context, credentials) {
      const config = {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
          'Authorization': 'Basic Y2xpZW50SWRQYXNzd29yZDpzZWNyZXQ=',
        }
      };
      return new Promise((resolve, reject) => {
        axios.post(
          '/oauth/token',
          qs.stringify({
            grant_type: 'password',
            username: credentials.username,
            password: credentials.password,
          }),
          config
        )
          .then(response => {
            this.state.username = credentials.username
            const token = response.data.access_token
            localStorage.setItem('access_token', token)
            context.commit('retrieveToken', token)
            console.log(token)
            resolve(response)
          })
          .catch(error => {
            console.log(error)
            reject(error)
          })
      })
    },

    destroyToken(context) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token

      if (context.getters.loggedIn) {
        return new Promise((resolve, reject) => {
          localStorage.removeItem('access_token')
          context.commit('destroyToken')
        })
      }
    },

    retrieveUserRoles(context) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token

        return new Promise((resolve, reject) => {
          axios
            .get('role/' + context.state.username)
            .then(response => {
              context.commit('retrieveUserRoles', response.data)
              localStorage.setItem('roles', response.data)
              resolve(response)
            })
            .catch(error => {
              console.log(error)
              reject(error)
            })
        })

    },

    getUsers(context) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token

      return new Promise((resolve, reject) => {
        axios
          .get('users')
          .then(response => {
            context.commit('getUsers', response.data)
            resolve(response)
          })
          .catch(error => {
            console.log(error)
            reject(error)
          })
      })
    },

    getFiles(context) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token

      return new Promise((resolve, reject) => {
        axios
          .get('files')
          .then(response => {
            context.commit('getFiles', response.data)
            resolve(response)
          })
          .catch(error => {
            console.log(error)
            reject(error)
          })
      })
    }
  }
})
