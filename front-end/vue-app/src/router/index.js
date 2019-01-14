import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Users from '@/components/Users'
import CreateUser from '@/components/CreateUser'
import DeleteUser from '@/components/DeleteUser'
import UpdateUser from '@/components/UpdateUser'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
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
      path: '/DeleteUser',
      name: 'DeleteUser',
      component: DeleteUser
    },
    {
      path: '/UpdateUser',
      name: 'UpdateUser',
      component: UpdateUser
    }
  ]
})
