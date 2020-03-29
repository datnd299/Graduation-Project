import request from '@/utils/request'
// import axios from 'axios'
export function getAccs(data) {
  return request({
    url: 'party-a/accounts',
    method: 'post',
    data: data,
  })
}
export function createNew(data) {
    return request({
      url: 'party-a/accounts/create-new',
      method: 'post',
      data: data,
    })
  }
