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


/*

describe('First test', () => {
    it('Should assert true to be true', () => {
        expect(true).to.be.true;
    });
});
*/


describe('用户注册登陆', function () {

    it('注册成功 POST: /api/register', function (done) {
        server.post('/api/register')
            .set('Accept', 'application/json')
            .send({
                deptId : 2,
                phone: "18321805753",
                password: "123456"
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                done()
            })
    })

    it('登陆成功 POST: /api/login', function (done) {
        server.post('/api/login')
            .set('Accept', 'application/json')
            .send({
                phone: "18321805753",
                password: "123456"
            })
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是true 但实际不是true').to.equal(true)
                expect(res.body.data, '返回的数据data对象应该不为null 但实际是null或undefined').to.not.equal(null)
                done()
            })
    })

    it('登陆失败 POST: /api/login', function (done) {
        server.post('/api/login')
            .set('Accept', 'application/json')
            .send({
                phone: "18321805753",
                password: "1234567"
            })
            .expect('Content-Type', /json/)
            .expect(401)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success, 'success属性值应该是false 但实际不是false').to.equal(false)
                expect(res.body.data, '返回的数据data对象应该是undefined 但实际不是undefined').to.equal(undefined)
                done()
            })
    })

})

