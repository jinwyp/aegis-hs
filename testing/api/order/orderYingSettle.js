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




describe('应收订单 - 结算 : ', function () {

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



    it('上游结算单 - 新建上游结算单1 POST: /api/ying/1/settleupstream', function (done) {
        server.post('/api/ying/1/settleupstream')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "tempSettleType" : "upstream",
                    "hsId" : 1,
                    "settleDate" : "2017-09-02 00:00:00",
                    "amount" : "2000",
                    "money" : "1000",
                    "discountType" : "RATE_DISCOUNT",
                    "discountInterest" : "0.2",
                    "discountDays" : "30",
                    "discountAmount" : "1000",
                    "orderId" : 1
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.settleDate).to.include('2017')
                done()
            })
    })

    it('上游结算单 - 新建上游结算单2 POST: /api/ying/1/settleupstream', function (done) {
        server.post('/api/ying/1/settleupstream')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "tempSettleType" : "upstream",
                    "hsId" : 2,
                    "settleDate" : "2017-09-30 00:00:00",
                    "amount" : "5000",
                    "money" : "500",
                    "discountType" : "CASH_DISCOUNT",
                    "discountInterest" : "0.3",
                    "discountDays" : "60",
                    "discountAmount" : "20",
                    "orderId" : 1
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.settleDate).to.include('2017')
                done()
            })
    })

    it('上游结算单 - 获取应收订单上游结算单列表 GET: /api/ying/1/settleupstream?pageNo=1&pageSize=2', function (done) {
        server.get('/api/ying/1/settleupstream?pageNo=1&pageSize=2')
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

    it('上游结算单 - 获取某个ID的上游结算单信息 GET: /api/ying/1/settleupstream/1', function (done) {
        server.get('/api/ying/1/settleupstream/1')
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.settleDate).to.include('2017')
                done()
            })
    })

    it('上游结算单 - 修改某个ID的上游结算单 PUT: /api/ying/1/settleupstream/1', function (done) {
        server.put('/api/ying/1/settleupstream/1')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "tempSettleType" : "upstream",
                    "hsId" : 1,
                    "settleDate" : "2017-09-02 00:00:00",
                    "amount" : 2000,
                    "money" : 1000,
                    "discountType" : "RATE_DISCOUNT",
                    "discountInterest" : 0.2,
                    "discountDays" : 30,
                    "discountAmount" : "5000",
                    "orderId" : 1,
                    "id" : 1
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




    it('下游结算单 - 新建下游结算单1 POST: /api/ying/1/settledownstream', function (done) {
        server.post('/api/ying/1/settledownstream')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "tempSettleType" : "downstream",
                    "hsId" : 1,
                    "settleDate" : "2017-09-02 00:00:00",
                    "amount" : "2000",
                    "money" : "100",
                    "discountType" : -1,
                    "discountInterest" : "",
                    "discountDays" : "",
                    "discountAmount" : "",
                    "settleGap" : "100",
                    "trafficCompanyId" : -1,
                    "orderId" : 1
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.settleDate).to.include('2017')
                done()
            })
    })

    it('下游结算单 - 新建下游结算单2 POST: /api/ying/1/settledownstream', function (done) {
        server.post('/api/ying/1/settledownstream')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "tempSettleType" : "downstream",
                    "hsId" : 1,
                    "settleDate" : "2017-09-02 00:00:00",
                    "amount" : "3000",
                    "money" : "100",
                    "discountType" : -1,
                    "discountInterest" : "",
                    "discountDays" : "",
                    "discountAmount" : "",
                    "settleGap" : "100",
                    "trafficCompanyId" : -1,
                    "orderId" : 1
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.settleDate).to.include('2017')
                done()
            })
    })

    it('下游结算单 - 获取应收订单下游结算单列表 GET: /api/ying/1/settledownstream?pageNo=1&pageSize=2', function (done) {
        server.get('/api/ying/1/settledownstream?pageNo=1&pageSize=2')
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

    it('下游结算单 - 获取某个ID的下游结算单信息 GET: /api/ying/1/settledownstream/1', function (done) {
        server.get('/api/ying/1/settledownstream/1')
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.settleDate).to.include('2017')
                done()
            })
    })

    it('下游结算单 - 修改某个ID的下游结算单 PUT: /api/ying/1/settledownstream/1', function (done) {
        server.put('/api/ying/1/settledownstream/1')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "tempSettleType" : "downstream",
                    "hsId" : 1,
                    "settleDate" : "2017-09-23 00:00:00",
                    "money" : "4000",
                    "discountType" : -1,
                    "discountInterest" : "",
                    "discountDays" : "",
                    "discountAmount" : "",
                    "settleGap" : "2000",
                    "trafficCompanyId" : -1,
                    "orderId" : 1,
                    "id" : 1
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




    it('运输方结算单 - 新建运输方结算单1 POST: /api/ying/1/settletraffic', function (done) {
        server.post('/api/ying/1/settletraffic')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "tempSettleType" : "traffic",
                    "hsId" : 1,
                    "settleDate" : "2017-09-01 00:00:00",
                    "amount" : "3000",
                    "money" : "200",
                    "discountType" : -1,
                    "discountInterest" : "",
                    "discountDays" : "",
                    "discountAmount" : "",
                    "settleGap" : 99999999,
                    "trafficCompanyId" : 1,
                    "orderId" : 1
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.settleDate).to.include('2017')
                done()
            })
    })

    it('运输方结算单 - 新建运输方结算单2 POST: /api/ying/1/settletraffic', function (done) {
        server.post('/api/ying/1/settletraffic')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "tempSettleType" : "traffic",
                    "hsId" : 2,
                    "settleDate" : "2017-09-23 00:00:00",
                    "amount" : "2000",
                    "money" : "100",
                    "discountType" : -1,
                    "discountInterest" : "",
                    "discountDays" : "",
                    "discountAmount" : "",
                    "settleGap" : 99999999,
                    "trafficCompanyId" : 5,
                    "orderId" : 1
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.settleDate).to.include('2017')
                done()
            })
    })

    it('运输方结算单 - 获取应收订单运输方结算单列表 GET: /api/ying/1/settletraffic?pageNo=1&pageSize=2', function (done) {
        server.get('/api/ying/1/settletraffic?pageNo=1&pageSize=2')
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

    it('运输方结算单 - 获取某个ID的运输方结算单信息 GET: /api/ying/1/settletraffic/1', function (done) {
        server.get('/api/ying/1/settletraffic/1')
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.settleDate).to.include('2017')
                done()
            })
    })

    it('运输方结算单 - 修改某个ID的运输方结算单 PUT: /api/ying/1/settletraffic/1', function (done) {
        server.put('/api/ying/1/settletraffic/1')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "tempSettleType" : "traffic",
                    "hsId" : 2,
                    "settleDate" : "2017-09-30 00:00:00",
                    "amount" : "6000",
                    "money" : "200",
                    "discountType" : -1,
                    "discountInterest" : "",
                    "discountDays" : "",
                    "discountAmount" : "",
                    "settleGap" : 99999999,
                    "trafficCompanyId" : 9,
                    "orderId" : 1,
                    "id" : 1
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