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
  export function approvePartyA(data) {
    return request({
      url: 'admin/users/party-a/approve-party',
      method: 'post',
      data: data
    })
  }
  export function approvePartyB(data) {
    return request({
      url: 'admin/users/party-b/approve-party',
      method: 'post',
      data: data
    })
  }
  export function approveAcc(data) {
    return request({
      url: 'admin/users/approve-acc',
      method: 'post',
      data: data
    })
  }

  
  
  
  

  
