import request from '@/utils/request'
// import axios from 'axios'
export function registerNToken(data) {
  return request({
    url: 'ntf/register-n-token',
    method: 'post',
    data: data
  })
}

export function getMine(data) {
  return request({
    url: 'ntf',
    method: 'post',
    data: data
  })
}