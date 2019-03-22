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
    file: JSON.parse(localStorage.getItem('file')) || null,
    username: '',
    users: JSON.parse(localStorage.getItem('users')) || null,
    files: JSON.parse(localStorage.getItem('files')) || null,
    folders: [],

    id: '',

    size: localStorage.getItem('size') || 10,
    pageUser: localStorage.getItem('pageUser') || 1,
    pageFile: localStorage.getItem('pageFile') || 1,
    count: localStorage.getItem('count') || 0,
    paginationItem: localStorage.getItem('paginationItem') || 1,
    enabledPagination: true,
    enabledTableUsers: true,
    enabledTableFiles: true,
    enabledApprove: false,

    user: [],
    enabledUserUpdate: false,
    enabledFileUpdate: false,

    isUserDelete: false,
    isFileDelete: false,

    folderId: localStorage.getItem('folderId') || 0,
    fileStructure: null,
    folderStructure: null,
    categories: null,

    enabledFolderCreate: false,
    enabledFileCreate: false,
    enabledPointSpinner: false,

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
    getFiles(state) {
      return state.files
    },
    getSize(state) {
      return state.size
    },
    getPageUser(state) {
      return state.pageUser
    },
    getPageFile(state) {
      return state.pageFile
    },
    getCount(state) {
      return state.count
    },
    getPaginationItem(state) {
      return state.paginationItem
    },
    enabledPagination(state) {
      return state.enabledPagination
    },
    enabledTableUsers(state) {
      return state.enabledTableUsers
    },
    enabledApprove(state) {
      return state.enabledApprove
    },
  },

  mutations: {
    setToken(state, token) {
      state.token = token;
    },
    destroyToken(state) {
      state.token = null;
      localStorage.removeItem('access_token');
    },
    setUserRoles(state, userRole) {
      state.user_role = userRole;
    },
    destroyUserRoles(state) {
      state.userRole = null;
      localStorage.removeItem('roles');
    },
    setUsers(state, users) {
      state.users = users;
    },
    destroyUsers(state) {
      state.users = null;
    },
    setFiles(state, files) {
      state.files = files;
    },
    destroyFiles(state) {
      state.files = null;
    },
    setFile(state, file) {
      state.file = file;
    },
    destroyFile(state) {
      state.file = null;
    },
    setFolders(state, folders) {
      state.folders = folders;
    },
    destroyFolders(state) {
      state.folders = null;
    },
    setSize(state, size) {
      state.size = size;
    },
    destroySize(state) {
      state.size = null;
    },
    setPageUser(state, pageUser) {
      state.pageUser = pageUser;
    },
    destroyPageUser(state) {
      state.pageUser = null;
    },
    setPageFile(state, pageFile) {
      state.pageFile = pageFile;
    },
    destroyPageFile(state) {
      state.pageFile = null;
    },
    setCount(state, count) {
      state.count = count;
    },
    destroyCount(state) {
      state.count = null;
    },
    setPaginationItem(state, paginationItem) {
      state.paginationItem = paginationItem;
    },
    destroyPaginationItem(state) {
      state.paginationItem = null;
    },
    enablePagination(state) {
      state.enabledPagination = true;
    },
    disablePagination(state) {
      state.enabledPagination = false;
    },
    enableTableUsers(state) {
      state.enabledTableUsers = true;
    },
    disableTableUsers(state) {
      state.enabledTableUsers = false;
    },
    enableTableFiles(state) {
      state.enabledTableFiles = true;
    },
    disableTableFiles(state) {
      state.enabledTableFiles = false;
    },
    enableApprove(state) {
      state.enabledApprove = true;
    },
    disableApprove(state) {
      state.enabledApprove = false;
    },
    setId(state, id) {
      state.id = id;
    },
    destroyId(state) {
      state.id = null;
    },
    setUser(state, user) {
      state.user = user;
    },
    destroyUser(state) {
      state.user = null;
    },
    enableUserUpdate(state) {
      state.enabledUserUpdate = true;
    },
    disableUserUpdate(state) {
      state.enabledUserUpdate = false;
    },
    enableUserDelete(state) {
      state.isUserDelete = true;
    },
    disableUserDelete(state) {
      state.isUserDelete = false;
    },
    enableFileDelete(state) {
      state.isFileDelete = true;
    },
    disableFileDelete(state) {
      state.isFileDelete = false;
    },
    enableFileUpdate(state) {
      state.enabledFileUpdate = true;
    },
    disableFileUpdate(state) {
      state.enabledFileUpdate = false;
    },
    setFolderId(state, folderId) {
      state.folderId = folderId;
    },
    destroyFolderId(state) {
      state.folderId = null;
      localStorage.removeItem('folderId');
    },
    setFileStructure(state, fileStructure) {
      state.fileStructure = fileStructure;
    },
    destroyFileStructure(state) {
      state.fileStructure = null;
    },
    setFolderStructure(state, folderStructure) {
      state.folderStructure = folderStructure;
    },
    destroyFolderStructure(state) {
      state.folderStructure = null;
    },
    setCategories(state, categories) {
      state.categories = categories;
    },
    destroyCategories(state) {
      state.categories = null;
    },
    enableFolderCreate(state) {
      state.enabledFolderCreate = true;
    },
    disableFolderCreate(state) {
      state.enabledFolderCreate = false;
    },
    enableFileCreate(state) {
      state.enabledFileCreate = true;
    },
    disableFileCreate(state) {
      state.enabledFileCreate = false;
    },
    enablePointSpinner(state) {
      state.enabledPointSpinner = true;
    },
    disablePointSpinner(state) {
      state.enabledPointSpinner = false;
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
            this.state.username = credentials.username;
            const token = response.data.access_token;
            localStorage.setItem('access_token', token);
            context.commit('setToken', token);
            console.log(token);
            resolve(response)
          })
          .catch(error => {
            console.log(error);
            reject(error)
          })
      })
    },

    destroyToken(context) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token;
      if (context.getters.loggedIn) {
        return new Promise(() => {
          localStorage.removeItem('access_token');
          context.commit('destroyToken')
        })
      }
    },

    retrieveUserRoles(context) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token;
      if (context.getters.loggedIn) {
        return new Promise((resolve, reject) => {
          axios
            .get('oauth/role')
            .then(response => {
              context.commit('setUserRoles', response.data);
              localStorage.setItem('roles', response.data);
              resolve(response)
            })
            .catch(error => {
              console.log(error);
              reject(error)
            })
        })
      }
    },

    getUsers(context) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token;
      if (context.getters.loggedIn && context.getters.hasRoleAdmin && context.getters.hasRoleUser) {
        return new Promise((resolve, reject) => {
          axios
            .get('users/?page=' + 1 + '&size=' + 10)
            .then(response => {
              context.commit('setUsers', response.data);
              resolve(response)
            })
            .catch(error => {
              console.log(error);
              reject(error)
            })
        })
      }
    },

    saveUserInformation(context, credentials) {
      localStorage.setItem('user', JSON.stringify(credentials.user));
      context.commit('setUser', credentials.user)
    },

    saveFileInformation(context, credentials) {
      localStorage.setItem('file', JSON.stringify(credentials.file));
      context.commit('setFile', credentials.file)
    },

    getUserInformation(context) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token;
      if (context.getters.loggedIn && (context.getters.hasRoleAdmin || context.getters.hasRoleUser)) {
        return new Promise((resolve, reject) => {
          axios
            .get('oauth/user')
            .then(response => {
              localStorage.setItem('user', JSON.stringify(response.data));
              context.commit('setUser', response.data);
              resolve(response)
            })
            .catch(error => {
              console.log(error);
              reject(error)
            })
        })
      }
    },

    uploadFile(context, credentials) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token;

      let multipartFile = new FormData();

      multipartFile.append('multipartFile', credentials.file);
      multipartFile.append('metaData', new Blob([JSON.stringify({
        "folderId": this.state.folderId,
        "description": credentials.description,
        "categories": credentials.selectedCategories
      })], {
        type: "application/json"
      }));

      if (context.getters.loggedIn && (context.getters.hasRoleAdmin || context.getters.hasRoleUser)) {
        return new Promise((resolve, reject) => {
          axios
            .post('files', multipartFile)
            .then(response => {
              resolve(response)
            })
            .catch(error => {
              console.log(error);
              reject(error)
            })
        })
      }
    },

    retrieveStructureAndCategories(context) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token;
      if (context.getters.loggedIn && (context.getters.hasRoleAdmin || context.getters.hasRoleUser)) {
        return new Promise((resolve, reject) => {
          axios
            .get('/structures', {
              params: {
                folderId: this.state.folderId
              }
            })
            .then(response => {
              context.commit('setFileStructure', response.data.fileStructureDtos);
              context.commit('setFolderStructure', response.data.folderStructureDtos);
              context.commit('setCategories', response.data.categoryDtos);
              resolve(response);
            })
            .catch(error => {
              console.log(error);
              reject(error);
            })
        })
      }

    },

    retrieveUsers(context) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token;
      if (context.getters.loggedIn && context.getters.hasRoleAdmin) {
        return new Promise((resolve, reject) => {
          axios
            .get('/users', {
              params: {
                page: this.state.pageUser,
                size: this.state.size,
              }
            })
            .then(response => {
              let count = response.data.pagination.count;
              let size = this.state.size;
              let paginationItem = 1;
              if (count <= 0 && size <= 0) {
              }
              if (count > 0 && size > 0) {
                if (count % size === 0) {
                  paginationItem = count / size;
                } else {
                  paginationItem = Math.floor((count / size)) + 1;
                }
              }
              localStorage.setItem('paginationItem', paginationItem);
              context.commit('setPaginationItem', paginationItem);
              context.commit('setCount', count);
              context.commit('setUsers', response.data.data);
              context.commit('setPageUser', this.state.pageUser);
              context.commit('setSize', size);
              localStorage.setItem('count', count);
              localStorage.setItem('pageUser', this.state.pageUser);
              localStorage.setItem('size', size);
              resolve(response);
            })
            .catch(error => {
              console.log(error);
              reject(error);
            })
        })
      }

    },

    retrieveFiles(context) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token;
      if (context.getters.loggedIn && context.getters.hasRoleAdmin && context.getters.hasRoleUser)
        return new Promise((resolve, reject) => {
          axios
            .get('/files', {
              params: {
                page: this.state.pageFile,
                size: this.state.size,
              }
            })
            .then(response => {
              let count = response.data.pagination.count;
              let size = this.state.size;
              let paginationItem = 1;
              if (count <= 0 && size <= 0) {
              }
              if (count > 0 && size > 0) {
                if (count % size === 0) {
                  paginationItem = count / size;
                } else {
                  paginationItem = Math.floor((count / size)) + 1;
                }
              }
              localStorage.setItem('paginationItem', paginationItem);
              context.commit('setPaginationItem', paginationItem);
              context.commit('setCount', count);
              context.commit('setFiles', response.data.data);
              context.commit('setPageFile', this.state.pageFile);
              context.commit('setSize', size);
              localStorage.setItem('count', count);
              localStorage.setItem('pageFile', this.state.pageFile);
              localStorage.setItem('size', size);
              resolve(response);
            })
            .catch(error => {
              console.log(error);
              reject(error);
            })
        })
    },

    deleteUser(context, credentials) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token;
      if (context.getters.loggedIn && (context.getters.hasRoleAdmin || context.getters.hasRoleUser)) {
        return new Promise((resolve, reject) => {
          axios
            .delete('/users', {
              params: {
                id: credentials
              }
            })
            .then(response => {
              resolve(response)
            })
            .catch(error => {
              console.log(error);
              reject(error)
            })
        })
      }
    },

    deleteFile(context, credentials) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token;
      if (context.getters.loggedIn && (context.getters.hasRoleUser || context.getters.hasRoleAdmin))
        return new Promise((resolve, reject) => {
          axios
            .delete('/files', {
              params: {
                id: credentials
              }
            })
            .then(response => {
              resolve(response)
            })
            .catch(error => {
              console.log(error);
              reject(error)
            })
        })
    },

    updateUser(context, credentials) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token;
      if (context.getters.loggedIn && (context.getters.hasRoleAdmin || context.getters.hasRoleUser)) {
        return new Promise(((resolve, reject) => {
          axios
            .put('users', credentials)
            .then(response => {
              resolve(response)
            })
            .catch((error) => {

              console.log(error.response.data.message);
              reject(error)
            })
        }))
      }
    },

    createUser(context, credentials) {
      return new Promise(((resolve, reject) => {
        axios
          .post('users', credentials)
          .then(response => {
            resolve(response)
          })
          .catch(error => {
            console.log(error);
            reject(error)
          })
      }))
    },

    createFolder(context, credentials) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token;
      if (context.getters.loggedIn && (context.getters.hasRoleAdmin || context.getters.hasRoleUser)) {
        return new Promise((resolve, reject) => {
          axios
            .post('folders', credentials)
            .then(response => {
              resolve(response)
            })
            .catch(error => {
              console.log(error);
              reject(error)
            })
        })
      }
    },

    updateFile(context, credentials) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token;
      if (context.getters.loggedIn && (context.getters.hasRoleAdmin || context.getters.hasRoleUser)) {
        return new Promise((resolve, reject) => {
          axios
            .put('files', credentials)
            .then(response => {
              resolve(response)
            })
            .catch(error => {
              console.log(error);
              reject(error)
            })
        })
      }
    },


    deleteFolder(context, credentials) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + context.state.token;
      if (context.getters.loggedIn && (context.getters.hasRoleAdmin || context.getters.hasRoleUser)) {
        return new Promise((resolve, reject) => {
          axios
            .delete('folders', {
              params: {
                "folderId": credentials,
              }
        })
          .then(response => {
            resolve(response)
          })
          .catch(error => {
            console.log(error);
            reject(error)
          })
      }
    )
  }
},


}
})
;
