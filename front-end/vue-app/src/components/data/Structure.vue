<template>
  <div class="container col-12 mt-3">
    <CreateFolderModal v-if="enabledFolderCreate"></CreateFolderModal>
    <CreateFileModal v-if="enabledFileCreate"></CreateFileModal>
    <UpdateFolderModal v-if="enabledFolderUpdate"></UpdateFolderModal>
    <Category></Category>
    <Panel></Panel>
    <Folders></Folders>
    <Files></Files>
  </div>
</template>

<script>
  import 'bootstrap-css-only/css/bootstrap.min.css';
  import 'mdbvue/build/css/mdb.css';
  import VueMaterial from 'vue-material'
  import 'vue-material/dist/vue-material.min.css'
  import {mapState, mapGetters, mapActions, mapMutations} from 'vuex';
  import Category from './Category';
  import Panel from './Panel';
  import Folders from './Folders';
  import Files from './Files';
  import CreateFolderModal from './CreateFolderModal';
  import CreateFileModal from './CreateFileModal';
  import UpdateFolderModal from './UpdateFolderModal';
  import {
    mdbContainer,
    mdbRow,
    mdbCol,
    mdbInput,
    mdbTextarea,
    mdbBtn,
    mdbBtnGroup,
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
      mdbBtnGroup,
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
      Category,
      Panel,
      Folders,
      Files,
      CreateFolderModal,
      CreateFileModal,
      UpdateFolderModal
    },

    data() {
      return {};
    },
    computed: {
      ...mapState([
        'enabledFolderCreate',
        'enabledFileCreate',
        'enabledFolderUpdate'
      ])
    },
    methods: {
      ...mapActions([
        'retrieveStructureAndCategories'
      ]),
    },
    mounted() {
      this.retrieveStructureAndCategories();
    }
  };
</script>

<style scoped>

</style>
