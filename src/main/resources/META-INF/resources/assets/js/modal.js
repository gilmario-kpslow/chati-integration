import {Component} from './component.js';
class Modal extends Component {

    el = undefined;
    backdrop = undefined;
    id = undefined;
    pai = undefined;
    constructor(id) {
        super();
        this.id = id;
    }

    create(header, content, footer) {

        this.backdrop = document.createElement('div');
        this.backdrop.classList.add('modal-backdrop');
        this.backdrop.classList.add('fade');
        this.backdrop.classList.add('show');
        this.backdrop.addEventListener('click', this.pulse);

        this.el = document.createElement('div');
        this.el.classList.add('modal');
        this.el.classList.add('fade');
        this.el.setAttribute('data-bs-backdrop', 'static');
        this.el.setAttribute('tabindex', '-1');
        this.el.setAttribute('aria-labelledby', this.id);
        this.el.setAttribute('aria-hidden', 'true');
        this.el.setAttribute('id', this.id);
        this.el.style.display = 'none';
//        this.el.addEventListener('click', this.pulse);

        const def = document.createElement('div');
        def.classList.add('modal-dialog');
        def.classList.add('modal-dialog-centered');

        this.el.appendChild(def);

        const _content = document.createElement('div');
        _content.classList.add('modal-content');

        def.appendChild(_content);

        // <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>

        const buttonHide = document.createElement('button');
        buttonHide.setAttribute('type', 'button');
        buttonHide.setAttribute('data-bs-dismiss', 'modal');
        buttonHide.setAttribute('aria-label', 'Close');
        buttonHide.classList.add('btn-close');
        buttonHide.addEventListener('click', this.hide);


        const _header = document.createElement('div');
        _header.classList.add('modal-header');
        _header.appendChild(header);
        _header.appendChild(buttonHide);
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

    show() {
        console.log(this.id);
        document.body.appendChild(this.backdrop);
        const modal = document.getElementById(this.id);
        modal.classList.add('show');
        modal.style.display = 'block';
        modal.setAttribute('aria-hidden', 'false');
        modal.setAttribute('modal', 'true');
        modal.setAttribute('role', 'dialog');
        document.body.classList.add('modal-open');
        document.body.style.overflow = 'hidden';
        document.body.style.paddingRight = '0px';
        document.body.setAttribute('data-bs-overflow', 'hidden');
        document.body.setAttribute('data-bs-padding-right', '0px');
    }

    hide = () => {
        const modal = document.getElementById(this.id);
        modal.classList.remove('show');
//        document.body.style = undefined;

        setTimeout(() => {
            document.body.classList.remove('modal-open');
            modal.classList.add('show');
            modal.style.display = 'none';
            modal.setAttribute('aria-hidden', 'true');
            modal.removeAttribute('modal');
            document.body.removeChild(this.backdrop);

        }, 400);
    }

    pulse() {
        const modal = document.getElementById(this.id);
        modal.classList.add('modal-static');
//        document.body.style = undefined;

        setTimeout(() => {
            modal.classList.remove('modal-static');
        }, 400);
    }

}

export {Modal};