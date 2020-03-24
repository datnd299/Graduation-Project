import axios from 'axios'

import VueToast from 'vue-toast-notification';
import Vue from 'vue';
Vue.use(VueToast);

import store from '@/store'
var getToken = function(){
    return 'a'
}

// create an axios instance
const service = axios.create({
  // baseURL: 'http://localhost:8086/api/v1/', // url = base url + request url
  baseURL: 'https://signboard-mn.herokuapp.com/api/v1/', // url = base url + request url
  withCredentials: false, // send cookies when cross-domain requests
  timeout: 50000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent

    if (store.getters.token) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      config.headers['X-Token'] = getToken()
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(

  response => {
    const res = response.data
    
    
    // if the custom code is not 20000, it is judged as an error.
    if (res.status !='success') {

     Vue.$toast.open({
        message: res.message || 'error',
        type: 'error',
        position:'top-right',
        duration:'3000'
        // all other options
    });
      
      
  
    

      // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
      if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
        // to re-login
        
      }
      return Promise.reject(res.message || 'error')
    } else {
      return res
    }
  },
  error => {
    console.log('err' + error) // for debug
    Vue.$toast.open({
      message: error.message || 'error',
      type: 'error',
      position:'top-right',
      duration:'3000'
      // all other options
  });
    return Promise.reject(error)
  }
)

export default service
