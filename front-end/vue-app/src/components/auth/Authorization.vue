<template>
  <mdb-container>
    <mdb-row>
      <mdb-col size="12" class="text-center mb-5">
        <mdb-modal-body class="grey-text">
          <mdb-input size="sm" label="Password" icon="user" group type="text" validate error="wrong"
                     success="right" required v-model="user.username"/>
          <mdb-input size="sm" label="Password" icon="key" group type="password" validate error="wrong"
                     success="right" required v-model="user.password"/>
        </mdb-modal-body>

        <mdb-modal-footer>
          <mdb-btn color="primary" @click="signIn">Sign in</mdb-btn>
        </mdb-modal-footer>
      </mdb-col>
    </mdb-row>
  </mdb-container>
</template>

<script>
  import 'bootstrap-css-only/css/bootstrap.min.css';
  import 'mdbvue/build/css/mdb.css';

  import {
    mdbContainer,
    mdbRow,
    mdbCol,
    mdbInput,
    mdbTextarea,
    mdbBtn,
    mdbIcon,
    mdbModal,
    mdbModalHeader,
    mdbModalBody,
    mdbModalFooter
  } from 'mdbvue';

  export default {
    name: 'Authorization',
    components: {
      mdbContainer,
      mdbRow,
      mdbCol,
      mdbInput,
      mdbTextarea,
      mdbBtn,
      mdbIcon,
      mdbModal,
      mdbModalHeader,
      mdbModalBody,
      mdbModalFooter
    },
    data() {
      return {
        showModal: false,
        user: {
          username: "",
          password: ""
        }
      };
    },

    computed: {
      text: function () {
        return 'textik'
      },
      loggedIn() {
        return this.$store.getters.loggedIn
      },
      hasRoleUser() {
        console.log(this.$store.getters.hasRoleUser)
        return this.$store.getters.hasRoleUser
      },
      hasRoleAdmin() {
        console.log(this.$store.getters.hasRoleAdmin)
        return this.$store.getters.hasRoleAdmin
      },
    },

    methods: {
      async signIn() {
        await this.$store.dispatch('retrieveToken', {
          username: this.user.username,
          password: this.user.password,
        })
          .then(response => {

          })
          .catch(error => {
            this.username = ''
            this.password = ''
          })

        await this.$store.dispatch('retrieveUserRoles')
          .then(response => {
            this.$router.push('/Main')
          })
          .catch(error => {
          })
      },
    }
  };
</script>

<style scoped>

</style>
