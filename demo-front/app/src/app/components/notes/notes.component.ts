import { Component } from '@angular/core';
import {NotesService} from '../../services/notes.service';
import {Note} from '../../interfaces/note';
import {MatList, MatListItem, MatListSubheaderCssMatStyler} from '@angular/material/list';
import {NgClass, NgForOf} from '@angular/common';
import {MatButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';

@Component({
  selector: 'app-notes',
  imports: [
    MatList,
    MatListItem,
    NgForOf,
    NgClass,
    MatButton,
    MatListSubheaderCssMatStyler,
    MatIcon
  ],
  templateUrl: './notes.component.html',
  styleUrl: './notes.component.css'
})
export class NotesComponent {

  notes: Note[] = [];

  constructor(private notasService: NotesService) { }


  ngOnInit(): void {
    this.getAllNotes();
  }

  getLineColor(index: number) {
    switch (index % 3) { // Por ejemplo, alternar entre 3 colores
      case 0: return 'color-line-1';
      case 1: return 'color-line-2';
      case 2: return 'color-line-3';
      default: return ''; // Si no coincide, retorna vacío
    }
  }


  getAllNotes() {
    console.log('getNotes');
    this.notasService.getNotes().subscribe(notes => {
      this.notes = notes;
    });
  }

  updateNote(nota: Note) {
    console.log('updateNote')
  }

  deleteNote(id: number) {
    console.log('deleteNote')
  }
}
