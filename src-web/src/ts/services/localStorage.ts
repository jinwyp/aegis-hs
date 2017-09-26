/**
 * Created by jin on 9/19/17.
 */



const getAccessToken = localStorage.getItem('accessToken') || ''
const saveAccessToken = function (newToken: string = '') {
    localStorage.setItem('accessToken', newToken)
}


const getEnum = function (key: string) {
    return JSON.parse(localStorage.getItem(key) || '[]')
}
const saveEnum = function (key: string, data: any[]) {
    localStorage.setItem(key, JSON.stringify(data))
}


export { getAccessToken, saveAccessToken, getEnum, saveEnum}

