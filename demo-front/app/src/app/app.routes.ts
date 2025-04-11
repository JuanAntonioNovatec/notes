import { Routes } from '@angular/router';
import {NotesComponent} from './components/notes/notes.component';
import {HomeComponent} from './components/home/home.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'notes', component: NotesComponent }, // Go to Notes Component

];
