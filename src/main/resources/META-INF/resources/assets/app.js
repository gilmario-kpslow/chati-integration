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

    limparMessagem();

};

const limparMessagem = (time) => {
    setTimeout(() => {
        const contem = document.getElementById('messagem');
        contem.innerHTML = '';
    }, time || 2000);

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
            case 'delete':
                form.onclick = deletar;
                break;
            case 'lista':
                form.onclick = lista;
                break;
            case 'download':
                form.onclick = download;
                break;
            case 'novo':
                form.onclick = novo;
                break;
            case 'upload':
                form.onclick = upload;
                break;
            default :
                ;
        }
    }

    const up = document.querySelector("input[data-action='upload']");
    up.addEventListener('input', upload);

};

const createObjectFrom = (form) => {
    const obj = {};
    for (let i = 0; i < form.length; i++) {
        const input = form[i];
        obj[input.attributes['name'].textContent] = input.value;
    }
    return obj;
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
        resp.json().then(d => {
            hideModal();
            hideLoader();
            lista();
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
        criarLista(resp);
    });
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
        input.value = a[input.attributes['name'].textContent];
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
        const div = document.createElement('div');
        div.classList.add('col-md-4');

        const card = document.createElement('div');
        card.classList.add('border');
        card.classList.add('rounded');
        card.classList.add('p-4');
        card.classList.add('mb-2');
        card.classList.add('hover-efect');

        const titulo = document.createElement('div');
        titulo.classList.add('h4');
        titulo.textContent = a.titulo;
        card.appendChild(titulo);

        const id = document.createElement('div');
        id.classList.add('fs-6');
        id.classList.add('text-secondary');
        id.classList.add('opacity-50');
        id.classList.add('shadow-sm');
        id.textContent = a.id;
        card.appendChild(id);

        const info = document.createElement('div');
        info.classList.add('info');
        info.classList.add('mb-1');
        info.classList.add('border-top');
        info.textContent = a.mensagem;
        card.appendChild(info);

        const botaoEditar = criarBotao('Editar', () => editar(a));
        botaoEditar.setAttribute('data-bs-target', '#exampleModal');
        botaoEditar.setAttribute('data-bs-backdrop', 'static');
        botaoEditar.setAttribute('data-bs-toggle', 'modal');

        const botaoExcluir = criarBotao('Excluir', () => {
            let s = `form[data-form='delete']`;
            const form = document.querySelector(s);
            form[0].value = a.id;
        });
        botaoExcluir.setAttribute('data-bs-target', '#confirmar');
        botaoExcluir.setAttribute('data-bs-backdrop', 'static');
        botaoExcluir.setAttribute('data-bs-toggle', 'modal');

        card.append(botaoEditar);
        card.append(botaoExcluir);
        card.append(criarBotao('Teste', () => executar(a.id)));
        div.appendChild(card);
        container.appendChild(div);
    });
};

const criarBotao = (titulo, acao) => {
    const botao = document.createElement('button');
    botao.classList.add('btn');
    botao.classList.add('btn-outline-primary');
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
        a.download = `backup.json`;
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


const limpar = () => {
    let s = `form[data-form='create']`;
    const form = document.querySelector(s);
    for (let i = 0; i < form.length; i++) {
        const input = form[i];
        input.value = "";
    }
};

const upload = (e) => {
    showLoader();
    if (e.target.files && e.target.files.length > 0) {
        const reader = new FileReader();
        reader.onload = (event) => {
            const data = event.target.result;
            const registros = JSON.parse(data);
            registros.forEach(r => {
                save(r);
            });
        };
        reader.readAsText(e.target.files[0]);
    }
};

init();

