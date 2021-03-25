import { ref, watch } from "vue";

class WebSocketUtil {

    #webSocket;
    #status;
    #SEND_JSON_CACHE = []

    constructor() {
        this.#status = ref('NO_CREATE');
    }

    get status() {
        return this.#status.value;
    }

    get isOk() {
        return this.status === 'OK';
    }

    watchStatus(callback) {
        return watch(this.#status, callback)
    }

    create(url = `ws://localhost:8080/websocket`, defaultSendObj = {}) {
        this.close()
        const token = localStorage.getItem('Token');
        const webSocket = new WebSocket(`${ url }?token=${ token }`);
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
    }

    sendObject(obj) {
        const json = JSON.parse(obj);
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