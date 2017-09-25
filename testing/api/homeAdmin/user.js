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




describe('管理用户', function () {


    it('获取用户列表 GET: /api/users?pageNo=1&pageSize=2', function (done) {
        server.get('/api/users?pageNo=1&pageSize=2')
            .set('Accept', 'application/json')
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


    it('新建用户 POST: /api/users', function (done) {
        server.post('/api/users')
            .set('Accept', 'application/json')
            .send({
                phone : '13564568304',
                password : '123456',
                deptId : 2,
                isActive  :  2,
                isAdmin  : 1
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id).to.be.a('number')
                // expect(res.body.data.name).to.include('新的用户')
                done()
            })
    })


    it('获取某个ID的用户信息 GET: /api/users/1' , function (done) {
        server.get('/api/users/1')
            .set('Accept', 'application/json')
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id).to.be.a('number')
                // expect(res.body.data.name).to.include('团队')
                done()
            })
    })


    it('修改某个ID的用户信息 PUT: /api/users/1', function (done) {
        server.put('/api/users/1')
            .set('Accept', 'application/json')
            .send({
                isActive: 1
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