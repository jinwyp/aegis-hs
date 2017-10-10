/**
 * Created by jin on 8/10/17.
 */

const prefix = '/api'
const apiPath = {
    getSignUpCaptcha : '/web/signup/captcha',
    signUpCheckCaptcha : prefix + '/user/signup/captcha',
    signUpCheckUsername : prefix + '/user/signup/username',
    signUpCheckMobilePhone : prefix + '/user/signup/phone',
    signUpGetSMSCode : prefix + '/user/signup/sms',
    signUpCheckSMSCode : prefix + '/user/signup/smscode',
    signUp : prefix + '/user/signup',
    signUpByMobile : prefix + '/user/signupmobile',




    saveUserInfo : prefix + '/users/session/info',


    getUserAddressList : prefix + '/users/address',
    saveUserAddressList : prefix + '/users/address',



    dictionary : prefix + '/enums',
    login : prefix + '/login',
    logout : prefix + '/logout',

    getUserInfo : prefix + '/user/session',
    modifyUserPassword : prefix + '/change_password',


    hsGetUserList : prefix + '/users',
    hsGetDepartmentList : prefix + '/departments',
    hsGetTeamList : prefix + '/teams',
    hsGetPartyList : prefix + '/parties',


    hsGetOrderList : '/api/yings',
    hsGetOrderConfig : '/api/ying'
}


export { apiPath }
