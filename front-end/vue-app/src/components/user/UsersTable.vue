<template>
  <div>
    <mdb-tbl>
      <mdb-tbl-head>
        <tr>
          <th>id</th>
          <th>login</th>
          <th>password</th>
          <th>gender</th>
          <th>birth date</th>
          <th>information</th>
          <th></th>
          <th></th>
        </tr>
      </mdb-tbl-head>
      <mdb-tbl-body>
        <tr v-for="user in users">
          <th>{{ user.id }}</th>
          <th>{{ user.name }}</th>
          <td>{{ user.password }}</td>
          <td>{{ user.gender }}</td>
          <td>{{ user.birthDate }}</td>
          <td>{{ user.information }}</td>
          <td>
            <button class="btn fas fa-pencil-alt" v-on:click="enableUpdatingTemplate(user)"></button>
          </td>
          <td>
            <button class="btn fas fa-trash-alt" v-on:click="enableDeletingTemplate(user.id)"></button>
          </td>
        </tr>
      </mdb-tbl-body>
    </mdb-tbl>

  </div>
</template>

<script>
  import {mapState, mapGetters, mapMutations, mapActions} from 'vuex'
  import {mdbTbl, mdbTblHead, mdbTblBody, mdbBtn, mdbAlert} from 'mdbvue';

  export default {
    name: "UsersTable",
    components: {
      mdbTbl,
      mdbTblHead,
      mdbTblBody,
      mdbBtn,
      mdbAlert,
    },
    data() {
      return {

      };
    },
    computed: {
      ...mapState([
        'users',
        'pageUser',
        'size',
        'enabledApprove',
        'isApproved',
        'isCancelled'
      ]),
    },
    methods: {
      ...mapActions([
        'retrieveUsers',
        'deleteUser'
      ]),
      ...mapMutations([
        'enableTableUsers',
        'enablePagination',
        'enableApprove',
        'disablePagination',
        'disableTableUsers',
        'disableApprove',
        'setId',
        'setUser',
        'enableUserUpdate',
        'enableUserDelete'
      ]),
      enableUpdatingTemplate(user) {
        this.setUser(user);
        this.enableUserUpdate();
      },
      enableDeletingTemplate(id) {
        this.enableUserDelete();
        this.setId(id);
        this.enableApprove();
      },
    },
    mounted() {
      this.retrieveUsers();
    }
  }
</script>

<style scoped>

</style>
