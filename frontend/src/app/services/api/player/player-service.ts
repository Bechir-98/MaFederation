import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PlayerResponce } from '../../../representations/Player/playerResponce';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {
  private baseUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getPlayers(): Observable<PlayerResponce[]> {
    return this.http.get<PlayerResponce[]>(`${this.baseUrl}/players`);
  }

 createPlayer(formData: FormData): Observable<any> {
    return this.http.post(`${this.baseUrl}/players/addplayer`, formData);
}

updatePlayer(playerId: number, player: Partial<PlayerResponce>): Observable<PlayerResponce> {
    return this.http.put<PlayerResponce>(`${this.baseUrl}/players/${playerId}`, player);
  }







}


