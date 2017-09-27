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




describe('当前登陆用户信息', function () {

    let Authorization = ''

    before(function (done) {

        server.post('/api/login')
            .set('Accept', 'application/json')
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



    it('获取当前登陆信息 GET: /api/user/session', function (done) {
        server.get('/api/user/session')
            .set('Authorization', Authorization)
            .set('Accept', 'application/json')
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err)
                expect(res.body.success).to.equal(true)
                expect(res.body.data).to.not.equal(null)
                expect(res.body.data.phone.length).to.equal(11)
                done()
            })
    })


/*

    it('修改当前用户信息 PUT: /api/user/session', function (done) {
        server.put('/api/user/session')
            .set('Authorization', Authorization)
            .set('Accept', 'application/json')
            .send({
                deptId: 2
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
*/

})