
import request from '@/utils/request'
export default {
  //条件分页课程查询的方法
  getCourseList(page, limit, courseObj) {
    return request({
      url: `/edu/front/course/getFrontCourseList/${page}/${limit}`,
      method: 'post',
      data: courseObj
    })
  },
  
  //查询所有分类的方法
  getAllSubject() {
    return request({
      url: '/edu/subject/findAll',
      method: 'get'
    })
  },
  
  //课程详情的方法
  getCourseInfo(courseId) {
    return request({
      url: `/edu/front/course/getFrontCourseInfo/${courseId}`,
      method: 'get'
    })
  }
}
