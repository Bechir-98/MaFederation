import { Component, Input, ChangeDetectorRef, OnInit } from '@angular/core';
import { UserFile } from '../../representations/User/userResponse';
import { UserService } from '../../services/api/user/user-service';
import { CommonModule } from '@angular/common';

type UserFileType = 'PASSPORT' | 'CV' | 'LICENSE' | 'OTHER';

@Component({
  selector: 'app-user-files-component',
  templateUrl: './user-files-component.html',
  styleUrls: ['./user-files-component.css'],
  imports: [CommonModule]
})
export class UserFilesComponent implements OnInit {
  @Input() UserId!: number;

  loadedCredentials: UserFile[] = [];
  fileContent?: string;
  showPreview = false;
  previewTitle = '';
  currentFileType = '';
  allFileTypes: UserFileType[] = ['PASSPORT', 'CV', 'LICENSE', 'OTHER'];

  constructor(private userService: UserService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.loadUserFiles();
    this.cdr.detectChanges();
  }

  loadUserFiles(): void {
    this.userService.loadUserFiles(this.UserId).subscribe({
      next: (files) => {
        console.log('API returned files:', files);
        this.loadedCredentials = files;
        console.log("loaded",this.loadedCredentials);
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error loading credentials:', err);
      }
    });
  }

  getFileByType(type: UserFileType): UserFile | undefined {
    return this.loadedCredentials.find(f => f.type === type);
  }

  get missingFileTypes(): UserFileType[] {
    const existing = this.loadedCredentials.map(f => f.type);
    return this.allFileTypes.filter(t => !existing.includes(t));
  }

  onFileSelected(event: Event, type: UserFileType): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      this.userService.uploadUserFiles(this.UserId, [file], type).subscribe({
        next: () => {
          this.loadUserFiles();
        },
        error: (err) => console.error('Upload error:', err)
      });
    }
  }

  openPreview(fileId: number, title: string): void {
    this.fileContent = undefined;
    this.previewTitle = title;
    this.showPreview = true;

    this.userService.loadUserFileById(fileId).subscribe({
      next: (file) => {
        console.log('File loaded:', file);
        const fileName = file.type || '';

        const isPdf = fileName.toLowerCase().endsWith('.pdf') || file.content?.startsWith('JVBERi');
        console.log('Is PDF:', isPdf);

        if (isPdf) {
          try {
            const binaryString = atob(file.content);
            const bytes = new Uint8Array(binaryString.length);
            for (let i = 0; i < binaryString.length; i++) {
              bytes[i] = binaryString.charCodeAt(i);
            }
            const blob = new Blob([bytes], { type: 'application/pdf' });
            this.fileContent = URL.createObjectURL(blob);
            this.currentFileType = 'pdf';
          } catch {
            console.error('Error creating PDF blob');
            this.currentFileType = 'unsupported';
          }
        } else {
          const isImageByExtension = this.isImageExtension(fileName);
          const isImageByContent = this.isImageByContent(file.content);
          const isImage = isImageByExtension || isImageByContent;

          if (isImage) {
            const mimeType = this.getImageMimeType(fileName) || 'image/png';
            this.fileContent = `data:${mimeType};base64,${file.content}`;
            this.currentFileType = 'image';
          } else {
            try {
              const binaryString = atob(file.content);
              const bytes = new Uint8Array(binaryString.length);
              for (let i = 0; i < binaryString.length; i++) {
                bytes[i] = binaryString.charCodeAt(i);
              }
              const blob = new Blob([bytes], { type: 'application/octet-stream' });
              this.fileContent = URL.createObjectURL(blob);
              this.currentFileType = 'other';
            } catch {
              console.error('Error creating file blob');
              this.currentFileType = 'unsupported';
            }
          }
        }

        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error loading file preview:', err);
        this.closePreview();
      }
    });
  }

  closePreview(): void {
    if (this.fileContent && this.currentFileType === 'pdf' && this.fileContent.startsWith('blob:')) {
      URL.revokeObjectURL(this.fileContent);
    }

    this.showPreview = false;
    this.currentFileType = '';
    this.fileContent = undefined;
  }

  private getImageMimeType(fileName?: string): string {
    if (!fileName) return 'image/png';
    const extension = fileName.toLowerCase().split('.').pop();
    switch (extension) {
      case 'jpg':
      case 'jpeg': return 'image/jpeg';
      case 'png': return 'image/png';
      case 'gif': return 'image/gif';
      case 'webp': return 'image/webp';
      case 'svg': return 'image/svg+xml';
      case 'bmp': return 'image/bmp';
      case 'tiff':
      case 'tif': return 'image/tiff';
      default: return 'image/png';
    }
  }

  private isImageByContent(base64Content: string): boolean {
    if (!base64Content) return false;
    const imageSignatures = [
      'iVBORw0KGgo', // PNG
      '/9j/',        // JPEG
      'R0lGODlh',    // GIF
      'UklGR',       // WEBP
      'PHN2Zw',      // SVG
      'Qk',          // BMP
    ];
    return imageSignatures.some(sig => base64Content.startsWith(sig));
  }

  private isImageExtension(fileName?: string): boolean {
    if (!fileName) return false;
    const extension = fileName.toLowerCase().split('.').pop();
    const imageExtensions = ['jpg', 'jpeg', 'png', 'gif', 'webp', 'svg', 'bmp', 'tiff', 'tif'];
    return imageExtensions.includes(extension || '');
  }

  isPdfFile(): boolean {
    return this.currentFileType === 'pdf';
  }

  isImageFile(): boolean {
    return this.currentFileType === 'image';
  }

  isOtherFile(): boolean {
    return this.currentFileType === 'other';
  }

  isUnsupportedFile(): boolean {
    return this.currentFileType === 'unsupported';
  }

  triggerEdit(type: UserFileType): void {
    const existingFile = this.getFileByType(type);
    if (!existingFile) return;

    const input = document.createElement('input');
    input.type = 'file';

    input.onchange = (event: Event) => {
      const target = event.target as HTMLInputElement;
      if (target.files && target.files.length > 0) {
        const newFile = target.files[0];

        // Note: updated call - userId removed as per backend
        this.userService.updateUserFile(newFile, existingFile.id).subscribe({
          next: (updatedFile: UserFile) => {
            this.loadUserFiles();

            if (this.showPreview && this.previewTitle === type) {
              setTimeout(() => {
                const refreshedFile = this.getFileByType(type);
                if (refreshedFile) {
                  this.openPreview(refreshedFile.id, type);
                }
              }, 500);
            }
          },
          error: (err) => {
            console.error('Update failed:', err);
          }
        });
      }
    };

    input.click();
  }

  deleteFile(fileId: number): void {
    if (confirm('Are you sure you want to delete this file?')) {
      // Note: userId removed here as well
      this.userService.deleteUserFile(fileId).subscribe({
        next: () => {
          this.loadUserFiles();

          if (this.showPreview) {
            this.closePreview();
          }
        },
        error: err => console.error('Delete error:', err)
      });
    }
  }
}
