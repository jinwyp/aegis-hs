
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




describe('应收订单 费用单:', function () {

    let Authorization = ''

    let orderId = config.order.getOrderYingId

    let expenseId = 1
    let delExpenseId = 2


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




    it(`费用单 - 新建费用单1 POST: /api/business/ying/${orderId}/fees`, function (done) {
        server.post(`/api/business/ying/${orderId}/fees`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 2,
                    "name" : "HELP_RECIVE_PAY_FEE",
                    "settleDate" : "2017-07-10 00:00:00",
                    "amount" : "2000",
                    "quantity" : "1000",
                    "otherPartyId" : "2",
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
                expect(res.body.data.name).to.include('HELP_RECIVE_PAY_FEE')

                expenseId = res.body.data.id
                done()
            })
    })

    it(`费用单 - 新建费用单2 POST: /api/business/ying/${orderId}/fees`, function (done) {
        server.post(`/api/business/ying/${orderId}/fees`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 2,
                    "name" : "TAX_MOTRO_FREIGHT",
                    "settleDate" : "2017-07-10 00:00:00",
                    "amount" : "10000",
                    "quantity" : "1000",
                    "otherPartyId" : "2",
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
                expect(res.body.data.name).to.include('TAX_MOTRO_FREIGHT')

                delExpenseId = res.body.data.id
                done()
            })
    })

    it(`费用单 - 获取应收订单费用单列表 GET: /api/business/ying/${orderId}/fees?pageNo=1&pageSize=2`, function (done) {
        server.get(`/api/business/ying/${orderId}/fees?pageNo=1&pageSize=2`)
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

    it(`费用单 - 获取某个ID的费用单信息 GET: /api/business/ying/${orderId}/fees/${expenseId}`, function (done) {
        server.get(`/api/business/ying/${orderId}/fees/${expenseId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('HELP_RECIVE_PAY_FEE')
                done()
            })
    })

    it(`费用单 - 修改某个ID的费用单 PUT: /api/business/ying/${orderId}/fees/${expenseId}`, function (done) {
        server.put(`/api/business/ying/${orderId}/fees/${expenseId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "name" : "HELP_RECIVE_PAY_FEE",
                    "settleDate" : "2017-07-10 00:00:00",
                    "amount" : 20003333,
                    "quantity" : "5000",
                    "otherPartyId" : "3",
                    "orderId" : orderId,
                    "id" : expenseId
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

    it(`费用单 - 删除某个ID的费用单 DELETE: /api/business/ying/${orderId}/fees/${delExpenseId}`, function (done) {
        server.delete(`/api/business/ying/${orderId}/fees/${delExpenseId}`)
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