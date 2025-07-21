export type FileType = 'PROFILE_PICTURE' | 'PASSPORT' | 'CV' | 'LICENSE' | 'OTHER';

export interface UserFileRepresentation {
  id: number;
  fileUrl: string;
  type: FileType;
  userId: number;
}
