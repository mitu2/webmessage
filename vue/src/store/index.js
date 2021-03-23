import { createStore } from 'vuex'
import { updateUserInfo } from "@/store/actions";

const store = createStore({
  state: {
    isLogin: false,
    userInfo: {},
  },
  mutations: {
    setLogin(state, bol) {
      state.isLogin = bol;
    },
    setUserInfo(state, userInfo) {
      state.userInfo = userInfo;
    }
  },
  actions: {
    updateUserInfo
  },
  modules: {
  }
});
export default store

