import { MemberRepresenation } from './member-representation';


export interface PlayerRepresentation extends MemberRepresenation {
  playerId:number;
  licenseNumber: string;
  clubId: number;
  position: string;
  jerseyNumber: number;
  height: number;      
  weight: number;
  categoryid:number;
}
