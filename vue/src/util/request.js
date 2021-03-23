import axios from "axios";
import router from "@/router";

export const TOKE_NAME = 'Token';
export let axiosInstance = createAxios();

function createAxios(config) {
    const instance = axios.create(config);

    // 添加响应拦截器
    instance.interceptors.response.use(function (response) {
        // 对响应数据做点什么
        return response;
    }, function (err) {
        if (err && err.response && err.response.status === 403) {
            router.push('/login')
                .then(() => console.log('用户未登录, 字动跳转登录页面'))
                .catch(err => console.log(err))
        }
        return Promise.reject(err);
    });
    return instance;
}

function notEmpty(method, ...parameters) {
    for (let parameter of parameters) {
        if (parameter === null || parameter === undefined || parameter.length === 0) {
            throw Error("method " + method + ' all parameter must not empty')
        }
    }
}


/**
 * 用户登录操作
 * @param username 用户名
 * @param password 密码
 * @returns {Promise<AxiosResponse<any>>}
 */
export function login(username, password) {
    notEmpty('login', username, password);
    return axiosInstance.post('/login', {
        username,
        password,
    }).then(({ data }) => {
        if (data && data.code === 200) {
            localStorage.setItem(TOKE_NAME, data.data);
            axiosInstance.defaults.headers['Authorization'] = 'Bearer ' + data.data;
        }
        return data;
    })
}

export function register(username, password, email) {
    notEmpty('register', username, password, email);
    return axiosInstance.post('/register', {
        username,
        password,
        email
    })
}

export default {
    install(Vue) {
        const { globalProperties } = Vue.config
        globalProperties.$http = axiosInstance;
        const token = localStorage.getItem(TOKE_NAME);
        if (token && token.length > 0) {
            axiosInstance.defaults.headers['Authorization'] = 'Bearer ' + token;
            const { $store } = globalProperties;
            $store.commit('setLogin', true)
            $store.dispatch('updateUserInfo')
                .then(status => {
                    if (!status) {
                        $store.commit('setLogin', false)
                    }
                })
                .catch(err => {
                    $store.commit('setLogin', false)
                    console.log(err)
                });
        }
    }
};
