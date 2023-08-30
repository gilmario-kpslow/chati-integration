//class StarLoad {
//canvas = undefined;
//        context = undefined;
//        tamanho = 100;
//        tempo = 2000;
//        animacao = 0;
//        init = () => {
//
////this.canvas = documento.createElement('canvas');
////this.context = this.canvas.getContext('2d');
////        this.renderer.appendChild(this.div.nativeElement, this.canvas);
////        this.renderer.setAttribute(this.canvas, 'width', `${this.tamanho}`);
////        this.renderer.setAttribute(this.canvas, 'height', `${this.tamanho}`);
////this.draw();
//}
//}
//
//
//
//
//limpar = () => {
//if (!this.context) {
//return;
//        }
//this.context.clearRect(0, 0, this.tamanho, this.tamanho);
//        // this.desenhaFundo();
//        // this.linhasInternas();
//        this.linhasExternasAnimadas();
//        }
//
//linhasInternas = () => {
//if (!this.context) {
//return;
//        }
//this.context.strokeStyle = "red";
//        this.context.save();
//        this.context.beginPath();
//        this.context.moveTo(this.tamanho * 0.5, 0);
//        this.context.lineTo(this.tamanho * 0.5, this.tamanho);
//        this.context.moveTo(0, this.tamanho * 0.5);
//        this.context.lineTo(this.tamanho, this.tamanho * 0.5);
//        this.context.moveTo(this.tamanho * 0.15, this.tamanho * 0.15);
//        this.context.lineTo(this.tamanho * 0.85, this.tamanho * 0.85);
//        this.context.moveTo(this.tamanho * 0.85, this.tamanho * 0.15);
//        this.context.lineTo(this.tamanho * 0.15, this.tamanho * 0.85);
//        this.context.stroke();
//        this.context.closePath();
//        this.context.restore();
//        }
//
//linhasExternas = () => {
//if (!this.context) {
//return;
//        }
//this.context.strokeStyle = "blue";
//        this.context.save();
//        this.context.beginPath();
//        this.context.moveTo(this.tamanho * 0.5, 0);
//        this.context.lineTo(this.tamanho * 0.85, this.tamanho * 0.15);
//        this.context.lineTo(this.tamanho, this.tamanho * 0.5);
//        this.context.lineTo(this.tamanho * 0.85, this.tamanho * 0.85);
//        this.context.lineTo(this.tamanho * 0.5, this.tamanho);
//        this.context.lineTo(this.tamanho * 0.15, this.tamanho * 0.85);
//        this.context.lineTo(0, this.tamanho * 0.5);
//        this.context.lineTo(this.tamanho * 0.15, this.tamanho * 0.15);
//        this.context.lineTo(this.tamanho * 0.5, 0);
//        this.context.stroke();
//        this.context.closePath();
//        this.context.restore();
//        }
//
//linhasExternasAnimadas = () => {
//if (!this.context) {
//return;
//        }
//
//const duracao = this.tempo / 9;
//        const agora = new Date().getTime();
//        if (this.animacao === 0) {
//this.animacao = agora;
//        }
//
//let tempo = agora - this.animacao;
//        this.context.strokeStyle = "blue";
//        this.context.save();
//        this.context.beginPath();
//        this.animePoint(this.context, this.tamanho * 0.5, 0, this.tamanho * 0.85, this.tamanho * 0.15, tempo, duracao, tempo > 0, tempo >= duracao);
//        this.animePoint(this.context, this.tamanho * 0.85, this.tamanho * 0.15, this.tamanho, this.tamanho * 0.5, tempo - duracao, duracao, tempo > duracao, tempo >= duracao * 2);
//        this.animePoint(this.context, this.tamanho, this.tamanho * 0.5, this.tamanho * 0.85, this.tamanho * 0.85, tempo - duracao * 2, duracao, tempo > duracao * 2, tempo >= duracao * 3);
//        this.animePoint(this.context, this.tamanho * 0.85, this.tamanho * 0.85, this.tamanho * 0.5, this.tamanho, tempo - duracao * 3, duracao, tempo > duracao * 3, tempo >= duracao * 4);
//        this.animePoint(this.context, this.tamanho * 0.5, this.tamanho, this.tamanho * 0.15, this.tamanho * 0.85, tempo - duracao * 4, duracao, tempo > duracao * 4, tempo >= duracao * 5);
//        this.animePoint(this.context, this.tamanho * 0.15, this.tamanho * 0.85, 0, this.tamanho * 0.5, tempo - duracao * 5, duracao, tempo > duracao * 5, tempo >= duracao * 6);
//        this.animePoint(this.context, 0, this.tamanho * 0.5, this.tamanho * 0.15, this.tamanho * 0.15, tempo - duracao * 6, duracao, tempo > duracao * 6, tempo >= duracao * 7);
//        this.animePoint(this.context, this.tamanho * 0.15, this.tamanho * 0.15, this.tamanho * 0.5, 0, tempo - duracao * 7, duracao, tempo > duracao * 7, tempo >= duracao * 8);
//        this.context.stroke();
//        this.context.closePath();
//        this.context.restore();
//        if (tempo > duracao * 9) {
//this.animacao = 0;
//        }
//}
//
//desenhaFundo = () => {
//if (!this.context) {
//return;
//        }
//const partes = 100
//        const q = this.tamanho / partes;
//        let x = 0;
//        let y = 0;
//        for (let i = 0; i < partes; i++) {
//for (let j = 0; j < partes; j++) {
//if ((i + j) % 2 == 0) {
//this.context.fillStyle = BACKGROUND_COLOR_1;
//        } else {
//this.context.fillStyle = BACKGROUND_COLOR_2;
//        }
//this.context?.fillRect(x, y, q, q);
//        x += q;
//        }
//y += q;
//        x = 0;
//        }
//}
//
//centraliza() {
//if (!this.context) {
//return;
//        }
//}
//
//draw() {
//if (!this.context) {
//return;
//        }
//this.limpar();
//        // this.drawStar();;
//        this.drawAnimatedStar();
//        requestAnimationFrame(this.draw.bind(this));
//        }
//
//drawStar() {
//if (!this.context) {
//return;
//        }
//this.context.save();
//        this.context.strokeStyle = "green";
//        this.context.beginPath();
//        this.context.moveTo(this.tamanho * 0.5, 0);
//        this.context.lineTo(this.tamanho * 0.85, this.tamanho * 0.85);
//        this.context.lineTo(0, this.tamanho * 0.5);
//        this.context.lineTo(this.tamanho * 0.85, this.tamanho * 0.15);
//        this.context.lineTo(this.tamanho * 0.5, this.tamanho);
//        this.context.lineTo(this.tamanho * 0.15, this.tamanho * 0.15);
//        this.context.lineTo(this.tamanho, this.tamanho * 0.5);
//        this.context.lineTo(this.tamanho * 0.15, this.tamanho * 0.85);
//        this.context.lineTo(this.tamanho * 0.5, 0);
//        this.context.stroke();
//        this.context.closePath();
//        this.context.restore();
//        }
//
//private drawAnimatedStar() {
//if (!this.context) {
//return;
//        }
//const duracao = this.tempo / 9;
//        const agora = new Date().getTime();
//        if (this.animacao === 0) {
//this.animacao = agora;
//        }
//
//let tempo = agora - this.animacao;
//        this.context.save();
//        this.context.strokeStyle = "green";
//        this.context.beginPath();
//        this.animePoint(this.context, this.tamanho * 0.5, 0, this.tamanho * 0.85, this.tamanho * 0.85, tempo, duracao, tempo > 0, tempo >= duracao);
//        this.animePoint(this.context, this.tamanho * 0.85, this.tamanho * 0.85, 0, this.tamanho * 0.5, tempo - duracao, duracao, tempo > duracao, tempo >= duracao * 2);
//        this.animePoint(this.context, 0, this.tamanho * 0.5, this.tamanho * 0.85, this.tamanho * 0.15, tempo - duracao * 2, duracao, tempo > duracao * 2, tempo >= duracao * 3);
//        this.animePoint(this.context, this.tamanho * 0.85, this.tamanho * 0.15, this.tamanho * 0.5, this.tamanho, tempo - duracao * 3, duracao, tempo > duracao * 3, tempo >= duracao * 4);
//        this.animePoint(this.context, this.tamanho * 0.5, this.tamanho, this.tamanho * 0.15, this.tamanho * 0.15, tempo - duracao * 4, duracao, tempo > duracao * 4, tempo >= duracao * 5);
//        this.animePoint(this.context, this.tamanho * 0.15, this.tamanho * 0.15, this.tamanho, this.tamanho * 0.5, tempo - duracao * 5, duracao, tempo > duracao * 5, tempo >= duracao * 6);
//        this.animePoint(this.context, this.tamanho, this.tamanho * 0.5, this.tamanho * 0.15, this.tamanho * 0.85, tempo - duracao * 6, duracao, tempo > duracao * 6, tempo >= duracao * 7);
//        this.animePoint(this.context, this.tamanho * 0.15, this.tamanho * 0.85, this.tamanho * 0.5, 0, tempo - duracao * 7, duracao, tempo > duracao * 7, tempo >= duracao * 8);
//        this.context.stroke();
//        this.context.closePath();
//        this.context.restore();
//        if (tempo > duracao * 9) {
//this.animacao = 0;
//        }
//}
//
//private animePoint(context: CanvasRenderingContext2D, pix: number, piy: number, pfx: number, pfy: number, tempo: number, duration: number, start: boolean, stop: boolean) {
//
//if (!start){
//return;
//        }
//context.moveTo(pix, piy);
//        if (stop) {
//context.lineTo(pfx, pfy);
//        return;
//        }
//
//const per = tempo / duration;
//        let px = ((pfx - pix) * per) + pix;
//        let py = ((pfy - piy) * per) + piy;
//        context.lineTo(px, py);
//        }
//}
