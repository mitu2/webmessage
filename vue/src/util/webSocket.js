import { ref, watch } from "vue";
import { message } from "ant-design-vue";

function getPort() {
    return location.port === '8090' ? '8080' : location.port;
}

class WebSocketUtil {

    #webSocket;
    #status = ref('NO_CREATE');
    #message = ref([]);
    #SEND_JSON_CACHE = [];

    url = `ws://${window.location.hostname}:${getPort()}/websocket`;

    constructor() {
    }

    get message() {
        return this.#message.value;
    }

    get status() {
        return this.#status.value;
    }

    get isOk() {
        return this.status === 'OK';
    }

    watchStatus(callback, options) {
        return watch(this.#status, callback, options)
    }
    watchMessage(callback, options) {
        return watch(this.#message, callback, options)
    }

    create(url = this.url, defaultSendObj) {
        this.close()
        const webSocket = new WebSocket(url);
        this.#status.value = 'CREATE';
        webSocket.onopen = () => {
            this.#status.value = 'OK';
            this.#webSocket = webSocket;
            this.#SEND_JSON_CACHE.forEach(json => {
                this.#webSocket.send(json);
            })
            this.#SEND_JSON_CACHE = []
            this.sendObject(defaultSendObj);
        };
        webSocket.onclose = () => {
            this.#status.value = 'CLOSE';
            // message.error({ content: "[WS] 链接断开", duration: 2 })
        }
        webSocket.onmessage = (ev) => {
            let msg;
            try {
                msg = JSON.parse(ev.data);
            } catch (e){
                console.error(e)
                return;
            }
            if (msg.type) {
                switch (msg.type) {
                    case 'ERROR':
                        message.error({content: msg.message, key: msg.message, duration: 3});
                        break
                    case 'TEXT':
                        this.#message.value.push(msg);
                        break
                    case 'INFO':
                        console.log(`[ws]: ${msg.data}`);
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