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




describe('参与商公司', function () {

    let Authorization = ''

    let partyId = 29
    let delPartyId = 30


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







    it('新建参与商 POST: /api/parties', function (done) {
        server.post('/api/parties')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                name: "新的参与商公司" + Math.floor(Math.random() * (9999 - 1000 + 1)) + 1000,
                shortName : '新的参与商公司简称',
                partyType : 1

            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('新的参与商公司')

                partyId = res.body.data.id
                done()
            })
    })

    it('获取参与商公司列表 GET: /api/parties?pageNo=1&pageSize=2', function (done) {
        server.get('/api/parties?pageNo=1&pageSize=2')
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

    it(`获取某个ID的参与商信息 GET: /api/parties/${partyId}`, function (done) {
        server.get(`/api/parties/${partyId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('新的参与商公司')
                done()
            })
    })

    it(`修改某个ID的参与商信息 PUT: /api/parties/${partyId}`, function (done) {
        server.put(`/api/parties/${partyId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                name: "新的参与商公司" + Math.floor(Math.random() * (9999 - 1000 + 1)) + 1000,
                partyType : 1,
                shortName : '新的参与商公司简称'
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

})