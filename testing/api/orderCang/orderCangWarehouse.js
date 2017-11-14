
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




describe('仓押订单 入库/出库:', function () {

    let Authorization = ''
    let orderId = config.order.getOrderCangId
    let rukuId = 1
    let delRukuId = 3
    let chukuId = 1
    let delChukuId = 3

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




    it(`入库单 - 新建入库单1 POST: /api/business/cang/${orderId}/rukus`, function (done) {
        server.post(`/api/business/cang/${orderId}/rukus`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 3,
                    "locality" : "库房场地",
                    "rukuDate" : "2017-10-05 00:00:00",
                    "rukuStatus" : "IN_STORAGE",
                    "rukuAmount" : "20000",
                    "rukuPrice" : "30",
                    "trafficMode" : "MOTOR",
                    "cars" : "10",
                    "jhh" : "",
                    "ship" : "",
                    "chukuDate" : "2099-12-30 00:00:00",
                    "chukuAmount" : 99999999,
                    "chukuPrice" : 99999999,
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
                expect(res.body.data.rukuStatus).to.include('IN_STORAGE')

                rukuId = res.body.data.id
                done()
            })
    })

    it(`入库单 - 新建入库单2 POST: /api/business/cang/${orderId}/rukus`, function (done) {
        server.post(`/api/business/cang/${orderId}/rukus`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 4,
                    "locality" : "库房地点",
                    "rukuDate" : "2017-12-08 00:00:00",
                    "rukuStatus" : "IN_STORAGE",
                    "rukuAmount" : "5000",
                    "rukuPrice" : "20",
                    "trafficMode" : "SHIP",
                    "cars" : "",
                    "jhh" : "",
                    "ship" : "2xxxx",
                    "chukuDate" : "2099-12-30 00:00:00",
                    "chukuAmount" : 99999999,
                    "chukuPrice" : 99999999,
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
                expect(res.body.data.rukuStatus).to.include('IN_STORAGE')
                done()
            })
    })

    it(`入库单 - 新建入库单3 POST: /api/business/cang/${orderId}/rukus`, function (done) {
        server.post(`/api/business/cang/${orderId}/rukus`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 4,
                    "locality" : "库房地点",
                    "rukuDate" : "2017-12-08 00:00:00",
                    "rukuStatus" : "IN_STORAGE",
                    "rukuAmount" : "98000",
                    "rukuPrice" : "80",
                    "trafficMode" : "SHIP",
                    "cars" : "",
                    "jhh" : "",
                    "ship" : "2xxxx",
                    "chukuDate" : "2099-12-30 00:00:00",
                    "chukuAmount" : 99999999,
                    "chukuPrice" : 99999999,
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
                expect(res.body.data.rukuStatus).to.include('IN_STORAGE')

                delRukuId = res.body.data.id
                done()
            })
    })

    it(`入库单 - 获取仓押订单入库单列表 GET: /api/business/cang/${orderId}/rukus?pageNo=1&pageSize=2`, function (done) {
        server.get(`/api/business/cang/${orderId}/rukus?pageNo=1&pageSize=2`)
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

    it(`入库单 - 获取某个ID的入库单信息 GET: /api/business/cang/${orderId}/rukus/${rukuId}`, function (done) {
        server.get(`/api/business/cang/${orderId}/rukus/${rukuId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.rukuStatus).to.include('IN_STORAGE')
                done()
            })
    })

    it(`入库单 - 修改某个ID的入库单 PUT: /api/business/cang/${orderId}/rukus/${rukuId}`, function (done) {
        server.put(`/api/business/cang/${orderId}/rukus/${rukuId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 3,
                    "locality" : "库房场地",
                    "rukuDate" : "2017-10-05 00:00:00",
                    "rukuStatus" : "IN_STORAGE",
                    "rukuAmount" : "50000",
                    "rukuPrice" : "30",
                    "trafficMode" : "MOTOR",
                    "cars" : "10",
                    "jhh" : "",
                    "ship" : "",
                    "chukuDate" : "2099-12-30 00:00:00",
                    "chukuAmount" : 99999999,
                    "chukuPrice" : 99999999,
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

    it(`入库单 - 删除某个ID的入库单 DELETE: /api/business/cang/${orderId}/rukus/${delRukuId}`, function (done) {
        server.delete(`/api/business/cang/${orderId}/rukus/${delRukuId}`)
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




    it(`出库单 - 新建出库单1 POST: /api/business/cang/${orderId}/chukus`, function (done) {
        server.post(`/api/business/cang/${orderId}/chukus`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 3,
                    "locality" : "库房场地",
                    "chukuDate" : "2017-10-01 00:00:00",
                    "chukuAmount" : "1000",
                    "chukuPrice" : "20",
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
                expect(res.body.data.chukuDate).to.include('2017')

                chukuId = res.body.data.id
                done()
            })
    })

    it(`出库单 - 新建出库单2 POST: /api/business/cang/${orderId}/chukus`, function (done) {
        server.post(`/api/business/cang/${orderId}/chukus`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 4,
                    "locality" : "库房场地",
                    "chukuDate" : "2017-10-01 00:00:00",
                    "chukuAmount" : "77000",
                    "chukuPrice" : "80",
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
                expect(res.body.data.chukuDate).to.include('2017')
                done()
            })
    })

    it(`出库单 - 新建出库单3 POST: /api/business/cang/${orderId}/chukus`, function (done) {
        server.post(`/api/business/cang/${orderId}/chukus`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 4,
                    "locality" : "库房场地",
                    "chukuDate" : "2017-10-01 00:00:00",
                    "chukuAmount" : "97000",
                    "chukuPrice" : "50",
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
                expect(res.body.data.chukuDate).to.include('2017')

                delChukuId = res.body.data.id
                done()
            })
    })

    it(`出库单 - 获取仓押订单出库单列表 GET: /api/business/cang/${orderId}/chukus?pageNo=1&pageSize=2`, function (done) {
        server.get(`/api/business/cang/${orderId}/chukus?pageNo=1&pageSize=2`)
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

    it(`出库单 - 获取某个ID的出库单信息 GET: /api/business/cang/${orderId}/chukus/${chukuId}`, function (done) {
        server.get(`/api/business/cang/${orderId}/chukus/${chukuId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.chukuDate).to.include('2017')
                done()
            })
    })

    it(`出库单 - 修改某个ID的出库单 PUT: /api/business/cang/${orderId}/chukus/${chukuId}`, function (done) {
        server.put(`/api/business/cang/${orderId}/chukus/${chukuId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 3,
                    "locality" : "库房场地",
                    "rukuDate" : "2017-10-05 00:00:00",
                    "rukuStatus" : "IN_STORAGE",
                    "rukuAmount" : "50000",
                    "rukuPrice" : "30",
                    "trafficMode" : "MOTOR",
                    "cars" : "10",
                    "jhh" : "",
                    "ship" : "",
                    "chukuDate" : "2099-12-30 00:00:00",
                    "chukuAmount" : 99999999,
                    "chukuPrice" : 99999999,
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

    it(`出库单 - 删除某个ID的出库单 DELETE: /api/business/cang/${orderId}/chukus/${delChukuId}`, function (done) {
        server.delete(`/api/business/cang/${orderId}/chukus/${delChukuId}`)
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