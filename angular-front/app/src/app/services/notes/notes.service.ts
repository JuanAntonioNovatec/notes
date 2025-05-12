import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Note} from '../../interfaces/note';
import {BaseService} from '../baseService';



@Injectable({
  providedIn: 'root'
})
export class NotesService extends BaseService {

  private finalEndpointName = 'notes';
  private apiUrl: string = '';

  constructor(private http: HttpClient) {
    super();
    this.apiUrl = super.getFullUrl(this.finalEndpointName);
  }

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
  deleteAllNotes(): Observable<{ message: string }> {
    return this.http.delete<{ message: string }>(`${this.apiUrl}/all`);
  }

}
