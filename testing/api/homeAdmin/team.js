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




describe('业务团队', function () {


    it('获取团队列表 GET: /api/teams?pageNo=1&pageSize=2', function (done) {
        server.get('/api/teams?pageNo=1&pageSize=2')
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


    it('新建团队 POST: /api/teams', function (done) {
        server.post('/api/teams')
            .set('Accept', 'application/json')
            .send({
                name: "新的团队" + Math.floor(Math.random() * (9999 - 1000 + 1)) + 1000,
                deptId : 2
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id).to.be.a('number')
                expect(res.body.data.name).to.include('新的团队')
                done()
            })
    })


    it('获取某个ID的团队信息 GET: /api/teams/16', function (done) {
        server.get('/api/teams/1')
            .set('Accept', 'application/json')
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id).to.be.a('number')
                expect(res.body.data.name).to.include('团队')
                done()
            })
    })


    it('修改某个ID的团队名称 PUT: /api/teams/16', function (done) {
        server.put('/api/teams/1')
            .set('Accept', 'application/json')
            .send({
                name: "新的团队" + Math.floor(Math.random() * (9999 - 1000 + 1)) + 1000,
                deptId : 2
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

