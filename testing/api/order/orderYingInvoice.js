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




describe('应收订单 - 发票 : ', function () {

    let Authorization = ''

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




    it('发票 - 新建发票1 POST: /api/ying/1/invoices', function (done) {
        server.post('/api/ying/1/invoices')
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
                    "orderId" : 1,
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
                done()
            })
    })

    it('发票 - 新建发票2 POST: /api/ying/1/invoices', function (done) {
        server.post('/api/ying/1/invoices')
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
                    "orderId" : 1,
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
                done()
            })
    })

    it('发票 - 获取应收订单发票列表 GET: /api/ying/1/invoices?pageNo=1&pageSize=2', function (done) {
        server.get('/api/ying/1/invoices?pageNo=1&pageSize=2')
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

    it('发票 - 获取某个ID的发票信息 GET: /api/ying/1/invoices/1', function (done) {
        server.get('/api/ying/1/invoices/1')
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

    it('发票 - 修改某个ID的发票 PUT: /api/ying/1/invoices/1', function (done) {
        server.put('/api/ying/1/invoices/1')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 2,
                    "invoiceDirection" : "INCOME",
                    "invoiceType" : "GOODS_INVOICE",
                    "openDate" : "2017-09-02 00:00:00",
                    "openCompanyId" : 1,
                    "receiverId" : 2,
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

    it('发票 - 删除某个ID的发票 DELETE: /api/yings/1/invoices/1', function (done) {
        server.put('/api/yings/1/invoices/1')
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