<template>
  <div>
    <mdb-container>
      <mdb-row>
        <mdb-col size="12" class="text-center mb-5">
          <mdb-modal-body class="grey-text">
            <!--<mdb-input size="sm" label="Login" icon="user" group type="text" validate error="wrong"-->
                       <!--success="right" required v-model="userNew.login"/>-->
            <mdb-input size="sm" label="Password" icon="key" group type="password" validate error="wrong"
                       success="right" required v-model="userNew.password"/>
            <mdb-input size="sm" label="Birth date" icon="fas fa-birthday-cake" group type="date" validate error="wrong"
                       success="right" required v-model="userNew.birthDate"/>
            <mdb-input size="sm" label="About yourself" icon="fas fa-info" group type="text" validate error="wrong"
                       success="right" required v-model="userNew.information"/>
            <b-form-group label="Gender">
              <b-form-radio-group v-model="selected"
                                  :options="options"
                                  name="radioInline">
              </b-form-radio-group>
            </b-form-group>
          </mdb-modal-body>

          <mdb-modal-footer>
            <mdb-btn color="primary" v-on:click="saveCustomer">update</mdb-btn>
          </mdb-modal-footer>
        </mdb-col>
      </mdb-row>
    </mdb-container>
  </div>
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
    name: "UpdateUser",
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
        options: [
          {text: 'Male', value: true},
          {text: 'Female', value: false},
        ],
        selected: '',

        userOld: this.$store.state.user,
        userNew: {
          userId: "",
          login: "",
          password: "",
          gender: "",
          birthDate: "",
          information: "",
        },
        validation: true,
        response: [],
      };
    },
    methods: {
      saveCustomer() {
        var data = {
          userId: this.userOld.userId,
          password: this.userNew.password,
          gender: this.selected,
          birthDate: this.userNew.birthDate,
          information: this.userNew.information,
        };
        this.$store.dispatch('updateUser', {
          data: data
        })
          .then(response => {
            this.$router.push('/Main')
          })
          .catch(error => {
            console.log(error)
          })
      }
      /* eslint-enable no-console */
    }
  };
</script>

<style scoped>

</style>
