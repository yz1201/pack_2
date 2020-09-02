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

    getListTeacher() {
        return request({
            url: '/edu/course/findAllTeachers',
            method: 'get',
        })
    },

    //修改课程信息
    // addCourseInfo(courseInfo) {
    //     return request({
    //         url: '/edu/subject/findAll',
    //         method: 'post',
    //         data: courseInfo
    //     })
    // },

    // publishCourse() {

    // }

   
}
