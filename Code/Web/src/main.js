import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import ApiService from "./common/api.service";
import MockService from "./common/mock/mock.service";
import { VERIFY_AUTH } from "./store/auth.module";
import '@/permission' 

import * as VueGoogleMaps from 'vue2-google-maps'

Vue.config.productionTip = false;

// Global 3rd party plugins
import "bootstrap";
import "popper.js";
import "tooltip.js";
import "perfect-scrollbar";

// Vue 3rd party plugins
import i18n from "./common/plugins/vue-i18n";
import vuetify from "./common/plugins/vuetify";
import VueToast from 'vue-toast-notification';
// Import any of available themes
import 'vue-toast-notification/dist/theme-default.css';
import "./common/plugins/bootstrap-vue";
import "./common/plugins/perfect-scrollbar";
import "./common/plugins/highlight-js";
import "@babel/polyfill";
import "@mdi/font/css/materialdesignicons.css";
import DLoading from '@/views/theme/components/DLoading.vue'
import './utils/g-filter'
import 'datejs'
Vue.component('d-loading', DLoading);

Vue.use(VueGoogleMaps, {
  load: {
    key: 'AIzaSyDwn1LVzfBeHNGrXB8g5DbG6AvIb_3Xap8',
    libraries: 'places', // This is required if you use the Autocomplete plugin

  },

  
});
// API service init
ApiService.init();

// Remove this to disable mock API
MockService.init();

// Ensure we checked auth before each page load.
router.beforeEach((to, from, next) => {
  Promise.all([store.dispatch(VERIFY_AUTH)]).then(next);

  // Scroll page to top on every route change
  setTimeout(() => {
    window.scrollTo(0, 0);
  }, 100);
});

new Vue({
  router,
  store,
  i18n,
  vuetify,
  VueToast,
  render: h => h(App)
}).$mount("#app");
