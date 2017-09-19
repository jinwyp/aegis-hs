/**
 * Created by jin on 9/19/17.
 */



const getAccessToken = localStorage.getItem('accessToken') || ''
const saveAccessToken = function (newToken: string = '') {
    localStorage.setItem('accessToken', newToken)
}


export { getAccessToken, saveAccessToken}

