<template>
  <div class="container col-8">
    <div class="row align-items-center">
      <div class="col-6">
        <mdb-btn class="col-12 btn btn-outline-black waves-effect" v-on:click="addFolder">add folder</mdb-btn>
      </div>
      <div class="col-6">
        <mdb-btn class="col-12 btn btn-outline-black waves-effect" v-on:click="addFile">add file</mdb-btn>
      </div>
    </div>
  </div>
</template>

<script>
  import 'bootstrap-css-only/css/bootstrap.min.css';
  import 'mdbvue/build/css/mdb.css';
  import VueMaterial from 'vue-material'
  import 'vue-material/dist/vue-material.min.css'
  import {mapState, mapGetters, mapActions, mapMutations} from 'vuex';
  import {
    mdbBtn
  } from 'mdbvue';

  export default {
    name: "Panel",
    components: {
      mdbBtn
    },
    methods: {
      ...mapMutations([
        'enableFolderCreate',
        'enableFileCreate'
      ]),
      addFolder() {
        this.enableFolderCreate();
      },
      addFile() {
        this.enableFileCreate();
      }
    }
  }
</script>

<style scoped>

</style>
