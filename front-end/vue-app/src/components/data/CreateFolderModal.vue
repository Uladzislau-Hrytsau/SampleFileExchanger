<template>
  <div class="container-fluid">
    <mdb-container>
      <mdb-modal @close="cancel" size="sm" class="text-center" dark>

        <mdb-modal-header center>
          <p class="heading">Are you sure you want to create new folder?</p>
        </mdb-modal-header>

        <mdb-modal-body>

          <mdb-input size="sm" required group type="text" v-model="name" label="name"/>

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
  import 'bootstrap-css-only/css/bootstrap.min.css';
  import 'mdbvue/build/css/mdb.css';
  import VueMaterial from 'vue-material'
  import 'vue-material/dist/vue-material.min.css'
  import {mapState, mapGetters, mapActions, mapMutations} from 'vuex';
  import {
    mdbContainer,
    mdbModal,
    mdbModalHeader,
    mdbModalBody,
    mdbModalFooter,
    mdbBtn,
    mdbInput
  } from 'mdbvue';

  export default {
    name: "CreateFolderModal",
    components: {
      mdbContainer,
      mdbModal,
      mdbModalHeader,
      mdbModalBody,
      mdbModalFooter,
      mdbBtn,
      mdbInput
    },
    data() {
      return {
        name: ''
      };
    },
    computed: {
      ...mapState([
        'enabledFolderCreate',
        'folderId'
      ])
    },
    methods: {
      ...mapActions([
        'createFolder',
        'retrieveStructureAndCategories'
      ]),
      ...mapMutations([
        'disableFolderCreate'
      ]),
      async approve() {
        let data = {
          id: this.folderId,
          name: this.name
        };
        await this.createFolder(data);
        this.retrieveStructureAndCategories();
        this.disableFolderCreate();
      },
      cancel() {
        this.disableFolderCreate();
      },
    }
  }
</script>

<style scoped>

</style>
