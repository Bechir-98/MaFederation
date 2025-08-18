import { ClubMemberResponse } from '../ClubMember/ClubMemberResponce';

export interface PlayerResponce extends ClubMemberResponse {
  jerseyNumber?: number;
  height?: number;
  weight?: number;
  position?: string;
  licenseNumber?:number,


}
