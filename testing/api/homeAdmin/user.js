/**
 * Created by jin on 9/18/17.
 */

const env = process.env.NODE_ENV || 'test'


//Require the dev-dependencies
const expect = require('chai').expect
const supertest = require('supertest')

const config = require('../testConfig')
const server = supertest(config.path.urlApi)




describe('管理用户', function () {

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


    it('获取用户列表无JWT GET: /api/users?pageNo=1&pageSize=2', function (done) {
        server.get('/api/users?pageNo=1&pageSize=2')
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(401)
            .end(function(err, res) {
                if (err) return done(err)
                // console.log('---------------------------')
                // console.log(res.req._headers)
                expect(res.body.success).to.equal(false)
                expect(res.body.data).to.not.equal(null)
                done()
            })
    })



    it('新建用户 POST: /api/users', function (done) {
        server.post('/api/users')
            .set('Authorization', Authorization)
            .set(config.headers)
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
                expect(res.body.data.createBy).to.include('13022117050')
                done()
            })
    })


    it('获取用户列表 GET: /api/users?pageNo=1&pageSize=2', function (done) {
        server.get('/api/users?pageNo=1&pageSize=2')
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                // console.log('---------------------------')
                // console.log(res.req._headers)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.pageNo).to.equal(1)
                expect(res.body.data.pageSize).to.equal(2)
                expect(res.body.data.results.length, 'data.results 的返回记录数量错误').to.equal(2)
                done()
            })
    })




    it('获取某个ID的用户信息 GET: /api/users/2' , function (done) {
        server.get('/api/users/2')
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.id).to.be.a('number')
                expect(res.body.data.phone).to.include('18321805753')
                done()
            })
    })


    it('修改某个ID的用户信息 PUT: /api/users/2', function (done) {
        server.put('/api/users/2')
            .set('Authorization', Authorization)
            .set(config.headers)
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