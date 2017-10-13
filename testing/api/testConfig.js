/**
 * Created by jin on 9/18/17.
 */


exports.path = {
    urlApi : 'http://localhost:8080'
}


exports.headers = {
    'Accept': 'application/json',
    'X-Requested-With': 'XMLHttpRequest'
}

exports.user = {
    admin : {
        phone: "13022117050",
        password: "123456"
    },

    user1 : {
        phone: "13564568304",
        password: "123456"
    }
}

exports.env = process.env.NODE_ENV || 'test'