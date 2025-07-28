import { UserPost } from "../User/userPost";

export interface ClubMemberPost extends UserPost {
    clubId: number; 
    type: string;
    categoryIds: number[];
}

