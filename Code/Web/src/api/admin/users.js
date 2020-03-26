import request from '@/utils/request'
// import axios from 'axios'
export function getPartyAs(data) {
  return request({
    url: 'admin/users/partyas',
    method: 'post',
    data: data
  })
}
export function getPartyBs(data) {
    return request({
      url: 'admin/users/partybs',
      method: 'post',
      data: data
    })
  }

  export function getPartyAAccs(data) {
    return request({
      url: 'admin/users/party-a/get-accs',
      method: 'post',
      data: data
    })
  }

