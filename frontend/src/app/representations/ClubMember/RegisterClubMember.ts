import { UserPost } from "../User/userPost";



export interface RegisterClubMember extends UserPost 

{
    clubId: number;

}