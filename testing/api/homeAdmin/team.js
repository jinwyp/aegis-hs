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

    let Authorization = ''

    let teamId = 16
    let delTeamId = 17

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




    it('获取团队列表 GET: /api/teams?pageNo=1&pageSize=2', function (done) {
        server.get('/api/teams?pageNo=1&pageSize=2')
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

    it('新建团队1 POST: /api/teams', function (done) {
        server.post('/api/teams')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                name: "新的团队" + Math.floor(Math.random() * (9999 - 1000 + 1)) + 1000,
                deptId : 2
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('新的团队')

                teamId = res.body.data.id
                done()
            })
    })

    it('新建团队2 POST: /api/teams', function (done) {
        server.post('/api/teams')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                name: "新的团队" + Math.floor(Math.random() * (9999 - 1000 + 1)) + 1000,
                deptId : 2
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('新的团队')

                delTeamId = res.body.data.id
                done()
            })
    })

    it('获取某个ID的团队信息 GET: /api/teams/16', function (done) {
        server.get('/api/teams/16')
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                expect(res.body.data.id, '返回的数据里面没有id字段').to.be.a('number')
                expect(res.body.data.name).to.include('团队')
                done()
            })
    })

    it('修改某个ID的团队名称 PUT: /api/teams/16', function (done) {
        server.put('/api/teams/16')
            .set('Authorization', Authorization)
            .set(config.headers)
            .send({
                name: "新的团队" + Math.floor(Math.random() * (9999 - 1000 + 1)) + 1000,
                deptId : 2
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

    it(`删除某个ID的团队名称 DELETE: /api/teams/${delTeamId}`, function (done) {
        console.log(`删除某个ID的团队名称 DELETE: /api/teams/${delTeamId}`)
        server.delete(`/api/teams/${delTeamId}`)
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

    it(`获取 已删除的ID的团队信息 应该返回400  GET: /api/teams/${delTeamId}`, function (done) {
        server.get(`/api/teams/${delTeamId}`)
            .set('Authorization', Authorization)
            .set(config.headers)
            .expect('Content-Type', /json/)
            .expect(400)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是false 但实际不是false').to.equal(false)
                expect(res.body.data, '返回的数据data对象应该是undefined 但实际不是undefined').to.equal(undefined)
                done()
            })
    })
})

