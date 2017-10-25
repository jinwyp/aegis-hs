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




describe('应收订单 发票:', function () {

    let Authorization = ''

    let orderId = config.order.getOrderYingId

    let invoiceId = 1
    let delInvoiceId = 2

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




    it(`发票 - 新建发票1 POST: /api/business/ying/${orderId}/invoices`, function (done) {
        server.post(`/api/business/ying/${orderId}/invoices`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "invoiceDirection" : "INCOME",
                    "invoiceType" : "GOODS_INVOICE",
                    "openDate" : "2017-09-01 00:00:00",
                    "openCompanyId" : 1,
                    "receiverId" : 1,
                    "orderId" : orderId,
                    "details" : [
                        {
                            "invoiceNumber" : "2000",
                            "cargoAmount"   : "1000",
                            "taxRate"       : "0.2",
                            "priceAndTax"   : "100"
                        },
                        {
                            "invoiceNumber" : "2000",
                            "cargoAmount" : "1000",
                            "taxRate" : "0.2",
                            "priceAndTax" : "100"
                        }
                    ]
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.openDate).to.include('2017')

                invoiceId = res.body.data.id
                done()
            })
    })

    it(`发票 - 新建发票2 POST: /api/business/ying/${orderId}/invoices`, function (done) {
        server.post(`/api/business/ying/${orderId}/invoices`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "invoiceDirection" : "INCOME",
                    "invoiceType" : "GOODS_INVOICE",
                    "openDate" : "2017-09-01 00:00:00",
                    "openCompanyId" : 1,
                    "receiverId" : 1,
                    "orderId" : orderId,
                    "details" : [
                        {
                            "invoiceNumber" : "2000",
                            "cargoAmount"   : "1000",
                            "taxRate"       : "0.2",
                            "priceAndTax"   : "100"
                        },
                        {
                            "invoiceNumber" : "2000",
                            "cargoAmount" : "1000",
                            "taxRate" : "0.2",
                            "priceAndTax" : "100"
                        }
                    ]
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.openDate).to.include('2017')

                delInvoiceId = res.body.data.id
                done()
            })
    })

    it(`发票 - 获取应收订单发票列表 GET: /api/business/ying/${orderId}/invoices?pageNo=1&pageSize=2`, function (done) {
        server.get(`/api/business/ying/${orderId}/invoices?pageNo=1&pageSize=2`)
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

    it(`发票 - 获取某个ID的发票信息 GET: /api/business/ying/${orderId}/invoices/${invoiceId}`, function (done) {
        server.get(`/api/business/ying/${orderId}/invoices/${invoiceId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.openDate).to.include('2017')
                done()
            })
    })

    it(`发票 - 修改某个ID的发票 PUT: /api/business/ying/${orderId}/invoices/${invoiceId}`, function (done) {
        server.put(`/api/business/ying/${orderId}/invoices/${invoiceId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "id" : invoiceId,
                    "hsId" : 2,
                    "invoiceDirection" : "INCOME",
                    "invoiceType" : "GOODS_INVOICE",
                    "openDate" : "2017-10-06 00:00:00",
                    "openCompanyId" : 1,
                    "receiverId" : 2,
                    "orderId" : 9,
                    "details" : [
                        {
                            "id" : 1,
                            "invoiceId" : 1,
                            "invoiceNumber" : "2000",
                            "cargoAmount" : 1000,
                            "taxRate" : 0.2,
                            "priceAndTax" : 100,

                        },
                        {
                            "id" : 2,
                            "invoiceId" : 1,
                            "invoiceNumber" : "2000",
                            "cargoAmount" : 1000,
                            "taxRate" : 0.2,
                            "priceAndTax" : 100,

                        }
                    ]
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

    it(`发票 - 删除某个ID的发票 DELETE: /api/business/ying/${orderId}/invoices/${delInvoiceId}`, function (done) {
        server.delete(`/api/business/ying/${orderId}/invoices/${delInvoiceId}`)
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