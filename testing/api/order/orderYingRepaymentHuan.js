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




describe('应收订单 还款:', function () {

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






    it('还款单 - 新建还款单1 POST: /api/business/ying/1/huankuans', function (done) {
        server.post('/api/business/ying/1/huankuans')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "skCompanyId" : 1,
                    "huankuankDate" : "2017-10-07 00:00:00",
                    "huankuanPrincipal" : "100",
                    "huankuanInterest" : "100",
                    "huankuanFee" : "100",
                    "orderId" : 1,
                    "huankuanMapList" : [
                        {
                            "jiekuanId":"2",
                            "fukuanId" : 1,
                            "principal" : "100",
                            "interest" : "100",
                            "fee" : "100"
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
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.huankuankDate).to.include('2017')
                done()
            })
    })

    it('还款单 - 新建还款单2 POST: /api/business/ying/1/huankuans', function (done) {
        server.post('/api/business/ying/1/huankuans')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "skCompanyId" : 1,
                    "huankuankDate" : "2017-10-07 00:00:00",
                    "huankuanPrincipal" : "100",
                    "huankuanInterest" : "100",
                    "huankuanFee" : "100",
                    "orderId" : 1,
                    "huankuanMapList" : [
                        {
                            "fukuanId" : 1,
                            "principal" : "100",
                            "interest" : "100",
                            "fee" : "100"
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
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.huankuankDate).to.include('2017')
                done()
            })
    })

    it('还款单 - 新建还款单3 还款map明细的本金总计不符合, POST: /api/business/ying/1/huankuans', function (done) {
        server.post('/api/business/ying/1/huankuans')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "skCompanyId" : 1,
                    "huankuankDate" : "2017-09-01 00:00:00",
                    "huankuanAmount" : "100",
                    "huankuanInterest" : "100",
                    "huankuanFee" : "10",
                    "orderId" : 1,
                    "huankuanMapList" : [
                        {
                            "fukuanId" : 1,
                            "principal" : "100000",
                            "interest" : "10000",
                            "fee" : "100"
                        }
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

    it('还款单 - 新建还款单4 还款map明细的本金总计不符合, POST: /api/business/ying/1/huankuans', function (done) {
        server.post('/api/business/ying/1/huankuans')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "skCompanyId" : 1,
                    "huankuankDate" : "2017-09-01 00:00:00",
                    "huankuanAmount" : "10000",
                    "huankuanInterest" : "100",
                    "huankuanFee" : "10",
                    "orderId" : 1,
                    "huankuanMapList" : [
                        {
                            "huankuanId":15,
                            "fukuanId" : 1,
                            "principal" : 10000,
                            "interest" : 100,
                            "fee" : "100"
                        }
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

    it('还款单 - 获取应收订单还款单列表 GET: /api/business/ying/1/huankuans?pageNo=1&pageSize=2', function (done) {
        server.get('/api/business/ying/1/huankuans?pageNo=1&pageSize=2')
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.pageNo).to.equal(1)
                expect(res.body.data.pageSize).to.equal(2)
                expect(res.body.data.results.length, 'data.results 的返回记录数量错误').to.equal(2)
                done()
            })
    })

    it('还款单 - 获取某个ID的还款单信息 GET: /api/business/ying/1/huankuans/1', function (done) {
        server.get('/api/business/ying/1/huankuans/1')
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.huankuankDate).to.include('2017')
                done()
            })
    })

    it('还款单 - 修改某个ID的还款单 PUT: /api/business/ying/1/huankuans/1', function (done) {
        server.put('/api/business/ying/1/huankuans/1')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : 1,
                    "skCompanyId" : 1,
                    "huankuankDate" : "2017-09-01 00:00:00",
                    "huankuanInterest" : 100,
                    "huankuanFee" : 3000,
                    "orderId" : 1,
                    "id" : 1
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data值应该是1 但实际不是1').to.equal(1)
                done()
            })
    })

    it('还款单 - 删除某个ID的还款单 DELETE: /api/business/ying/1/huankuans/2', function (done) {
        server.delete('/api/business/ying/1/huankuans/2')
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