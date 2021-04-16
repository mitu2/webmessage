import { ref, watch } from "vue";
import { message } from "ant-design-vue";
import SockJS from "sockjs-client";

class WebSocketUtil {

    #webSocket;
    #status = ref('CLOSE');
    #messages = ref([]);
    #SEND_JSON_CACHE = [];

    constructor() {
    }

    get messages() {
        return this.#messages.value;
    }

    get status() {
        return this.#status.value;
    }

    get isOk() {
        return this.status === 'RUN';
    }

    watchStatus(callback, options) {
        return watch(this.#status, callback, options)
    }

    watchMessage(callback, options) {
        return watch(this.#messages, callback, options)
    }

    create(url = '/web-socket', defaultSendObj) {
        this.close()
        const webSocket = new SockJS(url);
        webSocket.onopen = () => {
            this.#status.value = 'RUN';
            this.#webSocket = webSocket;
            this.#SEND_JSON_CACHE.forEach(json => {
                this.#webSocket.send(json);
            })
            this.#SEND_JSON_CACHE = []
            this.sendObject(defaultSendObj);
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
            if (msg.type) {
                switch (msg.type) {
                    case 'ERROR':
                        message.error({ content: msg.message, key: msg.message, duration: 3 });
                        break
                    case 'TEXT':
                        this.#messages.value.push(msg);
                        break
                    case 'INFO':
                        console.log(`[ws]: ${ msg.data }`);
                        break
                    default:

                }
            }


        };
    }

    sendObject(obj) {
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
            this.#webSocket.close();
        }
    }

    get webSocket() {
        return this.#webSocket;
    }

    static install(Vue) {
        const globalProperties = Vue.config.globalProperties;
        globalProperties.$wsocket = new WebSocketUtil()
    }
}

export default WebSocketUtil;