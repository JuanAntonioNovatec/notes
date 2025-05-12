import {Component, OnInit} from '@angular/core';
import {Router, RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {MatToolbar} from '@angular/material/toolbar';
import {MatIcon} from '@angular/material/icon';
import {MatAnchor} from '@angular/material/button';
import {AuthService} from './services/auth/auth.service';
import {NgIf, NgStyle} from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, MatToolbar, MatIcon, RouterLinkActive, MatAnchor, NgIf, NgStyle],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'app';
  role: string | null = null;

  constructor(
    private authService: AuthService,
    private _router: Router
  ) {}

  ngOnInit(): void {
    this.role = this.authService.obtenerRol();
    console.log('Role', this.role);
  }
}
