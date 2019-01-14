<template>
  <div class="container">
    <b-table striped hover :items="files" :fields="fields">
    </b-table>
  </div>
</template>
<script>
  import http from "../http-common";

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
          'category'
        ],
        files: []
      };
    },

    methods: {
      /* eslint-disable no-console */
      retrieveUsers() {
        http
          .get("/users")
          .then(response => {
            this.users = response.data; // JSON are parsed automatically.
            console.log(response.data);
          })
          .catch(e => {
            console.log(e);
          });
      },
      refreshList() {
        this.retrieveUsers();
      }
      /* eslint-enable no-console */
    },
    mounted() {
      this.retrieveUsers();
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
