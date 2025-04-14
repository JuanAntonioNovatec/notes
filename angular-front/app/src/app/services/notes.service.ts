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

  addNote(noteText: string): Observable<Note> {
    return this.http.post<Note>(this.apiUrl, { text: noteText });
  }

  updateNote(note: Note): Observable<Note> {
    console.log('yeeehhh updating!!!!');
    console.log(note);
    return this.http.put<Note>(`${this.apiUrl}/${note.id}`, note);
  }

  // updateNote(id: number, text: string): void {
  //   const nota = this.notas.find(n => n.id === id);
  //   if (nota) {
  //     nota.text = text;
  //   }
  // }
  //
  deleteNote(id: number): Observable<string> {
    return this.http.delete<string>(`${this.apiUrl}/${id}`);
  }
}
