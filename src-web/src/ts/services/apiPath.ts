/**
 * Created by jin on 8/10/17.
 */

const prefix = '/api'
const apiPath = {

    dictionary : prefix + '/enums',
    login : prefix + '/login',
    logout : prefix + '/logout',

    getUserInfo : prefix + '/user/session',
    modifyUserPassword : prefix + '/change_password',
    getUserListInSameDepartment: prefix + '/user/list_dept_user',


    hsGetUserList : prefix + '/users',
    hsGetDepartmentList : prefix + '/departments',
    hsGetTeamList : prefix + '/teams',
    hsGetPartyList : prefix + '/parties',


    hsGetOrderList : prefix + '/business',

    hsGetYingOrderList : prefix + '/business/yings',
    hsGetYingOrderConfig : prefix + '/business/ying',

    hsGetCangOrderList : prefix + '/business/cangs',
    hsGetCangOrderConfig : prefix + '/business/cang'

}


export { apiPath }
