<template>

  <div class="container mt-5">
    <button class="btn btn-dark" @click="obtainAccessToken()">users</button>
    <table class="table table-bordered table-hover">
      <thead>
      <tr>
        <th>userId</th>
        <th>login</th>
        <th>password</th>
        <th>gender</th>
        <th>birthDate</th>
        <th>information</th>
        <th></th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in users">
        <th>{{ user.userId }}</th>
        <th>{{ user.login }}</th>
        <td>{{ user.password }}</td>
        <td>{{ user.gender }}</td>
        <td>{{ user.birthDate }}</td>
        <td>{{ user.information }}</td>
        <td>
          <button class="btn btn-dark" @click="deleteUser(user.userId)">delete</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
  import http from "../../http-common";
  import { Cookie } from 'ng2-cookies';
  import VueRequests from 'vue-requests'
  import { Http, Response, Headers, RequestOptions } from '@angular/http';
  import 'rxjs/add/operator/map';
  import 'rxjs/rx';
  // import { Observable } from 'rxjs/Observable';

  export default {
    name: "Users",
    data() {
      return {
        fields: [
          'userId',
          'login',
          'password',
          'gender',
          'birthDate',
          'information',
          'delete'
        ],
        users: [],
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
      deleteUser(userId) {
        http
          .delete("/user/" + userId)
          .then(response => {
            console.log(response.data);
            this.$router.push('/Users');
            this.retrieveUsers();
          })
          .catch(e => {
            this.validation = false;
            this.response.push(e.response.data.message);
            console.log(e);
          });
      },
      obtainAccessToken(){
        let params = new URLSearchParams();
        params.append('username',"vlad");
        params.append('password',"256247");
        params.append('grant_type','password');
        params.append('client_id','clientIdPassword');

        let headers = new Headers({'Content-type': 'application/x-www-form-urlencoded; charset=utf-8', 'Authorization': 'Basic '+btoa("clientIdPassword:secret")});
        let options = new RequestOptions({ headers: headers });
        console.log(params.toString());
        http.post('http://localhost:8088/oauth/token', params.toString(), options)
          .map(res => res.json())
          .subscribe(
            data => this.saveToken(data),
            err => alert('Invalid Credentials')
          );
      },
      saveToken(token){
        var expireDate = new Date().getTime() + (1000 * token.expires_in);
        Cookie.set("access_token", token.access_token, expireDate);
        console.log('Obtained Access token');
        this._router.navigate(['/']);
      },
      // getResource(resourceUrl) : Observable<Foo>{
      //   var headers = new Headers({'Content-type': 'application/x-www-form-urlencoded; charset=utf-8', 'Authorization': 'Bearer '+Cookie.get('access_token')});
      //   var options = new RequestOptions({ headers: headers });
      //   return http.get(resourceUrl, options)
      //     .map((res:Response) => res.json())
      //     .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
      // }
      /* eslint-enable no-console */
    },
    mounted() {
      this.retrieveUsers();
    }
  };
</script>

<style>
  .list {
    text-align: left;
    max-width: 450px;
    margin: auto;
  }
</style>
