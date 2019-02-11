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

  export default {
    name: "files",
    data() {
      return {
        files: [],
      };
    },

    methods: {
      retrieveFiles() {
        this.$store.dispatch('getFiles')
          .then(response => {
            this.files = response.data
            console.log(this.files)
          })
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
