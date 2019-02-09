import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)
axios.defaults.baseURL = 'http://localhost:8088'
const qs = require('query-string');

export const store = new Vuex.Store({

  state: {
    token: localStorage.getItem('access_token') || null,
    users: [],
    files: [],
  },
  getters: {
    loggedIn(state) {
      return state.token !== null
    },
  },
  mutations: {
    retrieveToken(state, token) {
      state.token = token
    },
    destroyToken(state) {
      state.token = null
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
          'Content-Type' : 'application/x-www-form-urlencoded',
          'Authorization' : 'Basic Y2xpZW50SWRQYXNzd29yZDpzZWNyZXQ=',
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
            const token = response.data.access_token
            console.log(token)
            localStorage.setItem('access_token', token)
            context.commit('retrieveToken', token)
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
