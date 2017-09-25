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
                expect(res.body.success).to.equal(true)
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
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                done()
            })
    })


})

