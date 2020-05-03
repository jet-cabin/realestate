import { req,publishHouse,rent,auditHouse, rentList,addMsg,queryMsgs,disableMsg,aggScore, score,updateHouse, remove, query, fetchList, createHouseRecord } from '@/api/house'

const house = {
    state: {
        list: null
    },
    mutations: {
        'SET_LIST': (state, list) => {
            state.list = list;
        }
    },
    actions: {
        houseList({ commit }, param) {
            return new Promise((res, rej) => {
                req(fetchList, param).then(resp => {
                    const data = resp.data
                    commit('SET_LIST', data)
                    res(resp)

                }).catch(() => {
                    rej()
                })
            });
        },
        house({ commit }, id) {
            return new Promise((res, rej) => {
                req(query, id).then(resp => {
                    res(resp.data)
                }).catch(() => { });
            })
        },
        create({ commit }, house) {
            return new Promise((res, rej) => {
                req(createHouseRecord, house).then(resp => {
                    res(resp.data);
                }).catch(() => {
                    rej();
                });
            })
        },
        update({ commit }, house) {
            return new Promise((res, rej) => {
                req(updateHouse, house).then(resp => {
                    res(resp.data);
                }).catch(() => {
                    rej();
                });
            })
        },
        remove({ commit }, id) {
            return new Promise((res, rej) => {
                req(remove, id).then(resp => {
                    res();
                }).catch(() => {
                    rej();
                })
            });
        },
        addMsg({ commit }, msg) {
            return new Promise((res, rej) => {
                req(addMsg, msg).then(resp => {
                    res(resp.data);
                }).catch(() => {
                    rej();
                })
            })
        },
        queryMessages({ commit }, param) {
            return new Promise((res, rej) => {
                req(queryMsgs, param).then(resp => {
                    res(resp.data);
                }).catch(() => {
                    rej();
                })
            })
        },
        disableMsg({ commit }, ps) {
            return new Promise((res, rej) => {
                req(disableMsg, ps).then(resp => {
                    res();
                }).catch(() => {
                    rej();
                })
            })
        },
        score({ commit }, sc) {
            return new Promise((res, rej) => {
                req(score, sc).then(resp => {
                    res();
                }).catch(() => {
                    rej();
                })
            })
        },
        aggScore({ commit }, param) {
            return new Promise((res, rej) => {
                req(aggScore, param).then(resp => {
                    res(resp.data);
                }).catch(() => {
                    rej();
                })
            })
        },
        rent({ commit }, h) {
            return new Promise((res, rej) => {
                req(rent, h).then(resp => {
                    res(resp.data);
                }).catch(() => {
                    rej();
                })
            })
        },
        queryRentList({ commit }, param) {
            return new Promise((res, rej) => {
                req(rentList, param).then(resp => {
                    res(resp.data);
                }).catch(() => {
                    rej();
                })
            })
        },
        publish({ commit }, h) {
            return new Promise((res, rej) => {
                req(publishHouse, h).then(resp => {
                    res(resp.data);
                }).catch(() => {
                    rej();
                })
            })
        },
        audit({ commit }, h) {
            return new Promise((res, rej) => {
                req(auditHouse, h).then(resp => {
                    res(resp.data);
                }).catch(() => {
                    rej();
                })
            })
        }
    }
}

export default house