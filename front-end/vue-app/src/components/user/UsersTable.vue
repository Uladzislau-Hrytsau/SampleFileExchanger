<template>
  <div>
    <div v-if="approved">
      <mdb-alert color="primary">
        <button class="btn" v-on:click="deleteAfterApprove(id)">approve</button>
        <button class="btn" v-on:click="cancelApprove">cancel</button>
      </mdb-alert>
    </div>
    <div v-else>
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
              <button class="btn fas fa-pencil-alt"></button>
            </td>
            <td>
              <button class="btn fas fa-trash-alt" v-on:click="approve(user.id)"></button>
            </td>
          </tr>
        </mdb-tbl-body>
      </mdb-tbl>
    </div>
  </div>
</template>

<script>
  import {mapState, mapMutations, mapActions} from 'vuex'
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
        approved: false,
        id: '',
      };
    },
    computed: {
      ...mapState(['users', 'pageUser', 'size']),
    },
    methods: {
      ...mapActions(['retrieveUsers', 'deleteUser']),
      approve(id) {
        this.approved = true;
        this.id = id
      },
      cancelApprove() {
        this.approved = false;
      },
      async deleteAfterApprove(id) {
        await this.deleteUser(id);
        this.approved = false;
        await this.retrieveUsers();
      },
    },
    mounted() {
      this.$store.dispatch('retrieveUsers').catch(error => {
        console.log(error)
      })
    }
  }
</script>

<style scoped>

</style>
