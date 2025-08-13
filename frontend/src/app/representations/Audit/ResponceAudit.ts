import { ValidationInfo } from "../ValidationInfo/validationInfo";


export interface ResponceAudit  extends ValidationInfo {
  id: number;
  createdAt: string;
  createdBy: string;
  updatedAt: string;
  updatedBy: string;
}





