/**
 * Created by jin on 9/26/17.
 */


/**
 * Created by jin on 9/18/17.
 */


//Require the dev-dependencies
const expect = require('chai').expect
const should = require('chai').should()
const supertest = require('supertest')

const config = require('../testConfig')
const server = supertest(config.path.urlApi)




describe('应收订单 统计范例3', function () {

    let Authorization = ''
    let orderId = 14

    let unitId = 8

    before(function (done) {

        server.post('/api/login')
            .set(config.headers)
            .send(config.user.user3)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                Authorization = res.body.data
                done()
            })
    })



    it('新建参与商 POST: /api/parties', function (done) {
        server.post('/api/parties')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                name: "资兴和顺",
                shortName : '资兴和顺',
                partyType : 3
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('资兴和顺')
                done()
            })
    })

    it('新建参与商 POST: /api/parties', function (done) {
        server.post('/api/parties')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                name: "漯河泰润",
                shortName : '漯河泰润',
                partyType : 3
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('漯河泰润')
                done()
            })
    })





    it('应收订单 - 新建应收订单1 POST: /api/business/yings', function (done) {
        server.post('/api/business/yings')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "teamId" : 6,
                    "line" : "资兴和顺 - 漯河泰润 - 嘉瑞 - 华润鲤鱼江",
                    "cargoType" : "COAL",
                    "upstreamSettleMode" : "ONE_PAPER_SETTLE",
                    "downstreamSettleMode" : "ONE_PAPER_SETTLE",
                    "mainAccounting" : 3,
                    "upstreamId" : 32,
                    "downstreamId" : 31,
                    "businessType" : "ying",
                    "orderPartyList" : [
                        {
                            "custType" : "TRAFFICKCER",
                            "customerId" : 33,
                            "position" : 1
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
                expect(res.body.data.id, '返回的数据data对象里面没有id字段').to.be.a('number')
                expect(res.body.data.line, '返回的数据data对象的line属性错误').to.include('嘉瑞')

                orderId = res.body.data.id
                done()
            })
    })

    it(`应收订单 - 获取某个ID的应收订单信息 GET: /api/business/yings/${orderId}`, function (done) {
        server.get(`/api/business/yings/${orderId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据data对象里面没有id字段').to.be.a('number')
                expect(res.body.data.line, '返回的数据data对象的line属性错误').to.include('嘉瑞')
                done()
            })
    })

    it(`核算单元 - 新建核算单元1 POST: /api/business/ying/${orderId}/units`, function (done) {
        server.post(`/api/business/ying/${orderId}/units`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsMonth" : "201704",
                    "maxPrepayRate" : 0.9,
                    "unInvoicedRate" : 0.7,
                    "contractBaseInterest" : 0.18,
                    "expectHKDays" : 45,
                    "tradeAddPrice" : 2,
                    "weightedPrice" : "415"
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据data对象里面没有id字段').to.be.a('number')
                expect(res.body.data.hsMonth, '返回的数据data对象的hsMonth属性错误').to.include('201704')

                unitId = res.body.data.id
                done()
            })
    })

    it(`核算单元 - 获取某个ID的核算单元信息 GET: /api/business/ying/${orderId}/units/${unitId}`, function (done) {
        server.get(`/api/business/ying/${orderId}/units/${unitId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据data对象里面没有id字段').to.be.a('number')
                expect(res.body.data.hsMonth, '返回的数据data对象的hsMonth属性错误').to.include('201704')
                done()
            })
    })







    it(`发运单 - 新建发运单1 POST: /api/business/ying/${orderId}/fayuns`, function (done) {
        server.post(`/api/business/ying/${orderId}/fayuns`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "fyDate" : "2017-04-10 00:00:00",
                    "fyAmount" : "4455",
                    "arriveStatus" : "ARRIVE",
                    "upstreamTrafficMode" : "MOTOR",
                    "upstreamCars" : "89",
                    "upstreamJHH" : "",
                    "upstreamShip" : "",
                    "downstreamTrafficMode" : "MOTOR",
                    "downstreamCars" : "89",
                    "downstreamJHH" : "",
                    "downstreamShip" : "",
                    "orderId" : orderId
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据data对象里面没有id字段').to.be.a('number')
                expect(res.body.data.fyDate).to.include('2017')
                done()
            })
    })












    it(`付款单 - 新建付款单1 POST: /api/business/ying/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "payDate" : "2017-04-14 00:00:00",
                    "receiveCompanyId" : 32,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payAmount" : "540000",
                    "capitalId" : 3,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-04-14 00:00:00",
                        "amount" : "999999",
                        "capitalId" : 3,
                        "useInterest" : 0.075,
                        "useDays" : "35"
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
                done()
            })
    })
    it(`付款单 - 新建付款单2 POST: /api/business/ying/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "payDate" : "2017-04-25 00:00:00",
                    "receiveCompanyId" : 32,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payAmount" : "480000",
                    "capitalId" : 3,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-04-25 00:00:00",
                        "amount" : "999999",
                        "capitalId" : 3,
                        "useInterest" : 0.075,
                        "useDays" : "35"
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
                done()
            })
    })
    it(`付款单 - 新建付款单3 POST: /api/business/ying/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "payDate" : "2017-05-03 00:00:00",
                    "receiveCompanyId" : 32,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payAmount" : "640000",
                    "capitalId" : 3,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-05-03 00:00:00",
                        "amount" : "999999",
                        "capitalId" : 3,
                        "useInterest" : 0.075,
                        "useDays" : "35"
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
                done()
            })
    })
    it(`付款单 - 新建付款单4 POST: /api/business/ying/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "payDate" : "2017-05-24 00:00:00",
                    "receiveCompanyId" : 32,
                    "payUsage" : "FIAL_PAYMENT",
                    "payAmount" : "170899.52",
                    "capitalId" : 3,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2299-12-30 00:00:00",
                        "amount" : "99999999",
                        "capitalId" : 3,
                        "useInterest" : "99999999",
                        "useDays" : "99999999"
                    }
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                console.log(res.body)
                if (err) return done(err)

                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.payDate).to.include('2017')
                done()
            })
    })
    it(`付款单 - 新建付款单5 POST: /api/business/ying/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "payDate" : "2017-05-24 00:00:00",
                    "receiveCompanyId" : 32,
                    "payUsage" : "TRADE_DEFICIT",
                    "payAmount" : "8910",
                    "capitalId" : 3,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-05-24 00:00:00",
                        "amount" : "999999",
                        "capitalId" : 3,
                        "useInterest" : 0.075,
                        "useDays" : "35"
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
                done()
            })
    })






    it(`回款单 - 新建回款单1 POST: /api/business/ying/${orderId}/huikuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/huikuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "huikuanDate" : "2017-05-17 00:00:00",
                    "huikuanAmount" : "1849569.52",
                    "huikuanUsage" : "PAYMENT_FOR_GOODS",
                    "huikuanMode" : "ELEC_REMITTANCE",
                    "huikuanBankPaper" : "",
                    "huikuanBankPaperDate" : null,
                    "huikuanBankDiscount" : "",
                    "huikuanBankDiscountRate" : "",
                    "huikuanBankPaperExpire" : null,
                    "huikuanBusinessPaper" : "",
                    "huikuanBusinessPaperDate" : null,
                    "huikuanBusinessDiscount" : "",
                    "huikuanBusinessDiscountRate" : "",
                    "huikuanBusinessPaperExpire" : null,
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
                expect(res.body.data.huikuanDate).to.include('2017')
                done()
            })
    })






    it(`下游结算单 - 新建下游结算单1 POST: /api/business/ying/${orderId}/settlebuyerdownstream`, function (done) {
        server.post(`/api/business/ying/${orderId}/settlebuyerdownstream`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "settleDate" : "2017-05-03 00:00:00",
                    "amount" : "4455.00",
                    "money" : "1849569.52",
                    "settleGap" : "0",
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







    it(`上游结算单 - 新建上游结算单1 POST: /api/business/ying/${orderId}/settlesellerupstream`, function (done) {
        server.post(`/api/business/ying/${orderId}/settlesellerupstream`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "settleDate" : "2017-05-23 00:00:00",
                    "amount" : "99999",
                    "money" : "99999",
                    "discountType" : "NO_DISCOUNT",
                    "discountInterest" : "9999",
                    "discountDays" : "9999",
                    "discountAmount" : "9999",
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






    it(`发票 - 新建发票1 POST: /api/business/ying/${orderId}/invoices`, function (done) {
        server.post(`/api/business/ying/${orderId}/invoices`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "invoiceDirection" : "INCOME",
                    "invoiceType" : "GOODS_INVOICE",
                    "openDate" : "2017-04-14 00:00:00",
                    "openCompanyId" : 32,
                    "receiverId" : 33,
                    "orderId" : orderId,
                    "details" : [
                        {
                            "invoiceNumber" : "12341234",
                            "cargoAmount" : "1000",
                            "taxRate" : "17",
                            "priceAndTax" : "380000"
                        },
                        {
                            "invoiceNumber" : "12341235",
                            "cargoAmount" : "326",
                            "taxRate" : "17",
                            "priceAndTax" : "160000"
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
    it(`发票 - 新建发票2 POST: /api/business/ying/${orderId}/invoices`, function (done) {
        server.post(`/api/business/ying/${orderId}/invoices`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "invoiceDirection" : "INCOME",
                    "invoiceType" : "GOODS_INVOICE",
                    "openDate" : "2017-05-02 00:00:00",
                    "openCompanyId" : 32,
                    "receiverId" : 33,
                    "orderId" : orderId,
                    "details" : [
                        {
                            "invoiceNumber" : "12341236",
                            "cargoAmount" : "2732",
                            "taxRate" : "17",
                            "priceAndTax" : "1120000"
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
    it(`发票 - 新建发票3 POST: /api/business/ying/${orderId}/invoices`, function (done) {
        server.post(`/api/business/ying/${orderId}/invoices`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "invoiceDirection" : "INCOME",
                    "invoiceType" : "GOODS_INVOICE",
                    "openDate" : "2017-05-23 00:00:00",
                    "openCompanyId" : 32,
                    "receiverId" : 33,
                    "orderId" : orderId,
                    "details" : [
                        {
                            "invoiceNumber" : "12341237",
                            "cargoAmount" : "397",
                            "taxRate" : "17",
                            "priceAndTax" : "170899.52"
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
    it(`发票 - 新建发票4 POST: /api/business/ying/${orderId}/invoices`, function (done) {
        server.post(`/api/business/ying/${orderId}/invoices`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "invoiceDirection" : "INCOME",
                    "invoiceType" : "GOODS_INVOICE",
                    "openDate" : "2017-04-21 00:00:00",
                    "openCompanyId" : 32,
                    "receiverId" : 33,
                    "orderId" : orderId,
                    "details" : [
                        {
                            "invoiceNumber" : "12341238",
                            "cargoAmount" : "1326",
                            "taxRate" : "17",
                            "priceAndTax" : "542652"
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
    it(`发票 - 新建发票5 POST: /api/business/ying/${orderId}/invoices`, function (done) {
        server.post(`/api/business/ying/${orderId}/invoices`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "invoiceDirection" : "INCOME",
                    "invoiceType" : "GOODS_INVOICE",
                    "openDate" : "2017-05-18 00:00:00",
                    "openCompanyId" : 32,
                    "receiverId" : 33,
                    "orderId" : orderId,
                    "details" : [
                        {
                            "invoiceNumber" : "12341239",
                            "cargoAmount" : "2732",
                            "taxRate" : "17",
                            "priceAndTax" : "1125464"
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
    it(`发票 - 新建发票6 POST: /api/business/ying/${orderId}/invoices`, function (done) {
        server.post(`/api/business/ying/${orderId}/invoices`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "invoiceDirection" : "INCOME",
                    "invoiceType" : "GOODS_INVOICE",
                    "openDate" : "2017-05-26 00:00:00",
                    "openCompanyId" : 32,
                    "receiverId" : 33,
                    "orderId" : orderId,
                    "details" : [
                        {
                            "invoiceNumber" : "12341240",
                            "cargoAmount" : "397",
                            "taxRate" : "17",
                            "priceAndTax" : "171693.52"
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
})


