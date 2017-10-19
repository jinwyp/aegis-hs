/**
 * Created by jin on 9/28/17.
 */


/**
 * Created by jin on 9/26/17.
 */


/**
 * Created by jin on 9/18/17.
 */

const env = process.env.NODE_ENV || 'test'


//Require the dev-dependencies
const expect = require('chai').expect
const should = require('chai').should()
const supertest = require('supertest')

const config = require('../testConfig')
const server = supertest(config.path.urlApi)




describe('应收订单 付款:', function () {

    let Authorization = ''

    let orderId = config.order.getOrderYingId
    let delOrderId = config.order.delOrderYingId

    let paymentId = 1
    let delPaymentId = 3


    before(function (done) {

        server.post('/api/login')
            .set(config.headers)
            .send(config.user.admin)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                Authorization = res.body.data
                done()
            })

    })




    it(`付款单 - 新建付款单1 POST: /api/business/ying/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "payDate" : "2017-10-12 00:00:00",
                    "receiveCompanyId" : 2,
                    "payUsage" : "TRADE_DEFICIT",
                    "payAmount" : "122",
                    "capitalId" : 2,
                    "useInterest" : "",
                    "useDays" : "",
                    "orderId" : orderId,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : 1,
                        "jiekuanDate" : "2299-12-30 00:00:00",
                        "amount" : "99999999",
                        "capitalId" : 1,
                        "useInterest" : "",
                        "useDays" : ""
                    }
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.payDate).to.include('2017')

                paymentId = res.body.data.id
                done()
            })
    })

    it(`付款单 - 新建付款单2 POST: /api/business/ying/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "payDate" : "2017-09-12 00:00:00",
                    "receiveCompanyId" : 2,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payAmount" : "122",
                    "capitalId" : 1,
                    "orderId" : orderId,
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.payDate).to.include('2017')
                done()
            })
    })

    it(`付款单 - 新建付款单3 POST: /api/business/ying/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "payDate" : "2017-09-12 00:00:00",
                    "receiveCompanyId" : 2,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payAmount" : "122",
                    "capitalId" : 1,
                    "orderId" : orderId,
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.payDate).to.include('2017')

                delPaymentId = res.body.data.id
                done()
            })
    })

    it(`付款单 - 新建付款单 非法输入 缺少借款金额和借款日期 POST: /api/business/ying/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "payDate" : "2017-09-01 00:00:00",
                    "receiveCompanyId" : 1,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payAmount" : "2200",
                    "payMode" : "ELEC_REMITTANCE",
                    "capitalId" : 1,
                    "useInterest" : "",
                    "useDays" : "",
                    "orderId" : 1
                }
            )
            .expect('Content-Type', /json/)
            .expect(400)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是false 但实际不是false').to.equal(false)
                expect(res.body.data, '返回的数据data对象应该是undefined 但实际不是undefined').to.equal(undefined)
                done()
            })
    })


    it(`付款单 - 获取应收订单付款单列表 GET: /api/business/ying/${orderId}/fukuans?pageNo=1&pageSize=2`, function (done) {
        server.get(`/api/business/ying/${orderId}/fukuans?pageNo=1&pageSize=2`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.pageNo).to.equal(1)
                expect(res.body.data.pageSize).to.equal(2)
                expect(res.body.data.results.length, 'data.results 的返回记录数量错误').to.equal(2)
                done()
            })
    })

    it(`付款单 - 获取某个ID的付款单信息 GET: /api/business/ying/${orderId}/fukuans/${paymentId}`, function (done) {
        server.get(`/api/business/ying/${orderId}/fukuans/${paymentId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.payDate).to.include('2017')
                done()
            })
    })

    it(`付款单 - 修改某个ID的付款单 PUT: /api/business/ying/${orderId}/fukuans/${paymentId}`, function (done) {
        server.put(`/api/business/ying/${orderId}/fukuans/${paymentId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "id" : paymentId,
                    "hsId" : 2,
                    "receiveCompanyId" : 3,
                    "payUsage" : "DEPOSITECASH",
                    "payMode" : "CASH",
                    "capitalId" : 5,
                    "useInterest" : "0.4",
                    "useDays" : "60",
                    "orderId" : orderId
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data值应该是1 但实际不是1').to.equal(1)
                done()
            })
    })

    it(`付款单 - 删除某个ID的付款单 DELETE: /api/business/ying/${orderId}/fukuans/${delPaymentId}`, function (done) {
        server.delete(`/api/business/ying/${orderId}/fukuans/${delPaymentId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({})
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data值应该是1 但实际不是1').to.equal(1)
                done()
            })
    })


})