<template>
  <div class="submitform">

    <div class="form-group">
      <label for="userId">userId</label>
      <input type="text" class="form-control" id="userId" required v-model="user.userId" name="userId">
    </div>

    <div class="form-group">
      <label for="login">login</label>
      <input type="text" class="form-control" id="login" required v-model="user.login" name="login">
    </div>

    <div class="form-group">
      <label for="password">password</label>
      <input type="text" class="form-control" id="password" required v-model="user.password" name="password">
    </div>

    <div class="form-group">
      <label for="birthDate">birthDate</label>
      <input type="date" class="form-control" id="birthDate" required v-model="user.birthDate" name="birthDate">
    </div>

    <div class="form-group">
      <label for="information">information</label>
      <input type="text" class="form-control" id="information" required v-model="user.information" name="information">
    </div>

    <div class="form-group">
      <form action="">
        <input required v-model="user.gender" type="radio" name="gender" value="true"><label>Male</label><br>
        <input required v-model="user.gender" type="radio" name="gender" value="false"><label>Female</label><br>
      </form>
    </div>

    <div v-if="!validation">
      <div class="form-group">
        {{ response }}
      </div>
    </div>

    <button v-on:click="saveCustomer" class="btn btn-success">update</button>
  </div>
</template>
<script>
  import http from "../../http-common";

  export default {
    name: "UpdateUser",
    data() {
      return {
        user: {
          userId: "",
          login: "",
          password: "",
          gender: "",
          birthDate: "",
          information: "",
        },
        validation: true,
        response: [],
      };
    },
    methods: {
      /* eslint-disable no-console */
      saveCustomer() {
        var data = {
          userId: this.user.userId,
          login: this.user.login,
          password: this.user.password,
          gender: this.user.gender,
          birthDate: this.user.birthDate,
          information: this.user.information,
        };

        http
          .put("/user", data)
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
