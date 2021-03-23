import axios from "axios";
import router from "@/router";
import store from "@/store";

export const axiosInstance = createAxios();

async function createAxios(config) {
    const instance = axios.create(config);
    const token = localStorage.getItem('token');
    if (token && token.length > 0) {
        if (await store.dispatch('updateUserInfo')) {
            instance.defaults.headers['Authorization'] = 'Bearer ' + token;
        }

    }
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
            localStorage.setItem('Token', data.data);
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
        Vue.config.globalProperties.$http = axiosInstance;
    }
};