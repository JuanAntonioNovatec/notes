import { Routes } from '@angular/router';
import {NotesComponent} from './components/notes/notes.component';
import {HomeComponent} from './components/home/home.component';
import {NoteDetailComponent} from './components/note-detail/note-detail.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'notes', component: NotesComponent } // Go to Notes Component
  //{ path: 'note-detail', component: NoteDetailComponent }, // Go to Notes Component

];
