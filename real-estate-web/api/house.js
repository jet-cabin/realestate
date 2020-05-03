import request from '@/utils/request'
import user from '../store/modules/user';


export function fetchList(params) {

    return request(
        {
            url: '/house/list',
            method: 'post',
            data: params
        }
    );
}

export function query(id) {
    return request({
        url: '/house/' + id,
        method: 'get'
    })
}

export function createHouseRecord(house) {
    return request({
        url: '/house',
        method: 'post',
        data: house
    }
    )
}
export function updateHouse(house) {
    return request({
        url: '/house/' + house.id,
        method: 'put',
        data: house
    }
    )
}
export function publishHouse(house) {
    return request({
        url: '/house/' + house.id + '/' + house.vendorId + ';publish',
        method: 'put'
    })
}

export function auditHouse(h){
    return request({
        url: '/house/' + h.id + '/'+h.contactPhone+';audit',
        method: 'put'
    })
}

export function rent(house) {
    return request({
        url: '/house/' + house.id + ';rent',
        method: 'put',
        data: house
    })
}

export function rentList(param) {
    return request({
        url: '/rent/list',
        method: 'post',
        data: param
    })
}

export function remove(id) {
    return request({
        url: '/house/' + id,
        // params: { 'id': id },
        method: 'delete'
    });
}

export function score(score) {
    return request({
        url: '/score',
        method: 'post',
        data: score
    })
}

export function aggScore(param) {
    return request({
        url: '/score/aggregation',
        method: 'get',
        params: param
    })
}
export function addMsg(msg) {
    return request({
        url: '/message',
        method: 'post',
        data: msg
    })
}

export function queryMsgs(params) {
    return request({
        url: '/message/list',
        method: 'post',
        data: params
    })
}
export function disableMsg(ps) {
    return request({
        url: '/message',
        data: ps,
        method: 'put'
    })
}

export function req(fn, data) {
    request.defaults.baseURL = "http://localhost:5050/v1"
    return fn(data)
}

