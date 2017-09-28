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



    it('付款单 - 新建付款单1 POST: /api/ying/1/fukuans', function (done) {
        server.post('/api/ying/1/fukuans')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "payDate" : "2017-09-01 00:00:00",
                    "receiveCompanyId" : 1,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payAmount" : "2200",
                    "payMode" : "ELEC_REMITTANCE",
                    "capitalId" : 1,
                    "useInterest" : "",
                    "useDays" : "",
                    "orderId" : 1
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id).to.be.a('number')
                expect(res.body.data.payDate).to.include('2017')
                done()
            })
    })

    it('付款单 - 新建付款单2 POST: /api/ying/1/fukuans', function (done) {
        server.post('/api/ying/1/fukuans')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 2,
                    "payDate" : "2017-09-27 00:00:00",
                    "receiveCompanyId" : 3,
                    "payUsage" : "DEPOSITECASH",
                    "payAmount" : "6000",
                    "payMode" : "CASH",
                    "capitalId" : 5,
                    "useInterest" : "0.4",
                    "useDays" : "30",
                    "orderId" : 1
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id).to.be.a('number')
                expect(res.body.data.payDate).to.include('2017')
                done()
            })
    })

    it('付款单 - 获取应收订单付款单列表 GET: /api/ying/1/fukuans?pageNo=1&pageSize=2', function (done) {
        server.get('/api/ying/1/fukuans?pageNo=1&pageSize=2')
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

    it('付款单 - 获取某个ID的付款单信息 GET: /api/ying/1/fukuans/1', function (done) {
        server.get('/api/ying/1/fukuans/1')
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id).to.be.a('number')
                expect(res.body.data.payDate).to.include('2017')
                done()
            })
    })

    it('付款单 - 修改某个ID的付款单 PUT: /api/ying/1/fukuans/1', function (done) {
        server.put('/api/ying/1/fukuans/1')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "id" : 1,
                    "hsId" : 2,
                    "receiveCompanyId" : 3,
                    "payUsage" : "DEPOSITECASH",
                    "payMode" : "CASH",
                    "capitalId" : 5,
                    "useInterest" : "0.4",
                    "useDays" : "60",
                    "orderId" : 1
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