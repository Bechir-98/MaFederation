import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PlayerRepresentation } from '../../../represantations/player-representation';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  public playerService: PlayerService;

  constructor(private http: HttpClient) {
    this.playerService = this;
  }

  getPlayers():Observable<PlayerRepresentation[]> {
    return this.http.get<PlayerRepresentation[]>('/api/players');
  }

  addPlayer(player: PlayerRepresentation):Observable<PlayerRepresentation> {
    return this.http.post<PlayerRepresentation>('/api/players', player);
  }

  getPlayerById(playerId: number): Observable<PlayerRepresentation> {
    return this.http.get<PlayerRepresentation>(`/api/players/${playerId}`);
  }
}
