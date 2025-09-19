// app.component.ts
import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd, RouterOutlet } from '@angular/router';
import { filter } from 'rxjs/operators';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AdminNavComponent } from './nav/admin/admin-nav/admin-nav';
import { ClubNavComponent } from './nav/club/club-nav';
import { UserNavComponent } from './User/user-nav-component/user-nav-component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    CommonModule,
    FormsModule,
    AdminNavComponent,
    ClubNavComponent,
    UserNavComponent
  ],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class AppComponent implements OnInit {
  showSidebar = false;
  isAdmin = false;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.updateSidebar();

    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => this.updateSidebar());
  }

  private updateSidebar(): void {
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('role');
    this.showSidebar = !!token;
    this.isAdmin = role === 'ADMIN';
  }
}
