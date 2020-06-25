import request from '@/utils/request'
// import axios from 'axios'
export function createNew(data) {
  return request({
    url: 'party-a/signboards/create-new',
    method: 'post',
    data: data,
  })
}

export function getSignboards(data) {
    return request({
      url: 'party-a/signboards/get',
      method: 'post',
      data: data,
    })
  }
  export function getSignboardByID(data) {
    return request({
      url: 'party-a/signboards/get-by-id',
      method: 'post',
      data: data,
    })
  }
  export function allSignboardsReport(data) {
    return request({
      url: 'party-a/signboards/report/all',
      method: 'post',
      data: data,
    })
  }