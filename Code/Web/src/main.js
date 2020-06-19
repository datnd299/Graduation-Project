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
import VueLodash from 'vue-lodash'
import lodash from 'lodash'
// Import any of available themes
import 'vue-toast-notification/dist/theme-default.css';
import "./common/plugins/bootstrap-vue";
import "./common/plugins/perfect-scrollbar";
import "./common/plugins/highlight-js";
import "@babel/polyfill";
import "@mdi/font/css/materialdesignicons.css";
import DLoading from '@/views/theme/components/DLoading.vue'
import './utils/g-filter'
import 'datejs';
import DisableAutocomplete from 'vue-disable-autocomplete';

Vue.use(DisableAutocomplete);
Vue.component('d-loading', DLoading);
Vue.use(VueLodash, { lodash: lodash })

import VueSocketIO from 'vue-socket.io'
import SocketIO from "socket.io-client"


const socketInstance = SocketIO('http://localhost:8086', {
  transports: ['websocket']
});
socketInstance.on('newMessageSended', () => {
  console.log("connected");
})
Vue.use(new VueSocketIO({
    debug: true,
    connection: socketInstance,
    
    vuex: {
        store,
        actionPrefix: 'SOCKET_',
        mutationPrefix: 'SOCKET_'
    },
     //Optional options
}))
console.log("imports done...");


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

const vue = new Vue({
  router,
  store,
  i18n,
  vuetify,
  VueToast,
  sockets:{
    connect() {
      console.log("socket connected...")
    },
    disconnected() {
      console.log("socket disconnected...")
    }
  },
  render: h => h(App)
});
vue.$mount("#app");



