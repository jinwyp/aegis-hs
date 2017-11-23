
/**
 * Created by jin on 9/18/17.
 */


//Require the dev-dependencies
const expect = require('chai').expect
const should = require('chai').should()
const supertest = require('supertest')

const config = require('../testConfig')
const server = supertest(config.path.urlApi)




describe('仓押订单 统计范例11 赵征提供 11.21日', function () {

    let Authorization = ''
    let orderId = 16

    let unitId = 10
    let borrowId = 16

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








    it('仓押订单 - 新建仓押订单1 POST: /api/business/cangs', function (done) {
        server.post('/api/business/cangs')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "teamId" : 13,
                    "line" : "广州鑫丰润 - 泰州立翔 - 嘉瑞 - 广州莲荷",
                    "cargoType" : "COAL",
                    "upstreamSettleMode" : "ONE_PAPER_SETTLE",
                    "downstreamSettleMode" : "ONE_PAPER_SETTLE",
                    "mainAccounting"       : 3,
                    "upstreamId"           : 27,
                    "downstreamId"         : 28,
                    "businessType" : "cang",
                    "orderPartyList" : [
                        {
                            "custType" : "TRAFFICKCER",
                            "customerId" : 22,
                            "customerPosition" : 1
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

    it(`仓押订单 - 获取某个ID的仓押订单信息 GET: /api/business/cangs/${orderId}`, function (done) {
        server.get(`/api/business/cangs/${orderId}`)
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

    it(`核算单元 - 新建核算单元1 POST: /api/business/cang/${orderId}/units`, function (done) {
        server.post(`/api/business/cang/${orderId}/units`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsMonth" : "201706",
                    "maxPrepayRate" : 1,
                    "unInvoicedRate" : 1,
                    "contractBaseInterest" : 0.15,
                    "expectHKDays" : 45,
                    "tradeAddPrice" : "2",
                    "weightedPrice" : 595
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据data对象里面没有id字段').to.be.a('number')
                expect(res.body.data.hsMonth, '返回的数据data对象的hsMonth属性错误').to.include('201706')

                unitId = res.body.data.id
                done()
            })
    })

    it(`核算单元 - 获取某个ID的核算单元信息 GET: /api/business/cang/${orderId}/units/${unitId}`, function (done) {
        server.get(`/api/business/cang/${orderId}/units/${unitId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据data对象里面没有id字段').to.be.a('number')
                expect(res.body.data.hsMonth, '返回的数据data对象的hsMonth属性错误').to.include('201706')
                done()
            })
    })







    it(`入库单 - 新建入库单1 POST: /api/business/cang/${orderId}/rukus`, function (done) {
        server.post(`/api/business/cang/${orderId}/rukus`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "orderId" : orderId,
                    "rukuDate" : "2017-06-29 00:00:00",
                    "rukuStatus" : "IN_STORAGE",
                    "rukuAmount" : "5000",
                    "rukuPrice" : "2975000",
                    "trafficMode" : "MOTOR",
                    "cars" : "",
                    "jhh" : "",
                    "ship" : "",
                    "locality" : "新沙港",
                    "chukuDate" : "2099-12-30 00:00:00",
                    "chukuAmount" : 99999999,
                    "chukuPrice" : 99999999
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
    it(`入库单 - 新建入库单2 POST: /api/business/cang/${orderId}/rukus`, function (done) {
        server.post(`/api/business/cang/${orderId}/rukus`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "orderId" : orderId,
                    "rukuDate" : "2017-07-05 00:00:00",
                    "rukuStatus" : "IN_STORAGE",
                    "rukuAmount" : "5102.7",
                    "rukuPrice" : "3036106.5",
                    "trafficMode" : "MOTOR",
                    "cars" : "",
                    "jhh" : "",
                    "ship" : "",
                    "locality" : "新沙港",
                    "chukuDate" : "2099-12-30 00:00:00",
                    "chukuAmount" : 99999999,
                    "chukuPrice" : 99999999
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



    it(`出库单 - 新建出库单1 POST: /api/business/cang/${orderId}/chukus`, function (done) {
        server.post(`/api/business/cang/${orderId}/chukus`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "locality" : "新沙港",
                    "chukuDate" : "2017-07-25 00:00:00",
                    "chukuAmount" : "8000",
                    "chukuPrice" : "4626496.36",
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
    it(`出库单 - 新建出库单2 POST: /api/business/cang/${orderId}/chukus`, function (done) {
        server.post(`/api/business/cang/${orderId}/chukus`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "locality" : "新沙港",
                    "chukuDate" : "2017-07-27 00:00:00",
                    "chukuAmount" : "2102.7",
                    "chukuPrice" : "1402580.21",
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








    it(`付款单 - 新建付款单1 POST: /api/business/cang/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/cang/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "payDate" : "2017-06-29 00:00:00",
                    "receiveCompanyId" : 27,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payAmount" : "4207000",
                    "capitalId" : 3,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-06-29 00:00:00",
                        "amount" : "999999",
                        "capitalId" : 3,
                        "useInterest" : 99999999,
                        "useDays" : "99999999"
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
    it(`付款单 - 新建付款单2 POST: /api/business/cang/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/cang/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "payDate" : "2017-07-05 00:00:00",
                    "receiveCompanyId" : 27,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payAmount" : "1804106.5",
                    "capitalId" : 3,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-07-05 00:00:00",
                        "amount" : "999999",
                        "capitalId" : 3,
                        "useInterest" : 99999999,
                        "useDays" : "99999999"
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
    it(`付款单 - 新建付款单3 POST: /api/business/cang/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/cang/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "payDate" : "2017-07-05 00:00:00",
                    "receiveCompanyId" : 27,
                    "payUsage" : "TRADE_DEFICIT",
                    "payAmount" : "20205.4",
                    "capitalId" : 3,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-05-03 00:00:00",
                        "amount" : "999999",
                        "capitalId" : 3,
                        "useInterest" : 99999999,
                        "useDays" : "99999999"
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










    it(`借款单 - 新建借款单1 POST: /api/business/cang/${orderId}/jiekuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/jiekuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "jiekuanDate" : "2017-07-06 00:00:00",
                    "amount" : "5000000",
                    "capitalId" : 17,
                    "useInterest" : "0.098",
                    "useDays" : "45"
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


    it(`还款单 - 新建还款单1 POST: /api/business/cang/${orderId}/huankuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/huankuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "orderId" : orderId,
                    "promise" : true,
                    "huankuankDate" : "2017-07-31 00:00:00",
                    "huankuanMapList" : [
                        {
                            "jiekuanId": borrowId,
                            "principal" : "5000000",
                            "interest" : "24427.16",
                            "fee" : "5483.33"
                        }
                    ]
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                if (res.body.success === false) { console.log(res.body) }
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.huankuankDate).to.include('2017')
                done()
            })
    })







    it(`回款单 - 新建回款单1 POST: /api/business/cang/${orderId}/huikuans`, function (done) {
        server.post(`/api/business/cang/${orderId}/huikuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "orderId" : orderId,
                    "huikuanDate" : "2017-06-29 00:00:00",
                    "huikuanAmount" : "1190000",
                    "huikuanUsage" : "DEPOSITECASH",
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
    it(`回款单 - 新建回款单2 POST: /api/business/cang/${orderId}/huikuans`, function (done) {
        server.post(`/api/business/cang/${orderId}/huikuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "orderId" : orderId,
                    "huikuanDate" : "2017-07-25 00:00:00",
                    "huikuanAmount" : "4626496.36",
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
    it(`回款单 - 新建回款单3 POST: /api/business/cang/${orderId}/huikuans`, function (done) {
        server.post(`/api/business/cang/${orderId}/huikuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "orderId" : orderId,
                    "huikuanDate" : "2017-07-27 00:00:00",
                    "huikuanAmount" : "212580.21",
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





    it(`买方上游结算单 - 新建买方上游结算单1 POST: /api/business/cang/${orderId}/settlebuyerupstream`, function (done) {
        server.post(`/api/business/cang/${orderId}/settlebuyerupstream`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "settleDate" : "2017-07-28 00:00:00",
                    "amount" : "10102.7",
                    "money" : "6011106.5",
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

    it(`卖方下游结算单 - 新建卖方下游结算单1 POST: /api/business/cang/${orderId}/settlesellerdownstream`, function (done) {
        server.post(`/api/business/cang/${orderId}/settlesellerdownstream`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "settleDate" : "2017-07-05 00:00:00",
                    "amount" : "99999",
                    "money" : "99999",
                    "discountType" : "NO_DISCOUNT",
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













    it(`发票 - 新建发票1 POST: /api/business/cang/${orderId}/invoices`, function (done) {
        server.post(`/api/business/cang/${orderId}/invoices`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "orderId" : orderId,
                    "invoiceDirection" : "INCOME",
                    "invoiceType" : "GOODS_INVOICE",
                    "openDate" : "2017-07-04 00:00:00",
                    "openCompanyId" : 27,
                    "receiverId" : 22,
                    "details" : [
                        {
                            "invoiceNumber" : "43211236",
                            "cargoAmount" : "10102.7",
                            "taxRate" : "0.17",
                            "priceAndTax" : "6011106.5"
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
    it(`发票 - 新建发票2 POST: /api/business/cang/${orderId}/invoices`, function (done) {
        server.post(`/api/business/cang/${orderId}/invoices`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "orderId" : orderId,
                    "invoiceDirection" : "INCOME",
                    "invoiceType" : "GOODS_INVOICE",
                    "openDate" : "2017-07-18 00:00:00",
                    "openCompanyId" : 22,
                    "receiverId" : 3,
                    "details" : [
                        {
                            "invoiceNumber" : "43211237",
                            "cargoAmount" : "10102.7",
                            "taxRate" : "0.17",
                            "priceAndTax" : "6031311.9"
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







    it(`统计验证 - 获取核算月统计信息 GET: /api/business/cang/${orderId}/analysis/${unitId}`, function (done) {
        server.get(`/api/business/cang/${orderId}/analysis/${unitId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)


                expect(res.body.data.ccsProfile, '发运信息 - 瑞茂通总收益数据不对').to.equal(17970.07)

                expect(res.body.data.totalCSSIntypeNumber, '进项票信息 - CCS已收到进项数量数据不对').to.equal(10102.70)
                expect(res.body.data.totalCCSIntypeMoney, '进项票信息 - CCS已收到进项金额数据不对').to.equal(6031311.9)
                expect(res.body.data.cssUninTypeNum, '进项票信息 - CCS未收到进项数量数据不对').to.equal(0)
                // expect(res.body.data.cssUninTypeMoney, '进项票信息 - CCS未收到进项金额数据不对').to.equal(0)


                expect(res.body.data.saleIncludeTaxTotalAmount, '毛利 - 销售含税总额数据不对').to.equal(6029076.57)
                expect(res.body.data.purchaseIncludeTaxTotalAmount, '毛利 - 采购含税总额数据不对').to.equal(6011106.50)

                expect(res.body.data.tradeCompanyAddMoney, '毛利 - 贸易公司加价数据不对').to.equal(20205.40)
                expect(res.body.data.vat, '毛利 - 应交增值税数据不对').to.equal(0)
                expect(res.body.data.withoutTaxIncome, '毛利 - 不含税收入数据不对').to.equal(5153056.90)
                expect(res.body.data.withoutTaxCost, '毛利 - 不含税成本数据不对').to.equal(5154967.44)

                expect(res.body.data.additionalTax, '毛利 - 税金及附加数据不对').to.equal(0)
                expect(res.body.data.stampDuty, '毛利 - 印花税数据不对').to.equal(3618.12)
                expect(res.body.data.opreationCrossProfile, '毛利 - 经营毛利数据不对').to.equal(-5528.66)
                expect(res.body.data.crossProfileATon, '毛利 - 单吨毛利数据不对').to.equal(-0.55)


                expect(res.body.data.upstreamCapitalPressure, '占压 - 上游资金占压数据不对').to.equal(0)
                expect(res.body.data.ownerCapitalPressure, '占压 - 自有资金占压数据不对').to.equal(0)

                expect(res.body.data.downstreamCapitalPressure, '占压 - 下游资金占压数据不对').to.equal(0)
                expect(res.body.data.cangPrePayment, '占压 - 预收款数据不对').to.equal(0)


                expect(res.body.data.totalInstorageNum, '出入库 - 入库总数量数据不对').to.equal(10102.7)
                expect(res.body.data.totalInstorageAmount, '出入库 - 入库总金额数据不对').to.equal(6011106.5)

                expect(res.body.data.totalOutstorageNum, '出入库 - 已出库数量数据不对').to.equal(10102.7)
                expect(res.body.data.instorageUnitPrice, '出入库 - 入库单价数据不对').to.equal(595)
                expect(res.body.data.totalOutstorageMoney, '出入库 - 已出库金额数据不对').to.equal(6029076.57)


                done()
            })
    })
})

