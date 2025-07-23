import { UserRepresentation } from "../User/user-representation";

export interface MemberRepresentation extends UserRepresentation {
    clubId?: number; 
    role: string;

}

