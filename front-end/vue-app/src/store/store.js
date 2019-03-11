// import Vue from 'vue'
import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

import VueMaterial from 'vue-material'
import 'vue-material/dist/vue-material.min.css'

Vue.use(VueMaterial);
Vue.use(Vuex);

axios.defaults.baseURL = 'http://localhost:8088';

const qs = require('query-string');

export const store = new Vuex.Store({

  state: {
    token: localStorage.getItem('access_token') || null,
    user_role: localStorage.getItem('roles') || null,
    user: JSON.parse(localStorage.getItem('user')) || null,
    file: JSON.parse(localStorage.getItem('file')) || null,
    username: '',
    users: [],
    files: [],
    folders: [],
    // count of all record
    recordAmount: 24,
    // amount of record displayed on a pagination item
    paginationSize: 10,
    currentPagination: 1,
  },

  getters: {
    loggedIn(state) {
      return state.token !== null
    },
    hasRoleUser(state) {
      return state.user_role == null ? false : state.user_role.includes('ROLE_USER')
    },
    hasRoleAdmin(state) {
      return state.user_role == null ? false : state.user_role.includes('ROLE_ADMIN')
    },
    getUsers(state) {
      return state.users
    },
    getPage(state) {
      return state.page
    },
    getRecordAmount(state) {
      return state.recordAmount
    },
    getPaginationSize(state) {
      return state.paginationSize
    },
    getCurrentPagination(state) {
      return state.currentPagination
    }
  },

  mutations: {
    setToken(state, token) {
      state.token = token
    },
    destroyToken(state) {
      state.token = null
    },
    setUserRoles(state, userRole) {
      state.user_role = userRole;
    },
    destroyUserRoles(state) {
      state.userRole = null
    },
    setUsers(state, users) {
      state.users = users
    },
    destroyUsers(state) {
      state.users = null
    },
    setFiles(state, files) {
      state.files = files
    },
    destroyFiles(state) {
      state.files = null
    },
    setUserInformation(state, user) {
      state.user = user
    },
    destroyUserInformation(state) {
      state.user = null
    },
    setFileInformation(state, file) {
      state.file = file
    },
    destroyFileInformation(state) {
      state.file = null
    },
    setFolders(state, folders) {
      state.folders = folders
    },
    destroyFolders(state) {
      state.folders = null
    },
    setRecordAmount(state, recordAmount) {
      state.recordAmount = recordAmount
    },
    destroyRecordAmount(state) {
      state.recordAmount = null
    },
    setPaginationSize(state, paginationSize) {
      (paginationSize == null || paginationSize <= 0) ? state.paginationSize = 1 : state.paginationSize = paginationSize
    },
    destroyPaginationSize(state) {
      state.paginationSize = null
    },
    setCurrentPagination(state, currentPagination) {
      state.currentPagination = currentPagination
    },
    getCurrentPagination(state) {
      state.currentPagination = null
    },
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
            context.commit('setToken', token)
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
      if (context.getters.loggedIn) {
        return new Promise((resolve, reject) => {
          axios
            .get('oauth/role')
            .then(response => {
              context.commit('setUserRoles', response.data)
              localStorage.setItem('roles', response.data)
              resolve(response)
            })
            .catch(error => {
              console.log(error)
              reject(error)
            })
        })
      }
    },

    getUsers(context, credentials) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token
      if (context.getters.loggedIn && context.getters.hasRoleAdmin && context.getters.hasRoleUser) {
        return new Promise((resolve, reject) => {
          axios
            .get('users/' + 1 + '/' + 10)
            .then(response => {
              context.commit('setUsers', response.data)
              resolve(response)
            })
            .catch(error => {
              console.log(error)
              reject(error)
            })
        })
      }
    },

    deleteUser(context, credentials) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token
      if (context.getters.loggedIn && (context.getters.hasRoleAdmin || context.getters.hasRoleUser)) {
        return new Promise((resolve, reject) => {
          axios
            .delete('user/' + credentials.userId)
            .then(response => {
              resolve(response)
            })
            .catch(error => {
              console.log(error)
              reject(error)
            })
        })
      }
    },

    saveUserInformation(context, credentials) {
      localStorage.setItem('user', JSON.stringify(credentials.user))
      context.commit('setUserInformation', credentials.user)
    },

    updateUser(context, credentials) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token
      if (context.getters.loggedIn && (context.getters.hasRoleAdmin || context.getters.hasRoleUser)) {
        return new Promise(((resolve, reject) => {
          axios
            .put('user', credentials.data)
            .then(response => {
              context.commit('destroyUserInformation')
              localStorage.removeItem('user')
              resolve(response)
            })
            .catch(error => {
              console.log(error)
              reject(error)
            })
        }))
      }
    },

    createUser(context, credentials) {
      return new Promise(((resolve, reject) => {
        axios
          .post('user', credentials.data)
          .then(response => {
            resolve(response)
          })
          .catch(error => {
            console.log(error)
            reject(error)
          })
      }))
    },

    getFiles(context) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token
      if (context.getters.loggedIn && context.getters.hasRoleAdmin && context.getters.hasRoleUser)
        return new Promise((resolve, reject) => {
          axios
            .get('files')
            .then(response => {
              context.commit('setFiles', response.data)
              resolve(response)
            })
            .catch(error => {
              console.log(error)
              reject(error)
            })
        })
    },

    deleteFile(context, credentials) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token
      if (context.getters.loggedIn && context.getters.hasRoleAdmin && context.getters.hasRoleUser)
        return new Promise((resolve, reject) => {
          axios
            .delete('file/' + credentials.id)
            .then(response => {
              resolve(response)
            })
            .catch(error => {
              console.log(error)
              reject(error)
            })
        })
    },

    saveFileInformation(context, credentials) {
      localStorage.setItem('file', JSON.stringify(credentials.file))
      context.commit('setFileInformation', credentials.file)
    },
    updateFile(context, credentials) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token
      if (context.getters.loggedIn && (context.getters.hasRoleAdmin || context.getters.hasRoleUser)) {
        return new Promise((resolve, reject) => {
          axios
            .put('file', credentials.data)
            .then(response => {
              context.commit('destroyFileInformation')
              localStorage.removeItem('file')
              resolve(response)
            })
            .catch(error => {
              console.log(error)
              reject(error)
            })
        })
      }
    },

    getUserInformation(context) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token
      if (context.getters.loggedIn && (context.getters.hasRoleAdmin || context.getters.hasRoleUser)) {
        return new Promise((resolve, reject) => {
          axios
            .get('oauth/user')
            .then(response => {
              localStorage.setItem('user', JSON.stringify(response.data))
              context.commit('setUserInformation', response.data)
              resolve(response)
            })
            .catch(error => {
              console.log(error)
              reject(error)
            })
        })
      }
    },

    uploadFile(context, credentials) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token
      let multipartFile = new FormData();
      multipartFile.append('file', JSON.stringify(credentials.file))
      multipartFile.append('multipartFile', credentials.multipartFile)
      const config = {
        headers: {
          'Content-Type': 'multipart/form-data',
        }
      };
      if (context.getters.loggedIn && (context.getters.hasRoleAdmin || context.getters.hasRoleUser)) {
        return new Promise((resolve, reject) => {
          axios
            .post('file', multipartFile, config)
            .then(response => {
              resolve(response)
            })
            .catch(error => {
              console.log(error)
              reject(error)
            })
        })
      }
    },

    displayStructure(context, credentials) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token;
      if (context.getters.loggedIn && (context.getters.hasRoleAdmin || context.getters.hasRoleUser)) {
        return new Promise((resolve, reject) => {
          axios
            .get('structure/' + credentials.folderId)
            .then(response => {
              context.commit('setFolders', response.data);
              resolve(response);
            })
            .catch(error => {
              console.log(error);
              reject(error);
            })
        })
      }

    },

    retrieveUsersSize(context) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token;
      if (context.getters.loggedIn && context.getters.hasRoleAdmin) {
        return new Promise((resolve, reject) => {
          axios
            .get('/users/amount')
            .then(response => {
              context.commit('setRecordAmount', response.data);
              resolve(response);
            })
            .catch(error => {
              console.log(error);
              reject(error);
            })
        })
      }

    },

    retrieveFilesSize(context) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token;
      if (context.getters.loggedIn && context.getters.hasRoleAdmin) {
        return new Promise((resolve, reject) => {
          axios
            .get('/files/amount')
            .then(response => {
              context.commit('setRecordAmount', response.data);
              resolve(response);
            })
            .catch(error => {
              console.log(error);
              reject(error);
            })
        })
      }

    }

  }
});
