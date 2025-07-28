
import { UserResponse } from "../User/userResponse";

export interface ClubMemberResponse extends UserResponse {
    clubId: number; 
    type: string;
    categoryIds: number[];
}

