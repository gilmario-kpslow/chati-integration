

//function chamada() {
//
//    showLoader();
//    const mesagem = {text: "Ola eu sou o chati chato"};
//
//    const req = new Request('https://chat.googleapis.com/v1/spaces/AAAAIXqQ2dQ/messages?key=AIzaSyDdI0hCZtE6vySjMm-WEfRq3CPzqKqqsHI&token=RL13cy_x537y_CniJPXkAAmR_nFfDGp_OPokt0ax81w%3D',
//            {
//                method: 'POST',
//                headers: {
//                    'Content-Type': 'application/json'
//                },
//                body: JSON.stringify(mesagem)
//            });
//
//    fetch(req).then((resp) => {
//        resp.json().then(d => {
//            console.log(d);
//            hideLoader();
//        }).catch((e) => {
//            error(e);
//            hideLoader();
//        });
//    }).catch((e) => {
//        error(e);
//        hideLoader();
//    });
//
//
//}

const showLoader = () => {
    const loader = document.getElementById('loader');
    loader.classList.remove('d-none');
};

const hideLoader = () => {
    const loader = document.getElementById('loader');
    loader.classList.add('d-none');
};

const error = (e) => {
    console.log(e);
    hideLoader();
    messagem('ERRO', e, 'DANGER');
};

const messagem = (titulo, messagem, tipo = 'SUCCESS') => {

    const m = document.createElement('div');
    m.classList.add('alert');
    m.classList.add(`alert-${tipo.toLowerCase()}`);
    const h5 = document.createElement('h6');
    h5.classList.add('alert-heading');
    h5.innerHTML = titulo;

    const detalhe = document.createElement('span');
    detalhe.innerHTML = messagem;

    m.appendChild(h5);
    m.appendChild(detalhe);

    const contem = document.getElementById('messagem');
    contem.appendChild(m);

    setTimeout(limparMessagem, 2000);

};

const limparMessagem = () => {
    const contem = document.getElementById('messagem');
    contem.innerHTML = '';
};

const init = () => {
//    console.log('init');
    const forms = document.getElementsByTagName("button");
    for (let i = 0; i < forms.length; i++) {
        const form = forms.item(i);
        const attribute = form.attributes['data-action'];
        if (!attribute) {
            continue;
        }
        const acao = attribute.nodeValue;

        if (!acao) {
            continue;
        }

        switch (acao) {
            case 'create':
                form.onclick = create;
                break;
            case 'lista':
                form.onclick = lista;
                break;
            default :
                ;
        }
    }
};

// Extrair os inputs de um form
const createObjectFrom = (form) => {
    const obj = {};
    for (let i = 0; i < form.length; i++) {
        let input = form[i];
        console.log(input.attributes['name'].textContent);
        obj[input.attributes['name'].textContent] = input.value;
    }
    return obj;
};

const create = (e) => {
//    console.log('CREATE', e.target);
    const action = e.target.attributes['data-form'].nodeValue;
//    console.log(action);
    let s = `form[data-form='${action}']`;
//    console.log(s);
    const form = document.querySelector(s);

//    console.log(form);
    const obj = createObjectFrom(form);
    console.log(obj);
    showLoader();

    const req = new Request('registro',
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(obj)
            });

    fetch(req).then((resp) => {
        resp.json().then(d => {
            console.log(d);
            // Hide Modal
            hideModal();
            hideLoader();
            lista();
        }).catch((e) => {
            error(e);
            hideLoader();
        });
    }).catch((e) => {
        error(e);
        hideLoader();
    });
};

const hideModal = () => {
    const btn = document.querySelector('button[data-bs-dismiss]');
    btn.click();
    console.log(btn);

};

const lista = (e) => {
    showLoader();
    const req = new Request('registro',
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

    fetch(req).then((resp) => {
        resp.json().then(d => {
            console.log(d);
            criarLista(d);
            hideLoader();
        }).catch((e) => {
            error(e);
            hideLoader();
        });
    }).catch((e) => {
        error(e);
        hideLoader();
    });
};

/**/
const criarLista = (lista) => {


    const divs = document.getElementsByTagName('div');

    let container;
    for (let i = 0; divs.length; i++) {
        const el = divs.item(i);
        if (el.attributes['data-lista']) {
            container = el;
            break;
        }
    }

    console.log(container);

    container.innerHTML = '';

    lista.forEach(a => {
        const div = document.createElement('div');
        div.classList.add('col-md-4');

        const card = document.createElement('div');
        card.classList.add('border');
        card.classList.add('rounded');
        card.classList.add('p-4');
        card.classList.add('mb-2');

        const titulo = document.createElement('div');
        titulo.classList.add('h4');
        titulo.textContent = a.titulo;
        card.appendChild(titulo);

        const info = document.createElement('div');
        info.classList.add('info');
        info.textContent = a.mensagem;
        card.appendChild(info);

        div.appendChild(card);

        container.appendChild(div);
    });
};


init();

