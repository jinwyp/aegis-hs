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




describe('应收订单', function () {

    let Authorization = ''

    before(function (done) {

        server.post('/api/login')
            .set(config.headers)
            .send({
                phone: "13022117050",
                password: "123456"
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                Authorization = res.body.data
                done()
            })

    });



    it('应收订单 - 新建应收订单1 POST: /api/yings', function (done) {
        server.post('/api/yings')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "deptId":2,
                "teamId":1,
                "line":"那曲 - 晋和 - 嘉瑞",
                "cargoType":"COAL",
                "upstreamSettleMode":"ONE_PAPER_SETTLE",
                "downstreamSettleMode":"ONE_PAPER_SETTLE",
                "mainAccounting":1,
                "upstreamId":2,
                "downstreamId":3
            })
            .expect('Content-Type', /json/)
            .expect(201)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.line).to.include('那曲')
                done()
            })
    })

    it('应收订单 - 新建应收订单2 POST: /api/yings', function (done) {
        server.post('/api/yings')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "deptId":2,
                "teamId":1,
                "line":"那曲 - 晋和 - 嘉瑞",
                "cargoType":"COAL",
                "upstreamSettleMode":"ONE_PAPER_SETTLE",
                "downstreamSettleMode":"ONE_PAPER_SETTLE",
                "mainAccounting":1,
                "upstreamId":2,
                "downstreamId":3,
                "orderPartyList":[
                    {"custType":"TRAFFICKCER","customerId":7},
                    {"custType":"ACCOUNTING_COMPANY","customerId":16}
                    ]
            })
            .expect('Content-Type', /json/)
            .expect(201)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.line).to.include('那曲')
                done()
            })
    })

    it('应收订单 - 新建应收订单3 POST: /api/yings', function (done) {
        server.post('/api/yings')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "deptId":2,
                "teamId":1,
                "line":"那曲 - 晋和 - 嘉瑞",
                "cargoType":"COAL",
                "upstreamSettleMode":"ONE_PAPER_SETTLE",
                "downstreamSettleMode":"ONE_PAPER_SETTLE",
                "mainAccounting":1,
                "upstreamId":2,
                "downstreamId":3
            })
            .expect('Content-Type', /json/)
            .expect(201)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.line).to.include('那曲')
                done()
            })
    })

    it('应收订单 - 新建应收订单4 非法输入不存在部门 deptId:9999 POST: /api/yings', function (done) {
        server.post('/api/yings')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "deptId" : 2,
                    "teamId" : 1,
                    "line" : "嘉瑞 - 那曲 - 山瑞",
                    "cargoType" : "COAL",
                    "upstreamSettleMode" : "ONE_PAPER_SETTLE",
                    "downstreamSettleMode" : "ONE_PAPER_SETTLE",
                    "mainAccounting" : 2,
                    "upstreamId" : 3,
                    "downstreamId" : 4,
                    "orderPartyList" : [
                        { "custType" : "UPSTREAM", "customerId" : 1},
                        { "custType" : "DOWNSTREAM", "customerId" : 2}
                    ]
                }
            )
            .expect('Content-Type', /json/)
            .expect(400)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.line).to.include('那曲')
                done()
            })
    })

    it('应收订单 - 新建应收订单5 非法输入不存在部门和团队 deptId:9999, teamId:99999 POST: /api/yings', function (done) {
        server.post('/api/yings')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "deptId" : 2,
                    "teamId" : 1,
                    "line" : "嘉瑞 - 那曲 - 山瑞",
                    "cargoType" : "COAL",
                    "upstreamSettleMode" : "ONE_PAPER_SETTLE",
                    "downstreamSettleMode" : "ONE_PAPER_SETTLE",
                    "mainAccounting" : 2,
                    "upstreamId" : 3,
                    "downstreamId" : 4,
                    "orderPartyList" : [
                        { "custType" : "UPSTREAM", "customerId" : 1},
                        { "custType" : "DOWNSTREAM", "customerId" : 2}
                    ]
                }
            )
            .expect('Content-Type', /json/)
            .expect(400)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.line).to.include('那曲')
                done()
            })
    })


    it('应收订单 - 新建应收订单6 非法输入不存在的公司ID mainAccounting:9999, upstreamId:99999, downstreamId:99999 POST: /api/yings', function (done) {
        server.post('/api/yings')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "deptId" : 2,
                    "teamId" : 1,
                    "line" : "嘉瑞 - 那曲 - 山瑞",
                    "cargoType" : "COAL",
                    "upstreamSettleMode" : "ONE_PAPER_SETTLE",
                    "downstreamSettleMode" : "ONE_PAPER_SETTLE",
                    "mainAccounting" : 99999,
                    "upstreamId" : 99999,
                    "downstreamId" : 99999,
                    "orderPartyList" : [
                        { "custType" : "UPSTREAM", "customerId" : 1},
                        { "custType" : "DOWNSTREAM", "customerId" : 2}
                    ]
                }
            )
            .expect('Content-Type', /json/)
            .expect(400)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.line).to.include('那曲')
                done()
            })
    })

    it('应收订单 - 新建应收订单7 非法输入不存在的参与方公司ID "custType" : "UPSTREAM", "customerId" : 9999 POST: /api/yings', function (done) {
        server.post('/api/yings')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "deptId" : 2,
                    "teamId" : 1,
                    "line" : "嘉瑞 - 那曲 - 山瑞",
                    "cargoType" : "COAL",
                    "upstreamSettleMode" : "ONE_PAPER_SETTLE",
                    "downstreamSettleMode" : "ONE_PAPER_SETTLE",
                    "mainAccounting" : 2,
                    "upstreamId" : 3,
                    "downstreamId" : 4,
                    "orderPartyList" : [
                        { "custType" : "UPSTREAM", "customerId" : 9999},
                        { "custType" : "DOWNSTREAM", "customerId" : 2}
                    ]
                }
            )
            .expect('Content-Type', /json/)
            .expect(400)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.line).to.include('那曲')
                done()
            })
    })

    it('应收订单 - 获取应收订单列表 GET: /api/yings?pageNo=1&pageSize=2', function (done) {
        server.get('/api/yings?pageNo=1&pageSize=2')
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.pageNo).to.equal(1)
                expect(res.body.data.pageSize).to.equal(2)
                expect(res.body.data.results.length, 'data.results 的返回记录数量错误').to.equal(2)

                done()
            })
    })

    it('应收订单 - 获取某个ID的应收订单信息 GET: /api/yings/1', function (done) {
        server.get('/api/yings/1')
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.line).to.include('那曲')
                done()
            })
    })

    it('应收订单 - 修改某个ID的应收订单名称 PUT: /api/yings/1', function (done) {
        server.put('/api/yings/1')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "id":1,
                "deptId":2,
                "teamId":2,
                "line":"那曲 - 晋和 - 嘉瑞",
                "cargoType":"COAL",
                "upstreamSettleMode":"ONE_PAPER_SETTLE",
                "downstreamSettleMode":"ONE_PAPER_SETTLE",
                "mainAccounting":1,
                "upstreamId":2,
                "downstreamId":3
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.equal(1)
                done()
            })
    })




    it('核算单元 - 新建核算单元1 POST: /api/ying/1/units', function (done) {
        server.post('/api/ying/1/units')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "hsMonth"           : "201709",
                "maxPrepayRate"        : "0.9",
                "unInvoicedRate"       : "0.7",
                "contractBaseInterest" : "0.2",
                "expectHKDays"         : "45",
                "tradeAddPrice"        : "0",
                "weightedPrice"        : "700"
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.hsMonth).to.include('201709')
                done()
            })
    })

    it('核算单元 - 新建核算单元2 POST: /api/ying/1/units', function (done) {
        server.post('/api/ying/1/units')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "hsMonth"           : "201710",
                "maxPrepayRate"        : 0.9,
                "unInvoicedRate"       : 0.7,
                "contractBaseInterest" : 0.2,
                "expectHKDays"         : 50,
                "tradeAddPrice"        : 0,
                "weightedPrice"        : 300
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.hsMonth).to.include('201710')
                done()
            })
    })

    it('核算单元 - 获取应收订单核算单元列表 GET: /api/ying/1/units?pageNo=1&pageSize=2', function (done) {
        server.get('/api/ying/1/units?pageNo=1&pageSize=2')
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.pageNo).to.equal(1)
                expect(res.body.data.pageSize).to.equal(2)
                expect(res.body.data.results.length, 'data.results 的返回记录数量错误').to.equal(2)
                done()
            })
    })

    it('核算单元 - 获取某个ID的核算单元信息 GET: /api/ying/1/units/1', function (done) {
        server.get('/api/ying/1/units/1')
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.hsMonth).to.include('201709')
                done()
            })
    })

    it('核算单元 - 修改某个ID的应收订单核算单元 PUT: /api/ying/1/units/1', function (done) {
        server.put('/api/ying/1/units/1')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "id"                   : 1,
                "hsMonth"           : "201709",
                "maxPrepayRate"        : 0.9,
                "unInvoicedRate"       : 0.7,
                "contractBaseInterest" : 0.2,
                "expectHKDays"         : 45,
                "tradeAddPrice"        : 200,
                "weightedPrice"        : 900
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.equal(1)
                done()
            })
    })




    it('发运单 - 新建发运单1 POST: /api/ying/1/fayuns', function (done) {
        server.post('/api/ying/1/fayuns')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "hsId"               : 1,
                "fyDate"                : "2017-09-02 00:00:00",
                "fyAmount"              : "2000",
                "arriveStatus"          : "ARRIVE",
                "upstreamTrafficMode"   : "MOTOR",
                "upstreamCars"          : 200,
                "upstreamJHH"           : "",
                "upstreamShip"          : "",
                "downstreamTrafficMode" : "SHIP",
                "downstreamCars"        : "",
                "downstreamJHH"         : "",
                "downstreamShip"        : "x1000",
                "orderId"               : 1
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.fyDate).to.include('2017')
                done()
            })
    })

    it('发运单 - 新建发运单2 POST: /api/ying/1/fayuns', function (done) {
        server.post('/api/ying/1/fayuns')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "hsId"               : 1,
                "fyDate"                : "2017-10-01 00:00:00",
                "fyAmount"              : "40000",
                "arriveStatus"          : "ARRIVE",
                "upstreamTrafficMode"   : "MOTOR",
                "upstreamCars"          : 200,
                "upstreamJHH"           : "",
                "upstreamShip"          : "",
                "downstreamTrafficMode" : "SHIP",
                "downstreamCars"        : "",
                "downstreamJHH"         : "",
                "downstreamShip"        : "x1003",
                "orderId"               : 1
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.fyDate).to.include('2017')
                done()
            })
    })

    it('发运单 - 获取应收订单发运单列表 GET: /api/ying/1/fayuns?pageNo=1&pageSize=2', function (done) {
        server.get('/api/ying/1/fayuns?pageNo=1&pageSize=2')
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.pageNo).to.equal(1)
                expect(res.body.data.pageSize).to.equal(2)
                expect(res.body.data.results.length, 'data.results 的返回记录数量错误').to.equal(2)
                done()
            })
    })

    it('发运单 - 获取某个ID的发运单信息 GET: /api/ying/1/fayuns/1', function (done) {
        server.get('/api/ying/1/fayuns/1')
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.fyDate).to.include('2017')
                done()
            })
    })

    it('发运单 - 修改某个ID的发运单 PUT: /api/ying/1/fayuns/1', function (done) {
        server.put('/api/ying/1/fayuns/1')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "id" : 1,
                    "hsId"               : 1,
                    "fyDate"                : "2017-09-02 00:00:00",
                    "arriveStatus"          : "ARRIVE",
                    "upstreamTrafficMode"   : "MOTOR",
                    "upstreamCars"          : 300,
                    "upstreamJHH"           : "",
                    "upstreamShip"          : "",
                    "downstreamTrafficMode" : "SHIP",
                    "downstreamCars"        : "",
                    "downstreamJHH"         : "",
                    "downstreamShip"        : "x3000",
                    "orderId"               : 1
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.equal(1)
                done()
            })
    })


})