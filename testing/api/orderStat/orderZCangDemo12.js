
/**
 * Created by jin on 9/18/17.
 */


//Require the dev-dependencies
const expect = require('chai').expect
const should = require('chai').should()
const supertest = require('supertest')

const config = require('../testConfig')
const server = supertest(config.path.urlApi)




describe('仓押订单 统计范例5', function () {

    let Authorization = ''
    let orderId = 17

    let unitId = 11
    let borrowId = 17

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
                    "hsMonth" : "201707",
                    "maxPrepayRate" : 1,
                    "unInvoicedRate" : 0.8,
                    "contractBaseInterest" : 0.15,
                    "expectHKDays" : 45,
                    "tradeAddPrice" : "2",
                    "weightedPrice" : 0
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据data对象里面没有id字段').to.be.a('number')
                expect(res.body.data.hsMonth, '返回的数据data对象的hsMonth属性错误').to.include('201707')

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
                expect(res.body.data.hsMonth, '返回的数据data对象的hsMonth属性错误').to.include('201707')
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
                    "rukuDate" : "2017-07-02 00:00:00",
                    "rukuStatus" : "IN_STORAGE",
                    "rukuAmount" : "14475.4",
                    "rukuPrice" : "7508824.24",
                    "trafficMode" : "MOTOR",
                    "cars" : "366",
                    "jhh" : "",
                    "ship" : "",
                    "locality" : "立翔",
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
                    "locality" : "立翔",
                    "chukuDate" : "2017-07-14 00:00:00",
                    "chukuAmount" : "4000",
                    "chukuPrice" : "1600000"
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
                    "locality" : "立翔",
                    "chukuDate" : "2017-07-21 00:00:00",
                    "chukuAmount" : "3000",
                    "chukuPrice" : "1000000"
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
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "locality" : "立翔",
                    "chukuDate" : "2017-07-25 00:00:00",
                    "chukuAmount" : "7475.4",
                    "chukuPrice" : "4923503.64"
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
                    "payAmount" : "5238000",
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
                    "payDate" : "2017-07-10 00:00:00",
                    "receiveCompanyId" : 27,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payAmount" : "2270824.24",
                    "capitalId" : 3,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-07-10 00:00:00",
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
                    "payDate" : "2017-07-10 00:00:00",
                    "receiveCompanyId" : 27,
                    "payUsage" : "TRADE_DEFICIT",
                    "payAmount" : "28950.8",
                    "capitalId" : 3,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-07-10 00:00:00",
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
                    "jiekuanDate" : "2017-06-30 00:00:00",
                    "amount" : "4000000",
                    "capitalId" : 17,
                    "useInterest" : "0.075",
                    "useDays" : "30"
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
    it(`借款单 - 新建借款单2 POST: /api/business/cang/${orderId}/jiekuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/jiekuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "jiekuanDate" : "2017-07-01 00:00:00",
                    "amount" : "2000000",
                    "capitalId" : 17,
                    "useInterest" : "0.075",
                    "useDays" : "30"
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
                    "huankuankDate" : "2017-07-26 00:00:00",
                    "huankuanMapList" : [
                        {
                            "jiekuanId": borrowId,
                            "principal" : "4000000",
                            "interest" : 0,
                            "fee" : 0
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
    it(`还款单 - 新建还款单2 POST: /api/business/cang/${orderId}/huankuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/huankuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "orderId" : orderId,
                    "promise" : true,
                    "huankuankDate" : "2017-07-27 00:00:00",
                    "huankuanMapList" : [
                        {
                            "jiekuanId": borrowId + 1,
                            "principal" : "2000000",
                            "interest" : 23716.58,
                            "fee" : 6750
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
                    "huikuanAmount" : "1550000",
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
                    "huikuanBusinessPaperExpire" : null
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
                    "huikuanDate" : "2017-07-14 00:00:00",
                    "huikuanAmount" : "1600000",
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
                    "huikuanDate" : "2017-07-21 00:00:00",
                    "huikuanAmount" : "1000000",
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
    it(`回款单 - 新建回款单4 POST: /api/business/cang/${orderId}/huikuans`, function (done) {
        server.post(`/api/business/cang/${orderId}/huikuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "orderId" : orderId,
                    "huikuanDate" : "2017-07-25 00:00:00",
                    "huikuanAmount" : "3373503.64",
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
                    "amount" : "14475.4",
                    "money" : "7508824.24",
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
                    "settleDate" : "2017-07-10 00:00:00",
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
                    "openDate" : "2017-07-17 00:00:00",
                    "openCompanyId" : 27,
                    "receiverId" : 22,
                    "details" : [
                        {
                            "invoiceNumber" : "12346123",
                            "cargoAmount" : "14475.4",
                            "taxRate" : "0.17",
                            "priceAndTax" : "7508824.24"
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
                    "openDate" : "2017-07-17 00:00:00",
                    "openCompanyId" : 22,
                    "receiverId" : 3,
                    "details" : [
                        {
                            "invoiceNumber" : "12346124",
                            "cargoAmount" : "14475.4",
                            "taxRate" : "0.17",
                            "priceAndTax" : "7537775.04"
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


                expect(res.body.data.purchaseIncludeTaxTotalAmount, '采购含税总额数据不对').to.equal(7508824.24)
                expect(res.body.data.saleIncludeTaxTotalAmount, '销售含税总额数据不对').to.equal(7523503.64)
                expect(res.body.data.tradeCompanyAddMoney, '毛利贸易公司加价数据不对').to.equal(28950.80)
                expect(res.body.data.withoutTaxIncome, '不含税收入数据不对').to.equal(6430344.99)
                expect(res.body.data.withoutTaxCost, '不含税成本数据不对').to.equal(6442542.77)
                // expect(res.body.data.vat, '应交增值税数据不对').to.equal(1418.12)
                expect(res.body.data.additionalTax, '税金及附加数据不对').to.equal(0)
                expect(res.body.data.stampDuty, '印花税数据不对').to.equal(4518.38)
                expect(res.body.data.opreationCrossProfile, '经营毛利数据不对').to.equal(-16716.16)
                expect(res.body.data.crossProfileATon, '单吨毛利数据不对').to.equal(-1.15)

                done()
            })
    })
})

