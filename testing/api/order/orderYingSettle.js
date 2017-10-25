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




describe('应收订单 结算单:', function () {

    let Authorization = ''

    let orderId = config.order.getOrderYingId

    let settleId = 1
    let delSettleId = 2


    before(function (done) {

        server.post('/api/login')
            .set(config.headers)
            .send(config.user.user1)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                Authorization = res.body.data
                done()
            })

    })





    it(`下游结算单 - 新建下游结算单1 POST: /api/business/ying/${orderId}/settlebuyerdownstream`, function (done) {
        server.post(`/api/business/ying/${orderId}/settlebuyerdownstream`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "orderId" : orderId,
                    "settleDate" : "2017-09-02 00:00:00",
                    "amount" : "2000",
                    "money" : "100",
                    "settleGap" : "100"
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.settleDate).to.include('2017')
                done()
            })
    })

    it(`下游结算单 - 新建下游结算单2 POST: /api/business/ying/${orderId}/settlebuyerdownstream`, function (done) {
        server.post(`/api/business/ying/${orderId}/settlebuyerdownstream`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "orderId" : orderId,
                    "settleDate" : "2017-09-02 00:00:00",
                    "amount" : "40000",
                    "money" : "100",
                    "settleGap" : "200"
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.settleDate).to.include('2017')
                done()
            })
    })

    it(`下游结算单 - 获取应收订单下游结算单列表 GET: /api/business/ying/${orderId}/settlebuyerdownstream?pageNo=1&pageSize=2`, function (done) {
        server.get(`/api/business/ying/${orderId}/settlebuyerdownstream?pageNo=1&pageSize=2`)
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
                expect(res.body.data.results, 'data.results 的返回记录数量错误').to.have.lengthOf(2)
                done()
            })
    })

    it(`下游结算单 - 获取某个ID的下游结算单信息 GET: /api/business/ying/${orderId}/settlebuyerdownstream/1`, function (done) {
        server.get(`/api/business/ying/${orderId}/settlebuyerdownstream/1`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.settleDate).to.include('2017')
                done()
            })
    })

    it(`下游结算单 - 修改某个ID的下游结算单 PUT: /api/business/ying/${orderId}/settlebuyerdownstream/1`, function (done) {
        server.put(`/api/business/ying/${orderId}/settlebuyerdownstream/1`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "settleDate" : "2017-09-23 00:00:00",
                    "money" : "5000",
                    "settleGap" : "330",
                    "orderId" : orderId,
                    "id" : 1
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

    it(`下游结算单 - 删除某个ID的下游结算单 DELETE: /api/business/ying/${orderId}/settlebuyerdownstream/2`, function (done) {
        server.delete(`/api/business/ying/${orderId}/settlebuyerdownstream/2`)
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






    it(`上游结算单 - 新建上游结算单1 POST: /api/business/ying/${orderId}/settlesellerupstream`, function (done) {
        server.post(`/api/business/ying/${orderId}/settlesellerupstream`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "settleDate" : "2017-09-02 00:00:00",
                    "amount" : "2000",
                    "money" : "1000",
                    "discountType" : "RATE_DISCOUNT",
                    "discountInterest" : "0.2",
                    "discountDays" : "30",
                    "discountAmount" : "1000",
                    "orderId" : orderId
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.settleDate).to.include('2017')
                done()
            })
    })

    it(`上游结算单 - 新建上游结算单2 POST: /api/business/ying/${orderId}/settlesellerupstream`, function (done) {
        server.post(`/api/business/ying/${orderId}/settlesellerupstream`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "tempSettleType" : "upstream",
                    "hsId" : 2,
                    "settleDate" : "2017-10-30 00:00:00",
                    "discountType" : "CASH_DISCOUNT",
                    "discountInterest" : "0.3",
                    "discountDays" : "60",
                    "discountAmount" : "20",
                    "orderId" : orderId
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.settleDate).to.include('2017')
                done()
            })
    })

    it(`上游结算单 - 新建上游结算单3 POST: /api/business/ying/${orderId}/settlesellerupstream`, function (done) {
        server.post(`/api/business/ying/${orderId}/settlesellerupstream`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "tempSettleType" : "upstream",
                    "hsId" : 3,
                    "settleDate" : "2017-10-30 00:00:00",
                    "discountType" : "CASH_DISCOUNT",
                    "discountInterest" : "0.3",
                    "discountDays" : "70",
                    "discountAmount" : "10",
                    "orderId" : orderId
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.settleDate).to.include('2017')
                done()
            })
    })


    it(`上游结算单 - 获取应收订单上游结算单列表 GET: /api/business/ying/${orderId}/settlesellerupstream?pageNo=1&pageSize=2`, function (done) {
        server.get(`/api/business/ying/${orderId}/settlesellerupstream?pageNo=1&pageSize=2`)
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
                expect(res.body.data.results, 'data.results 的返回记录数量错误').to.have.lengthOf(2)
                done()
            })
    })

    it(`上游结算单 - 获取某个ID的上游结算单信息 GET: /api/business/ying/${orderId}/settlesellerupstream/1`, function (done) {
        server.get(`/api/business/ying/${orderId}/settlesellerupstream/1`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.settleDate).to.include('2017')
                done()
            })
    })

    it(`上游结算单 - 修改某个ID的上游结算单 PUT: /api/business/ying/${orderId}/settlesellerupstream/1`, function (done) {
        server.put(`/api/business/ying/${orderId}/settlesellerupstream/1`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "settleDate" : "2017-09-02 00:00:00",
                    "amount" : 2000,
                    "money" : 1000,
                    "discountType" : "RATE_DISCOUNT",
                    "discountInterest" : 0.2,
                    "discountDays" : 30,
                    "discountAmount" : "5000",
                    "orderId" : orderId,
                    "id" : 1
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

    it(`上游结算单 - 删除某个ID的上游结算单 DELETE: /api/business/ying/${orderId}/settlesellerupstream/2`, function (done) {
        server.delete(`/api/business/ying/${orderId}/settlesellerupstream/2`)
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





    it(`运输方结算单 - 新建运输方结算单1 POST: /api/business/ying/${orderId}/settletraffic`, function (done) {
        server.post(`/api/business/ying/${orderId}/settletraffic`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "tempSettleType" : "traffic",
                    "hsId" : 1,
                    "settleDate" : "2017-09-01 00:00:00",
                    "amount" : "3000",
                    "money" : "200",
                    "discountType" : -1,
                    "discountInterest" : "",
                    "discountDays" : "",
                    "discountAmount" : "",
                    "settleGap" : 99999999,
                    "trafficCompanyId" : 1,
                    "orderId" : orderId
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.settleDate).to.include('2017')

                settleId = res.body.data.id
                done()
            })
    })

    it(`运输方结算单 - 新建运输方结算单2 POST: /api/business/ying/${orderId}/settletraffic`, function (done) {
        server.post(`/api/business/ying/${orderId}/settletraffic`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "tempSettleType" : "traffic",
                    "hsId" : 2,
                    "settleDate" : "2017-09-23 00:00:00",
                    "amount" : "2000",
                    "money" : "100",
                    "discountType" : -1,
                    "discountInterest" : "",
                    "discountDays" : "",
                    "discountAmount" : "",
                    "settleGap" : 99999999,
                    "trafficCompanyId" : 5,
                    "orderId" : orderId
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.settleDate).to.include('2017')

                delSettleId = res.body.data.id
                done()
            })
    })

    it(`运输方结算单 - 获取应收订单运输方结算单列表 GET: /api/business/ying/${orderId}/settletraffic?pageNo=1&pageSize=2`, function (done) {
        server.get(`/api/business/ying/${orderId}/settletraffic?pageNo=1&pageSize=2`)
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
                expect(res.body.data.results, 'data.results 的返回记录数量错误').to.have.lengthOf(2)
                done()
            })
    })

    it(`运输方结算单 - 获取某个ID的运输方结算单信息 GET: /api/business/ying/${orderId}/settletraffic/${settleId}`, function (done) {
        server.get(`/api/business/ying/${orderId}/settletraffic/${settleId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.settleDate).to.include('2017')
                done()
            })
    })

    it(`运输方结算单 - 修改某个ID的运输方结算单 PUT: /api/business/ying/${orderId}/settletraffic/${settleId}`, function (done) {
        server.put(`/api/business/ying/${orderId}/settletraffic/${settleId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "tempSettleType" : "traffic",
                    "hsId" : 2,
                    "settleDate" : "2017-09-30 00:00:00",
                    "amount" : "6000",
                    "money" : "200",
                    "discountType" : -1,
                    "discountInterest" : "",
                    "discountDays" : "",
                    "discountAmount" : "",
                    "settleGap" : 99999999,
                    "trafficCompanyId" : 9,
                    "orderId" : orderId,
                    "id" : settleId
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

    it(`运输方结算单 - 删除某个ID的运输方结算单 DELETE: /api/business/ying/${orderId}/settletraffic/${delSettleId}`, function (done) {
        console.log(`删除某个ID的运输方结算单 DELETE: /api/business/ying/${orderId}/settletraffic/${delSettleId}`)
        server.delete(`/api/business/ying/${orderId}/settletraffic/${delSettleId}`)
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

    it(`运输方结算单 - 不能重复删除某个ID的运输方结算单 应该返回400 DELETE: /api/business/ying/${orderId}/settletraffic/${delSettleId}`, function (done) {
        console.log(`不能重复删除某个ID的运输方结算单 应该返回400 DELETE: /api/business/ying/${orderId}/settletraffic/${delSettleId}`)
        server.delete(`/api/business/ying/${orderId}/settletraffic/${delSettleId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({})
            .expect('Content-Type', /json/)
            .expect(400)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是false 但实际不是false').to.equal(false)
                expect(res.body.data, '返回的数据data对象应该是undefined 但实际不是undefined').to.equal(undefined)
                done()
            })
    })
})