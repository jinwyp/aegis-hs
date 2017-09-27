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



    it('新建应收订单1 POST: /api/yings', function (done) {
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
                expect(res.body.data.id).to.be.a('number')
                expect(res.body.data.line).to.include('那曲')
                done()
            })
    })


    it('新建应收订单2 POST: /api/yings', function (done) {
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
                expect(res.body.data.id).to.be.a('number')
                expect(res.body.data.line).to.include('那曲')
                done()
            })
    })



    it('获取应收订单列表 GET: /api/yings?pageNo=1&pageSize=2', function (done) {
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
                expect(res.body.data.results.length).to.equal(2)
                done()
            })
    })




    it('获取某个ID的应收订单信息 GET: /api/yings/1', function (done) {
        server.get('/api/yings/1')
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id).to.be.a('number')
                expect(res.body.data.line).to.include('那曲')
                done()
            })
    })


    it('修改某个ID的应收订单名称 PUT: /api/yings/1', function (done) {
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




    it('新建核算单元1 POST: /api/ying/1/units', function (done) {
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
                expect(res.body.data.id).to.be.a('number')
                expect(res.body.data.hsMonth).to.include('201709')
                done()
            })
    })

    it('新建核算单元2 POST: /api/ying/1/units', function (done) {
        server.post('/api/ying/1/units')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "hsMonth"           : "201710",
                "maxPrepayRate"        : "0.9",
                "unInvoicedRate"       : "0.7",
                "contractBaseInterest" : "0.2",
                "expectHKDays"         : "50",
                "tradeAddPrice"        : "0",
                "weightedPrice"        : "500"
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id).to.be.a('number')
                expect(res.body.data.hsMonth).to.include('201710')
                done()
            })
    })



})