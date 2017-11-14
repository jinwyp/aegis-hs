
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




describe('应收订单 借款单:', function () {

    let Authorization = ''

    let orderId = config.order.getOrderYingId

    let borrowId = 1
    let delBorrowId = 3


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




    it(`借款单 - 新建借款单1 POST: /api/business/ying/${orderId}/jiekuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/jiekuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "jiekuanDate" : "2017-09-01 00:00:00",
                    "amount" : "2200",
                    "capitalId" : 17,
                    "useInterest" : "0.1",
                    "useDays" : "30",
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
                expect(res.body.data.jiekuanDate).to.include('2017')

                borrowId = res.body.data.id
                done()
            })
    })

    it(`借款单 - 新建借款单2 POST: /api/business/ying/${orderId}/jiekuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/jiekuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "jiekuanDate" : "2017-10-01 00:00:00",
                    "amount" : "5600",
                    "capitalId" : 17,
                    "useInterest" : "0.1",
                    "useDays" : "30",
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
                expect(res.body.data.jiekuanDate).to.include('2017')
                done()
            })
    })

    it(`借款单 - 新建借款单3 POST: /api/business/ying/${orderId}/jiekuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/jiekuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "jiekuanDate" : "2017-10-01 00:00:00",
                    "amount" : "7800",
                    "capitalId" : 17,
                    "useInterest" : "0.1",
                    "useDays" : "30",
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
                expect(res.body.data.jiekuanDate).to.include('2017')

                delBorrowId = res.body.data.id
                done()
            })
    })

    it(`借款单 - 获取应收订单借款单列表 GET: /api/business/ying/${orderId}/jiekuans?pageNo=1&pageSize=2`, function (done) {
        server.get(`/api/business/ying/${orderId}/jiekuans?pageNo=1&pageSize=2`)
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

    it(`借款单 - 获取某个ID的借款单信息 GET: /api/business/ying/${orderId}/jiekuans/${borrowId}`, function (done) {
        server.get(`/api/business/ying/${orderId}/jiekuans/${borrowId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.jiekuanDate).to.include('2017')
                done()
            })
    })

    it(`借款单 - 修改某个ID的借款单 PUT: /api/business/ying/${orderId}/jiekuans/${borrowId}`, function (done) {
        server.put(`/api/business/ying/${orderId}/jiekuans/${borrowId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "id" : 1,
                    "hsId" : 1,
                    "capitalId" : 17,
                    "amount" : "6000",
                    "useInterest" : "0.4",
                    "jiekuanDate" : "2017-10-01 00:00:00",
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

    it(`借款单 - 删除某个ID的借款单 DELETE: /api/business/ying/${orderId}/jiekuans/${delBorrowId}`, function (done) {
        server.delete(`/api/business/ying/${orderId}/jiekuans/${delBorrowId}`)
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


    it(`借款单 - 获取应收订单尚未匹配还款的借款单列表 GET: /api/business/ying/${orderId}/jiekuansUnfinished`, function (done) {
        server.get(`/api/business/ying/${orderId}/jiekuansUnfinished`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data, 'data 的返回记录数量错误').to.have.lengthOf(2)
                done()
            })
    })

})