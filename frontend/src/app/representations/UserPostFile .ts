export type FileType = 'PROFILE_PICTURE' | 'PASSPORT' | 'CV' | 'LICENSE' | 'OTHER';

export interface UserPostFile {
  fileUrl: string;
  type: FileType;
}
