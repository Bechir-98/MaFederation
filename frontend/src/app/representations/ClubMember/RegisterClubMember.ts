import { UserType } from "../../services/api/verification/user/verification-request";
import { UserPost } from "../User/userPost";



export interface RegisterClubMember extends UserPost 

{
    clubId: number;
    type?:UserType;
}