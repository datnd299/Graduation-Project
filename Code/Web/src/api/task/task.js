import request from '@/utils/request'
// import axios from 'axios'
export function createNew(data) {
  return request({
    url: 'tasks/create-new',
    method: 'post',
    data: data
  })
}

export function getAllOfMyPT(data) {
  return request({
    url: 'tasks/get-all-of-my-pt',
    method: 'post',
    data: data
  })
}
export function getById(data) {
  return request({
    url: 'tasks/get-by-id',
    method: 'post',
    data: data
  })
}
export function approveTask(data) {
  return request({
    url: 'tasks/approve',
    method: 'post',
    data: data
  })
}

export function getTaskByPlace(data) {
  return request({
    url: 'tasks/get-by-place',
    method: 'post',
    data: data
  })
}
