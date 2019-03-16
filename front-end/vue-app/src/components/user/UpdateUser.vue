<template>
  <mdb-container>
    <mdb-row>
      <mdb-col size="12" class="text-center mb-5">

        <mdb-modal-body class="grey-text">
          <mdb-input size="sm" icon="key" group type="password" validate error="wrong"
                     success="right" required v-model="newUser.password" v-bind:label="user.password"/>
          <mdb-input size="sm" icon="fas fa-birthday-cake" group type="date" validate error="wrong"
                     success="right" required v-model="newUser.birthDate" v-bind:label="user.birthDate"/>
          <mdb-input size="sm" icon="fas fa-info" group type="password" validate error="wrong"
                     success="right" required v-model="newUser.information" v-bind:label="user.information"/>
          <b-form-group v-bind:label="user.gender.toString()">
            <b-form-radio-group v-model="newUser.gender"
                                :options="options"
                                name="radioInline">
            </b-form-radio-group>
          </b-form-group>
        </mdb-modal-body>

        <mdb-modal-footer>
          <button v-on:click="update" class="btn primary-color">update</button>
        </mdb-modal-footer>
      </mdb-col>
    </mdb-row>
  </mdb-container>
</template>
<script>
  import {mapState, mapGetters, mapMutations, mapActions} from 'vuex'
  import 'bootstrap-css-only/css/bootstrap.min.css';
  import 'mdbvue/build/css/mdb.css';
  import VueMaterial from 'vue-material'
  import 'vue-material/dist/vue-material.min.css'

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
        newUser: {
          password: '',
          gender: '',
          birthDate: '',
          information: '',
        },
        options: [
          {text: 'Male', value: true},
          {text: 'Female', value: false},
        ],
      };
    },
    computed: {
      ...mapState(['user']),
    },
    methods: {
      ...mapActions(['updateUser']),
      update() {
        let data = {
          id: this.user.id,
          password: this.newUser.password,
          gender: this.newUser.gender,
          birthDate: this.newUser.birthDate,
          information: this.newUser.information,
        };
        this.updateUser(data);
      }
    }
  };
</script>

<style scoped>

</style>
