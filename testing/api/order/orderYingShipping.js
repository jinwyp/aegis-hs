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




describe('应收订单 - 发运单: ', function () {

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





    it('发运单 - 新建发运单1 POST: /api/business/ying/1/fayuns', function (done) {
        server.post('/api/business/ying/1/fayuns')
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
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据data对象里面没有id字段').to.be.a('number')
                expect(res.body.data.fyDate).to.include('2017')
                done()
            })
    })

    it('发运单 - 新建发运单2 POST: /api/business/ying/1/fayuns', function (done) {
        server.post('/api/business/ying/1/fayuns')
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
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据data对象里面没有id字段').to.be.a('number')
                expect(res.body.data.fyDate).to.include('2017')
                done()
            })
    })

    it('发运单 - 新建发运单 非法输入不存在的核算单元ID hsId:99999, POST: /api/business/ying/1/fayuns', function (done) {
        server.post('/api/business/ying/1/fayuns')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "hsId"               : 99999,
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
            .expect(400)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是false 但实际不是false').to.equal(false)
                expect(res.body.data, '返回的数据data对象应该是undefined 但实际不是undefined').to.equal(undefined)
                done()
            })
    })

    it('发运单 - 获取应收订单发运单列表 GET: /api/business/ying/1/fayuns?pageNo=1&pageSize=2', function (done) {
        server.get('/api/business/ying/1/fayuns?pageNo=1&pageSize=2')
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.pageNo, 'pageNo值应该是1 但实际不是1').to.equal(1)
                expect(res.body.data.pageSize, 'pageSize值应该是2 但实际不是2').to.equal(2)
                expect(res.body.data.results.length, 'data.results 的返回记录数量错误').to.equal(2)
                done()
            })
    })

    it('发运单 - 获取某个ID的发运单信息 GET: /api/business/ying/1/fayuns/1', function (done) {
        server.get('/api/business/ying/1/fayuns/1')
            .set('Authorization', Authorization)
            .set(config.headers)
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

    it('发运单 - 修改某个ID的发运单 PUT: /api/business/ying/1/fayuns/1', function (done) {
        server.put('/api/business/ying/1/fayuns/1')
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
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的data值应该是1 但实际不是1').to.equal(1)
                done()
            })
    })

    it('发运单 - 删除某个ID的发运单 DELETE: /api/business/yings/1/fayuns/1', function (done) {
        server.put('/api/business/yings/1/fayuns/1')
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