import {Component} from './component.js';
class Modal extends Component {

    el = undefined;
    id = undefined;
    pai = undefined;
    constructor(id, pai) {
        super();
        this.id = id;
    }

    create(header, content, footer) {
        // <div class="modal fade" id="uploaderModal" data-bs-backdrop="static" tabindex="-1" aria-labelledby="uploaderModal" aria-hidden="true">
        this.el = document.createElement('div');
        this.el.classList.add('modal');
        this.el.classList.add('fade');
        this.el.setAttribute('data-bs-backdrop', 'static');
        this.el.setAttribute('tabindex', '-1');
        this.el.setAttribute('aria-labelledby', this.id);
        this.el.setAttribute('aria-hidden', 'true');

        const def = document.createElement('div');
        def.classList.add('modal-dialog');
        def.classList.add('modal-dialog-centered');

        this.el.appendChild(def);

        const _content = document.createElement('div');
        _content.classList.add('modal-content');

        def.appendChild(_content);

        const _header = document.createElement('div');
        _header.classList.add('modal-header');
        _header.appendChild(header);
        _content.appendChild(_header);

        const _body = document.createElement('div');
        _body.classList.add('modal-body');
        _body.appendChild(content);
        _content.appendChild(_body);


        const _footer = document.createElement('div');
        _footer.classList.add('modal-footer');
        _footer.appendChild(footer);
        _content.appendChild(_footer);

        return this.el;
    }

    show = () => {
        console.log(this.id);
        const botao = document.createElement('button');
        botao.setAttribute('type', 'button');
        botao.textContent = 'titulo';
        botao.setAttribute('data-bs-target', this.id);
        botao.setAttribute('data-bs-backdrop', 'static');
        botao.setAttribute('data-bs-toggle', 'modal');
        document.body.appendChild(botao);
        // botao.click();
        // document.body.removeChild(botao);
    }

    hide = () => {
        setTimeout(() => {
            const loader = document.getElementById('loader');
            loader.classList.add('d-none');
        }, 400);
    }

}

export {Modal};