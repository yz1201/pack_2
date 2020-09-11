import Cookies from 'js-cookie'
import request from './request'

// const TokenKey = 'Admin-Token'
const TokenKey = 'TOKEN'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

