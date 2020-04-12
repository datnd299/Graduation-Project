import request from '@/utils/request'
// import axios from 'axios'
export function rentPlace(data) {
  return request({
    url: 'party-a/places/rent-place',
    method: 'post',
    data: data,
  })
}
export function getPlaces(data) {
  return request({
    url: 'party-a/places/get-places',
    method: 'post',
    data: data,
  })
}
