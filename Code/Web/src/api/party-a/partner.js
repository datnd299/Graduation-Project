import request from '@/utils/request'
// import axios from 'axios'
export function getPartner(data) {
  return request({
    url: 'party-a/partners/get',
    method: 'post',
    data: data,
  })
}