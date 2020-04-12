import request from '@/utils/request'
// import axios from 'axios'
export function login(data) {
  return request({
    url: 'users/login',
    method: 'post',
    data: data
  })
}
export function logout(data) {
  return request({
    url: 'users/logout',
    method: 'post',
    data: data
  })
}
export function signupPartyA(data) {
  return request({
    url: 'users/signup/party-a',
    method: 'post',
    data: data
  })
}

export function signupPartyB(data) {
  return request({
    url: 'users/signup/party-B',
    method: 'post',
    data: data
  })
}
export function changePassword(data) {
  return request({
    url: 'users/change-password',
    method: 'post',
    data: data
  })
}
export function createPAAccount(data) {
  return request({
    url: 'users/create-pa-account',
    method: 'post',
    data: data
  })
}

export function getMyInfo(data) {
  return request({
    url: 'users/my-info',
    method: 'post',
    data: data
  })
}

export function updateAcc(data) {
  return request({
    url: 'users/update-my-info',
    method: 'post',
    data: data
  })
}



