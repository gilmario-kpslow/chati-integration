import { Entity } from './entity.model';

export interface Chat extends Entity {
  titulo: string;
  mensagem: string;
  url: string;
  cor: string;
  chave: string;
  ativo: boolean;
  provider: string;

}
