<template>
  <div class="container col-8">
    <mdb-row class="m-3">
      <mdb-btn-group>
        <mdb-btn outline="mdb-color" rounded>all</mdb-btn>
        <mdb-btn v-bind:key="category.name" v-for="category in categories" outline="mdb-color" v-text="category.name" rounded></mdb-btn>
      </mdb-btn-group>
    </mdb-row>
  </div>
</template>

<script>
  import {mapState, mapGetters, mapActions, mapMutations} from 'vuex';
  import 'bootstrap-css-only/css/bootstrap.min.css';
  import 'mdbvue/build/css/mdb.css';
  import VueMaterial from 'vue-material'
  import 'vue-material/dist/vue-material.min.css'
  import {
    mdbRow,
    mdbBtnGroup,
    mdbBtn
  } from 'mdbvue'

  export default {
    name: "Category",
    components: {
      mdbRow,
      mdbBtnGroup,
      mdbBtn
    },
    computed: {
      ...mapState([
        'categories'
      ])
    }
  }
</script>

<style scoped>

</style>
