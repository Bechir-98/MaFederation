import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PlayerRepresentation } from '../../../representations/player-representation';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {
  private baseUrl: string = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getPlayers(): Observable<PlayerRepresentation[]> {
    return this.http.get<PlayerRepresentation[]>(`${this.baseUrl}/players`);
  }

  addPlayer(player: PlayerRepresentation): Observable<PlayerRepresentation> {
    return this.http.post<PlayerRepresentation>(`${this.baseUrl}/players`, player);
  }

  getPlayerById(playerId: number): Observable<PlayerRepresentation> {
    return this.http.get<PlayerRepresentation>(`${this.baseUrl}/players/${playerId}`);
  }

  updatePlayer(playerId: number, player: PlayerRepresentation): Observable<PlayerRepresentation> {
    return this.http.put<PlayerRepresentation>(`${this.baseUrl}/players/${playerId}`, player);
  }

  deletePlayer(playerId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/players/${playerId}`);
  }

  getPlayersByClub(clubId: number): Observable<PlayerRepresentation[]> {
    return this.http.get<PlayerRepresentation[]>(`${this.baseUrl}/players/club/${clubId}`);
  }
}