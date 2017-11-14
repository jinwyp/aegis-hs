// /**
//  * Created by jin on 9/28/17.
//  */
//
//
// /**
//  * Created by jin on 9/26/17.
//  */
//
//
// /**
//  * Created by jin on 9/18/17.
//  */
//
// const env = process.env.NODE_ENV || 'test'
//
//
// //Require the dev-dependencies
// const expect = require('chai').expect
// const should = require('chai').should()
// const supertest = require('supertest')
//
// const config = require('../testConfig')
// const server = supertest(config.path.urlApi)
//
//
//
//
// describe('应收订单 结算单:', function () {
//
//     let Authorization = ''
//
//     let orderId = config.order.getOrderYingId
//
//     let buyerSettleId = 1
//     let delBuyerSettleId = 2
//
//     let sellerSettleId = 1
//     let delSellerSettleId = 3
//
//     before(function (done) {
//
//         server.post('/api/login')
//             .set(config.headers)
//             .send(config.user.user1)
//             .expect('Content-Type', /json/)
//             .expect(200)
//             .end(function(err, res) {
//                 if (err) return done(err)
//                 Authorization = res.body.data
//                 done()
//             })
//
//     })
//
//
//
//
//
//     it(`下游结算单 - 新建下游结算单1 POST: /api/business/ying/${orderId}/settlebuyerdownstream`, function (done) {
//         server.post(`/api/business/ying/${orderId}/settlebuyerdownstream`)
//             .set('Authorization', Authorization)
//             .set(config.headers)
//             .send(
//                 {
//                     "hsId" : 1,
//                     "orderId" : orderId,
//                     "settleDate" : "2017-09-02 00:00:00",
//                     "amount" : "2000",
//                     "money" : "100",
//                     "settleGap" : "100"
//                 }
//             )
//             .expect('Content-Type', /json/)
//             .expect(200)
//             .end(function(err, res) {
//                 if (err) return done(err)
//                 expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
//                 expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
//                 expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
//                 expect(res.body.data.settleDate).to.include('2017')
//
//                 buyerSettleId = res.body.data.id
//                 done()
//             })
//     })
//
//     it(`下游结算单 - 新建下游结算单2 POST: /api/business/ying/${orderId}/settlebuyerdownstream`, function (done) {
//         server.post(`/api/business/ying/${orderId}/settlebuyerdownstream`)
//             .set('Authorization', Authorization)
//             .set(config.headers)
//             .send(
//                 {
//                     "hsId" : 1,
//                     "orderId" : orderId,
//                     "settleDate" : "2017-09-02 00:00:00",
//                     "amount" : "40000",
//                     "money" : "100",
//                     "settleGap" : "200"
//                 }
//             )
//             .expect('Content-Type', /json/)
//             .expect(200)
//             .end(function(err, res) {
//                 if (err) return done(err)
//                 expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
//                 expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
//                 expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
//                 expect(res.body.data.settleDate).to.include('2017')
//
//                 delBuyerSettleId = res.body.data.id
//                 done()
//             })
//     })
//
//     it(`下游结算单 - 获取应收订单下游结算单列表 GET: /api/business/ying/${orderId}/settlebuyerdownstream?pageNo=1&pageSize=2`, function (done) {
//         server.get(`/api/business/ying/${orderId}/settlebuyerdownstream?pageNo=1&pageSize=2`)
//             .set('Authorization', Authorization)
//             .set(config.headers)
//             .expect('Content-Type', /json/)
//             .expect(200)
//             .end(function(err, res) {
//                 if (err) return done(err)
//                 expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
//                 expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
//                 expect(res.body.data.pageNo).to.equal(1)
//                 expect(res.body.data.pageSize).to.equal(2)
//                 expect(res.body.data.results, 'data.results 的返回记录数量错误').to.have.lengthOf(2)
//                 done()
//             })
//     })
//
//     it(`下游结算单 - 获取某个ID的下游结算单信息 GET: /api/business/ying/${orderId}/settlebuyerdownstream/${buyerSettleId}`, function (done) {
//         server.get(`/api/business/ying/${orderId}/settlebuyerdownstream/${buyerSettleId}`)
//             .set('Authorization', Authorization)
//             .set(config.headers)
//             .expect('Content-Type', /json/)
//             .expect(200)
//             .end(function(err, res) {
//                 if (err) return done(err)
//                 expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
//                 expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
//                 expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
//                 expect(res.body.data.settleDate).to.include('2017')
//                 done()
//             })
//     })
//
//     it(`下游结算单 - 修改某个ID的下游结算单 PUT: /api/business/ying/${orderId}/settlebuyerdownstream/${buyerSettleId}`, function (done) {
//         server.put(`/api/business/ying/${orderId}/settlebuyerdownstream/${buyerSettleId}`)
//             .set('Authorization', Authorization)
//             .set(config.headers)
//             .send(
//                 {
//                     "hsId" : 1,
//                     "settleDate" : "2017-09-23 00:00:00",
//                     "money" : "5000",
//                     "settleGap" : "330",
//                     "orderId" : orderId,
//                     "id" : 1
//                 }
//             )
//             .expect('Content-Type', /json/)
//             .expect(200)
//             .end(function(err, res) {
//                 if (err) return done(err)
//                 expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
//                 expect(res.body.data, '返回的数据data值应该是1 但实际不是1').to.equal(1)
//                 done()
//             })
//     })
//
//     it(`下游结算单 - 删除某个ID的下游结算单 DELETE: /api/business/ying/${orderId}/settlebuyerdownstream/${delBuyerSettleId}`, function (done) {
//         server.delete(`/api/business/ying/${orderId}/settlebuyerdownstream/${delBuyerSettleId}`)
//             .set('Authorization', Authorization)
//             .set(config.headers)
//             .send({})
//             .expect('Content-Type', /json/)
//             .expect(200)
//             .end(function(err, res) {
//                 if (err) return done(err)
//                 expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
//                 expect(res.body.data, '返回的数据data值应该是1 但实际不是1').to.equal(1)
//                 done()
//             })
//     })
//
//
//
//
//
//
//
//     it(`上游结算单 - 新建上游结算单1 POST: /api/business/ying/${orderId}/settlesellerupstream`, function (done) {
//         server.post(`/api/business/ying/${orderId}/settlesellerupstream`)
//             .set('Authorization', Authorization)
//             .set(config.headers)
//             .send(
//                 {
//                     "hsId" : 1,
//                     "settleDate" : "2017-09-02 00:00:00",
//                     "amount" : "2000",
//                     "money" : "1000",
//                     "discountType" : "RATE_DISCOUNT",
//                     "discountInterest" : "0.2",
//                     "discountDays" : "30",
//                     "discountAmount" : "1000",
//                     "orderId" : orderId
//                 }
//             )
//             .expect('Content-Type', /json/)
//             .expect(200)
//             .end(function(err, res) {
//                 if (err) return done(err)
//                 expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
//                 expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
//                 expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
//                 expect(res.body.data.settleDate).to.include('2017')
//
//                 sellerSettleId = res.body.data.id
//                 done()
//             })
//     })
//
//     it(`上游结算单 - 新建上游结算单2 POST: /api/business/ying/${orderId}/settlesellerupstream`, function (done) {
//         server.post(`/api/business/ying/${orderId}/settlesellerupstream`)
//             .set('Authorization', Authorization)
//             .set(config.headers)
//             .send(
//                 {
//                     "tempSettleType" : "upstream",
//                     "hsId" : 2,
//                     "settleDate" : "2017-10-30 00:00:00",
//                     "discountType" : "CASH_DISCOUNT",
//                     "discountInterest" : "0.3",
//                     "discountDays" : "60",
//                     "discountAmount" : "20",
//                     "orderId" : orderId
//                 }
//             )
//             .expect('Content-Type', /json/)
//             .expect(200)
//             .end(function(err, res) {
//                 if (err) return done(err)
//                 expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
//                 expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
//                 expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
//                 expect(res.body.data.settleDate).to.include('2017')
//                 done()
//             })
//     })
//
//     it(`上游结算单 - 新建上游结算单3 POST: /api/business/ying/${orderId}/settlesellerupstream`, function (done) {
//         server.post(`/api/business/ying/${orderId}/settlesellerupstream`)
//             .set('Authorization', Authorization)
//             .set(config.headers)
//             .send(
//                 {
//                     "tempSettleType" : "upstream",
//                     "hsId" : 3,
//                     "settleDate" : "2017-10-30 00:00:00",
//                     "discountType" : "CASH_DISCOUNT",
//                     "discountInterest" : "0.3",
//                     "discountDays" : "70",
//                     "discountAmount" : "10",
//                     "orderId" : orderId
//                 }
//             )
//             .expect('Content-Type', /json/)
//             .expect(200)
//             .end(function(err, res) {
//                 if (err) return done(err)
//                 expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
//                 expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
//                 expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
//                 expect(res.body.data.settleDate).to.include('2017')
//
//                 delSellerSettleId = res.body.data.id
//                 done()
//             })
//     })
//
//
//     it(`上游结算单 - 获取应收订单上游结算单列表 GET: /api/business/ying/${orderId}/settlesellerupstream?pageNo=1&pageSize=2`, function (done) {
//         server.get(`/api/business/ying/${orderId}/settlesellerupstream?pageNo=1&pageSize=2`)
//             .set('Authorization', Authorization)
//             .set(config.headers)
//             .expect('Content-Type', /json/)
//             .expect(200)
//             .end(function(err, res) {
//                 if (err) return done(err)
//                 expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
//                 expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
//                 expect(res.body.data.pageNo).to.equal(1)
//                 expect(res.body.data.pageSize).to.equal(2)
//                 expect(res.body.data.results, 'data.results 的返回记录数量错误').to.have.lengthOf(2)
//                 done()
//             })
//     })
//
//     it(`上游结算单 - 获取某个ID的上游结算单信息 GET: /api/business/ying/${orderId}/settlesellerupstream/${sellerSettleId}`, function (done) {
//         server.get(`/api/business/ying/${orderId}/settlesellerupstream/${sellerSettleId}`)
//             .set('Authorization', Authorization)
//             .set(config.headers)
//             .expect('Content-Type', /json/)
//             .expect(200)
//             .end(function(err, res) {
//                 if (err) return done(err)
//                 expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
//                 expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
//                 expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
//                 expect(res.body.data.settleDate).to.include('2017')
//                 done()
//             })
//     })
//
//     it(`上游结算单 - 修改某个ID的上游结算单 PUT: /api/business/ying/${orderId}/settlesellerupstream/${sellerSettleId}`, function (done) {
//         server.put(`/api/business/ying/${orderId}/settlesellerupstream/${sellerSettleId}`)
//             .set('Authorization', Authorization)
//             .set(config.headers)
//             .send(
//                 {
//                     "hsId" : 1,
//                     "settleDate" : "2017-09-02 00:00:00",
//                     "amount" : 3000,
//                     "money" : 2000,
//                     "discountType" : "RATE_DISCOUNT",
//                     "discountInterest" : 0.2,
//                     "discountDays" : 30,
//                     "discountAmount" : "5000",
//                     "orderId" : orderId,
//                     "id" : sellerSettleId
//                 }
//             )
//             .expect('Content-Type', /json/)
//             .expect(200)
//             .end(function(err, res) {
//                 if (err) return done(err)
//                 expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
//                 expect(res.body.data, '返回的数据data值应该是1 但实际不是1').to.equal(1)
//                 done()
//             })
//     })
//
//     it(`上游结算单 - 删除某个ID的上游结算单 DELETE: /api/business/ying/${orderId}/settlesellerupstream/${delSellerSettleId}`, function (done) {
//         server.delete(`/api/business/ying/${orderId}/settlesellerupstream/${delSellerSettleId}`)
//             .set('Authorization', Authorization)
//             .set(config.headers)
//             .send({})
//             .expect('Content-Type', /json/)
//             .expect(200)
//             .end(function(err, res) {
//                 if (err) return done(err)
//                 expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
//                 expect(res.body.data, '返回的数据data值应该是1 但实际不是1').to.equal(1)
//                 done()
//             })
//     })
//
//
//
//
// })