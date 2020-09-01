import request from '@/utils/request'

export default {
    getTeacherList(currentPage, pageSize, teacherQuery) {
        return request({
            // url: '/edu/teacher/pageTeacherByCondition/' + currentPage + '/' + pageSize,
            url: `/edu/teacher/pageTeacherByCondition/${currentPage}/${pageSize}`,
            method: 'post',
            data: teacherQuery,
        })
    }


}
