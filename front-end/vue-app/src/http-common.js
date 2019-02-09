import axios from "axios";
import VueCookies from 'vue-cookies'
import 'bootstrap-css-only/css/bootstrap.min.css';
import 'mdbvue/build/css/mdb.css';

Vue.use(VueCookies);

const qs = require('query-string');

export default axios.create({
  baseURL: "http://localhost:8088",

});
