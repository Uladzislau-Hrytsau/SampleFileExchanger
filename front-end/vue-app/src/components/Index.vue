<template>
  <div>
    <h4>Login</h4>
    <form>
      <label for="login">Login</label>
      <div>
        <input id="login" type="text" v-model="login" required autofocus placeholder="Login">
      </div>
      <div>
        <label for="password">Password</label>
        <div>
          <input id="password" type="password" v-model="password" required placeholder="Password">
        </div>
      </div>
      <div>
        <button type="submit" @click="signIn()">
          Sign in
        </button>
      </div>
    </form>
  </div>
</template>

<script>

  import http from "../http-common";
  const qs = require('query-string');

  export default {
    data() {
      return {
        login: "",
        password: ""
      }
    },
    methods: {
      signIn() {
        const requestBody = {
          username: 'vlad',
          password: '256247',
          grant_type: 'password',
        }
        const config = {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': 'Basic Y2xpZW50SWRQYXNzd29yZDpzZWNyZXQ=',
          }
        }
        http.post('/oauth/token',qs.stringify(requestBody), config
          /*{
            headers: {
              "Content-Type": "application/x-www-form-urlencoded",
              "Authorization": "Basic Y2xpZW50SWRQYXNzd29yZDpzZWNyZXQ=",
              "cache-control": "no-cache",
              "Access-Control-Allow-Origin": "*"
            },
            body: {
              'grant_type': 'password',
              'username': 'vlad',
              'password': '256247'
            }
          }*/
        )
          .then(response => {
            console.log(response)
          })
          .catch(function (error) {
            console.error(error);
          });
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
    margin: 0 10px;
  }

  a {
    color: #42b983;
  }
</style>
