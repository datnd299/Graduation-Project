import request from '@/utils/request'
// import axios from 'axios'
export function upload(data,uploadProgress) {
  return request({
    url: '/file/upload',
    method: 'post',
    data: data,
    headers:{
        'Content-Type': 'multipart/form-data'
    },
    onUploadProgress: uploadProgress
  })
}