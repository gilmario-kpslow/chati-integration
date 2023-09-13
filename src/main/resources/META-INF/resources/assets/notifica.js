const habilitarNotificacoes = () => {
    if (!("Notification" in window)) {
        alert("Este browser não suporta notificações de Desktop");
    }
    if (Notification.permission === "granted") {
        notificar("Opa!", "As notificações já estão ativas!");
    } else if (Notification.permission !== "denied") {
        Notification.requestPermission(function (permission) {
            notificar("Sucesso!", "As notificações foram ativadas!");
        });
    }
};

const notificar = (titulo, mensagem) => {
    const img = "assets/logo.svg";
    if (Notification.permission === "granted") {
        new Notification(titulo, {body: mensagem, icon: img});
    }
};

export {notificar, habilitarNotificacoes};