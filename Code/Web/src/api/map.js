

import request from '@/utils/request'
// import axios from 'axios'
export function getPlaceByLatLng(data) {
  return request({
    url: 'https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&sensor=true&key=AIzaSyDwn1LVzfBeHNGrXB8g5DbG6AvIb_3Xap8',
    method: 'get',
    data: data
  })
}