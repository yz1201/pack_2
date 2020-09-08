import request from '@/utils/request'

export default {
    //生成统计数据
    createStatistics(date) {
        return request({
            url: '/statistics/registerCounter/' + date,
            method: 'post',
        })
    },

    //获取统计数据
    getStaData(searchObj) {
        return request({
            url: '/getStaData',
            method: 'get',
            data: searchObj
        })
    },

}
