import Cookies from 'js-cookie'

const TokenKey = 's_token'
const NToken = 's_ntoken'
const IDUserKey = 's_uid'
const nameKey = 's_name'
const role = 's_role'

    // const otcKey = 'otc'
    // const employeeCodeKey = 'employee-code'
    // const adminKey = 'admin'
    // const groupKey = 'idGroup'
    // const orderLevelKey = 'Order_Level_View'

export function GetToken() {
    return Cookies.get(TokenKey)
}

export function SetToken(token) {
    return Cookies.set(TokenKey, token,{ expires: 365 })
}

export function RemoveToken() {
    return Cookies.remove(TokenKey)
}

export function GetNToken() {
    return Cookies.get(NToken)
}

export function SetNToken(token) {
    return Cookies.set(NToken, token,{ expires: 365 })
}

export function RemoveNToken() {
    return Cookies.remove(NToken)
}

export function GetUID() {
    return Cookies.get(IDUserKey)
}

export function SetUID(idUser) {
    return Cookies.set(IDUserKey, idUser)
}

export function RemoveUID() {
    return Cookies.remove(IDUserKey)
}

export function GetName() {
    return Cookies.get(nameKey)
}

export function SetName(name) {
    return Cookies.set(nameKey, name,{ expires: 365 })
}

export function RemoveName() {
    return Cookies.remove(nameKey)
}


export function GetRole() {
    return Cookies.get(role)
}

export function SetRole(name) {
    return Cookies.set(role, name,{ expires: 365 })
}

export function RemoveRole() {
    return Cookies.remove(role)
}