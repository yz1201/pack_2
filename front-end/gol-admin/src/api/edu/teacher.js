import request from '@/utils/request'

export default {
    //获取讲师信息
    getTeacherList(currentPage, pageSize, teacherQuery) {
        return request({
            // url: '/edu/teacher/pageTeacherByCondition/' + currentPage + '/' + pageSize,
            url: `/edu/teacher/pageTeacherByCondition/${currentPage}/${pageSize}`,
            method: 'post',
            data: teacherQuery,
        })
    },
    //根据主键删除
    deleteTeacherById(id){
        return request({
            url: `/edu/teacher/delete/${id}`,
            method: 'post',
        })
    },
    //添加讲师
    addTeacher(teacher){
        return request({
            url: '/edu/teacher/add',
            method: 'post',
            data: teacher,
        })
    },
    //根据id修改相应讲师信息
    updateTeacher(teacher){
        return request({
            url: '/edu/teacher/modifyInfo',
            method: 'post',
            data: teacher,
        })
    },
    //获取某讲师信息，--删除
    getTeacherInfo(id){
        return request({
            url: `/edu/teacher/findOne/${id}`,
            method: 'get',
        })
    }

}
