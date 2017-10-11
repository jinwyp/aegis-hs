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
    let orderId = ''

    before(function (done) {

        server.post('/api/login')
            .set(config.headers)
            .send({
                phone: "13564568304",
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



    it('应收订单 - 新建应收订单11 POST: /api/yings', function (done) {
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
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.line).to.include('那曲')
                orderId = res.body.data.id
                done()
            })
    })

    it('应收订单 - 转移订单给另一个财务人员 POST: /api/yings/' + orderId + '/to/4', function (done) {
        server.post('/api/yings/' + orderId + '/to/4')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({})
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



})