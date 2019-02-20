<template>
  <div>
    <mdb-container>
      <mdb-row>
        <mdb-col size="12" class="text-center mb-5">
          <mdb-modal-body class="grey-text">
            <mdb-input size="sm" label="Description" icon="far fa-comment-alt" group type="text" validate error="wrong"
                       success="right" required v-model="fileNew.description"/>
            <label>
              <select v-model="fileNew.categoryId" class="browser-default custom-select">
                <option selected value="1">Default</option>
                <option value="2">Work</option>
                <option value="3">Entertainment</option>
              </select>
            </label>
          </mdb-modal-body>
          <mdb-modal-footer>
            <mdb-btn color="primary" v-on:click="saveFile">update</mdb-btn>
          </mdb-modal-footer>
        </mdb-col>
      </mdb-row>
    </mdb-container>
  </div>
</template>
<script>

  import 'bootstrap-css-only/css/bootstrap.min.css';
  import 'mdbvue/build/css/mdb.css';

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
    name: "UpdateUser",
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
        fileOld: this.$store.state.file,
        fileNew: {
          description: '',
          categoryId: '',
        },
        validation: true,
        response: [],
      };
    },
    methods: {
      saveFile() {
        var data = {
          id: this.fileOld.id,
          userId: this.fileOld.userId,
          url: this.fileOld.url,
          description: this.fileNew.description,
          categoryId: this.fileNew.categoryId,
        };
        this.$store.dispatch('updateFile', {
          data: data
        })
          .then(response => {
            this.$router.push('/Main')
          })
          .catch(error => {
            console.log(error)
          })
      }
    }
  };
</script>

<style scoped>

</style>
