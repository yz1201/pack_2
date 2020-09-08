import request from '@/utils/request'

export default {
  //生成订单
  createOrders(courseId) {
    return request({
      url: 'order/generateOrder/' + courseId,
      method: 'post'
    })
  },

  //根据订单no查询订单信息
  getOrdersInfo(orderNo) {
    return request({
      url: 'order/getOrderInfo/' + orderNo,
      method: 'get'
    })
  },

  //生成二维码的方法
  createQRcode(orderNo) {
    return request({
      url: '/payLog/createQrCode/' + orderNo,
      method: 'get'
    })
  },

  //查询订单状态的方法
  queryPayStatus(orderNo) {
    return request({
      url: '/payLog/queryPayStatus/' + orderNo,
      method: 'get'
    })
  },

  //获取用户所有订单
  getOrderList(){
    return request({
      url: '/order/orderList',
      method: 'get'
    })
  },

  //根据订单id删除订单
  removeOrderById(orderId){
    return request({
      url: `/order/removeOrder/${orderId}`,
      method: 'delete'
    })
  },

  //查询订单表中订单的状态 判断课程是否购买
  isBought(courseId) {
    return request({
      url: `/order/isBought/${courseId}`,
      method: 'get'
    })
  },
}
