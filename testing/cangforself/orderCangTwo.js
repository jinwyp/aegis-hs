

//Require the dev-dependencies
const expect = require('chai').expect
const should = require('chai').should()
const supertest = require('supertest')

const config = require('../testConfig')
const server = supertest(config.path.urlApi)




describe('仓押订单', function () {

    let Authorization = ''
    let orderId = 12
    let delOrderId = config.order.delOrderCangId

    let unitId = 6
    let delUnitId = 5

    before(function (done) {

        server.post('/api/login')
            .set(config.headers)
            .send(config.user.user5)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                Authorization = res.body.data
                done()
            })
    })


    it('新建参与商 POST: /api/parties', function (done) {
        server.post('/api/parties')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                name: '广州鑫丰润能源科技有限公司',
                shortName : '广州鑫丰润能',
                partyType : 3

            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('广州鑫丰润能')

                partyId = res.body.data.id
                done()
            })
    })


    it('新建参与商 POST: /api/parties', function (done) {
        server.post('/api/parties')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                name: '泰州立翔电力燃料有限公司',
                shortName : '泰州立翔电',
                partyType : 3

            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('泰州立翔电')

                partyId = res.body.data.id
                done()
            })
    })



    it('新建参与商 POST: /api/parties', function (done) {
        server.post('/api/parties')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                name: '广州莲荷能源科技有限责任公司',
                shortName : '广州莲荷',
                partyType : 3

            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('广州莲荷能')

                partyId = res.body.data.id
                done()
            })
    })

    it('新建参与商 POST: /api/parties', function (done) {
        server.post('/api/parties')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                name: '北京领先创融网络科技有限公司',
                shortName : '中瑞财富',
                partyType : 2

            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('北京领先创')

                partyId = res.body.data.id
                done()
            })
    })


    it('仓押订单 - 新建仓押订单1 POST: /api/business/cangs', function (done) {
        server.post('/api/business/cangs')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "teamId":13,
                    "line":"广州鑫丰润-泰州立翔-嘉瑞-广州莲荷",
                    "cargoType":"COAL",
                    "upstreamSettleMode":"ONE_PAPER_SETTLE",
                    "downstreamSettleMode":"ONE_PAPER_SETTLE",
                    "mainAccounting" : 3,
                    "upstreamId" : 30,
                    "downstreamId" : 32,
                    "businessType" : "cang",
                    "orderPartyList" : [
                        {
                            "custType" : "TRAFFICKCER",
                            "customerId" : 31,
                            "position" : 1
                        },
                        {"custType" : "FUNDER", "customerId" : 33, "position" : 1}
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

                orderId = res.body.data.id
                done()
            })
    })



    it(`核算单元 - 新建核算单元1 POST: /api/business/cang/12/units`, function (done) {
        server.post(`/api/business/cang/12/units`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                "hsMonth"           : "201706",
                "maxPrepayRate"        : "1.0",
                "unInvoicedRate"       : "0.8",
                "contractBaseInterest" : "0.15",
                "expectHKDays"         : "60",
                "tradeAddPrice"        : "2",
                "weightedPrice"        : "0"
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据data对象里面没有id字段').to.be.a('number')
                expect(res.body.data.hsMonth).to.include('201706')

                unitId = res.body.data.id
                done()
            })
    })



    // 入库

    it(`入库单 - 新建入库单1 POST: /api/business/cang/12/rukus`, function (done) {
        server.post(`/api/business/cang/12/rukus`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" :unitId,
                    "locality" : "立翔",
                    "rukuDate" : "2017-06-20 00:00:00",
                    "rukuStatus" : "IN_STORAGE",
                    "rukuAmount" : "10102.70",
                    "rukuPrice" : "6011106.50",
                    "trafficMode" : "MOTOR",
                    "cars" : "366",
                    "jhh" : "",
                    "ship" : "",
                    "orderId" : orderId
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.rukuStatus).to.include('IN_STORAGE')

                rukuId = res.body.data.id
                done()
            })
    })


    it(`出库单 - 新建出库单1 POST: /api/business/cang/${orderId}/chukus`, function (done) {
        server.post(`/api/business/cang/${orderId}/chukus`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "locality" : "立翔",
                    "chukuDate" : "2017-07-25 00:00:00",
                    "chukuAmount" : "7000",
                    "chukuPrice" : "4626496.36",
                    "orderId" : orderId
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.chukuDate).to.include('2017')

                chukuId = res.body.data.id
                done()
            })
    })

    it(`出库单 - 新建出库单2 POST: /api/business/cang/${orderId}/chukus`, function (done) {
        server.post(`/api/business/cang/${orderId}/chukus`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "locality" : "立翔",
                    "chukuDate" : "2017-07-27 00:00:00",
                    "chukuAmount" : "3102.70",
                    "chukuPrice" : "1402580.21",
                    "orderId" : orderId
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.chukuDate).to.include('2017')

                chukuId = res.body.data.id
                done()
            })
    })




    it(`借款单 - 新建借款单1 POST: /api/business/cang/${orderId}/jiekuans`, function (done) {
        server.post(`/api/business/cang/${orderId}/jiekuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "orderId" : orderId,
                    "hsId" : unitId,
                    "jiekuanDate": "2017-04-06 00:00:00",
                    "amount" : "5000000",
                    "capitalId" : 33,
                    "useDays": "35",
                    "useInterest": "0.075"
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)


                chukuId = res.body.data.id
                done()
            })
    })

    it(`还款单 - 新建还款单1 POST: /api/business/cang/${orderId}/huankuans`, function (done) {
        server.post(`/api/business/cang/${orderId}/huankuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "orderId" : orderId,
                    "promise" : true,
                    "huankuankDate" : "2017-07-26 00:00:00",
                    "huankuanMapList" : [
                        {
                            "jiekuanId":"10",
                            "principal" : "50000000",
                            "interest" : "24427.16",
                            "fee" : "5483.33"
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
    it(`发票 - 新建发票1 POST: /api/business/ying/${orderId}/invoices`, function (done) {
        server.post(`/api/business/ying/${orderId}/invoices`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "invoiceDirection" : "INCOME",
                    "invoiceType" : "GOODS_INVOICE",
                    "openDate" : "2017-07-04 00:00:00",
                    "openCompanyId" : 30,
                    "receiverId" : 31,
                    "orderId" : orderId,
                    "details" : [
                        {
                            "invoiceNumber" : "12346123",
                            "cargoAmount" : "10102.70",
                            "taxRate" : "0.17",
                            "priceAndTax" : "6011106.30"
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
                expect(res.body.data.openDate).to.include('2017')
                done()
            })
    })

    it(`发票 - 新建发票1 POST: /api/business/ying/${orderId}/invoices`, function (done) {
        server.post(`/api/business/ying/${orderId}/invoices`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "invoiceDirection" : "INCOME",
                    "invoiceType" : "GOODS_INVOICE",
                    "openDate" : "2017-07-04 00:00:00",
                    "openCompanyId" : 31,
                    "receiverId" : 3,
                    "orderId" : orderId,
                    "details" : [
                        {
                            "invoiceNumber" : "12346124",
                            "cargoAmount" : "10102.70",
                            "taxRate" : "0.17",
                            "priceAndTax" : "6031311.90"
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
                expect(res.body.data.openDate).to.include('2017')
                done()
            })
    })


    it(`付款单 - 新建付款单1 POST: /api/business/ying/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "payDate" : "2017-06-29 00:00:00",
                    "receiveCompanyId" : 30,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payAmount" : "4207000.00",
                    "orderId" : orderId,
                    "capitalId" : 3
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.payDate).to.include('2017')
                done()
            })
    })

    it(`付款单 - 新建付款单2 POST: /api/business/ying/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "payDate" : "2017-07-05 00:00:00",
                    "receiveCompanyId" : 30,
                    "payUsage" : "PAYMENT_FOR_GOODS",
                    "payAmount" : "1804106.50 ",
                    "orderId" : orderId,
                    "capitalId" : 3
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.payDate).to.include('2017')
                done()
            })
    })

    it(`付款单 - 新建付款单3 POST: /api/business/ying/${orderId}/fukuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/fukuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId" : unitId,
                    "payDate" : "2017-07-05 00:00:00",
                    "receiveCompanyId" : 30,
                    "payUsage" : "TRADE_DEFICIT",
                    "payAmount" : "20205.40",
                    "orderId" : orderId,
                    "capitalId" : 3
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.payDate).to.include('2017')
                done()
            })
    })

    it(`回款单 - 新建回款单1 POST: /api/business/ying/${orderId}/huikuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/huikuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId":unitId,
                    "huikuanCompanyId":33,
                    "huikuanDate":"2017-07-28 00:00:00",
                    "huikuanAmount":"1190000.00 ",
                    "huikuanUsage":"PAYMENT_FOR_GOODS",
                    "huikuanMode":"DEPOSITECASH",
                    "huikuanBankPaper":"",
                    "huikuanBankPaperDate":"",
                    "huikuanBankDiscount":"",
                    "huikuanBankDiscountRate":"",
                    "huikuanBankPaperExpire":"",
                    "huikuanBusinessPaper":"",
                    "huikuanBusinessPaperDate":"",
                    "huikuanBusinessDiscount":"",
                    "huikuanBusinessDiscountRate":"",
                    "huikuanBusinessPaperExpire":"",
                    "orderId" : orderId
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.huikuanDate).to.include('2017')
                done()
            })
    })

    it(`回款单 - 新建回款单2 POST: /api/business/ying/${orderId}/huikuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/huikuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId":unitId,
                    "huikuanCompanyId":32,
                    "huikuanDate":"2017-06-29 00:00:00",
                    "huikuanAmount":"4626496.36",
                    "huikuanUsage":"PAYMENT_FOR_GOODS",
                    "huikuanMode":"ELEC_REMITTANCE",
                    "huikuanBankPaper":"",
                    "huikuanBankPaperDate":"",
                    "huikuanBankDiscount":"",
                    "huikuanBankDiscountRate":"",
                    "huikuanBankPaperExpire":"",
                    "huikuanBusinessPaper":"",
                    "huikuanBusinessPaperDate":"",
                    "huikuanBusinessDiscount":"",
                    "huikuanBusinessDiscountRate":"",
                    "huikuanBusinessPaperExpire":"",
                    "orderId" : orderId
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.huikuanDate).to.include('2017')
                done()
            })
    })

    it(`回款单 - 新建回款单2 POST: /api/business/ying/${orderId}/huikuans`, function (done) {
        server.post(`/api/business/ying/${orderId}/huikuans`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send(
                {
                    "hsId":unitId,
                    "huikuanCompanyId":32,
                    "huikuanDate":"2017-07-25 00:00:00",
                    "huikuanAmount":"212580.21",
                    "huikuanUsage":"PAYMENT_FOR_GOODS",
                    "huikuanMode":"ELEC_REMITTANCE",
                    "huikuanBankPaper":"",
                    "huikuanBankPaperDate":"",
                    "huikuanBankDiscount":"",
                    "huikuanBankDiscountRate":"",
                    "huikuanBankPaperExpire":"",
                    "huikuanBusinessPaper":"",
                    "huikuanBusinessPaperDate":"",
                    "huikuanBusinessDiscount":"",
                    "huikuanBusinessDiscountRate":"",
                    "huikuanBusinessPaperExpire":"",
                    "orderId" : orderId
                }
            )
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.huikuanDate).to.include('2017')
                done()
            })
    })
})

