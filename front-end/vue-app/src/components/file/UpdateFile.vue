<template>
  <div class="submitform">

    <div class="form-group">
      <label for="id">id</label>
      <input type="text" class="form-control" id="id" required v-model="file.id" name="id">
    </div>

    <div class="form-group">
      <label for="description">description</label>
      <input type="text" class="form-control" id="description" required v-model="file.description" name="description">
    </div>

    <div class="form-group">
      <label for="categoryId">categoryId</label>
      <input type="text" class="form-control" id="categoryId" required v-model="file.categoryId" name="categoryId">
    </div>

    <div v-if="!validation">
      <div class="form-group">
        {{ response }}
      </div>
    </div>

    <button v-on:click="saveFile" class="btn btn-success">update</button>
  </div>
</template>

<script>
  import http from "../../http-common";

  export default {
    name: "UpdateFile",
    data() {
      return {
        file: {
          id: "",
          description: "",
          categoryId: "",
        },
        validation: true,
        response: [],
      };
    },
    methods: {
      /* eslint-disable no-console */
      saveFile() {
        var data = {
          id: this.file.id,
          description: this.file.description,
          categoryId: this.file.categoryId,
        };

        http
          .put("/file", data)
          .then(response => {
            console.log(response.data);
            this.$router.push('/');
          })
          .catch(e => {
            this.validation = false;
            this.response.push(e.response.data.message);
            console.log(e);
          });
      }
      /* eslint-enable no-console */
    }
  };
</script>

<style>
  .submitform {
    max-width: 300px;
    margin: auto;
  }
</style>
