<template>
  <div class="container col-12 mt-3">

      <div class="container col-8">
        <mdb-row class="m-3">
          <mdb-btn-group>
            <mdb-btn outline="mdb-color" rounded>all</mdb-btn>
            <mdb-btn v-for="value in values" outline="mdb-color" v-text="value.name" :key="value" rounded></mdb-btn>
          </mdb-btn-group>
        </mdb-row>
      </div>

      <div class="container col-8">
        <div class="row align-items-center">
          <div class="col-6">
            <mdb-btn class="col-12 btn btn-outline-black waves-effect">add folder</mdb-btn>
          </div>
          <div class="col-6">
            <mdb-btn class="col-12 btn btn-outline-black waves-effect">add file</mdb-btn>
          </div>
        </div>
      </div>

      <div class="container col-12 mt-3">
        <mdb-container>
          <mdb-row>
            <div class="card testimonial-ca rd autocomplete" v-for="folder in folders" v-bind:key="folder">
              <div class="card-body">
                <div>
                  <button class="btn fas fa-download animated tada infinite"></button>
                  <button class="btn fas fa-info animated tada infinite"></button>
                  <button class="btn far fa-trash-alt animated tada infinite"></button>
                </div>
                <div class="mt-2">
                  <h4 class="far fa-folder fa-5x" v-on:click="displayStructure(folder.id)"></h4>
                  <h4 class=" mt-2 card-title" v-text="folder.name" v-on:click="displayStructure(folder.id)"></h4>
                </div>
              </div>
            </div>
          </mdb-row>
        </mdb-container>
      </div>

      <div class="container col-12 mt-3">
        <mdb-container>
          <mdb-row>
            <div class="card testimonial-ca rd autocomplete" v-for="file in files" v-bind:key="file">
              <div class="card-body">
                <div>
                  <button class="btn fas fa-download animated tada infinite"></button>
                  <button class="btn fas fa-info animated tada infinite"></button>
                  <button class="btn far fa-trash-alt animated tada infinite"></button>
                </div>
                <div class="mt-2">
                  <h4 class="far fa-file fa-5x"></h4>
                  <h4 class=" mt-2 card-title" v-text="file.realName"></h4>
                </div>
              </div>
            </div>
          </mdb-row>
        </mdb-container>
      </div>

  </div>
</template>

<script>
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
    mdbModalFooter,
    mdbCard,
    mdbCardImage,
    mdbCardHeader,
    mdbCardBody,
    mdbCardTitle,
    mdbCardText,
    mdbCardFooter,
    mdbCardUp,
    mdbCardAvatar,
    mdbCardGroup,
    mdbView,
    mdbMask,
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
      mdbModalFooter,
      mdbCard,
      mdbCardImage,
      mdbCardHeader,
      mdbCardBody,
      mdbCardTitle,
      mdbCardText,
      mdbCardFooter,
      mdbCardUp,
      mdbCardAvatar,
      mdbCardGroup,
      mdbView,
      mdbMask,
    },

    data() {
      return {
        folders: [],
        files: [],
        selected: 1,
        multipartFile: "",
        folderId: "",
        description: "",
        categories: [],
        validation: true,
        response: [],
      };
    },

    methods: {

      displayStructure(folderId) {
        this.$store.dispatch('displayStructure', {
          folderId: folderId
        })
          .then(response => {
            this.folders = response.data.folderStructureDtos;
            this.files = response.data.fileStructureDtos;
            console.log(this.folders);
            console.log(this.files);
          })
          .catch(error => {
            console.log(error);
          })
      },

      uploadFile() {
        let file = {
          folderId: this.folderId,
          description: this.description,
          categories: this.categories,
        };
        this.$store.dispatch('uploadFile', {
          file: file,
          multipartFile: this.multipartFile
        })
          .then(response => {
            console.log("----- successfully -----")
            this.$router.push('/Main');
          })
          .catch(error => {
            console.log(error)
          })
      },
    },

    mounted() {
      this.displayStructure(0);
    }
  };
</script>

<style scoped>
  .btn {
    border: none;
    padding: 12px 16px;
    font-size: 16px;
    cursor: pointer;
  }
</style>
