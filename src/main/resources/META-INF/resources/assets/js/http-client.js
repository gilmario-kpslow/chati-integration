class HttpClient {

    api;
    startLoad;
    endLoad;
    constructor(api, startLoad, endLoad) {
        this.api = api;
        this.startLoad = startLoad;
        this.endLoad = endLoad;
    }

    post(url, data, call) {

    }

    get(url, call) {
        this.load();
        try {
            const req = new Request(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            fetch(req).then((resp) => {
                resp.json().then(d => {
                    if (call) {
                        call(d);
                    }
                });
            });
        } catch (e) {
            throw e;
        } finally {
            this.end();
        }
    }

    load() {
        if (this.startLoad) {
            this.startLoad();
        }
    }

    end() {
        if (this.endLoad) {
            this.endLoad();
        }
    }

}

export {HttpClien};