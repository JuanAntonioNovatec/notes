import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Note} from '../interfaces/note';


@Injectable({
  providedIn: 'root'
})
export class NotesService {

  private apiUrl = 'http://localhost:8080/api/notes';

  constructor(private http: HttpClient) { }

  getNotes(): Observable<Note[]> {
    return this.http.get<Note[]>(this.apiUrl);
  }

  addNote(text: string): void {
    // const nuevaNota: Note = {  };
    // this.notas.push(nuevaNota);
  }

  // updateNote(id: number, text: string): void {
  //   const nota = this.notas.find(n => n.id === id);
  //   if (nota) {
  //     nota.text = text;
  //   }
  // }
  //
  // eliminarNota(id: number): void {
  //   this.notas = this.notas.filter(nota => nota.id !== id);
  // }
}
