import request from '@/utils/request'

export default {

    //获取课程对应的章节跟小节信息
    getAllChapterVideo(courseId) {
        return request({
            url: `/edu/chapter/findAllChapterVideos/${courseId}`,
            method: 'get'
        })
    },

    //添加课程章节
    addChapter(chapter) {
        return request({
            url: '/edu/chapter/addChapter',
            method: 'post',
            data: chapter,
        })
    },

    //修改课程章节
    updateChapter(chapter) {
        return request({
            url: '/edu/chapter/updateChapterById',
            method: 'post',
            data: chapter,
        })
    },

    //删除课程章节
    deleteChapter(chapterId) {
        return request({
            url: `/edu/chapter/${chapterId}`,
            method: 'delete',
        })
    },

    //获取课程章节
    getChapter(chapterId){
        return request({
            url: `/edu/chapter/getChapterInfo/${chapterId}`,
            method: 'get'
        })
    }


}
