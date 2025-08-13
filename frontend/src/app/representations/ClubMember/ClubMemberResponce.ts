import { Category } from '../Category/category';
import { UserResponse } from "../User/userResponse";

export interface ClubMemberResponse extends UserResponse {
    
    clubId?: number;
    categories?: Category[];
}

