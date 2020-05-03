import request from '@/utils/request'

export function fetchList(params) {
  return request({
    url: '/user/users',
    method: 'POST',
    data:params
  })
}

export function register(user) {
  return request({
    url: '/user/register',
    method: 'post',
    data: user
  })
}

export function login(user) {
  return request({
    url: '/user/login',
    method: 'post',
    data: user
  })
}

export function getInfo() {
  return request({
    url: '/user/info',
    method: 'get',
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

export function req(fn, data) {
  request.defaults.baseURL = "http://localhost:6060/v1"
  return fn(data)
}
