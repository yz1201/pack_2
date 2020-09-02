import request from '@/utils/request'

export default {

    //获取课程分类信息
    getSubjectList() {
        return request({
            url: '/edu/subject/findAll',
            method: 'get',
        })
    },

    // getSubjectList(){
    //     return request({
    //         url: '/edu/subject/findAllMainSubject',
    //         method: 'get',
    //     })
    // }

}
