import request from '@/utils/request'
// import axios from 'axios'
export function getMyRooms(data) {
  return request({
    url: 'chat/get-my-rooms',
    method: 'post',
    data: data,
  })
}
export function getMessages(data) {
    return request({
      url: 'chat/get-messages',
      method: 'post',
      data: data,
    })
  }