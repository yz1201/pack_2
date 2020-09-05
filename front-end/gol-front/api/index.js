import request from '@/utils/request'
export default {
  //广告位
  getTopBannerAdList() {
      return request({
        url: '/cms/front/banner/getAll',
        method: 'get'
      })
    },

  //查询8热门课程
  getHotCourse() {
    return request({
      url: `/edu/front/getHotCourses`,
      method: 'get'
    })
  },

  //查询4热门老师
  getHotTeacher() {
    return request({
      url: `/edu/front/getHotTeachers`,
      method: 'get'
    })
  },
}
