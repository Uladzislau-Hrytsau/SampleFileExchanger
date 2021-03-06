// import Vue from 'vue'
import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/components/Main'
import Users from '@/components/user/Users'
import Files from '@/components/file/Files'
import Structure from '@/components/data/Structure'
import Registration from '@/components/auth/Registration'
import Authorization from '@/components/auth/Authorization'
import Logout from '@/components/auth/Logout'
import 'bootstrap-css-only/css/bootstrap.min.css';
import 'mdbvue/build/css/mdb.css';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Main',
      component: Main
    },
    {
      path: '/Users',
      name: 'Users',
      component: Users,
      meta: {
        requiresAuth: true,
      }
    },
    {
      path: '/Files',
      name: 'Files',
      component: Files,
      meta: {
        requiresAuth: true,
      }
    },
    {
      path: '/Structure',
      name: 'Structure',
      component: Structure,
      meta: {
        requiresAuth: true,
      }
    },
    {
      path: '/Registration',
      name: 'Registration',
      component: Registration,
    },
    {
      path: '/Authorization',
      name: 'Authorization',
      component: Authorization,
    },
    {
      path: '/Logout',
      name: 'Logout',
      component: Logout,
      meta: {
        requiresAuth: true,
      }
    },
  ]
})
