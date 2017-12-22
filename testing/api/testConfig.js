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
        phone: "15093080576",
        password: "123456"
    },
    user10 : {
        phone: "18321805753",
        password: "123456"
    },

    user1 : {
        phone: "13564568304",
        password: "123456"
    },
    user2 : {
        phone: "13564568301",
        password: "123456"
    },
    user3 : {
        phone: "13564568302",
        password: "123456"
    },
    user4 : {
        phone: "13564568303",
        password: "123456"
    },
    user5 : {
        phone: "18321805753",
        password: "123456"
    }
}

exports.order = {
    getOrderYingId : 1,
    getOrderYingUnitId : 1,
    delOrderYingId : 3,
    getOrderCangId : 9,
    delOrderCangId : 11
}

exports.env = process.env.NODE_ENV || 'test'