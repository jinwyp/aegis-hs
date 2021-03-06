
/**
 * Created by jin on 9/18/17.
 */


//Require the dev-dependencies
const expect = require('chai').expect
const should = require('chai').should()
const supertest = require('supertest')

const config = require('../testConfig')
const server = supertest(config.path.urlApi)




describe('仓押订单 统计范例12 赵征提供 12.08日', function () {

    let Authorization = ''
    let orderId = 18

    let unitId = 12
    let borrowId = 24

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
                    "mainAccounting"       : 5,
                    "upstreamId"           : 123,
                    "downstreamId"         : 122,
                    "businessType" : "cang",
                    "orderPartyList" : [
                        {
                            "custType" : "TRAFFICKCER",
                            "customerId" : 31,
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
                    "hsMonth" : "201707",
                    "maxPrepayRate" : 1,
                    "unInvoicedRate" : 1,
                    "contractBaseInterest" : 0.15,
                    "expectHKDays" : 45,
                    "tradeAddPrice" : "2",
                    "weightedPrice" : 527.110038053065
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
                    "rukuDate" : "2017-06-29 00:00:00",
                    "rukuStatus" : "IN_STORAGE",
                    "rukuAmount" : "14475.4",
                    "rukuPrice" : "7508824.24",
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
                    "chukuDate" : "2017-07-14 00:00:00",
                    "chukuAmount" : "3035.42",
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
                    "locality" : "新沙港",
                    "chukuDate" : "2017-07-21 00:00:00",
                    "chukuAmount" : "1897.14",
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
                    "locality" : "新沙港",
                    "chukuDate" : "2017-07-25 00:00:00",
                    "chukuAmount" : "9542.84",
                    "chukuPrice" : "5030128.64"
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
                    "receiveCompanyId" : 123,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payMode"  : "ELEC_REMITTANCE",
                    "payAmount" : "5238000",
                    "capitalId" : 5,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-06-29 00:00:00",
                        "amount" : "999999",
                        "capitalId" : 5,
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
                    "receiveCompanyId" : 123,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payMode"  : "ELEC_REMITTANCE",
                    "payAmount" : "2270824.24",
                    "capitalId" : 5,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-07-10 00:00:00",
                        "amount" : "999999",
                        "capitalId" : 5,
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
                    "receiveCompanyId" : 31,
                    "payUsage" : "TRADE_DEFICIT",
                    "payMode"  : "ELEC_REMITTANCE",
                    "payAmount" : "28950.8",
                    "capitalId" : 5,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-07-10 00:00:00",
                        "amount" : "999999",
                        "capitalId" : 5,
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
    it(`付款单 - 新建付款单4 POST: /api/business/cang/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/cang/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "payDate" : "2017-07-10 00:00:00",
                    "receiveCompanyId" : 32,
                    "payUsage" : "FREIGNHT",
                    "payMode"  : "ELEC_REMITTANCE",
                    "payAmount" : "100000",
                    "capitalId" : 5,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-07-10 00:00:00",
                        "amount" : "999999",
                        "capitalId" : 5,
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
                    "useInterest" : "0.078",
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
                    "useInterest" : "0.078",
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
                    "huankuanDate" : "2017-07-26 00:00:00",
                    "huankuanMapList" : [
                        {
                            "jiekuanId": borrowId,
                            "principal" : "4000000",
                            "interest" : 15811.05,
                            "fee" : 4500,
                            "ccsPay" : false
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
                expect(res.body.data.huankuanDate).to.include('2017')
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
                    "huankuanDate" : "2017-07-27 00:00:00",
                    "huankuanMapList" : [
                        {
                            "jiekuanId": borrowId + 1,
                            "principal" : "2000000",
                            "interest" : 7905.53,
                            "fee" : 2250,
                            "ccsPay" : false
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
                expect(res.body.data.huankuanDate).to.include('2017')
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
                    "huikuanAmount" : "3480128.64",
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
                    "settleDate" : "2017-07-10 00:00:00",
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






    it(`费用单 - 新建费用单1 POST: /api/business/cang/${orderId}/fees`, function (done) {
        server.post(`/api/business/ying/${orderId}/fees`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "name" : "TAX_MOTRO_FREIGHT",
                    "settleDate" : "2017-07-10 00:00:00",
                    "amount" : "100000",
                    "quantity" : "14475.4",
                    "otherPartyId" : "32",
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
                    "openDate" : "2017-07-07 00:00:00",
                    "openCompanyId" : 123,
                    "receiverId" : 31,
                    "details" : [
                        {
                            "invoiceNumber" : "43211238",
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
                    "openDate" : "2017-07-14 00:00:00",
                    "openCompanyId" : 31,
                    "receiverId" : 5,
                    "details" : [
                        {
                            "invoiceNumber" : "43211239",
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

                expect(res.body.data.ccsProfile, '发运信息 - 瑞茂通总收益数据不对').to.equal(15304.40)

                expect(res.body.data.totalCSSIntypeNumber, '进项票信息 - CCS已收到进项数量数据不对').to.equal(14475.40)
                expect(res.body.data.totalCCSIntypeMoney, '进项票信息 - CCS已收到进项金额数据不对').to.equal(7537775.04)
                expect(res.body.data.cssUninTypeNum, '进项票信息 - CCS未收到进项数量数据不对').to.equal(0)
                expect(res.body.data.cssUninTypeMoney, '进项票信息 - CCS未收到进项金额数据不对').to.equal(100000.00)


                expect(res.body.data.purchaseCargoAmountOfMoney, '毛利 - 采购货款总额数据不对').to.equal(7508824.24)
                expect(res.body.data.saleCargoAmountOfMoney, '毛利 - 销售货款总额数据不对').to.equal(7630128.64)


                expect(res.body.data.tradeCompanyAddMoney, '毛利 - 毛利贸易公司加价数据不对').to.equal(28950.80)
                expect(res.body.data.vat, '毛利 - 应交增值税数据不对').to.equal(13418.90)
                expect(res.body.data.withoutTaxIncome, '毛利 - 不含税收入数据不对').to.equal(6521477.47)
                expect(res.body.data.withoutTaxCost, '毛利 - 不含税成本数据不对').to.equal(6442542.77)


                expect(res.body.data.additionalTax, '毛利 - 税金及附加数据不对').to.equal(1610.27)
                expect(res.body.data.stampDuty, '毛利 - 印花税数据不对').to.equal(4550.37)
                expect(res.body.data.opreationCrossProfile, '毛利 - 经营毛利数据不对').to.equal(-17316.03)
                expect(res.body.data.crossProfileATon, '毛利 - 单吨毛利数据不对').to.equal(-1.20)


                expect(res.body.data.upstreamCapitalPressure, '占压 - 上游资金占压数据不对').to.equal(0)
                expect(res.body.data.ownerCapitalPressure, '占压 - 自有资金占压数据不对').to.equal(0)

                expect(res.body.data.downstreamCapitalPressure, '占压 - 下游资金占压数据不对').to.equal(0)
                expect(res.body.data.cangPrePayment, '占压 - 预收款数据不对').to.equal(0)

                done()
            })
    })
})

