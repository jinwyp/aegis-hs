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




describe('应收订单 - 结算/费用/发票 : ', function () {

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



    it('费用单 - 新建费用单1 POST: /api/ying/1/fees', function (done) {
        server.post('/api/ying/1/fees')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 2,
                    "name" : "HELP_RECIVE_PAY_FEE",
                    "amount" : "2000",
                    "orderId" : 1
                }
            )
            .expect('Content-Type', /json/)
            .expect(201)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id).to.be.a('number')
                expect(res.body.data.name).to.include('HELP_RECIVE_PAY_FEE')
                done()
            })
    })

    it('费用单 - 新建费用单2 POST: /api/ying/1/fees', function (done) {
        server.post('/api/ying/1/fees')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 2,
                    "name" : "TAX_MOTRO_FREIGHT",
                    "amount" : "10000",
                    "orderId" : 1
                }
            )
            .expect('Content-Type', /json/)
            .expect(201)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id).to.be.a('number')
                expect(res.body.data.name).to.include('TAX_MOTRO_FREIGHT')
                done()
            })
    })

    it('费用单 - 获取应收订单费用单列表 GET: /api/ying/1/fees?pageNo=1&pageSize=2', function (done) {
        server.get('/api/ying/1/fees?pageNo=1&pageSize=2')
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

    it('费用单 - 获取某个ID的费用单信息 GET: /api/ying/1/fees/1', function (done) {
        server.get('/api/ying/1/fees/1')
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id).to.be.a('number')
                expect(res.body.data.name).to.include('HELP_RECIVE_PAY_FEE')
                done()
            })
    })

    it('费用单 - 修改某个ID的费用单 PUT: /api/ying/1/fees/1', function (done) {
        server.put('/api/ying/1/fees/1')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "name" : "HELP_RECIVE_PAY_FEE",
                    "amount" : 20003333,
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



    it('发票 - 新建发票1 POST: /api/ying/1/invoices', function (done) {
        server.post('/api/ying/1/invoices')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 2,
                    "invoiceDirection" : "INCOME",
                    "invoiceType" : "GOODS_INVOICE",
                    "openDate" : "2017-09-02 00:00:00",
                    "openCompanyId" : 1,
                    "receiverId" : 2,
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
                expect(res.body.data.openDate).to.include('2017')
                done()
            })
    })

    it('发票 - 新建发票2 POST: /api/ying/1/invoices', function (done) {
        server.post('/api/ying/1/invoices')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 2,
                    "invoiceDirection" : "OUTOUT",
                    "invoiceType" : "FRIGHT_INVOICE",
                    "openDate" : "2017-09-08 00:00:00",
                    "openCompanyId" : 2,
                    "receiverId" : 1,
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
                expect(res.body.data.openDate).to.include('2017')
                done()
            })
    })

    it('发票 - 获取应收订单发票列表 GET: /api/ying/1/invoices?pageNo=1&pageSize=2', function (done) {
        server.get('/api/ying/1/invoices?pageNo=1&pageSize=2')
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

    it('发票 - 获取某个ID的发票信息 GET: /api/ying/1/invoices/1', function (done) {
        server.get('/api/ying/1/invoices/1')
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id).to.be.a('number')
                expect(res.body.data.openDate).to.include('2017')
                done()
            })
    })

    it('发票 - 修改某个ID的发票 PUT: /api/ying/1/invoices/1', function (done) {
        server.put('/api/ying/1/invoices/1')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 2,
                    "invoiceDirection" : "INCOME",
                    "invoiceType" : "GOODS_INVOICE",
                    "openDate" : "2017-09-02 00:00:00",
                    "openCompanyId" : 1,
                    "receiverId" : 2,
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