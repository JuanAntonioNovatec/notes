import {Component, inject} from '@angular/core';
import {NotesService} from '../../services/notes/notes.service';
import {Note} from '../../interfaces/note';
import {MatList, MatListItem, MatListSubheaderCssMatStyler} from '@angular/material/list';
import {NgClass, NgForOf} from '@angular/common';
import {MatButton, MatFabButton, MatIconButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {MatSnackBar} from '@angular/material/snack-bar';
import {MatDialog} from '@angular/material/dialog';
import {NoteDetailComponent} from '../note-detail/note-detail.component';
import {DialogActions} from '../note-detail/DialogActionModes';
import {ConfirmDialogComponent} from '../../confirm-dialog/confirm-dialog.component';
import {ErrorService} from '../../services/errors-dialog/errors-dialog.service';

@Component({
  selector: 'app-notes',
  imports: [
    MatList,
    MatListItem,
    NgForOf,
    NgClass,

    MatListSubheaderCssMatStyler,
    MatIcon,
    MatIconButton,
    MatFabButton
  ],
  templateUrl: './notes.component.html',
  styleUrl: './notes.component.css'
})
export class NotesComponent {

  notes: Note[] = [];
  private _snackBar = inject(MatSnackBar);

  constructor(
    private notesService: NotesService,
    private errorService: ErrorService,
  public dialog: MatDialog
  ) { }


  ngOnInit(): void {
    this.getAllNotes();
  }

  openNoteDialog(nota?: Note) {
    const dialogRef = this.dialog.open(NoteDetailComponent, {
      data: nota // Pasar la nota si se está editando
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log('result');
        console.log(result);
        if (result.action === DialogActions.POSITIVE) { //new note created
          this.notesService.addNote(result.noteText).subscribe(result => {
            console.log('Created!!')
            this._snackBar.open('New note added !');
            this.getAllNotes(false);
          })

        } else if(result.action === DialogActions.NEGATIVE) {
          console.log('A update hostia');
          const note: Note = {
            text: result.noteText,
            id: result.noteId
          }
          this.notesService.updateNote(note).subscribe(result => {
            console.log('Created!!')
            this._snackBar.open('Note updated !');
            this.getAllNotes(false);
          })
        }
      }
    });

  }

  openDeleteConfirmDialog(id: number): void {
    const dialogRef = this.dialog.open(
      ConfirmDialogComponent, {
        data: {
          title: 'Delete a note',
          content: 'Are you sure?'
        }
      }

    );

    dialogRef.afterClosed().subscribe(result => {

      console.log('result', result);
      if (result) {
        this.deleteNote(id);  // Llama a tu función de eliminación aquí
      }
    });
  }

  getLineColor(index: number) {
    switch (index % 3) { // Por ejemplo, alternar entre 3 colores
      case 0: return 'color-line-1';
      case 1: return 'color-line-2';
      case 2: return 'color-line-3';
      default: return ''; // Si no coincide, retorna vacío
    }
  }

  /**
   * reload all notes from database
   * @param showSnackbar Show or not the snackbar. true by default!
   */
  getAllNotes(showSnackbar: boolean = true) {
    console.log('getNotes');
    this.notesService.getNotes().subscribe(notes => {
      this.notes = notes;
      if(showSnackbar) {
        this._snackBar.open('Notes loaded !', undefined, {
          duration: 1000,
        });
      }
    }, error => {
      console.log(error)
      this.errorService.showError(
        'Seems to bee some problem fetching data. Please check the api status.',
        '400px'
      );
      // this._snackBar.open('Server error', undefined, {
      //   duration: 1000,
      // });
    });
  }

  updateNote(note: Note) {
    this.openNoteDialog(note)
  }

  deleteNote(id: number) {
    console.log('delete', id)
    this.notesService.deleteNote(id).subscribe(next => {
      console.log('result', next)
      this._snackBar.open('Note deleted !');
      this.getAllNotes(false)
      });


    };

  addNote(){
    this.openNoteDialog()
  }

  deleteAllNotes() {
    const dialogRef = this.dialog.open(
      ConfirmDialogComponent, {
        data: {
          title: 'Delete all notes.',
          content: 'Are you sure? All note will be deleted.'
        }
      });
    dialogRef.afterClosed().subscribe(result => {
      //console.log('result', result);
      //console.log(result);
      if (result == DialogActions.POSITIVE) { //new note created
        console.log('delete all');
        this.notesService.deleteAllNotes().subscribe(result => {
         console.log('removed', result)
         console.log(result)
         // @ts-ignore
         this._snackBar.open(result.message, 'Close');
         console.log('delete all');
         this.getAllNotes(false);
        })

      } else if (result === DialogActions.NEGATIVE) {
        console.log('operation cancelled');

        /**
         console.log('A update hostia');
         const note: Note = {
         text: result.noteText,
         id: result.noteId
         }
         this.notesService.updateNote(note).subscribe(result => {
         console.log('Created!!')
         this._snackBar.open('Note updated !');
         this.getAllNotes(false);
         })
         **/
      }

    });
  }
}


