<template>
  <div>
    <mdb-container>
      <mdb-row>
        <mdb-col size="12" class="text-center mb-5">
          <mdb-modal-body class="grey-text">
            <mdb-input size="sm" label="Login" icon="user" group type="text" required v-model="user.name"/>
            <mdb-input size="sm" label="Password" icon="key" group type="password" required v-model="user.password"/>
            <mdb-input size="sm" label="Birth date" icon="fas fa-birthday-cake" group type="date" required v-model="user.birthDate"/>
            <mdb-input size="sm" label="About yourself" icon="fas fa-info" group type="text" required v-model="user.information"/>
            <b-form-group label="Gender">
              <b-form-radio-group v-model="gender"
                                  :options="genders"
                                  name="radioInline">
              </b-form-radio-group>
            </b-form-group>
          </mdb-modal-body>
          <mdb-modal-footer>
            <mdb-btn color="primary" v-on:click="signUp">Sign up</mdb-btn>
          </mdb-modal-footer>
        </mdb-col>
      </mdb-row>
    </mdb-container>
  </div>

</template>

<script>
  import {mapState, mapGetters, mapMutations, mapActions} from 'vuex'
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
    name: "Registration",
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
        genders: [
          {text: 'Male', value: true},
          {text: 'Female', value: false},
        ],
        user: {
          login: "",
          password: "",
          gender: "",
          birthDate: "",
          information: "",
        },
      };
    },
    methods: {
      ...mapActions([
        'createUser'
      ]),
      signUp() {
        let data = {
          name: this.user.name,
          password: this.user.password,
          gender: this.gender,
          birthDate: this.user.birthDate,
          information: this.user.information,
        };
        this.createUser(data);
        this.$router.push('/Authorization');
      }
    }
  };
</script>

<style scoped>

</style>
