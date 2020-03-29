import request from '@/utils/request'
// import axios from 'axios'
export function createPlace(data) {
  return request({
    url: 'party-b/place-for-rent/create-new-place',
    method: 'post',
    data: data,
  })
}
export function getMine(data) {
    return request({
      url: 'party-b/place-for-rent/get-mine',
      method: 'post',
      data: data,
    })
  }
  export function getByParty(data) {
    return request({
      url: 'party-b/place-for-rent/get-by-party',
      method: 'post',
      data: data,
    })
  }

 