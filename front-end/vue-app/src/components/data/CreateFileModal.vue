<template>
  <div class="container-fluid">
    <mdb-container>
      <mdb-modal @close="cancel" size="sl" class="text-center" dark>

        <mdb-modal-header center>
          <p class="heading">Are you sure you want to upload file?</p>
        </mdb-modal-header>

        <mdb-modal-body>

          <PointSpinner v-if="enabledPointSpinner"></PointSpinner>

          <div>
            <multiselect
              v-model="selectedCategories"
              :options="categories"
              :multiple="true"
              :group-select="true"
              placeholder="categories"
              track-by="name" label="name">
            </multiselect>
          </div>

          <mdb-input size="sm" required group type="text" v-model="description" label="description"/>
          <input type="file" id="file" ref="file" v-on:change="handleFileUpload()"/>

        </mdb-modal-body>

        <mdb-modal-footer center>
          <mdb-btn outline="dark" @click="approve">Create</mdb-btn>
          <mdb-btn color="dark" @click="cancel">Cancel</mdb-btn>
        </mdb-modal-footer>

      </mdb-modal>
    </mdb-container>
  </div>
</template>

<script>
  import Vue from 'vue';
  import Multiselect from 'vue-multiselect';

  Vue.component('multiselect', Multiselect);
  import 'bootstrap-css-only/css/bootstrap.min.css';
  import 'mdbvue/build/css/mdb.css';
  import VueMaterial from 'vue-material'
  import 'vue-material/dist/vue-material.min.css'
  import {mapState, mapGetters, mapActions, mapMutations} from 'vuex';
  import PointSpinner from '../spinner/PointSpinner';
  import {
    mdbContainer,
    mdbModal,
    mdbModalHeader,
    mdbModalBody,
    mdbModalFooter,
    mdbInput,
    mdbBtn
  } from 'mdbvue';

  export default {
    name: "CreateFileModal",
    components: {
      mdbContainer,
      mdbModal,
      mdbModalHeader,
      mdbModalBody,
      mdbModalFooter,
      mdbInput,
      mdbBtn,
      PointSpinner,
    },
    data() {
      return {
        file: '',
        description: '',

        selectedCategories: []
      };
    },
    computed: {
      ...mapState([
        'categories',
        'enabledPointSpinner'
      ])
    },
    methods: {
      ...mapActions([
        'uploadFile',
        'retrieveStructureAndCategories'
      ]),
      ...mapMutations([
        'disableFileCreate',
        'enablePointSpinner',
        'disablePointSpinner'
      ]),
      async approve() {
        console.log(this.selectedCategories);
        let categories = [];
        this.selectedCategories.forEach(value => categories.push(value.id));
        this.enablePointSpinner();
        let data = {
          file: this.file,
          description: this.description,
          selectedCategories: categories
        };
        await this.uploadFile(data);
        // TODO: do i need to get only files or structure and categories
        this.retrieveStructureAndCategories();
        this.disableFileCreate();
        this.disablePointSpinner();
      },
      cancel() {
        this.disablePointSpinner();
        this.disableFileCreate();
      },
      handleFileUpload() {
        this.file = this.$refs.file.files[0];
      }
    }
  }
</script>

<style scoped>

</style>
