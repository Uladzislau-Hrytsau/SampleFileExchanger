<template>
  <div class="submitform">

    <div class="form-group">
      <label for="user_id">user_id</label>
      <input type="text" class="form-control" id="user_id" required v-model="file.user_id" name="user_id">
    </div>

    <div class="form-group">
      <label for="url">url</label>
      <input type="text" class="form-control" id="url" required v-model="file.url" name="url">
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

    <button v-on:click="saveFile" class="btn btn-success">create</button>
  </div>
</template>

<script>

  export default {
    name: "file",
    data() {
      return {
        file: {
          user_id: "",
          url: "",
          description: "",
          date: "",
          categoryId: "",
        },
        validation: true,
        response: [],
      };
    },
    methods: {
      saveFile() {
        var data = {
          user_id: this.file.user_id,
          url: this.file.url,
          description: this.file.description,
          date: this.file.date,
          categoryId: this.file.categoryId,
        };

        const config = {
          headers: {
            'Authorization': 'Bearer ' + $cookies.get('token'),
          }
        };

        http
          .post("/file", data, config)
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
