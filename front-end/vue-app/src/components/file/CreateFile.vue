<!--<div>-->
<!--<mdb-container>-->
<!--<mdb-row>-->
<!--<mdb-col size="12" class="text-center mb-5">-->
<!--<mdb-modal-body class="grey-text">-->

<!--<mdb-input size="sm" label="folderId" icon="user" group type="text" validate error="wrong"-->
<!--success="right" required v-model="folderId"/>-->

<!--<mdb-input size="sm" label="description" icon="key" group type="text" validate error="wrong"-->
<!--success="right" required v-model="description"/>-->

<!--<mdb-input size="sm" label="categories" icon="key" group type="text" validate error="wrong"-->
<!--success="right" required v-model="categories"/>-->

<!--<div class="md-layout-item">-->
<!--<md-field>-->
<!--<label for="categories">Movies</label>-->
<!--<md-select v-model="categories" name="categories" id="categories" md-dense multiple>-->
<!--<md-option value="fight-club">Fight Club</md-option>-->
<!--<md-option value="godfather">Godfather</md-option>-->
<!--<md-option value="godfather-ii">Godfather II</md-option>-->
<!--<md-option value="godfather-iii">Godfather III</md-option>-->
<!--<md-option value="godfellas">Godfellas</md-option>-->
<!--<md-option value="pulp-fiction">Pulp Fiction</md-option>-->
<!--<md-option value="scarface">Scarface</md-option>-->
<!--</md-select>-->
<!--</md-field>-->
<!--</div>-->

<!--<label>-->
<!--<select v-model="selected" class="browser-default custom-select">-->
<!--<option selected value="1">Default</option>-->
<!--<option value="2">Work</option>-->
<!--<option value="3">Entertainment</option>-->
<!--</select>-->
<!--</label>-->

<!--<div class="large-12 medium-12 small-12 cell">-->
<!--<label>File-->
<!--<input type="file" id="multipartFile" ref="file" v-on:change="handleFileUpload()"/>-->
<!--</label>-->
<!--</div>-->

<!--</mdb-modal-body>-->
<!--<mdb-modal-footer>-->
<!--<mdb-btn color="primary" v-on:click="uploadFile">Create</mdb-btn>-->
<!--</mdb-modal-footer>-->
<!--<div v-if="!validation">-->
<!--<div class="form-group">-->
<!--{{ response }}-->
<!--</div>-->
<!--</div>-->
<!--</mdb-col>-->
<!--</mdb-row>-->
<!--</mdb-container>-->
<!--</div>-->

<!--<div>-->
<!--<div>-->
<!--<div class="md-layout md-gutter">-->
<!--<div class="md-layout-item">-->
<!--<md-field>-->
<!--<label for="categories">categories</label>-->
<!--<md-select v-model="categories" name="values" id="values" md-dense multiple>-->
<!--<md-option v-for="value in values" :value="value" :key="value">{{ value }}</md-option>-->
<!--</md-select>-->
<!--</md-field>-->
<!--</div>-->
<!--</div>-->
<!--</div>-->

<!--<div>-->
<!--<v-folder :data="data" :conf="conf" @change="onChange"></v-folder>-->
<!--</div>-->
<!--</div>-->
<!--<div>-->
<!--<div><p>tewsfsefeswdxawdawdt</p></div>-->
<!--<sl-vue-tree v-model="nodes" @nodeclick="nodeClick">-->

<!--<template slot="toggle" slot-scope="{ node }">-->
<!--<span v-if="!node.isLeaf">-->
<!--<icon name="caret-right" v-if="!node.isExpanded"></icon>-->
<!--<icon name="caret-down" v-if="node.isExpanded"></icon>-->
<!--</span>-->
<!--</template>-->

<!--<template slot="title" slot-scope="{ node }">-->
<!--<icon name="file" v-if="node.isLeaf"></icon>-->
<!--{{ node.title }}-->
<!--</template>-->

<!--<template slot="sidebar" slot-scope="{ node }">-->
<!--<icon name="circle" v-if="node.data.isModified"></icon>-->
<!--</template>-->
<!--</sl-vue-tree>-->
<!--</div>-->

<!--right-->
<!--<mdb-container class="container-fluid mt-5">-->
<!--<mdb-row>-->
<!--<mdb-col v-for="value in values">-->
<!--<mdb-card-image class="far fa-folder fa-5x mt-4 mb-4 ml-4 mr-4">-->
<!--</mdb-card-image>-->
<!--</mdb-col>-->

<!--<mdb-col sm="2" class="mt-1 mb-1 ml-1 mr-1">-->
<!--<mdb-card dark>-->
<!--<mdb-card-body color="elegant" class="white-text">-->
<!--<mdb-card-title> add</mdb-card-title>-->
<!--</mdb-card-body>-->
<!--</mdb-card>-->
<!--</mdb-col>-->

<!--</mdb-row>-->
<!--</mdb-container>-->
<!--right-->
<template>
  <div class="container col-12 mt-3">

    <div class="container col-8 border-bottom border-dark">
      <mdb-row class="m-3">
        <mdb-btn-group>
          <mdb-btn outline="mdb-color" rounded>all</mdb-btn>
          <mdb-btn v-for="value in values" outline="mdb-color" v-text="value.name" :key="value" rounded></mdb-btn>
        </mdb-btn-group>
      </mdb-row>
    </div>

    <div class="container col-8 border-bottom border-dark">
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
          <div class="card testimonial-ca rd autocomplete" v-for="value in values">
            <div class="card-body">
              <div>
                <button class="btn fas fa-info animated tada infinite"></button>
                <button class="btn far fa-edit animated tada infinite"></button>
                <button class="btn far fa-trash-alt animated tada infinite">
                  <!--<i class="far fa-trash-alt"></i>-->
                  <!--<i class="far fa-edit"></i>-->
                </button>
              </div>
              <div class="mt-2" v-on:click="displayStructure(value.id)">
                <h4 class="far fa-folder fa-5x"></h4>
                <h4 class=" mt-2 card-title" v-text="value.name"></h4>
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
  import axios from 'axios'
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
        values: [],
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
        // folderId = 9;
        this.$store.dispatch('displayStructure', {
          folderId: folderId
        })
          .then(response => {
            this.values = response.data
          })
          .catch(error => {
            console.log(error )
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

  .btn:hover {
  }

  .custom-fon {

  }

  /*.myOwnClassButton:hover .fa-trash-alt,*/
  /*.myOwnClassButton .fa-edit {*/
  /*display: none;*/
  /*}*/
  /*.myOwnClassButton:hover .fa-edit {*/
  /*display: inline;*/
  /*}*/
</style>
