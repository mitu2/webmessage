import { axiosInstance } from "@/util/request";

export function updateUserInfo(store) {
    const { commit } = store;
    return axiosInstance.get('/api/user')
        .then(({ data }) => {
            const bol = data.code === 200 && data.data.enabled;
            if (bol) {
                commit('setLogin', true);
                commit('setUserInfo', data.data);
            }
            return bol
        })
}

export function getUserRefGroup(store) {

}