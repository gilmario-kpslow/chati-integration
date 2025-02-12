class StarLoad {
    canvas = undefined;
    context = undefined;
    tamanho = 100;
    tempo = 3000;
    animacao = 0;

    init(pai) {
        this.canvas = window.document.createElement('canvas');
        this.context = this.canvas.getContext('2d');
        pai.appendChild(this.canvas);
        this.canvas.setAttribute('width', `${this.tamanho}`);
        this.canvas.setAttribute('height', `${this.tamanho}`);
        this.draw();
    }

    draw() {
        this.limpar();
        this.drawAnimatedStar();
        requestAnimationFrame(this.draw.bind(this));
    }

    limpar() {
        this.context.clearRect(0, 0, this.tamanho, this.tamanho);
        this.linhasExternasAnimadas();
    }

    linhasExternasAnimadas() {
        const duracao = this.tempo / 9;
        const agora = new Date().getTime();
        if (this.animacao === 0) {
            this.animacao = agora;
        }

        let tempo = agora - this.animacao;
        this.context.strokeStyle = "blue";
        this.context.save();
        this.context.beginPath();
        this.animePoint(this.context, this.tamanho * 0.5, 0, this.tamanho * 0.85, this.tamanho * 0.15, tempo, duracao, tempo > 0, tempo >= duracao);
        this.animePoint(this.context, this.tamanho * 0.85, this.tamanho * 0.15, this.tamanho, this.tamanho * 0.5, tempo - duracao, duracao, tempo > duracao, tempo >= duracao * 2);
        this.animePoint(this.context, this.tamanho, this.tamanho * 0.5, this.tamanho * 0.85, this.tamanho * 0.85, tempo - duracao * 2, duracao, tempo > duracao * 2, tempo >= duracao * 3);
        this.animePoint(this.context, this.tamanho * 0.85, this.tamanho * 0.85, this.tamanho * 0.5, this.tamanho, tempo - duracao * 3, duracao, tempo > duracao * 3, tempo >= duracao * 4);
        this.animePoint(this.context, this.tamanho * 0.5, this.tamanho, this.tamanho * 0.15, this.tamanho * 0.85, tempo - duracao * 4, duracao, tempo > duracao * 4, tempo >= duracao * 5);
        this.animePoint(this.context, this.tamanho * 0.15, this.tamanho * 0.85, 0, this.tamanho * 0.5, tempo - duracao * 5, duracao, tempo > duracao * 5, tempo >= duracao * 6);
        this.animePoint(this.context, 0, this.tamanho * 0.5, this.tamanho * 0.15, this.tamanho * 0.15, tempo - duracao * 6, duracao, tempo > duracao * 6, tempo >= duracao * 7);
        this.animePoint(this.context, this.tamanho * 0.15, this.tamanho * 0.15, this.tamanho * 0.5, 0, tempo - duracao * 7, duracao, tempo > duracao * 7, tempo >= duracao * 8);
        this.context.stroke();
        this.context.closePath();
        this.context.restore();
        if (tempo > duracao * 9) {
            this.animacao = 0;
        }
    }

    drawAnimatedStar() {
        const duracao = this.tempo / 9;
        const agora = new Date().getTime();
        if (this.animacao === 0) {
            this.animacao = agora;
        }

        let tempo = agora - this.animacao;
        this.context.save();
        this.context.strokeStyle = "green";
        this.context.beginPath();
        this.animePoint(this.context, this.tamanho * 0.5, 0, this.tamanho * 0.85, this.tamanho * 0.85, tempo, duracao, tempo > 0, tempo >= duracao);
        this.animePoint(this.context, this.tamanho * 0.85, this.tamanho * 0.85, 0, this.tamanho * 0.5, tempo - duracao, duracao, tempo > duracao, tempo >= duracao * 2);
        this.animePoint(this.context, 0, this.tamanho * 0.5, this.tamanho * 0.85, this.tamanho * 0.15, tempo - duracao * 2, duracao, tempo > duracao * 2, tempo >= duracao * 3);
        this.animePoint(this.context, this.tamanho * 0.85, this.tamanho * 0.15, this.tamanho * 0.5, this.tamanho, tempo - duracao * 3, duracao, tempo > duracao * 3, tempo >= duracao * 4);
        this.animePoint(this.context, this.tamanho * 0.5, this.tamanho, this.tamanho * 0.15, this.tamanho * 0.15, tempo - duracao * 4, duracao, tempo > duracao * 4, tempo >= duracao * 5);
        this.animePoint(this.context, this.tamanho * 0.15, this.tamanho * 0.15, this.tamanho, this.tamanho * 0.5, tempo - duracao * 5, duracao, tempo > duracao * 5, tempo >= duracao * 6);
        this.animePoint(this.context, this.tamanho, this.tamanho * 0.5, this.tamanho * 0.15, this.tamanho * 0.85, tempo - duracao * 6, duracao, tempo > duracao * 6, tempo >= duracao * 7);
        this.animePoint(this.context, this.tamanho * 0.15, this.tamanho * 0.85, this.tamanho * 0.5, 0, tempo - duracao * 7, duracao, tempo > duracao * 7, tempo >= duracao * 8);
        this.context.stroke();
        this.context.closePath();
        this.context.restore();
        if (tempo > duracao * 9) {
            this.animacao = 0;
        }
    }

    animePoint(context, pix, piy, pfx, pfy, tempo, duration, start, stop) {

        if (!start) {
            return;
        }
        context.moveTo(pix, piy);
        if (stop) {
            context.lineTo(pfx, pfy);
            return;
        }

        const per = tempo / duration;
        let px = ((pfx - pix) * per) + pix;
        let py = ((pfy - piy) * per) + piy;
        context.lineTo(px, py);
    }

}

export {StarLoad};
