import { Entity } from './entity.model';

export interface Chat extends Entity {
  titulo: string;
  mensagem: string;
  url: string;
  cor: string;
  topico: string;
}
