import { ValidationInfo } from "../ValidationInfo/validationInfo";


export interface PostAudit extends ValidationInfo{

createdAt: string;
createdBy: string;
updatedAt: string;
updatedBy: string;

}