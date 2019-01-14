<template>
  <div class="submitform">
    <div class="form-group">
      <label for="userId">userId</label>
      <input type="text" class="form-control" id="userId" required v-model="userId" name="userId">
    </div>
    <div class="form-group">
      <div v-if="!validation">
        <div class="form-group">
          {{ response }}
        </div>
      </div>
    </div>
    <div class="form-group">
      <button v-on:click="deleteUser" class="btn btn-success">Delete</button>
    </div>
  </div>
</template>

<script>

  import http from "../http-common";

  export default {
    name: "DeleteUser",
    data() {
      return {
        userId: "",
        validation: true,
        response: [],
      };
    },
    methods: {
      deleteUser() {
        http
          .delete("/user/" + this.userId)
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
    }

  };
</script>

<style>
  .submitform {
    max-width: 300px;
    margin: auto;
  }
</style>
