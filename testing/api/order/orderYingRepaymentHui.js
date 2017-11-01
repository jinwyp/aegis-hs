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




describe('应收订单 回款:', function () {

    let Authorization = ''

    let orderId = config.order.getOrderYingId

    let repaymentId = 1
    let delRepaymentId = 3
    
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





    it(`回款单 - 新建回款单1 POST: /api/business/ying/${orderId}/huikuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/huikuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    // "huikuanCompanyId" : 1,
                    "huikuanDate" : "2017-09-03 00:00:00",
                    "huikuanAmount" : "3000",
                    "huikuanUsage" : "PAYMENT_FOR_GOODS",
                    "huikuanMode" : "ELEC_REMITTANCE",
                    "huikuanBankPaper" : "",
                    "huikuanBankPaperDate" : "",
                    "huikuanBankDiscount" : "",
                    "huikuanBankDiscountRate" : "",
                    "huikuanBankPaperExpire" : "",
                    "huikuanBusinessPaper" : "",
                    "huikuanBusinessPaperDate" : "",
                    "huikuanBusinessDiscount" : "",
                    "huikuanBusinessDiscountRate" : "",
                    "huikuanBusinessPaperExpire" : "",
                    "orderId" : 1
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.huikuanDate).to.include('2017')

                repaymentId = res.body.data.id
                done()
            })
    })

    it(`回款单 - 新建回款单2 POST: /api/business/ying/${orderId}/huikuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/huikuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 2,
                    // "huikuanCompanyId" : 1,
                    "huikuanDate" : "2017-10-03 00:00:00",
                    "huikuanAmount" : "4000",
                    "huikuanUsage" : "PAYMENT_FOR_GOODS",
                    "huikuanMode" : "ELEC_REMITTANCE",
                    "huikuanBankPaper" : "",
                    "huikuanBankPaperDate" : "",
                    "huikuanBankDiscount" : "",
                    "huikuanBankDiscountRate" : "",
                    "huikuanBankPaperExpire" : "",
                    "huikuanBusinessPaper" : "",
                    "huikuanBusinessPaperDate" : "",
                    "huikuanBusinessDiscount" : "",
                    "huikuanBusinessDiscountRate" : "",
                    "huikuanBusinessPaperExpire" : "",
                    "orderId" : 1
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.huikuanDate).to.include('2017')
                done()
            })
    })

    it(`回款单 - 新建回款单3 POST: /api/business/ying/${orderId}/huikuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/huikuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 3,
                    // "huikuanCompanyId" : 4,
                    "huikuanDate" : "2017-10-27 00:00:00",
                    "huikuanAmount" : "10000",
                    "huikuanUsage" : "PAYMENT_FOR_GOODS",
                    "huikuanMode" : "BANK_ACCEPTANCE",
                    "huikuanBankPaper" : true,
                    "huikuanBankPaperDate" : "2017-09-01 00:00:00",
                    "huikuanBankDiscount" : true,
                    "huikuanBankDiscountRate" : "0.3",
                    "huikuanBankPaperExpire" : "2017-09-30 00:00:00",
                    "huikuanBusinessPaper" : "",
                    "huikuanBusinessPaperDate" : "",
                    "huikuanBusinessDiscount" : "",
                    "huikuanBusinessDiscountRate" : "",
                    "huikuanBusinessPaperExpire" : "",
                    "orderId" : 1
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.huikuanDate).to.include('2017')

                done()
            })
    })

    it(`回款单 - 新建回款单4 POST: /api/business/ying/${orderId}/huikuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/huikuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 3,
                    // "huikuanCompanyId" : 4,
                    "huikuanDate" : "2017-10-29 00:00:00",
                    "huikuanAmount" : "60000",
                    "huikuanUsage" : "PAYMENT_FOR_GOODS",
                    "huikuanMode" : "BANK_ACCEPTANCE",
                    "huikuanBankPaper" : true,
                    "huikuanBankPaperDate" : "2017-09-01 00:00:00",
                    "huikuanBankDiscount" : true,
                    "huikuanBankDiscountRate" : "0.3",
                    "huikuanBankPaperExpire" : "2017-09-30 00:00:00",
                    "huikuanBusinessPaper" : "",
                    "huikuanBusinessPaperDate" : "",
                    "huikuanBusinessDiscount" : "",
                    "huikuanBusinessDiscountRate" : "",
                    "huikuanBusinessPaperExpire" : "",
                    "orderId" : 1
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.huikuanDate).to.include('2017')

                delRepaymentId = res.body.data.id
                done()
            })
    })

    it(`回款单 - 获取应收订单回款单列表 GET: /api/business/ying/${orderId}/huikuans?pageNo=1&pageSize=2`, function (done) {
        server.get(`/api/business/ying/${orderId}/huikuans?pageNo=1&pageSize=2`)
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

    it(`回款单 - 获取某个ID的回款单信息 GET: /api/business/ying/${orderId}/huikuans/${repaymentId}`, function (done) {
        server.get(`/api/business/ying/${orderId}/huikuans/${repaymentId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.huikuanDate).to.include('2017')
                done()
            })
    })

    it(`回款单 - 修改某个ID的回款单 PUT: /api/business/ying/${orderId}/huikuans/${repaymentId}`, function (done) {
        server.put(`/api/business/ying/${orderId}/huikuans/${repaymentId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    // "huikuanCompanyId" : 1,
                    "huikuanDate" : "2017-09-03 00:00:00",
                    "huikuanUsage" : "PAYMENT_FOR_GOODS",
                    "huikuanMode" : "ELEC_REMITTANCE",
                    "huikuanBankPaper" : "",
                    "huikuanBankPaperDate" : "",
                    "huikuanBankDiscount" : "",
                    "huikuanBankDiscountRate" : "",
                    "huikuanBankPaperExpire" : "",
                    "huikuanBusinessPaper" : "",
                    "huikuanBusinessPaperDate" : "",
                    "huikuanBusinessDiscount" : "",
                    "huikuanBusinessDiscountRate" : "",
                    "huikuanBusinessPaperExpire" : "",
                    "orderId" : 1,
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

    it(`回款单 - 删除某个ID的回款单 DELETE: /api/business/ying/${orderId}/huikuans/${delRepaymentId}`, function (done) {
        server.delete(`/api/business/ying/${orderId}/huikuans/${delRepaymentId}`)
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