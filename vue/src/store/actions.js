import { axiosInstance } from "@/util/request";

export function updateUserInfo({ commit }) {
    return axiosInstance.get('/user')
        .then(({ data }) => {
            const bol = data && data.enabled;
            if (bol) {
                commit('setLogin', true);
                commit('setUserInfo', data.data);
            }
            return bol
        })
}