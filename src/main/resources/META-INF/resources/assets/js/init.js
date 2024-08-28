import { StarLoad } from './star-load.js';
import { Socket } from './websocket.js';
import { Modal } from './modal.js';
import { habilitarNotificacoes, notificar } from './notifica.js';

const star = new StarLoad();

let listaChats = [];

let logado = false;

let socket;

const limpar = () => {
    let s = `form[data-form='create']`;
    const form = document.querySelector(s);
    for (let i = 0; i < form.length; i++) {
        const input = form[i];
        input.value = "";
    }
};

const create = (e) => {
    const action = e.target.attributes['data-form'].nodeValue;
    let s = `form[data-form='${action}']`;
    const form = document.querySelector(s);
    const obj = createObjectFrom(form);
    save(obj);
};

const save = (obj) => {
    showLoader();
    const req = new Request('registro', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(obj)
    });
    fetch(req).then((resp) => {
        if (resp.status > 299) {
            resp.json().then(d => {
                error(d.messagem);
            });
            return;
        }
        resp.json().then(d => {
            lista();
            hideModal();
            messagem("Sucesso!", "Registro criado com sucesso!", 'success');
        }).catch((e) => {
            error(e);
        }).finally(() => {
            hideLoader();
        });
    }
    ).catch((e) => {
        error(e);
        hideLoader();
    });
};

const showLoader = () => {
    const loader = document.getElementById('loader');
    loader.classList.remove('d-none');
};

const hideLoader = () => {
    setTimeout(() => {
        const loader = document.getElementById('loader');
        loader.classList.add('d-none');
    }, 400);
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
    limparMessagem(2000, m);

    notificar(titulo, messagem);
};

const limparMessagem = (time, mensagem) => {
    setTimeout(() => {
        const contem = document.getElementById('messagem');
        contem.removeChild(mensagem);
    }, time || 2000);
};

const criarAcoes = () => {
    const menu = document.getElementById('acoes');
    const mapNovo = new Map();
    mapNovo.set('data-bs-toggle', 'modal');
    mapNovo.set('data-bs-target', '#exampleModal');
    mapNovo.set('data-bs-backdrop', 'static');

    const mapUp = new Map();
    mapUp.set('data-bs-toggle', 'modal');
    mapUp.set('data-bs-target', '#uploaderModal');
    mapUp.set('data-bs-backdrop', 'static');

    const cl = ['btn', 'btn-outline-primary', 'dropdown-item'];

    const novoItem = criarItem(mapNovo, cl, 'Novo', novo);

    const listaItem = criarItem(null, cl, 'Atualizar', lista);

    const downloadItem = criarItem(null, cl, 'Donwload Backup', download);

    const uploadItem = criarItem(mapUp, cl, 'Upload Backup', null);

    const notificaItem = criarItem(null, cl, 'Notificar', habilitarNotificacoes);

    menu.appendChild(novoItem);
    menu.appendChild(listaItem);
    menu.appendChild(downloadItem);
    menu.appendChild(uploadItem);
    menu.appendChild(notificaItem);
};

const criarItem = (att, cl, name, call) => {
    const li = document.createElement('li');
    const button = document.createElement('button');
    button.setAttribute('type', 'button');
    li.appendChild(button);

    if (att) {
        att.forEach((k, v) => {
            console.log(k, v);
            button.setAttribute(v, k);
        });
    }

    if (cl) {
        cl.forEach((k) => {
            button.classList.add(k);
        });
    }

    button.textContent = name;
    button.addEventListener('click', call);

    return li;
};

const initUpload = () => {
    const up = document.querySelector("input[data-action='upload']");
    up.addEventListener('input', upload);
    const filtro = document.getElementById('filtro');
    filtro.addEventListener('keyup', filtrar);
};

const initWebSocket = () => {
    socket = new Socket();
    socket.connect('gilmario', (a) => {
        console.log(a);
    }, (m) => {
        console.log(m);
        messagem("Ops!", m.data, 'info');
    }, (e) => messagem("Error", e, 'error'));
};



const createObjectFrom = (form) => {
    const obj = {};
    for (let i = 0; i < form.length; i++) {
        const input = form[i];
        if (input.attributes['name']) {
            obj[input.attributes['name'].textContent] = input.value;
        }
    }
    return obj;
};

const saveTag = (obj) => {
    showLoader();
    const req = new Request('tag', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(obj)
    });
    fetch(req).then((resp) => {
        resp.json().then(d => {
            hideModal();
            hideLoader();
            listaTags();
            messagem("Sucesso!", "Registro criado com sucesso!", 'success');
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
    document.querySelectorAll('button[data-bs-dismiss]').forEach(a => a.click());
};
const novo = () => {
    limpar();
};
const lista = () => {
    pesquisarLista((resp) => {
        listaChats = resp;
        filtrar();
    });
};

const filtrar = (event) => {
    let filtro = "";
    if (event) {
        console.log(event.target.value);
        filtro = event.target.value.toLowerCase();
    }
    criarLista(listaChats.filter(a => a.titulo.toLowerCase().startsWith(filtro) || a.mensagem.toLowerCase().startsWith(filtro)));
};

const filtrarClear = () => {
    const filtro = document.getElementById('filtro').value = "";
    criarLista(listaChats);
};

const executar = (id, params) => {
    showLoader();
    const req = new Request(`registro/executar/${id}?${params}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    fetch(req).then((resp) => {
        hideLoader();
        if (resp.status <= 299) {
            hideLoader();
            messagem('OK', 'Testa enviado', 'INFO');
        } else {
            resp.json().then(r => {
                messagem('Error', r.messagem, 'DANGER');
            });
        }

    }).catch((e) => {
        error(e);
        hideLoader();
    });
};
const deletar = (e) => {
    const action = e.target.attributes['data-form'].nodeValue;
    let s = `form[data-form='${action}']`;
    const form = document.querySelector(s);
    const obj = createObjectFrom(form);
    showLoader();
    const req = new Request(`registro/${obj.id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    fetch(req).then((resp) => {
        hideLoader();
        if (resp.status <= 299) {
            hideModal();
            lista();
            messagem('Sucesso!', 'Registro excluido com sucesso', 'SUCCESS');
            hideLoader();
        } else {
            resp.json().then(r => {
                messagem('Error', r.messagem, 'DANGER');
            });
        }

    }).catch((e) => {
        error(e);
        hideLoader();
    });
};
const editar = (a) => {
    let s = `form[data-form='create']`;
    const form = document.querySelector(s);
    for (let i = 0; i < form.length; i++) {
        const input = form[i];
        if (input.attributes['name']) {
            input.value = a[input.attributes['name'].textContent];
        }
    }
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

    container.innerHTML = '';
    lista.forEach(a => {

//        console.log(a);

        const div = document.createElement('div');
        div.classList.add('col-xl-3');
        div.classList.add('col-lg-4');
        div.classList.add('col-md-6');
        div.classList.add('col-sm-6');
        div.classList.add('col-xs-12');
        const card = document.createElement('div');
        card.classList.add('rounded');
        card.classList.add('shadow-sm');
        card.classList.add('p-3');
        card.classList.add('mb-2');
        card.classList.add('hover-efect');
        card.style.backgroundColor = `${a.cor}ff`;
        const tituloContent = document.createElement('div');
        tituloContent.classList.add('d-flex');
        tituloContent.classList.add('justify-content-between');
        tituloContent.classList.add('border-bottom');

        const titulo = document.createElement('div');
        titulo.classList.add('h4');
        titulo.classList.add('text-light');
        titulo.textContent = a.titulo;
        tituloContent.appendChild(titulo);
        tituloContent.appendChild(menuCard(a));

        card.appendChild(tituloContent);

//        const id = document.createElement('div');
//        id.classList.add('fs-6');
//        id.classList.add('text-light');
//        id.classList.add('opacity-50');
//        id.classList.add('shadow-sm');
//        id.textContent = a.id;
//        card.appendChild(id);
        const info = document.createElement('div');

        const divInfo = document.createElement('div');

        const labelMensagem = document.createElement('label');
        labelMensagem.textContent = "Mensagem";
        labelMensagem.classList.add('text-light');
        labelMensagem.classList.add('fw-bolder');

        divInfo.appendChild(labelMensagem);

        info.classList.add('info');
        info.classList.add('mb-2');
        info.classList.add('py-2');
        info.classList.add('text-light');
//        info.classList.add('border-top');
        info.textContent = a.mensagem;
        divInfo.appendChild(info);

        card.appendChild(divInfo);
//        const acoes = document.createElement('div');
//        acoes.classList.add('d-flex');
//        acoes.classList.add('justify-content-between');
//        acoes.classList.add('border-top');
//        card.appendChild(acoes);
//        const botaoEditar = criarBotao('Editar', () => editar(a));
//        botaoEditar.setAttribute('data-bs-target', '#exampleModal');
//        botaoEditar.setAttribute('data-bs-backdrop', 'static');
//        botaoEditar.setAttribute('data-bs-toggle', 'modal');
//        const botaoExcluir = criarBotao('Excluir', () => {
//            let s = `form[data-form='delete']`;
//            const form = document.querySelector(s);
//            form[0].value = a.id;
//        });
//        botaoExcluir.setAttribute('data-bs-target', '#confirmar');
//        botaoExcluir.setAttribute('data-bs-backdrop', 'static');
//        botaoExcluir.setAttribute('data-bs-toggle', 'modal');
//        acoes.append(botaoEditar);
//        acoes.append(botaoExcluir);
//        acoes.append(criarBotao('Teste', () => executar(a.id)));
        const link = document.createElement('div');

        const label = document.createElement('label');
        label.textContent = "URL";
        label.classList.add('text-light');
        label.classList.add('fw-bolder');

        link.appendChild(label);

        const input = document.createElement('textarea');
//        input.classList.add('text-light');
        input.setAttribute('readonly', '');
        input.setAttribute('rows', '4');
        input.classList.add('form-control');



        const params = parametros(a.mensagem);
        const url = `${location.href}registro/executar/${a.id}${params}`;
        input.value = url;
        input.setAttribute('title', 'Click para copiar a URL');
        input.addEventListener('click', () => {
            copy(url);
            messagem('Info', `URL: ${url}\r\n copiada para a área de trabalho.`, 'INFO');
        });

        link.appendChild(input);

        card.append(link);

        div.appendChild(card);
        container.appendChild(div);
    });
};

const copy = (text) => {
    if ('clipboard' in navigator) {
        return navigator.clipboard.writeText(text).then();
    }
};

const past = (call) => {
    if ('clipboard' in navigator) {
        return navigator.clipboard.readText().then((t => call(t)));
    }
};

const parametros = (a) => {

    const regex = /\${\w+}/g;
    const infos = a.match(regex);

    if (infos) {
        return `?${infos.map(a => a.replace('$', '').replace('{', '').replace('}', '')).reduce((a, b) => `${a}=xxxxx&${b}`)}=xxxxx`;
    }
    return "";
};

const menuCard = (a) => {

    const drop = document.createElement('div');
    drop.classList.add('dropdown');
    drop.classList.add('dropstart');

    const botaoAtivador = document.createElement('button');
    botaoAtivador.classList.add('btn');
    botaoAtivador.classList.add('btn-default');
    botaoAtivador.classList.add('dropdown-toggle');
    botaoAtivador.classList.add('hidden-seta-menu');
    botaoAtivador.setAttribute('type', 'button');
    botaoAtivador.setAttribute('data-bs-toggle', 'dropdown');
    botaoAtivador.setAttribute('aria-expanded', 'false');

    const img = document.createElement('img');
    img.setAttribute('src', './assets/more_vert_white.svg');
    img.setAttribute('width', '24');
    img.setAttribute('alt', 'Menu');
    img.classList.add('img-fluid');

    botaoAtivador.appendChild(img);

    drop.appendChild(botaoAtivador);

    const ul = document.createElement('ul');
    ul.classList.add('dropdown-menu');

    const li1 = document.createElement('li');

    const botaoEditar = criarBotao('Editar', () => editar(a));
    botaoEditar.setAttribute('data-bs-target', '#exampleModal');
    botaoEditar.setAttribute('data-bs-backdrop', 'static');
    botaoEditar.setAttribute('data-bs-toggle', 'modal');
    botaoEditar.classList.add('dropdown-item');
    botaoEditar.classList.add('text-dark');

    li1.appendChild(botaoEditar);
    ul.appendChild(li1);


    const li2 = document.createElement('li');
    const botaoExcluir = criarBotao('Excluir', () => {
        let s = `form[data-form='delete']`;
        const form = document.querySelector(s);
        form[0].value = a.id;
    });
    botaoExcluir.setAttribute('data-bs-target', '#confirmar');
    botaoExcluir.setAttribute('data-bs-backdrop', 'static');
    botaoExcluir.setAttribute('data-bs-toggle', 'modal');
    botaoExcluir.classList.add('dropdown-item');
    botaoExcluir.classList.add('text-dark');

    li2.appendChild(botaoExcluir);
    ul.appendChild(li2);

    const li3 = document.createElement('li');
    const btnTeste = criarBotao('Teste', () => {
        executar(a.id);
    });
    btnTeste.classList.add('dropdown-item');
    btnTeste.classList.add('text-dark');

    li3.appendChild(btnTeste);
    ul.appendChild(li3);

    drop.appendChild(ul);

    return drop;

};

const criarBotao = (titulo, acao) => {
    const botao = document.createElement('button');
    botao.classList.add('btn');
    botao.classList.add('btn-outline-secondary');
    botao.classList.add('flex-fill');
    botao.classList.add('text-light');
    botao.classList.add('border-light');
    botao.setAttribute('type', 'button');
    botao.textContent = titulo;
    botao.addEventListener('click', acao);
    return botao;
};

const download = () => {
    const dados = pesquisarLista((resp) => {
        const filesql = window.URL.createObjectURL(new Blob([JSON.stringify(resp)], {type: 'application/json'}));
        const a = document.createElement('a');
        a.href = filesql;
        const date = new Date();
        a.download = `chatbackup-backup-${location.hostname}-${date.getFullYear()}${date.getMonth()}${date.getDate()}-${date.getHours()}${date.getMinutes()}${date.getSeconds()}.json`;
        a.target = '_blank';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    });
};

const pesquisarLista = (call) => {
    showLoader();
    const req = new Request('registro', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    fetch(req).then((resp) => {
        resp.json().then(d => {
            call(d);
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

const getVersao = (call) => {
    showLoader();
    const req = new Request('versao', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    fetch(req).then((resp) => {
        resp.json().then(d => {
            call(d);
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

const upload = (e) => {
    showLoader();
    if (e.target.files && e.target.files.length > 0) {
        const reader = new FileReader();
        reader.onload = (event) => {
            const data = event.target.result;
            console.log(data);
            const req = new Request('registro/restore', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: data
            });
            fetch(req).then((resp) => {
                if (resp.status > 299) {
                    resp.json().then(d => {
                        error(d.messagem);
                    });
                    return;
                }

                lista();
                hideModal();
                messagem("Sucesso!", "Backup restaurado!", 'success');
                hideLoader();

            }
            ).catch((e) => {
                error(e);
                hideLoader();
            });
        };
        reader.readAsText(e.target.files[0]);
    }
};

const loadTags = (app) => {
    showLoader();
    const req = new Request('tag', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    fetch(req).then((resp) => {
        resp.json().then(d => {
            app.tags = d;
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

const init = () => {

    const loader = document.getElementById('loader');
    star.init(loader);
    initUpload();
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
            case 'delete':
                form.onclick = deletar;
                break;
            case 'filtroClear':
                form.onclick = filtrarClear;
                break;
            default :
                ;
        }
    }

    criarAcoes();
    initWebSocket();

    getVersao((data) => {
        lista();
        const ele = document.getElementById("versao");
        console.log(data);

        ele.textContent = `${data.projeto}: ${data.versao} - ${data.data}`;
    });

    // const div = document.createElement("div");
    // div.textContent = "FOOTER";

    // const divh = document.createElement("div");
    // divh.textContent = "HEADER";

    // const corpo = document.createElement("div");
    // corpo.textContent = "CORPO";

    // const modal = new Modal('teste');
    // console.log(modal);
    // const modal_ = modal.create(divh, corpo, div);
    // document.body.appendChild(modal_);
    // if (modal) {
    //     modal.show();
    // }
};


try {
    init();
} catch (e) {
    console.log(e);
}

