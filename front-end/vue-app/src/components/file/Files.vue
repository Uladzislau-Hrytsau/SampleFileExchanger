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
        <td>
          <button class="btn btn-dark" @click="updateFile(file)">update</button>
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
          })
      },
      deleteFile(id) {
        this.$store.dispatch('deleteFile', {
          id: id
        })
          .then(response => {
            this.retrieveFiles();
          })
          .catch(error => {
            console.log(error)
          })
      },
      updateFile(file) {
        this.$store.dispatch('saveFileInformation', {
          file: file,
        })
        this.$router.push('/UpdateFile')
      },
    },
    mounted() {
      this.retrieveFiles();
    },
  };
</script>

<style scoped>

</style>
