
/**
 * Created by jin on 9/18/17.
 */


//Require the dev-dependencies
const expect = require('chai').expect
const should = require('chai').should()
const supertest = require('supertest')

const config = require('../testConfig')
const server = supertest(config.path.urlApi)




describe('仓押订单', function () {

    let Authorization = ''
    let orderId = config.order.getOrderCangId
    let delOrderId = config.order.delOrderCangId

    let unitId = 3
    let delUnitId = 5

    before(function (done) {

        server.post('/api/login')
            .set(config.headers)
            .send(config.user.user1)
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
                    "teamId":5,
                    "line":"山瑞 - 那曲 - 宁夏瑞茂通",
                    "cargoType":"COAL",
                    "upstreamSettleMode":"ONE_PAPER_SETTLE",
                    "downstreamSettleMode":"TWO_PAPER_SETTLE",
                    "mainAccounting" : 2,
                    "upstreamId" : 4,
                    "downstreamId" : 5,
                    "businessType" : "cang"
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

    it('仓押订单 - 新建仓押订单2 POST: /api/business/cangs', function (done) {
        server.post('/api/business/cangs')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "teamId" : 6,
                    "line" : "新疆瑞茂通 - 宁夏瑞茂通 - 和辉",
                    "cargoType" : "STEELS",
                    "upstreamSettleMode" : "TWO_PAPER_SETTLE",
                    "downstreamSettleMode" : "ONE_PAPER_SETTLE",
                    "mainAccounting" : 5,
                    "upstreamId" : 10,
                    "downstreamId" : 12,
                    "businessType" : "cang",
                    "orderPartyList" : [
                        {
                            "custType" : "UPSTREAM", "customerId" : 4
                        },
                        {
                            "custType"   : "DOWNSTREAM", "customerId" : 7
                        },
                        {
                            "custType" : "FUNDER", "customerId" : 17
                        },
                        {
                            "custType"   : "TRAFFICKCER", "customerId" : 9
                        },
                        {
                            "custType" : "TRANSPORT_COMPANY", "customerId" : 18
                        },
                        {
                            "custType"   : "ACCOUNTING_COMPANY", "customerId" : 19
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
                expect(res.body.data.line, '返回的数据data对象的line属性错误').to.include('和辉')
                done()
            })
    })

    it('仓押订单 - 新建仓押订单3 POST: /api/business/cangs', function (done) {
        server.post('/api/business/cangs')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "businessType":"cang",
                "teamId":1,
                "line":"那曲 - 晋和 - 嘉瑞",
                "cargoType":"COAL",
                "upstreamSettleMode":"ONE_PAPER_SETTLE",
                "downstreamSettleMode":"ONE_PAPER_SETTLE",
                "mainAccounting":1,
                "upstreamId":2,
                "downstreamId":3,
                "orderPartyList" : [
                    { "custType" : "UPSTREAM", "customerId" : 1},
                    { "custType" : "DOWNSTREAM", "customerId" : 2}
                ]
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据data对象里面没有id字段').to.be.a('number')
                expect(res.body.data.line, '返回的数据data对象的line属性错误').to.include('那曲')

                delOrderId = res.body.data.id
                done()
            })
    })

    it('仓押订单 - 获取仓押订单列表 GET: /api/business/cangs?pageNo=1&pageSize=2', function (done) {
        server.get('/api/business/cangs?pageNo=1&pageSize=2')
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
                expect(res.body.data.results, 'data.results 的返回记录数量错误').to.have.lengthOf(2)
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
                expect(res.body.data.line, '返回的数据data对象的line属性错误').to.include('那曲')
                done()
            })
    })

    it(`仓押订单 - 非法数据 修改某个ID的仓押订单 PUT: /api/business/cangs/${orderId}`, function (done) {
        console.log(`提示信息: 修改某个ID的仓押订单 PUT: /api/business/cangs/${orderId}`)
        server.put(`/api/business/cangs/${orderId}`)
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
                "downstreamId":3,
                "orderPartyList" : [
                    { "custType" : "UPSTREAM", "customerId" : 9999},
                    { "custType" : "DOWNSTREAM", "customerId" : 9999},
                    { "custType" : "TRAFFICKCER", "customerId":9999},
                    { "custType" : "ACCOUNTING_COMPANY", "customerId":9999}
                ]
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

    it(`仓押订单 - 删除某个ID的仓押订单 DELETE: /api/business/cangs/${delOrderId}`, function (done) {
        console.log(`提示信息: 删除某个ID的仓押订单 DELETE: /api/business/cangs/${delOrderId}`)
        server.delete(`/api/business/cangs/${delOrderId}`)
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

    it(`仓押订单 - 不能重复删除某个ID的仓押订单 DELETE: /api/business/cangs/${delOrderId}`, function (done) {
        console.log(`提示信息: 不能重复删除某个ID的仓押订单 DELETE: /api/business/cangs/${delOrderId}`)
        server.delete(`/api/business/cangs/${delOrderId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({})
            .expect('Content-Type', /json/)
            .expect(400)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是false 但实际不是false').to.equal(false)
                expect(res.body.data, '返回的数据data对象应该是undefined 但实际不是undefined').to.equal(undefined)
                done()
            })
    })




    it(`核算单元 - 新建核算单元1 POST: /api/business/cang/${orderId}/units`, function (done) {
        server.post(`/api/business/cang/${orderId}/units`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "orderId"           : orderId,
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
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据data对象里面没有id字段').to.be.a('number')
                expect(res.body.data.hsMonth).to.include('201709')

                unitId = res.body.data.id
                done()
            })
    })

    it(`核算单元 - 新建核算单元2 POST: /api/business/cang/${orderId}/units`, function (done) {
        server.post(`/api/business/cang/${orderId}/units`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "orderId"           : orderId,
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
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据data对象里面没有id字段').to.be.a('number')
                expect(res.body.data.hsMonth).to.include('201710')
                done()
            })
    })

    it(`核算单元 - 获取仓押订单核算单元列表 GET: /api/business/cang/${orderId}/units?pageNo=1&pageSize=2`, function (done) {
        server.get(`/api/business/cang/${orderId}/units?pageNo=1&pageSize=2`)
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
                expect(res.body.data.results, 'data.results 的返回记录数量错误').to.have.lengthOf(2)
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
                expect(res.body.data.hsMonth).to.include('201709')
                done()
            })
    })

    it(`核算单元 - 修改某个ID的仓押订单核算单元 PUT: /api/business/cang/${orderId}/units/${unitId}`, function (done) {
        server.put(`/api/business/cang/${orderId}/units/${unitId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "id"                   : 3,
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
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的data值应该是1 但实际不是1').to.equal(1)
                done()
            })
    })


    it(`核算单元 - 不能修改 不是自己订单下的核算单元 PUT: /api/business/cang/${orderId}/units/1`, function (done) {
        server.put(`/api/business/cang/${orderId}/units/1`)
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
            .expect(400)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是false 但实际不是false').to.equal(false)
                expect(res.body.data, '返回的数据data对象应该是undefined 但实际不是undefined').to.equal(undefined)
                done()
            })
    })

})

