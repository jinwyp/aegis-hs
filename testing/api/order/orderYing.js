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




describe('应收订单', function () {

    let Authorization = ''
    let orderId = config.order.getOrderYingId
    let delOrderId = config.order.delOrderYingId

    let unitId = 1
    let delUnitId = 3

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




    it('应收订单 - 新建应收订单1 POST: /api/business/yings', function (done) {
        server.post('/api/business/yings')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "businessType":"ying",
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

    it('应收订单 - 新建应收订单2 POST: /api/business/yings', function (done) {
        server.post('/api/business/yings')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "businessType":"ying",
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

    it('应收订单 - 新建应收订单3 POST: /api/business/yings', function (done) {
        server.post('/api/business/yings')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "businessType":"ying",
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

    it('应收订单 - 新建应收订单 非法输入 不存在部门和团队 teamId:99999 POST: /api/business/yings', function (done) {
        server.post('/api/business/yings')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "businessType":"ying",
                    "teamId" : 99999,
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
                expect(res.body.success, 'success属性值应该是false 但实际不是false').to.equal(false)
                expect(res.body.data, '返回的数据data对象应该是undefined 但实际不是undefined').to.equal(undefined)
                done()
            })
    })

    it('应收订单 - 新建应收订单 非法输入 不存在的公司ID mainAccounting:9999, upstreamId:99999, downstreamId:99999 POST: /api/business/yings', function (done) {
        server.post('/api/business/yings')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "businessType":"ying",
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
                expect(res.body.success, 'success属性值应该是false 但实际不是false').to.equal(false)
                expect(res.body.data, '返回的数据data对象应该是undefined 但实际不是undefined').to.equal(undefined)
                done()
            })
    })

    it('应收订单 - 新建应收订单 非法输入 不存在的参与方公司ID "custType" : "UPSTREAM", "customerId" : 9999 POST: /api/business/yings', function (done) {
        server.post('/api/business/yings')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "businessType":"ying",
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
                        { "custType" : "DOWNSTREAM", "customerId" : 9999},
                        { "custType" : "TRAFFICKCER", "customerId":9999},
                        { "custType" : "ACCOUNTING_COMPANY", "customerId":9999}
                    ]
                }
            )
            .expect('Content-Type', /json/)
            .expect(400)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是false 但实际不是false').to.equal(false)
                expect(res.body.data, '返回的数据data对象应该是undefined 但实际不是undefined').to.equal(undefined)
                done()
            })
    })

    it('应收订单 - 获取应收订单列表 GET: /api/business/yings?pageNo=1&pageSize=2', function (done) {
        server.get('/api/business/yings?pageNo=1&pageSize=2')
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

    it(`应收订单 - 修改某个ID的应收订单 PUT: /api/business/yings/${orderId}`, function (done) {
        server.put(`/api/business/yings/${orderId}`)
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
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data值应该是1 但实际不是1').to.equal(1)
                done()
            })
    })

    it(`应收订单 - 删除某个ID的应收订单 DELETE: /api/business/yings/${delOrderId}`, function (done) {
        console.log(`提示信息: 删除某个ID的应收订单 DELETE: /api/business/yings/` + delOrderId)
        server.delete(`/api/business/yings/` + delOrderId)
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

    it(`应收订单 - 不能重复删除某个ID的应收订单 DELETE: /api/business/yings/${delOrderId}`, function (done) {
        console.log(`提示信息: 不能重复删除某个ID的应收订单 DELETE: /api/business/yings/${delOrderId}`)
        server.delete(`/api/business/yings/${delOrderId}`)
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




    it(`核算单元 - 新建核算单元1 POST: /api/business/ying/${orderId}/units`, function (done) {
        server.post(`/api/business/ying/${orderId}/units`)
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
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据data对象里面没有id字段').to.be.a('number')
                expect(res.body.data.hsMonth, '返回的数据data对象的hsMonth属性错误').to.include('201709')

                unitId = res.body.data.id
                done()
            })
    })

    it(`核算单元 - 新建核算单元2 POST: /api/business/ying/${orderId}/units`, function (done) {
        server.post(`/api/business/ying/${orderId}/units`)
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
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据data对象里面没有id字段').to.be.a('number')
                expect(res.body.data.hsMonth, '返回的数据data对象的hsMonth属性错误').to.include('201710')
                done()
            })
    })

    it(`核算单元 - 新建核算单元3 POST: /api/business/ying/${orderId}/units`, function (done) {
        server.post(`/api/business/ying/${orderId}/units`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "hsMonth"           : "201711",
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
                expect(res.body.data.hsMonth, '返回的数据data对象的hsMonth属性错误').to.include('201710')
                done()
            })
    })

    it(`核算单元 - 获取应收订单核算单元列表 GET: /api/business/ying/${orderId}/units?pageNo=1&pageSize=2`, function (done) {
        server.get(`/api/business/ying/${orderId}/units?pageNo=1&pageSize=2`)
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
                expect(res.body.data.hsMonth, '返回的数据data对象的hsMonth属性错误').to.include('201709')
                done()
            })
    })

    it(`核算单元 - 修改某个ID的应收订单核算单元 PUT: /api/business/ying/${orderId}/units/${unitId}`, function (done) {
        server.put(`/api/business/ying/${orderId}/units/${unitId}`)
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
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的data值应该是1 但实际不是1').to.equal(1)
                done()
            })
    })




})




describe('应收订单', function () {

    let Authorization = ''
    let orderId = 7
    let orderId2 = 8

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
    });

    it('移交订单权限 - 新建应收订单7 POST: /api/business/yings', function (done) {
        server.post('/api/business/yings')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "businessType":"ying",
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
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.line, '返回的数据data对象的line属性错误').to.include('那曲')
                orderId = res.body.data.id
                done()
            })
    })

    it('移交订单权限 - 新建应收订单8 POST: /api/business/yings', function (done) {
        server.post('/api/business/yings')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "businessType":"ying",
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
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.line, '返回的数据data对象的line属性错误').to.include('那曲')
                done()
            })
    })

    it(`移交订单权限 - 转移订单给另一个财务人员(13564568301) POST: /api/business/yings/${orderId}/to/3`, function (done) {
        console.log(`提示信息: 转移订单给另一个财务人员(13564568301) POST: /api/business/yings/${orderId}/to/3`)
        server.post(`/api/business/yings/${orderId}/to/3`)
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

    it(`移交订单权限 - 转移订单后不能在查看原订单 GET: /api/business/yings/${orderId}`, function (done) {
        server.get(`/api/business/yings/${orderId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(400)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是false 但实际不是false').to.equal(false)
                expect(res.body.data, '返回的数据data对象应该是undefined 但实际不是undefined').to.equal(undefined)
                done()
            })
    })

    it('移交订单权限 - 不是自己的订单转移给另一个财务人员(13564568301) POST: /api/business/yings/1/to/3', function (done) {
        console.log(`提示信息: 转移订单给另一个财务人员(13564568301) POST: /api/business/yings/1/to/3`)
        server.post('/api/business/yings/1/to/3')
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

    it(`移交订单权限 - 转移订单给不存在的财务人员 POST: /api/business/yings/${orderId2}/to/99999`, function (done) {
        console.log(`提示信息: 转移订单给另一个财务人员(13564568301) POST: /api/business/yings/${orderId2}/to/99999`)
        server.post(`/api/business/yings/${orderId2}/to/99999`)
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

})

