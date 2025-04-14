import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {ErrorDialogComponent} from '../../components/error-dialog/error-dialog.component';


@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  constructor(private dialog: MatDialog) {}

  public showError(message: string, width: string = '300px'): void {
    this.dialog.open(ErrorDialogComponent, {
      data: { message },
      width: '300px'
    });
  }
}
