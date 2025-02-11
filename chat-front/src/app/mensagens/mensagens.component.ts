import { Component, Inject, inject, model } from '@angular/core';

import { MensagemConfig } from './mensagem-config';
import { MAT_DIALOG_DATA, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogModule, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { DialogModule } from '@angular/cdk/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-mensagens',
  templateUrl: './mensagens.component.html',
  styleUrls: ['./mensagens.component.css'],
  standalone: true,
  imports: [MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose, DialogModule],
  providers: []
})
export class MensagensComponent {
  dialogRef = inject(MatDialogRef);
  config? = inject<MensagemConfig>(MAT_DIALOG_DATA);
  animal = model(this.config);


}
