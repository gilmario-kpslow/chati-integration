import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';

@Component({
  selector: 'app-pergunta',
  imports: [
    CommonModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    MatButtonModule,
  ],
  templateUrl: './pergunta.component.html',
  styleUrl: './pergunta.component.css',
})
export class PerguntaComponent {
  dialogRef = inject(MatDialogRef);
  data = inject<any>(MAT_DIALOG_DATA);

  confirmar() {
    this.dialogRef.close(true);
  }
}
