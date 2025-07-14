import { UserRepresentation } from './user-representation';
import {PlayerCategoryRepresentation} from './playercategory-represenation';

export interface PlayerRepresentation extends UserRepresentation {
  playerId:number;
  memberId:Number;
  licenseNumber: string;
  clubId: number;
  position: string;
  jerseyNumber: number;
  height: number;      
  weight: number;
  category: PlayerCategoryRepresentation;
}
