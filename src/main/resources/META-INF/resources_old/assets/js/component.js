class Component {

    el = undefined;
    options = undefined;

    constructor(options = undefined) {
        this.options = options;
    }

    create(pai) {

    }

    destroy() {
        this.el = undefined;
    }

}

export {Component};