<template>
  <div>
    <mdb-container>
      <mdb-row>
        <mdb-col size="12" class="text-center mb-5">
          <mdb-modal-body class="grey-text">
            <mdb-input size="sm" label="Url" icon="user" group type="text" validate error="wrong"
                       success="right" required v-model="file.url"/>
            <mdb-input size="sm" label="Description" icon="key" group type="text" validate error="wrong"
                       success="right" required v-model="file.description"/>
            <label>
              <select v-model="selected" class="browser-default custom-select">
                <option selected value="1">Default</option>
                <option value="2">Work</option>
                <option value="3">Entertainment</option>
              </select>
            </label>

            <div class="large-12 medium-12 small-12 cell">
              <label>File
                <input type="file" id="upFile" ref="file" v-on:change="handleFileUpload()"/>
              </label>
              <button v-on:click="uploadFile">Submit</button>
            </div>

          </mdb-modal-body>
          <mdb-modal-footer>
            <mdb-btn color="primary" v-on:click="createFile">Create</mdb-btn>
          </mdb-modal-footer>
          <div v-if="!validation">
            <div class="form-group">
              {{ response }}
            </div>
          </div>
        </mdb-col>
      </mdb-row>
    </mdb-container>
  </div>

</template>

<script>
  import 'bootstrap-css-only/css/bootstrap.min.css';
  import 'mdbvue/build/css/mdb.css';

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
    mdbModalFooter
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
      mdbModalFooter
    },
    data() {
      return {
        selected: 1,
        upFile: "",
        file: {
          url: "",
          description: "",
          categoryId: "",
        },

        validation: true,
        response: [],

      };
    },
    methods: {
      createFile() {
        var data = {
          userId: this.$store.state.user.userId,
          url: this.file.url,
          description: this.file.description,
          categoryId: this.selected,
        };
        this.$store.dispatch('createFile', {
          data: data
        })
          .then(response => {
            this.$router.push('/Main');
            this.$store.dispatch('')
          })
          .catch(error => {
            this.validation = false;
            this.response.push(error.response.data.message)
            console.log(error)
          })
      },
      uploadFile() {
        let data = {
          upFile: this.upFile
        };
        this.$store.dispatch('uploadFile', {
          data: data
        })
          .then(response => {
            console.log("----- successfully -----")
          })
          .catch(error => {
            console.log(error)
          })

        // let formData = new FormData();
        // formData.append('file', this.file);
        // axios.post( 'uploading',
        //   formData,
        //   {
        //     headers: {
        //       'Content-Type': 'multipart/form-data'
        //     }
        //   }
        // ).then(function(){
        //   console.log('SUCCESS!!');
        // })
        //   .catch(function(){
        //     console.log('FAILURE!!');
        //   });
      },
      handleFileUpload() {
        this.upFile = this.$refs.file.files[0];
      }
    },
    mounted() {
      this.$store.dispatch('getUserInformation')
    }
  };
</script>

<style scoped>

</style>
