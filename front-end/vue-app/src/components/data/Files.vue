<template>
  <div class="container col-8 mt-4">
    <mdb-container>
      <mdb-row>
        <div class="card testimonial-ca rd autocomplete" v-for="file in fileStructure">
          <div class="card-body">
            <div>
              <button class="btn fas fa-download animated tada infinite" v-on:click="download(file)"></button>
              <button class="btn fas fa-info animated tada infinite" v-on:click="enableUpdatingTemplate(file.id)"></button>
              <button class="btn far fa-trash-alt animated tada infinite" v-on:click="deleteFileByFolderId(file.id)"></button>
            </div>
            <div class="mt-2">
              <h4 class="far fa-file fa-5x"></h4>
              <h4 class=" mt-2 card-title" v-text="file.name.slice(0,8).concat('...')"></h4>
            </div>
          </div>
        </div>
      </mdb-row>
    </mdb-container>
  </div>
</template>

<script>
  import 'bootstrap-css-only/css/bootstrap.min.css';
  import 'mdbvue/build/css/mdb.css';
  import VueMaterial from 'vue-material'
  import 'vue-material/dist/vue-material.min.css'
  import {mapState, mapGetters, mapActions, mapMutations} from 'vuex';
  import {
    mdbContainer,
    mdbRow
  } from 'mdbvue'

  export default {
    name: "Files",
    components: {
      mdbContainer,
      mdbRow
    },
    computed: {
      ...mapState([
        'fileStructure',
      ])
    },
    methods: {
      ...mapMutations([
        'setFile',
        'enableFileUpdate',
        'enableUserSide'
      ]),
      ...mapActions([
        'deleteFile',
        'retrieveStructureAndCategories',
        'downloadFile',
        'getFileInformationByFileId'
      ]),
      async enableUpdatingTemplate(id) {
        await this.getFileInformationByFileId(id);
        this.enableUserSide();
        this.enableFileUpdate()
      },
      async deleteFileByFolderId(fileId) {
        await this.deleteFile(fileId);
        this.retrieveStructureAndCategories();
      },
      download(file) {
        this.downloadFile(file);
      }
    }
  }
</script>

<style scoped>

</style>
