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




describe('应收订单 统计范例2', function () {

    let Authorization = ''
    let orderId = 13

    let unitId = 7

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
                name: "郴州博太",
                shortName : '郴州博太',
                partyType : 3
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('郴州博太')
                done()
            })
    })

    it('新建参与商 POST: /api/parties', function (done) {
        server.post('/api/parties')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                name: "华润鲤鱼江",
                shortName : '华润鲤鱼江',
                partyType : 3
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('华润鲤鱼江')
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
                    "line" : "郴州博太 - 晋和 - 华润鲤鱼江",
                    "cargoType" : "COAL",
                    "upstreamSettleMode" : "ONE_PAPER_SETTLE",
                    "downstreamSettleMode" : "ONE_PAPER_SETTLE",
                    "mainAccounting" : 1,
                    "upstreamId" : 30,
                    "downstreamId" : 31,
                    "businessType" : "ying",
                    "orderPartyList" : []
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据data对象里面没有id字段').to.be.a('number')
                expect(res.body.data.line, '返回的数据data对象的line属性错误').to.include('晋和')

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
                expect(res.body.data.line, '返回的数据data对象的line属性错误').to.include('晋和')
                done()
            })
    })

    it(`核算单元 - 新建核算单元1 POST: /api/business/ying/${orderId}/units`, function (done) {
        server.post(`/api/business/ying/${orderId}/units`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsMonth" : "201706",
                    "maxPrepayRate" : 0.9,
                    "unInvoicedRate" : 0.7,
                    "contractBaseInterest" : 0.2,
                    "expectHKDays" : 45,
                    "tradeAddPrice" : 0,
                    "weightedPrice" : "612"
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
                expect(res.body.data.hsMonth, '返回的数据data对象的hsMonth属性错误').to.include('201706')
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
                    "fyDate" : "2017-06-20 00:00:00",
                    "fyAmount" : "1510.61",
                    "arriveStatus" : "ARRIVE",
                    "upstreamTrafficMode" : "RAIL",
                    "upstreamCars" : "",
                    "upstreamJHH" : "1000001",
                    "upstreamShip" : "",
                    "downstreamTrafficMode" : "MOTOR",
                    "downstreamCars" : "166",
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
                    "payDate" : "2017-07-06 00:00:00",
                    "receiveCompanyId" : 30,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payAmount" : "510000",
                    "capitalId" : 17,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-07-06 00:00:00",
                        "amount" : "510000",
                        "capitalId" : 17,
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
                    "hsId" : unitId,
                    "payDate" : "2017-08-10 00:00:00",
                    "receiveCompanyId" : 30,
                    "payUsage" : "FIAL_PAYMENT",
                    "payAmount" : "54294.93",
                    "orderId" : orderId,
                    "capitalId" : 1,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-08-10 00:00:00",
                        "amount" : "99999999",
                        "capitalId" : 1,
                        "useInterest" : "99999999",
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






    it(`还款单 - 新建还款单1 POST: /api/business/ying/${orderId}/huankuans`, function (done) {
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
                            "jiekuanId":"9",
                            "principal" : "510000",
                            "interest" : "1700.02",
                            "fee" : "560.00"
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
                expect(res.body.data.huankuankDate).to.include('2017')
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
                    "huikuanDate" : "2017-07-28 00:00:00",
                    "huikuanAmount" : "569968.26",
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
                    "settleDate" : "2017-07-17 00:00:00",
                    "amount" : "1510.61",
                    "money" : "569968.26",
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
                    "settleDate" : "2017-08-04 00:00:00",
                    "amount" : "99999",
                    "money" : "99999",
                    "discountType" : "NO_DISCOUNT",
                    // "discountInterest" : "9999",
                    // "discountDays" : "9999",
                    // "discountAmount" : "9999",
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
                    "openDate" : "2017-07-06 00:00:00",
                    "openCompanyId" : 30,
                    "receiverId" : 31,
                    "orderId" : orderId,
                    "details" : [
                        {
                            "invoiceNumber" : "66661234",
                            "cargoAmount" : "1000",
                            "taxRate" : "17",
                            "priceAndTax" : "340000"
                        },
                        {
                            "invoiceNumber" : "66661235",
                            "cargoAmount" : "367",
                            "taxRate" : "17",
                            "priceAndTax" : "170000"
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
                    "openDate" : "2017-08-04 00:00:00",
                    "openCompanyId" : 30,
                    "receiverId" : 31,
                    "orderId" : orderId,
                    "details" : [{
                        "invoiceNumber" : "66661236",
                        "cargoAmount" : "143.61",
                        "taxRate" : "17",
                        "priceAndTax" : "55994.95"
                    }]
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


