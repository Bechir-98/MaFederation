import { UserRepresentation } from "./user-representation";

export interface MemberRepresentation extends UserRepresentation {
    clubId?: number; 
    role: string;

}

