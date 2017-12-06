
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
            .send(config.user.user1)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function (err, res) {
                if (err) return done(err)
                Authorization = res.body.data
                done()
            })
    })

    it(`应收订单 - 修改某个ID的应收订单 PUT: /api/business/yings/${orderId}`, function (done) {
        server.put(`/api/business/yings/${orderId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "id": orderId,
                "deptId": 2,
                "teamId": 2,
                "line": "那曲 - 晋和 - 嘉瑞",
                "cargoType": "COAL",
                "upstreamSettleMode": "ONE_PAPER_SETTLE",
                "downstreamSettleMode": "ONE_PAPER_SETTLE",
                "mainAccounting": 1,
                "upstreamId": 2,
                "downstreamId": 3,
                "orderPartyList": [
                    {"custType": "UPSTREAM", "customerId": 9999},
                    {"custType": "DOWNSTREAM", "customerId": 9999},
                    {"custType": "TRAFFICKCER", "customerId": 9999},
                    {"custType": "ACCOUNTING_COMPANY", "customerId": 9999}
                ]
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function (err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data值应该是1 但实际不是1').to.equal(1)
                done()
            })
    })
 })