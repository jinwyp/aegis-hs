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


    it('获取参与商公司列表 GET: /api/parties?pageNo=1&pageSize=2', function (done) {
        server.get('/api/parties?pageNo=1&pageSize=2')
            .set('Accept', 'application/json')
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


    it('新建参与商 POST: /api/parties', function (done) {
        server.post('/api/parties')
            .set('Accept', 'application/json')
            .send({
                name: "新的参与商公司" + Math.floor(Math.random() * (9999 - 1000 + 1)) + 1000,
                shortName : '新的参与商公司简称',
                partyType : 1

            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('新的参与商公司')
                done()
            })
    })


    it('获取某个ID的参与商信息 GET: /api/parties/1', function (done) {
        server.get('/api/parties/1')
            .set('Accept', 'application/json')
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('江苏晋和电力燃料有限公司')
                done()
            })
    })


    it('修改某个ID的参与商信息 PUT: /api/parties/19', function (done) {
        server.put('/api/parties/19')
            .set('Accept', 'application/json')
            .send({
                name: "新的参与商公司" + Math.floor(Math.random() * (9999 - 1000 + 1)) + 1000,
                partyType : 1,
                shortName : '新的参与商公司简称'
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

})