import { createStore } from 'vuex'
import { axiosInstance } from "@/util/request";

const options = {
    state: {
        userConfig: {
            status: 'GUEST',
            myselfId: -1,
        },
        userCache: {},
        chatConfig: {
            id: -1,
            type: 'Broadcast'
        },
        chatCache: {}
    },
    mutations: {},
    actions: {},
    modules: {},
    getters: {}
};

options.mutations.initChatCache = function (state, name) {
    if (!state.chatCache[name]) {
        state.chatCache[name] = [];
    }
}

options.getters.isGuest = function (state/*, getters*/) {
    return state.userConfig.status === 'GUEST';
};

options.actions.updateUserConfig = function (store) {
    const { state } = store;
    return axiosInstance.get('/api/user')
        .then(({ data }) => {
            const user = data.data;
            const cond = data.code === 200 && user.enabled;
            if (cond) {
                state.userConfig = {
                    status: 'LOGIN',
                    myselfId: user.id
                }
                state.userCache[user.id] = user;
            }
            return cond
        })
};

export default createStore(options);
