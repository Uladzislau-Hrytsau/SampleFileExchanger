import axios from "axios";
import VueCookies from 'vue-cookies'
import Vue from 'vue'

Vue.use(VueCookies);

const qs = require('query-string');

export default axios.create({
  baseURL: "http://localhost:8088",

});
