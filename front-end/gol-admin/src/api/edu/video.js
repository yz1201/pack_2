import request from '@/utils/request'

export default {
    addVideo(video) {
        return request({
            url: '/edu/video/addVideo',
            method: 'post',
            data: video,
        })
    },

    deleteVideo(videoId){
        return request({
            url:`/edu/video/delete/${videoId}`,
            method:'delete'
        })
    },

    updateVideo(video){
        return request({
            url: '/edu/video/updateVideoById',
            method: 'post',
            data: video,
        })
    },

    getVideo(id){
        return request({
            url:`/edu/video/getVideoById/${id}`,
            method:'get'
        })
    },

    deleteAliVod(videoSourceId){
        return request({
            url:`/vod/video/delete/${videoSourceId}`,
            method:'delete'
        })
    },
}