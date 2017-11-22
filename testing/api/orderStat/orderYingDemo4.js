
/**
 * Created by jin on 9/18/17.
 */


//Require the dev-dependencies
const expect = require('chai').expect
const should = require('chai').should()
const supertest = require('supertest')

const config = require('../testConfig')
const server = supertest(config.path.urlApi)




describe('应收订单 统计范例4 赵征提供 11.14', function () {

    let Authorization = ''
    let orderId = 15

    let unitId = 9
    let borrowId = 9


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








    it('应收订单 - 新建应收订单1 POST: /api/business/yings', function (done) {
        server.post('/api/business/yings')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "teamId" : 2,
                    "line" : "宁夏福星 - 林州林通 - 晋和 - 国电投临河",
                    "cargoType" : "COAL",
                    "upstreamSettleMode" : "ONE_PAPER_SETTLE",
                    "downstreamSettleMode" : "ONE_PAPER_SETTLE",
                    "mainAccounting" : 1,
                    "upstreamId" : 28,
                    "downstreamId" : 31,
                    "businessType" : "ying",
                    "orderPartyList" : [
                        {
                            "custType" : "TRAFFICKCER",
                            "customerId" : 30,
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
                    "orderId" : orderId,
                    "hsMonth" : "201708",
                    "maxPrepayRate" : 0.9,
                    "unInvoicedRate" : 0.7,
                    "contractBaseInterest" : 0.2,
                    "expectHKDays" : 45,
                    "tradeAddPrice" : 2,
                    "weightedPrice" : "140"
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据data对象里面没有id字段').to.be.a('number')
                expect(res.body.data.hsMonth, '返回的数据data对象的hsMonth属性错误').to.include('201708')

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
                expect(res.body.data.hsMonth, '返回的数据data对象的hsMonth属性错误').to.include('201708')
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
                    "fyDate" : "2017-08-10 00:00:00",
                    "fyAmount" : "4000",
                    "arriveStatus" : "ARRIVE",
                    "upstreamTrafficMode" : "MOTOR",
                    "upstreamCars" : "100",
                    "upstreamJHH" : "",
                    "upstreamShip" : "",
                    "downstreamTrafficMode" : "MOTOR",
                    "downstreamCars" : "100",
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
    it(`发运单 - 新建发运单2 POST: /api/business/ying/${orderId}/fayuns`, function (done) {
        server.post(`/api/business/ying/${orderId}/fayuns`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "fyDate" : "2017-08-25 00:00:00",
                    "fyAmount" : "4000",
                    "arriveStatus" : "ARRIVE",
                    "upstreamTrafficMode" : "MOTOR",
                    "upstreamCars" : "100",
                    "upstreamJHH" : "",
                    "upstreamShip" : "",
                    "downstreamTrafficMode" : "MOTOR",
                    "downstreamCars" : "100",
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
    it(`发运单 - 新建发运单3 POST: /api/business/ying/${orderId}/fayuns`, function (done) {
        server.post(`/api/business/ying/${orderId}/fayuns`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "fyDate" : "2017-08-28 00:00:00",
                    "fyAmount" : "2273",
                    "arriveStatus" : "ARRIVE",
                    "upstreamTrafficMode" : "MOTOR",
                    "upstreamCars" : "60",
                    "upstreamJHH" : "",
                    "upstreamShip" : "",
                    "downstreamTrafficMode" : "MOTOR",
                    "downstreamCars" : "60",
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
                    "payDate" : "2017-08-10 00:00:00",
                    "receiveCompanyId" : 28,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payAmount" : "100000",
                    "capitalId" : 17,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-08-10 00:00:00",
                        "amount" : "100000",
                        "capitalId" : 17,
                        "useInterest" : 0.098,
                        "useDays" : "45"
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
                    "payDate" : "2017-08-11 00:00:00",
                    "receiveCompanyId" : 28,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payAmount" : "30000",
                    "capitalId" : 1,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-08-11 00:00:00",
                        "amount" : "999999999",
                        "capitalId" : 1,
                        "useInterest" : 1,
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
    it(`付款单 - 新建付款单3 POST: /api/business/ying/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "payDate" : "2017-08-14 00:00:00",
                    "receiveCompanyId" : 28,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payAmount" : "160000",
                    "capitalId" : 17,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-08-14 00:00:00",
                        "amount" : "160000",
                        "capitalId" : 17,
                        "useInterest" : 0.098,
                        "useDays" : "40"
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
                    "payDate" : "2017-08-17 00:00:00",
                    "receiveCompanyId" : 28,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payAmount" : "190000",
                    "capitalId" : 17,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-08-17 00:00:00",
                        "amount" : "190000",
                        "capitalId" : 17,
                        "useInterest" : 0.098,
                        "useDays" : "40"
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
    it(`付款单 - 新建付款单5 POST: /api/business/ying/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "payDate" : "2017-08-18 00:00:00",
                    "receiveCompanyId" : 28,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payAmount" : "110000",
                    "capitalId" : 1,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-08-18 00:00:00",
                        "amount" : "999999999",
                        "capitalId" : 1,
                        "useInterest" : 1,
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
    it(`付款单 - 新建付款单6 POST: /api/business/ying/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "payDate" : "2017-08-22 00:00:00",
                    "receiveCompanyId" : 28,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payAmount" : "190000",
                    "capitalId" : 17,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-08-22 00:00:00",
                        "amount" : "190000",
                        "capitalId" : 17,
                        "useInterest" : 0.098,
                        "useDays" : "40"
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
    it(`付款单 - 新建付款单7 POST: /api/business/ying/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "payDate" : "2017-08-24 00:00:00",
                    "receiveCompanyId" : 28,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payAmount" : "140000",
                    "capitalId" : 17,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-08-24 00:00:00",
                        "amount" : "140000",
                        "capitalId" : 17,
                        "useInterest" : 0.098,
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
    it(`付款单 - 新建付款单8 POST: /api/business/ying/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "payDate" : "2017-08-28 00:00:00",
                    "receiveCompanyId" : 28,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payAmount" : "260000",
                    "capitalId" : 17,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-08-24 00:00:00",
                        "amount" : "260000",
                        "capitalId" : 17,
                        "useInterest" : 0.098,
                        "useDays" : "30"
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
    it(`付款单 - 新建付款单9 POST: /api/business/ying/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "payDate" : "2017-09-04 00:00:00",
                    "receiveCompanyId" : 28,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payAmount" : "110000",
                    "capitalId" : 17,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-09-04 00:00:00",
                        "amount" : "110000",
                        "capitalId" : 17,
                        "useInterest" : 0.098,
                        "useDays" : "20"
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
    it(`付款单 - 新建付款单10 POST: /api/business/ying/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "payDate" : "2017-10-17 00:00:00",
                    "receiveCompanyId" : 28,
                    "payUsage" : "FIAL_PAYMENT",
                    "payAmount" : "79000",
                    "capitalId" : 1,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-10-17 00:00:00",
                        "amount" : "999999999",
                        "capitalId" : 1,
                        "useInterest" : 1,
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
    it(`付款单 - 新建付款单11 POST: /api/business/ying/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "payDate" : "2017-11-01 00:00:00",
                    "receiveCompanyId" : 28,
                    "payUsage" : "FIAL_PAYMENT",
                    "payAmount" : "16039.78",
                    "capitalId" : 1,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-11-01 00:00:00",
                        "amount" : "999999999",
                        "capitalId" : 1,
                        "useInterest" : 1,
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
    it(`付款单 - 新建付款单12 POST: /api/business/ying/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "payDate" : "2017-11-01 00:00:00",
                    "receiveCompanyId" : 28,
                    "payUsage" : "TRADE_DEFICIT",
                    "payAmount" : "20546",
                    "capitalId" : 1,
                    "jiekuan" : {
                        "orderId" : orderId,
                        "hsId" : unitId,
                        "jiekuanDate" : "2017-11-01 00:00:00",
                        "amount" : "999999999",
                        "capitalId" : 1,
                        "useInterest" : 1,
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
                    "huankuankDate" : "2017-09-26 00:00:00",
                    "huankuanMapList" : [
                        {
                            "jiekuanId": borrowId,
                            "principal" : "100000",
                            "interest" : "979.160000000003",
                            "fee" : "249"
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
    it(`还款单 - 新建还款单2 POST: /api/business/ying/${orderId}/huankuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/huankuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "orderId" : orderId,
                    "promise" : true,
                    "huankuankDate" : "2017-09-26 00:00:00",
                    "huankuanMapList" : [
                        {
                            "jiekuanId": borrowId + 1,
                            "principal" : "160000",
                            "interest" : "1337.76000000001",
                            "fee" : "356"
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
    it(`还款单 - 新建还款单3 POST: /api/business/ying/${orderId}/huankuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/huankuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "orderId" : orderId,
                    "promise" : true,
                    "huankuankDate" : "2017-09-26 00:00:00",
                    "huankuanMapList" : [
                        {
                            "jiekuanId": borrowId + 2,
                            "principal" : "190000",
                            "interest" : "1583.29999999999",
                            "fee" : "421"
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
    it(`还款单 - 新建还款单4 POST: /api/business/ying/${orderId}/huankuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/huankuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "orderId" : orderId,
                    "promise" : true,
                    "huankuankDate" : "2017-09-26 00:00:00",
                    "huankuanMapList" : [
                        {
                            "jiekuanId": borrowId + 3,
                            "principal" : "190000",
                            "interest" : "1293.04000000001",
                            "fee" : "269"
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
    it(`还款单 - 新建还款单5 POST: /api/business/ying/${orderId}/huankuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/huankuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "orderId" : orderId,
                    "promise" : true,
                    "huankuankDate" : "2017-09-26 00:00:00",
                    "huankuanMapList" : [
                        {
                            "jiekuanId": borrowId + 4,
                            "principal" : "140000",
                            "interest" : "933.329999999987",
                            "fee" : "215"
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
    it(`还款单 - 新建还款单6 POST: /api/business/ying/${orderId}/huankuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/huankuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "orderId" : orderId,
                    "promise" : true,
                    "huankuankDate" : "2017-09-26 00:00:00",
                    "huankuanMapList" : [
                        {
                            "jiekuanId": borrowId + 5,
                            "principal" : "260000",
                            "interest" : "1361.42000000001",
                            "fee" : "382"
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
    it(`还款单 - 新建还款单7 POST: /api/business/ying/${orderId}/huankuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/huankuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "orderId" : orderId,
                    "promise" : true,
                    "huankuankDate" : "2017-09-26 00:00:00",
                    "huankuanMapList" : [
                        {
                            "jiekuanId": borrowId + 6,
                            "principal" : "110000",
                            "interest" : "470.539999999994",
                            "fee" : "121"
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
                    "huikuanDate" : "2017-09-28 00:00:00",
                    "huikuanAmount" : "1000000",
                    "huikuanUsage" : "PAYMENT_FOR_GOODS",
                    "huikuanMode" : "BANK_ACCEPTANCE",
                    "huikuanBankPaper" : true,
                    "huikuanBankPaperDate" : "2017-09-28 00:00:00",
                    "huikuanBankDiscount" : true,
                    "huikuanBankDiscountRate" : 0.05,
                    "huikuanBankPaperExpire" : "2017-10-28 00:00:00",
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
    it(`回款单 - 新建回款单2 POST: /api/business/ying/${orderId}/huikuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/huikuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "huikuanDate" : "2017-10-15 00:00:00",
                    "huikuanAmount" : "438220",
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
                    "settleDate" : "2017-09-04 00:00:00",
                    "amount" : "10273",
                    "money" : "1438220",
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
                    "settleDate" : "2017-10-15 00:00:00",
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






    it(`保证金 - 新建保证金1 POST: /api/business/ying/${orderId}/bails`, function (done) {
        server.post(`/api/business/ying/${orderId}/bails`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "bailDate" : "2017-08-01 00:00:00",
                    "bailType" : "RECV_UP",
                    "bailAmount" : "100000",
                    "orderId" : orderId,
                    "openCompanyId" : 28,
                    "receiverId" : 1
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.bailDate , '返回的数据 res.body.data 里面日期字段错误').to.include('2017')

                done()
            })
    })
    it(`保证金 - 新建保证金2 POST: /api/business/ying/${orderId}/bails`, function (done) {
        server.post(`/api/business/ying/${orderId}/bails`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "bailDate" : "2017-08-02 00:00:00",
                    "bailType" : "PAY_DOWN",
                    "bailAmount" : "100000",
                    "orderId" : orderId,
                    "openCompanyId" : 1,
                    "receiverId" : 31
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.bailDate , '返回的数据 res.body.data 里面日期字段错误').to.include('2017')

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
                    "openDate" : "2017-09-04 00:00:00",
                    "openCompanyId" : 28,
                    "receiverId" : 30,
                    "orderId" : orderId,
                    "details" : [
                        {
                            "invoiceNumber" : "43211234",
                            "cargoAmount" : "9450",
                            "taxRate" : "0.17",
                            "priceAndTax" : "1290000"
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
                    "openDate" : "2017-09-15 00:00:00",
                    "openCompanyId" : 30,
                    "receiverId" : 1,
                    "orderId" : orderId,
                    "details" : [
                        {
                            "invoiceNumber" : "43211235",
                            "cargoAmount" : "9450",
                            "taxRate" : "0.17",
                            "priceAndTax" : "1308900"
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







    it(`统计验证 - 获取核算月统计信息 GET: /api/business/ying/${orderId}/analysis/${unitId}`, function (done) {
        server.get(`/api/business/ying/${orderId}/analysis/${unitId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)


                expect(res.body.data.ccsProfile, '发运信息 - 瑞茂通总收益数据不对').to.equal(24675.67)
                expect(res.body.data.totalCSSIntypeNumber, '进项票信息 - CCS已收到进项数量数据不对').to.equal(9450)
                expect(res.body.data.totalCCSIntypeMoney, '进项票信息 - CCS已收到进项金额数据不对').to.equal(1308900)
                expect(res.body.data.cssUninTypeNum, '进项票信息 - CCS未收到进项数量数据不对').to.equal(823)
                expect(res.body.data.cssUninTypeMoney, '进项票信息 - CCS未收到进项金额数据不对').to.equal(125190.33)


                expect(res.body.data.settleGrossProfileNum, '毛利 - 结算量数据不对').to.equal(10273.00)
                expect(res.body.data.saleIncludeTaxTotalAmount, '毛利 - 销售含税总额数据不对').to.equal(1438220.00)
                expect(res.body.data.purchaseIncludeTaxTotalAmount, '毛利 - 采购含税总额数据不对').to.equal(1413544.33)

                expect(res.body.data.tradeCompanyAddMoney, '毛利 - 贸易公司加价数据不对').to.equal(20546.00)
                expect(res.body.data.vat, '毛利 - 应交增值税数据不对').to.equal(600.04)
                expect(res.body.data.withoutTaxIncome, '毛利 - 不含税收入数据不对').to.equal(1229247.86)
                expect(res.body.data.withoutTaxCost, '毛利 - 不含税成本数据不对').to.equal(1225718.23)
                expect(res.body.data.additionalTax, '毛利 - 税金及附加数据不对').to.equal(72.00)
                expect(res.body.data.stampDuty, '毛利 - 印花税数据不对').to.equal(861.69)
                expect(res.body.data.opreationCrossProfile, '毛利 - 经营毛利数据不对').to.equal(2595.94)
                expect(res.body.data.crossProfileATon, '毛利 - 单吨毛利数据不对').to.equal(0.25)

                expect(res.body.data.upstreamCapitalPressure, '占压 - 上游资金占压数据不对').to.equal(-120546.00)
                expect(res.body.data.downstreamCapitalPressure, '占压 - 下游资金占压数据不对').to.equal(100000.00)
                expect(res.body.data.yingPrePayment, '占压 - 预收款数据不对').to.equal(-100000.00)

                done()
            })
    })
})

