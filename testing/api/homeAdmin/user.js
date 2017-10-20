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

    let userId = 2
    let delUserId = 3

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





    it('新建用户1 POST: /api/users', function (done) {
        server.post('/api/users')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                phone : '13564568304',
                password : '123456',
                deptId : 2,
                isActive  :  0,
                isAdmin  : 1
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.createBy).to.include('13022117050')
                expect(res.body.data.phone).to.include('13564568304')

                userId = res.body.data.id
                done()
            })
    })

    it('新建用户2 POST: /api/users', function (done) {
        server.post('/api/users')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                phone : '13564568301',
                password : '123456',
                deptId : 2,
                isActive  :  1,
                isAdmin  : 1
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.createBy).to.include('13022117050')
                expect(res.body.data.phone).to.include('13564568301')
                done()
            })
    })

    it('新建用户3 POST: /api/users', function (done) {
        server.post('/api/users')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                phone : '13564568302',
                password : '123456',
                deptId : 2,
                isActive  :  2,
                isAdmin  : 1
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.createBy).to.include('13022117050')
                expect(res.body.data.phone).to.include('13564568302')
                done()
            })
    })


    it('新建用户 新建手机号已存在的用户 POST: /api/users', function (done) {
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
            .expect(400)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是false 但实际不是false').to.equal(false)
                expect(res.body.data, '返回的数据data对象应该是undefined 但实际不是undefined').to.equal(undefined)
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
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.pageNo).to.equal(1)
                expect(res.body.data.pageSize).to.equal(2)
                expect(res.body.data.results.length, 'data.results 的返回记录数量错误').to.equal(2)
                done()
            })
    })

    it('获取用户列表无JWT GET: /api/users?pageNo=1&pageSize=2', function (done) {
        server.get('/api/users?pageNo=1&pageSize=2')
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(401)
            .end(function(err, res) {
                if (err) return done(err)
                // console.log('---------------------------')
                // console.log(res.req._headers)
                expect(res.body.success, 'success属性值应该是false 但实际不是false').to.equal(false)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data, '返回的数据data对象应该是undefined 但实际不是undefined').to.equal(undefined)
                done()
            })
    })

    it(`获取某个ID的用户信息 GET: /api/users/${userId}` , function (done) {
        server.get(`/api/users/${userId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.phone).to.include('13564568304')
                done()
            })
    })

    it(`修改某个ID的用户信息 PUT: /api/users/${userId}`, function (done) {
        console.log(`提示信息: 修改某个ID的用户信息 PUT: /api/users/${userId}`)
        server.put(`/api/users/${userId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                isActive: 1
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