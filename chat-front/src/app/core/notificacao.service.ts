import { inject, Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({ providedIn: 'root' })
export class NotificacaoService {
  private _snackBar = inject(MatSnackBar);

  notificar(text: string) {
    this._snackBar.open(text, 'close', {
      horizontalPosition: 'right',
      verticalPosition: 'top',
      duration: 5000,
    });
  }
}
