<template>
  <div class="container mt-5">
    <table class="table table-bordered table-hover">
      <thead>
      <tr>
        <th>id</th>
        <th>user_id</th>
        <th>url</th>
        <th>description</th>
        <th>date</th>
        <th>categoryId</th>
        <th></th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="file in files">
        <th>{{ file.id }}</th>
        <th>{{ file.user_id }}</th>
        <td>{{ file.url }}</td>
        <td>{{ file.description }}</td>
        <td>{{ file.date }}</td>
        <td>{{ file.categoryId }}</td>
        <td>
          <button class="btn btn-dark" @click="deleteFile(file.id)">delete</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
  import http from "../../http-common";

  export default {
    name: "files",
    data() {
      return {
        fields: [
          'id',
          'user_id',
          'url',
          'description',
          'date',
          'categoryId'
        ],
        files: []
      };
    },

    methods: {
      /* eslint-disable no-console */
      retrieveFiles() {
        http
          .get("/files")
          .then(response => {
            this.files = response.data; // JSON are parsed automatically.
            console.log(response.data);
          })
          .catch(e => {
            console.log(e);
          });
      },
      deleteFile(id) {
        http
          .delete("/file/" + id)
          .then(response => {
            console.log(response.data);
            this.$router.push('/Files');
            this.retrieveFiles();
          })
          .catch(e => {
            this.validation = false;
            this.response.push(e.response.data.message);
            console.log(e);
          });
      }
      /* eslint-enable no-console */
    },
    mounted() {
      this.retrieveFiles();
    },
  };
</script>

<style>
  .list {
    text-align: left;
    max-width: 450px;
    margin: auto;
  }
</style>
