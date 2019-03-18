<template>
  <!--<div>-->
  <!--<mdb-container>-->
  <!--<mdb-row>-->
  <!--<mdb-col size="12" class="text-center mb-5">-->
  <!--<mdb-modal-body class="grey-text">-->
  <!--<mdb-input size="sm" label="Description" icon="far fa-comment-alt" group type="text" validate error="wrong"-->
  <!--success="right" required v-model="fileNew.description"/>-->
  <!--<label>-->
  <!--<select v-model="fileNew.categoryId" class="browser-default custom-select">-->
  <!--<option selected value="1">Default</option>-->
  <!--<option value="2">Work</option>-->
  <!--<option value="3">Entertainment</option>-->
  <!--</select>-->
  <!--</label>-->
  <!--</mdb-modal-body>-->
  <!--<mdb-modal-footer>-->
  <!--<mdb-btn color="primary" v-on:click="saveFile">update</mdb-btn>-->
  <!--</mdb-modal-footer>-->
  <!--</mdb-col>-->
  <!--</mdb-row>-->
  <!--</mdb-container>-->
  <!--</div>-->

  <div class="container-fluid">
    <mdb-container>
      <mdb-modal v-if="enabledFileUpdate" @close="cancel" size="xl" class="text-center" dark>

        <mdb-modal-header center>
          <p class="heading">Are you sure you want to update information about the file?</p>
        </mdb-modal-header>

        <mdb-modal-body>

          <mdb-input size="sm" icon="fas fa-birthday-cake" group type="text" required v-model="newFile.realName"
                     v-bind:label="file.realName"/>
          <mdb-input size="sm" icon="key" group type="text" required v-model="newFile.description"
                     v-bind:label="file.description"/>
          <mdb-input size="sm" icon="fas fa-info" group type="date" required v-model="newFile.date"
                     v-bind:label="file.date"/>

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
        newFile: {
          realName: '',
          description: '',
          date: ''
        },
      };
    },
    computed: {
      ...mapState([
        'enabledFileUpdate',
        'file'
      ]),
    },
    methods: {
      ...mapMutations([
        'destroyFile',
        'disableFileUpdate'
      ]),
      ...mapActions([
        'updateFile',
        'retrieveFiles'
      ]),
      async approve() {
        let data = {
          id: this.file.id,
          realName: this.newFile.realName,
          description: this.newFile.description,
          date: this.newFile.date,
        };
        await this.updateFile(data);
        this.retrieveFiles();
        this.destroyFile();
        this.disableFileUpdate();
      },
      cancel() {
        this.destroyFile();
        this.disableFileUpdate();
      },
    }
  };
</script>

<style scoped>

</style>
