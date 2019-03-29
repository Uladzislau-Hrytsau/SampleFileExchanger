<template>
  <div class="container-fluid">
    <mdb-container>
      <mdb-modal @close="cancel" size="sl" class="text-center" dark>

        <mdb-modal-header center>
          <p class="heading">Are you sure you want to update the folder?</p>
        </mdb-modal-header>

        <mdb-modal-body>

          <mdb-input size="sm" required group type="text" v-model="name" :label="this.folder.name"/>

        </mdb-modal-body>

        <mdb-modal-footer center>
          <mdb-btn outline="dark" @click="approve">Update</mdb-btn>
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
    name: "UpdateFolderModal",
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
        'folder'
      ])
    },
    methods: {
      ...mapActions([
        'retrieveStructureAndCategories',
        'updateFolder'
      ]),
      ...mapMutations([
        'disableFolderUpdate'
      ]),
      async approve() {
        await this.updateFolder(this.name);
        this.retrieveStructureAndCategories();
        this.disableFolderUpdate();
      },
      cancel() {
        this.disableFolderUpdate();
      },
    }
  }
</script>

<style scoped>

</style>
