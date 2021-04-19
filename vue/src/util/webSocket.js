import { ref } from "vue";
import SockJS from "sockjs-client";
import { message } from "ant-design-vue";

class WebSocketUtil {

    #webSocket;
    #status = ref('CLOSE');
    #typeHandlers = {};
    #SEND_JSON_CACHE = [];

    get status() {
        return this.#status;
    }

    get isOk() {
        return this.#status.value === 'RUN';
    }

    create(url = '/web-socket', defaultSendObj) {
        this.close()
        const webSocket = new SockJS(url);
        this.#status.value = 'RUN';
        webSocket.onopen = () => {
            message.success({ content: '服务器连接成功！', key: 'SERVER_SUCCESS' })
            this.#webSocket = webSocket;
            this.#SEND_JSON_CACHE.forEach(json => {
                this.#webSocket.send(json);
            })
            this.#SEND_JSON_CACHE = []
            this.sendJSON(defaultSendObj);
        };
        webSocket.onclose = () => {
            this.#status.value = 'CLOSE';
        }
        webSocket.onmessage = (ev) => {
            let msg;
            try {
                msg = JSON.parse(ev.data);
            } catch (e) {
                console.error(e)
                return;
            }
            const handlers = this.#typeHandlers[msg.type];
            if (handlers && handlers.length > 0 && Array.isArray(handlers)) {
                handlers.forEach(h => h(msg))
            }
        };
    }

    addHandler(type, callback) {
        if (!this.#typeHandlers[type]) {
            this.#typeHandlers[type] = []
        }
        let enabled = true;
        this.#typeHandlers[type].push((msg) => {
            if (enabled) {
                callback(msg);
            }
        })
        return () => enabled = false;
    }

    sendJSON(obj) {
        if (!obj || obj.length === 0) {
            return;
        }
        const json = JSON.stringify(obj);
        if (this.isOk) {
            this.#webSocket.send(json)
        }
        this.#SEND_JSON_CACHE.push(json);
    }

    close() {
        if (this.isOk) {
            this.#status.value = 'CLOSE';
            this.#webSocket.close();
        }
    }

    get webSocket() {
        return this.#webSocket;
    }

    static install(Vue) {
        const globalProperties = Vue.config.globalProperties;
        window.wsDebug = globalProperties.$wsocket = new WebSocketUtil()
    }
}

export default WebSocketUtil;