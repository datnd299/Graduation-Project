import request from '@/utils/request'
// import axios from 'axios'
export function login(data) {
  return request({
    url: 'users/login',
    method: 'post',
    data: data
  })
}
