<template>
  <div v-show="$cookies.get('token')">
    <mdb-container>
      <mdb-row>
        <mdb-col size="12" class="text-center mb-5">
          <mdb-modal-body class="grey-text">
            <mdb-input size="sm" label="Login" icon="user" group type="text" validate error="wrong"
                       success="right" required v-model="user.login"/>
            <mdb-input size="sm" label="Password" icon="key" group type="password" validate error="wrong"
                       success="right" required v-model="user.password"/>
            <mdb-input size="sm" label="Birth date" icon="fas fa-birthday-cake" group type="date" validate error="wrong"
                       success="right" required v-model="user.birthDate"/>
            <mdb-input size="sm" label="About yourself" icon="fas fa-info" group type="text" validate error="wrong"
                       success="right" required v-model="user.information"/>
            <b-form-group label="Gender">
              <b-form-radio-group v-model="selected"
                                  :options="options"
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
  <!--<div v-if="!validation">-->
  <!--<div class="form-group">-->
  <!--{{ response }}-->
  <!--</div>-->
  <!--</div>-->

</template>

<script>
  import http from "../http-common";
  import axios from 'axios'
  import VueCookies from "../http-common"
  import 'bootstrap-css-only/css/bootstrap.min.css';
  import 'mdbvue/build/css/mdb.css';

  const qs = require('query-string');

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

        selected: '',
        options: [
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

        validation: true,
        response: [],

      };
    },
    methods: {
      signUp() {
        var data = {
          login: this.user.login,
          password: this.user.password,
          gender: this.selected,
          birthDate: this.user.birthDate,
          information: this.user.information,
        };

        http
          .post("/user", data)
          .then(response => {
            console.log(response.data);
            this.$router.push('/Authorization');
          })
          .catch(e => {
            this.validation = false;
            this.response.push(e.response.data.message);
            console.log(e);
          });
      }
    }
  };
</script>

<style>
  .submitform {
    max-width: 300px;
    margin: auto;
  }
</style>
