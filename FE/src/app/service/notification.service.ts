import { Injectable } from '@angular/core';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  constructor(private snackBar: MatSnackBar) { }
  showNotification(message: string): void {
    const config: MatSnackBarConfig = {
      duration: 3000,
      horizontalPosition: 'end',
      verticalPosition: 'bottom',
    };

    this.snackBar.open(message, 'Close', config);
  }
}
