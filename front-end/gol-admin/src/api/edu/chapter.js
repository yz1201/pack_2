import request from '@/utils/request'

export default {

    //获取课程对应的章节跟小节信息
    getAllChapterVideo(courseId) {
        return request({
            url: `/edu/chapter/findAllChapterVideos/${courseId}`,
            method:'get'
        })
    }

}
