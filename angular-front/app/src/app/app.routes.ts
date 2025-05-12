import { Routes } from '@angular/router';
import {NotesComponent} from './components/notes/notes.component';
import {HomeComponent} from './components/home/home.component';
import {NoteDetailComponent} from './components/note-detail/note-detail.component';
import {RegisterComponent} from './components/register/register.component';
import {LoginComponent} from './login/login.component';
import {UsersComponent} from './users/users.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },
  { path: 'notes', component: NotesComponent }, // Go to Notes Component
  { path: 'register', component: RegisterComponent }, // Go to Notes Component
  { path: 'login', component: LoginComponent }, // Go to Notes Component
  { path: 'users', component: UsersComponent }, // Go to Notes Component

];
