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




describe('事业部门', function () {


    it('获取部门列表 GET: /api/departments?pageNo=1&pageSize=2', function (done) {
        server.get('/api/departments?pageNo=1&pageSize=2')
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


    it('新建部门 POST: /api/departments', function (done) {
        server.post('/api/departments')
            .set('Accept', 'application/json')
            .send({
                name: "新的部门" + Math.floor(Math.random() * (9999 - 1000 + 1)) + 1000
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('新的部门')
                done()
            })
    })


    it('获取某个ID的部门信息 GET: /api/departments/1', function (done) {
        server.get('/api/departments/1')
            .set('Accept', 'application/json')
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('管理部')
                done()
            })
    })


    it('修改某个ID的部门名称 PUT: /api/departments/3', function (done) {
        server.put('/api/departments/3')
            .set('Accept', 'application/json')
            .send({
                name: "新的部门" + Math.floor(Math.random() * (9999 - 1000 + 1)) + 1000
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