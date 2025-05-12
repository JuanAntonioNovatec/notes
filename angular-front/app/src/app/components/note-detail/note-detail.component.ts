import { Component, Inject } from '@angular/core';
import {
  MatDialogRef,
  MAT_DIALOG_DATA,
  MatDialogContent,
  MatDialogActions,
  MatDialogTitle
} from '@angular/material/dialog';
import {NotesService} from '../../services/notes/notes.service';
import {Note} from '../../interfaces/note';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatButton} from '@angular/material/button';
import {FormsModule} from '@angular/forms';
import {DialogActions} from './DialogActionModes';



@Component({
  selector: 'app-note-detail',
  templateUrl: './note-detail.component.html',
  imports: [
    MatDialogContent,
    MatFormField,
    MatDialogActions,
    MatLabel,
    MatFormField,
    MatInput,
    MatButton,
    FormsModule,
    MatDialogTitle
  ],
  styleUrls: ['./note-detail.component.css']
})
export class NoteDetailComponent {
  noteText: string = '';
  noteId: number | null = null;
  mode: DialogActions = DialogActions.POSITIVE

  constructor(
    public dialogRef: MatDialogRef<NoteDetailComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Note | null,
    private notasService: NotesService
  ) {
    if (data) {
      console.log('data', data);
      this.mode = DialogActions.NEGATIVE
      this.noteText = data.text; // Rellenar el contenido si se está editando
      this.noteId = data.id

    }
  }


  onNoClick(): void {
    this.dialogRef.close(); // Cierra el modal
  }

  action(): void {
    console.log('adding')
    console.log(this.noteText)

    this.dialogRef.close({ action: this.mode, noteText: this.noteText, noteId: this.noteId });

    // if (this.) {
    //   // Si no hay datos, se está creando una nueva nota
    //   //this.dialogRef.close(note); // Return the new note to the caller.
    //   this.dialogRef.close(
    //     { action: DialogActions.CREATE, noteText: this.noteText });
    //   //});
    // } else {
      // Si hay datos, se está editando una nota existente
      // const editedNota: Note = { id: this.data.id, text: this.contenidoNota };
      // this.notasService.up(editedNota);
      //   .subscribe(() => {
      //   this.dialogRef.close(editedNota); // Return the updated note
      // });
    }

  protected readonly DialogActions = DialogActions;
}


