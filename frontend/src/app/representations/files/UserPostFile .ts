export type FileType =  | 'PASSPORT' | 'CV' | 'LICENSE' | 'OTHER';

export interface UserPostFile {
  fileUrl: string;
  type: FileType;
}
