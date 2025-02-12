import {HttpClient} from './http-client.js';

class ChatCrud {

    limpar() {
        let s = `form[data-form='create']`;
        const form = document.querySelector(s);
        for (let i = 0; i < form.length; i++) {
            const input = form[i];
            input.value = "";
        }
    }

    create(e) {
        const action = e.target.attributes['data-form'].nodeValue;
        let s = `form[data-form='${action}']`;
        const form = document.querySelector(s);
        const obj = createObjectFrom(form);
        save(obj);
    }

    save(obj) {
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
                console.log("TESTE", d);
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
    }

}

export {ChatCrud};
