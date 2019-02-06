import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/Index'
import Users from '@/components/user/Users'
import CreateUser from '@/components/user/CreateUser'
import UpdateUser from '@/components/user/UpdateUser'
import Files from '@/components/file/Files'
import CreateFile from '@/components/file/CreateFile'
import UpdateFile from '@/components/file/UpdateFile'
import VueCookies from 'vue-cookies'

Vue.use(Router);
Vue.use(VueCookies);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index
    },
    {
      path: '/Users',
      name: 'Users',
      component: Users
    },
    {
      path: '/CreateUser',
      name: 'CreateUser',
      component: CreateUser
    },
    {
      path: '/UpdateUser',
      name: 'UpdateUser',
      component: UpdateUser
    },
    {
      path: '/Files',
      name: 'Files',
      component: Files
    },
    {
      path: '/CreateFile',
      name: 'CreateFile',
      component: CreateFile
    },
    {
      path: '/UpdateFile',
      name: 'UpdateFile',
      component: UpdateFile
    }
  ]
})
