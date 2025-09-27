import { Component, OnInit } from '@angular/core';
import { ModService } from '../../services/api/mod/mod.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';  // <-- needed for *ngFor
import { Logs } from "../../representations/Logs/LogsRepresentation";

@Component({
  selector: 'app-audit-logs',
  standalone: true,                // if using standalone component
  imports: [FormsModule, CommonModule],
  templateUrl: './audit-logs.html',
  styleUrls: ['./audit-logs.css']  // <-- fixed typo
})
export class AuditLogs implements OnInit {

  logs: Logs[] = [];
  sortOrder: 'NEW' | 'OLD' = 'NEW';

  constructor(private logsService: ModService) {}

  ngOnInit(): void {
    this.loadLogs();
  }

  loadLogs(): void {
    this.logsService.getLogs().subscribe({
      next: (data) => {
        this.logs = this.sortOrder === 'NEW'
          ? data.sort((a,b) => new Date(b.actionTime).getTime() - new Date(a.actionTime).getTime())
          : data.sort((a,b) => new Date(a.actionTime).getTime() - new Date(b.actionTime).getTime());
        console.log(this.logs)},
      error: (err) => console.error('Error fetching logs', err)
    });
  }

  onSortChange(): void {
    this.loadLogs();
  }
}
