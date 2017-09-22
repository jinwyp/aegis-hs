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

    login : prefix + '/user/login',

    getUserInfo : prefix + '/users/session',
    saveUserInfo : prefix + '/users/session/info',
    modifyUserPassword : prefix + '/users/session/password',

    getUserAddressList : prefix + '/users/address',
    saveUserAddressList : prefix + '/users/address',



    hsGetUserList : prefix + '/test/users',
    hsGetDepartmentList : prefix + '/test/departments',
    hsGetTeamList : prefix + '/test/teams'
}


export { apiPath }
