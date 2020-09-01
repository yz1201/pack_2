import request from '@/utils/request'

export function getTeachers(params) {
  return request({
    url: '/edu/table/list',
    method: 'get',
    params
  })
}
