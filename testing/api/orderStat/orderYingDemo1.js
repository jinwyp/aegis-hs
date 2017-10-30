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




describe('应收订单 统计范例1', function () {

    let Authorization = ''
    let orderId = 12

    let unitId = 6

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
                name: "江西蒙晋能源贸易有限公司",
                shortName : '江西蒙晋',
                partyType : 3
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('江西蒙晋')
                done()
            })
    })

    it('新建参与商 POST: /api/parties', function (done) {
        server.post('/api/parties')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                name: "江苏华电物流有限公司",
                shortName : '江苏华电',
                partyType : 3
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('江苏华电')
                done()
            })
    })

    it('新建参与商 POST: /api/parties', function (done) {
        server.post('/api/parties')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                name: "创硕",
                shortName : '创硕',
                partyType : 3
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('创硕')
                done()
            })
    })

    it('新建参与商 POST: /api/parties', function (done) {
        server.post('/api/parties')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                name: "泰州立翔",
                shortName : '泰州立翔',
                partyType : 3
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('泰州立翔')
                done()
            })
    })



    it('应收订单 - 新建应收订单1 POST: /api/business/yings', function (done) {
        server.post('/api/business/yings')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "teamId" : 12,
                    "line" : "江西蒙晋 - 创硕 - 泰州立翔 - 那曲 - 江苏华电",
                    "cargoType" : "STEELS",
                    "upstreamSettleMode" : "ONE_PAPER_SETTLE",
                    "downstreamSettleMode" : "ONE_PAPER_SETTLE",
                    "mainAccounting" : 2,
                    "upstreamId" : 26,
                    "downstreamId" : 27,
                    "businessType" : "ying",
                    "orderPartyList" : [
                        {
                            "custType" : "TRAFFICKCER",
                            "customerId" : 28,
                            "position" : 1
                        },
                        {"custType" : "TRAFFICKCER", "customerId" : 29, "position" : 1}
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
                expect(res.body.data.line, '返回的数据data对象的line属性错误').to.include('那曲')

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
                expect(res.body.data.line, '返回的数据data对象的line属性错误').to.include('那曲')
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
                    "contractBaseInterest" : 0.15,
                    "expectHKDays" : 45,
                    "tradeAddPrice" : "1",
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
                    "fyDate" : "2017-04-01 00:00:00",
                    "fyAmount" : "8000",
                    "arriveStatus" : "ARRIVE",
                    "upstreamTrafficMode" : "SHIP",
                    "upstreamCars" : "",
                    "upstreamJHH" : "",
                    "upstreamShip" : "利电5",
                    "downstreamTrafficMode" : "SHIP",
                    "downstreamCars" : "",
                    "downstreamJHH" : "",
                    "downstreamShip" : "利电5",
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
                    "fyDate" : "2017-04-02 00:00:00",
                    "fyAmount" : "2000",
                    "arriveStatus" : "ARRIVE",
                    "upstreamTrafficMode" : "SHIP",
                    "upstreamCars" : "",
                    "upstreamJHH" : "",
                    "upstreamShip" : "利电5",
                    "downstreamTrafficMode" : "SHIP",
                    "downstreamCars" : "",
                    "downstreamJHH" : "",
                    "downstreamShip" : "利电5",
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
                    "fyDate" : "2017-04-03 00:00:00",
                    "fyAmount" : "6000",
                    "arriveStatus" : "ARRIVE",
                    "upstreamTrafficMode" : "SHIP",
                    "upstreamCars" : "",
                    "upstreamJHH" : "",
                    "upstreamShip" : "利电5",
                    "downstreamTrafficMode" : "SHIP",
                    "downstreamCars" : "",
                    "downstreamJHH" : "",
                    "downstreamShip" : "利电5",
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
    it(`发运单 - 新建发运单4 POST: /api/business/ying/${orderId}/fayuns`, function (done) {
        server.post(`/api/business/ying/${orderId}/fayuns`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "fyDate" : "2017-04-04 00:00:00",
                    "fyAmount" : "4000",
                    "arriveStatus" : "ARRIVE",
                    "upstreamTrafficMode" : "SHIP",
                    "upstreamCars" : "",
                    "upstreamJHH" : "",
                    "upstreamShip" : "利电5",
                    "downstreamTrafficMode" : "SHIP",
                    "downstreamCars" : "",
                    "downstreamJHH" : "",
                    "downstreamShip" : "利电5",
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
    it(`发运单 - 新建发运单5 POST: /api/business/ying/${orderId}/fayuns`, function (done) {
        server.post(`/api/business/ying/${orderId}/fayuns`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "fyDate" : "2017-04-05 00:00:00",
                    "fyAmount" : "3000",
                    "arriveStatus" : "ARRIVE",
                    "upstreamTrafficMode" : "SHIP",
                    "upstreamCars" : "",
                    "upstreamJHH" : "",
                    "upstreamShip" : "利电5",
                    "downstreamTrafficMode" : "SHIP",
                    "downstreamCars" : "",
                    "downstreamJHH" : "",
                    "downstreamShip" : "利电5",
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
    it(`发运单 - 新建发运单6 POST: /api/business/ying/${orderId}/fayuns`, function (done) {
        server.post(`/api/business/ying/${orderId}/fayuns`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "fyDate" : "2017-04-06 00:00:00",
                    "fyAmount" : "7000",
                    "arriveStatus" : "ARRIVE",
                    "upstreamTrafficMode" : "SHIP",
                    "upstreamCars" : "",
                    "upstreamJHH" : "",
                    "upstreamShip" : "利电5",
                    "downstreamTrafficMode" : "SHIP",
                    "downstreamCars" : "",
                    "downstreamJHH" : "",
                    "downstreamShip" : "利电5",
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
    it(`发运单 - 新建发运单7 POST: /api/business/ying/${orderId}/fayuns`, function (done) {
        server.post(`/api/business/ying/${orderId}/fayuns`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "fyDate" : "2017-04-07 00:00:00",
                    "fyAmount" : "10000",
                    "arriveStatus" : "ARRIVE",
                    "upstreamTrafficMode" : "SHIP",
                    "upstreamCars" : "",
                    "upstreamJHH" : "",
                    "upstreamShip" : "利电5",
                    "downstreamTrafficMode" : "SHIP",
                    "downstreamCars" : "",
                    "downstreamJHH" : "",
                    "downstreamShip" : "利电5",
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
    it(`发运单 - 新建发运单8 POST: /api/business/ying/${orderId}/fayuns`, function (done) {
        server.post(`/api/business/ying/${orderId}/fayuns`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "fyDate" : "2017-04-08 00:00:00",
                    "fyAmount" : "530",
                    "arriveStatus" : "ARRIVE",
                    "upstreamTrafficMode" : "SHIP",
                    "upstreamCars" : "",
                    "upstreamJHH" : "",
                    "upstreamShip" : "利电5",
                    "downstreamTrafficMode" : "SHIP",
                    "downstreamCars" : "",
                    "downstreamJHH" : "",
                    "downstreamShip" : "利电5",
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
})


