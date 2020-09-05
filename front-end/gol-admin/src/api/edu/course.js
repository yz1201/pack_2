import request from '@/utils/request'

export default {

    //添加课程信息
    addCourseInfo(courseInfo) {
        return request({
            url: '/edu/course/addCourseInfo',
            method: 'post',
            data: courseInfo
        })
    },
    //获取所有讲师列表
    getListTeacher() {
        return request({
            url: '/edu/course/findAllTeachers',
            method: 'get',
        })
    },
    //根据课程id获取相应部分课程信息
    getCourseInfoById(courseId) {
        return request({
            url: `/edu/course/getCourseInfoById/${courseId}`,
            method: 'get',
        })
    },
    //修改课程信息
    updateCourseInfo(courseInfo) {
        return request({
            url: '/edu/course/updateCourseInfo',
            method: 'post',
            data: courseInfo,
        })
    },

    //查询要发布的课程信息
    getPublishCourseInfo(courseId) {
        return request({
            url: `/edu/course/getPublishedCourse/${courseId}`,
            method: 'get',
        })
    },
    //课程发布
    publishCourse(courseId) {
        return request({
            url: `/edu/course/publishCourse/${courseId}`,
            method: 'post',
        })
    },

    getCourseListPage(page, limit, courseQuery) {
        return request({
            url: `/edu/course/getCourseListPageByCondition/${page}/${limit}`,
            method: 'post',
            data: courseQuery,
        })
    },

    deleteCourseById(courseId){
        return request({
            url: `/edu/course/delete/${courseId}`,
            method: 'post',
        })
    },

}
