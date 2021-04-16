import axios from "axios";
import router from "@/router";
import { message } from "ant-design-vue";
import { watch } from "vue";

export let axiosInstance = createAxios();

function createAxios(config) {
    const instance = axios.create(config);

    // 添加响应拦截器
    instance.interceptors.response.use(function (response) {
        // 对响应数据做点什么
        return response;
    }, function (err) {
        if (err && err.response && err.response.status === 403) {
            message.error({ content: '登录过期, 正在为您跳转登陆页面', key: '403', duration: 2 })
            router.push('/login')
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
 * @param isRememberMe 是否记录
 * @returns {Promise<AxiosResponse<any>>}
 */
export function login(username, password, isRememberMe = true) {
    notEmpty('login', username, password);
    const data = {
        username,
        password,
    }
    if (isRememberMe) {
        data["remember-me"] = 'on';
    }
    return axiosInstance({
        method: 'post',
        url: '/login',
        data,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        transformRequest: [
            function (data) {
                return stringify(data)
            },
        ]
    });
}

function stringify(data) {
    let ret = ''
    for (const it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
    }
    ret = ret.substring(0, ret.lastIndexOf('&'))
    return ret
}


export function register(username, password, email) {
    notEmpty('register', username, password, email);
    return axiosInstance.post('/register', {
        username,
        password,
        email
    })
}

const urls = [ '/login', '/register' ];

function goLink(router, link, text) {
    router.push(link);
    message.error({ content: text, key: 'GO_LINK', duration: 2 });
}

function watchRoute(globalVueLib, immediate = false) {
    const { $store , $router } = globalVueLib;
    return watch(() => globalVueLib.$route, (to) => {
        if (!to) {
            return;
        }
        const { isLogin } = $store.state;
        if (urls.indexOf(to.path) === -1) {
            if (!isLogin) {
                goLink($router, '/login', '您未登录自动为您跳转登录页面');
            }
        } else {
            if (isLogin) {
                goLink($router, '/', '您已经登录, 如果访问请先退出登录');
            }
        }
    }, {
        immediate
    });
}

let unWatchRoute;

export default {
    install(Vue) {
        const { globalProperties } = Vue.config
        globalProperties.$http = axiosInstance;
        window.axios = axiosInstance;
        const { $store, $router } = globalProperties;

        // NOTE: 不需要检验Token格式了
        $store.dispatch('updateUserInfo')
            .then(status => {
                unWatchRoute && unWatchRoute();
                if (status) {
                    unWatchRoute = watchRoute(globalProperties, true)
                } else {
                    goLink($router, '/login', '您未登录自动为您跳转登录页面');
                    unWatchRoute = watchRoute(globalProperties)
                }
            })
            .catch(err => {
                console.log(err)
                unWatchRoute && unWatchRoute();
                goLink(router, '/login', '您未登录自动为您跳转登录页面');
                unWatchRoute = watchRoute(globalProperties)
            })
    }
};
