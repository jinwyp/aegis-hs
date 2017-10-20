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




describe('应收订单 保证金:', function () {

    let Authorization = ''

    let orderId = config.order.getOrderYingId

    let depositId = 1
    let delDepositId = 3


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




    it(`保证金 - 新建保证金1 POST: /api/business/ying/${orderId}/bails`, function (done) {
        server.post(`/api/business/ying/${orderId}/bails`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "bailDate" : "2017-10-05 00:00:00",
                    "bailType" : 1,
                    "bailAmount" : "23000",
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
                expect(res.body.data.bailDate , '返回的数据 res.body.data 里面日期字段错误').to.include('2017')

                depositId = res.body.data.id
                done()
            })
    })

    it(`保证金 - 新建保证金2 POST: /api/business/ying/${orderId}/bails`, function (done) {
        server.post(`/api/business/ying/${orderId}/bails`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "bailDate" : "2017-11-05 00:00:00",
                    "bailType" : 1,
                    "bailAmount" : "25000",
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
                expect(res.body.data.bailDate , '返回的数据 res.body.data 里面日期字段错误').to.include('2017')
                done()
            })
    })

    it(`保证金 - 新建保证金3 POST: /api/business/ying/${orderId}/bails`, function (done) {
        server.post(`/api/business/ying/${orderId}/bails`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "bailDate" : "2017-12-05 00:00:00",
                    "bailType" : 1,
                    "bailAmount" : "28000",
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
                expect(res.body.data.bailDate , '返回的数据 res.body.data 里面日期字段错误').to.include('2017')

                delDepositId = res.body.data.id
                done()
            })
    })

    it(`保证金 - 获取应收订单保证金列表 GET: /api/business/ying/${orderId}/bails?pageNo=1&pageSize=2`, function (done) {
        server.get(`/api/business/ying/${orderId}/bails?pageNo=1&pageSize=2`)
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

    it(`保证金 - 获取某个ID的保证金信息 GET: /api/business/ying/${orderId}/bails/${depositId}`, function (done) {
        server.get(`/api/business/ying/${orderId}/bails/${depositId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.bailDate , '返回的数据 res.body.data 里面日期字段错误').to.include('2017')
                done()
            })
    })

    it(`保证金 - 修改某个ID的保证金 PUT: /api/business/ying/${orderId}/bails/${depositId}`, function (done) {
        server.put(`/api/business/ying/${orderId}/bails/${depositId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "id" : depositId,
                    "hsId" : 1,
                    "bailDate" : "2017-10-05 00:00:00",
                    "bailType" : 1,
                    "bailAmount" : "28000",
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

    it(`保证金 - 删除某个ID的保证金 DELETE: /api/business/ying/${orderId}/bails/${delDepositId}`, function (done) {
        server.delete(`/api/business/ying/${orderId}/bails/${delDepositId}`)
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

    it(`保证金 - 不能重复删除某个ID的保证金 DELETE: /api/business/ying/${orderId}/bails/${delDepositId}`, function (done) {
        console.log(`提示信息: 不能重复删除某个ID的应收订单 DELETE: /api/business/ying/${orderId}/bails/${delDepositId}`)
        server.delete(`/api/business/ying/${orderId}/bails/${delDepositId}`)
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

    it(`获取 已删除的ID的保证金信息 应该返回400  GET: /api/business/ying/${orderId}/bails/${delDepositId}`, function (done) {
        server.get(`/api/business/ying/${orderId}/bails/${delDepositId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
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