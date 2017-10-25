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




describe('事业部门 - ', function () {

    let Authorization = ''

    let departmentId = 3
    let delDepartmentId = 5


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




    it('新建部门1 POST: /api/departments', function (done) {
        server.post('/api/departments')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                name: "新的部门" + Math.floor(Math.random() * (9999 - 1000 + 1)) + 1000
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('新的部门')

                departmentId = res.body.data.id
                done()
            })
    })

    it('新建部门2 POST: /api/departments', function (done) {
        server.post('/api/departments')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                name: "新的部门" + Math.floor(Math.random() * (9999 - 1000 + 1)) + 1000
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('新的部门')
                done()
            })
    })

    it('新建部门3 POST: /api/departments', function (done) {
        server.post('/api/departments')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                name: "新的部门" + Math.floor(Math.random() * (9999 - 1000 + 1)) + 1000
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('新的部门')

                delDepartmentId = res.body.data.id
                done()
            })
    })

    it('获取部门列表 GET: /api/departments?pageNo=1&pageSize=2', function (done) {
        server.get('/api/departments?pageNo=1&pageSize=2')
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

    it(`获取某个ID的部门信息 GET: /api/departments/${departmentId}`, function (done) {
        server.get(`/api/departments/${departmentId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('新的部门')
                done()
            })
    })

    it(`修改某个ID的部门名称 PUT: /api/departments/${departmentId}`, function (done) {
        console.log(`提示信息: 修改某个ID的部门名称 PUT: /api/departments/${departmentId}`)
        server.put(`/api/departments/${departmentId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                name: "新的部门" + Math.floor(Math.random() * (9999 - 1000 + 1)) + 1000
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

    it(`删除某个ID的部门 DELETE: /api/departments/${delDepartmentId}`, function (done) {
        console.log(`提示信息: 删除某个ID的部门 DELETE: /api/departments/${delDepartmentId}`)
        server.delete(`/api/departments/${delDepartmentId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({})
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