class Loader {

    el = undefined;

    constructor(pai) {
        this.el = document.createElement('div');
        this.el.classList.add('loader');
        this.el.classList.add('d-none');
        this.el.classList.add('rounded');
        this.el.classList.add('mx-2');
        this.el.classList.add('my-2');
        this.el.classList.add('p-2');
        this.el.classList.add('bg-info');
        this.el.classList.add('bg-gradient');
        this.el.classList.add('text-light');

        const content = document.createElement('div');
        content.classList.add('loader-content');

        const icon = document.createElement('i');
        icon.classList.add('fa');
        icon.classList.add('fa-refresh');
        icon.classList.add('fa-spin');
        icon.classList.add('fa-5x');

        content.appendChild(icon);

        const text = document.createElement('div');
        text.innerHTML = 'Aguarde...';
        content.appendChild(text);

        this.el.appendChild(content);



        pai.appendChildren(this.el);
    }

    showLoader = () => {
        const loader = document.getElementById('loader');
        loader.classList.remove('d-none');
    }

    hideLoader = () => {
        setTimeout(() => {
            const loader = document.getElementById('loader');
            loader.classList.add('d-none');
        }, 400);
    }

}

export {Loader};