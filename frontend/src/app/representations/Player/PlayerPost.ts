import { ClubMemberPost } from '../ClubMember/ClubMemberPost';

export interface PlayerPost extends ClubMemberPost{

  jerseyNumber: number;
  height: number;
  weight: number;
  licenseNumber:number;
  position: string;


}
