<template>
  <div class="container-fluid">
    <mdb-container>
      <mdb-modal v-if="enabledUserUpdate" @close="cancel" size="xl" class="text-center" dark>
        <mdb-modal-header center>
          <p class="heading">Are you sure you want to update information about the user?</p>
        </mdb-modal-header>
        <mdb-modal-body>
          <mdb-input size="sm" icon="key" group type="password" required v-model="newUser.password" v-bind:label="user.password"/>
          <mdb-input size="sm" icon="fas fa-birthday-cake" group type="date" required v-model="newUser.birthDate" v-bind:label="user.birthDate"/>
          <mdb-input size="sm" icon="fas fa-info" group type="text" required v-model="newUser.information" v-bind:label="user.information"/>
          <b-form-group v-bind:label="user.gender ? 'Male' : 'Female'">
            <b-form-radio-group size="sm" v-model="newUser.gender"
                                :options="genders"
                                name="radioInline">
            </b-form-radio-group>
          </b-form-group>
        </mdb-modal-body>
        <mdb-modal-footer center>
          <mdb-btn outline="dark" @click="approve">Yes</mdb-btn>
          <mdb-btn color="dark" @click="cancel">No</mdb-btn>
        </mdb-modal-footer>
      </mdb-modal>
    </mdb-container>
  </div>
</template>
<script>
  import {mapState, mapGetters, mapMutations, mapActions} from 'vuex'
  import 'bootstrap-css-only/css/bootstrap.min.css';
  import 'mdbvue/build/css/mdb.css';
  import VueMaterial from 'vue-material';
  import 'vue-material/dist/vue-material.min.css';

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
        genders: [
          {text: 'Male', value: true},
          {text: 'Female', value: false},
        ],
      };
    },
    computed: {
      ...mapState([
        'user',
        'enabledUserUpdate'
      ]),
    },
    methods: {
      ...mapActions([
        'updateUser',
        'retrieveUsers'
      ]),
      ...mapMutations([
        'disableUserUpdate',
        'destroyUser'
      ]),
      async approve() {
        let data = {
          id: this.user.id,
          password: this.newUser.password || this.user.password,
          gender: this.newUser.gender || this.user.gender,
          birthDate: this.newUser.birthDate || this.user.birthDate,
          information: this.newUser.information || this.user.information,
        };
        await this.updateUser(data);
        this.retrieveUsers();
        this.destroyUser();
        this.disableUserUpdate();
      },
      cancel() {
        this.destroyUser();
        this.disableUserUpdate();
      },
    }
  };
</script>

<style scoped>

</style>
