import { Component, Input, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClubServices } from '../../services/api/club/club-services';
import { ClubFile } from '../../representations/Club/ResponseClub';

type ClubFileType = 'LICENSE' | 'REGISTRATION_CERTIFICATE' | 'AFFILIATION_AGREEMENT' | 'CLUB_OFFICIALS';

@Component({
  selector: 'app-file-card-component',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './club-files-component.html',
  styleUrls: ['./club-files-component.css']
})
export class ClubFiles implements OnInit {

  @Input() clubId!: number;

  loadedCredentials: ClubFile[] = [];
  fileContent?: string;
  showPreview = false;
  previewTitle = '';
  currentFileType = '';
  allFileTypes: ClubFileType[] = ['LICENSE', 'REGISTRATION_CERTIFICATE', 'AFFILIATION_AGREEMENT', 'CLUB_OFFICIALS'];

  constructor(private clubService: ClubServices, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.loadClubFiles();
    this.cdr.detectChanges();
  }

  loadClubFiles(): void {
    this.clubService.loadClubFiles(this.clubId).subscribe({
      next: (files) => {
        console.log('API returned files:', files);
        this.loadedCredentials = files;
        
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error loading credentials:', err);
      }
    });
  }

  getFileByType(type: ClubFileType): ClubFile | undefined {
    return this.loadedCredentials.find(f => f.type === type);
  }

  get missingFileTypes(): ClubFileType[] {
    const existing = this.loadedCredentials.map(f => f.type);
    return this.allFileTypes.filter(t => !existing.includes(t));
  }

  onFileSelected(event: Event, type: ClubFileType): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      this.clubService.uploadClubFiles(this.clubId, [file], type).subscribe({
        next: (files: ClubFile[]) => {
          // Force reload the files from server to ensure we get the latest data
          this.loadClubFiles();
        },
        error: (err) => console.error('Upload error:', err)
      });
    }
  }
 
  openPreview(fileId: number, title: string): void {
    this.fileContent = undefined;
    this.previewTitle = title;
    this.showPreview = true;

    this.clubService.loadClubFileById(this.clubId, fileId).subscribe({
      next: (file) => {
        console.log('File loaded:', file);
        console.log('File name:', file.name);
        console.log('Title passed:', title);
        
        const fileName = file.name || '';
        
        // Check for PDF first
        const isPdf = fileName.toLowerCase().endsWith('.pdf') || file.content?.startsWith('JVBERi');
        console.log('Is PDF:', isPdf);
        
        if (isPdf) {
          // Handle PDF
          try {
            const binaryString = atob(file.content);
            const bytes = new Uint8Array(binaryString.length);
            for (let i = 0; i < binaryString.length; i++) {
              bytes[i] = binaryString.charCodeAt(i);
            }
            const blob = new Blob([bytes], { type: 'application/pdf' });
            this.fileContent = URL.createObjectURL(blob);
            this.currentFileType = 'pdf';
            console.log('PDF blob created');
          } catch (error) {
            console.error('Error creating PDF blob:', error);
            this.currentFileType = 'unsupported';
          }
        } else {
          // For non-PDFs, check if it's an image by content or try as image first
          const isImageByExtension = this.isImageExtension(fileName);
          console.log('Is image by extension:', isImageByExtension, 'for file:', fileName);
          
          // Try to detect if it's an image by base64 content signature
          const isImageByContent = this.isImageByContent(file.content);
          console.log('Is image by content:', isImageByContent);
          
          const isImage = isImageByExtension || isImageByContent;
          console.log('Final image decision:', isImage);
          
          if (isImage) {
            // Handle as image
            const mimeType = this.getImageMimeType(fileName) || 'image/png'; // fallback
            this.fileContent = `data:${mimeType};base64,${file.content}`;
            this.currentFileType = 'image';
            console.log('Image data URI created, MIME type:', mimeType);
          } else {
            // Handle as other file
            try {
              const binaryString = atob(file.content);
              const bytes = new Uint8Array(binaryString.length);
              for (let i = 0; i < binaryString.length; i++) {
                bytes[i] = binaryString.charCodeAt(i);
              }
              const blob = new Blob([bytes], { type: 'application/octet-stream' });
              this.fileContent = URL.createObjectURL(blob);
              this.currentFileType = 'other';
              console.log('Other file blob created');
            } catch (error) {
              console.error('Error creating file blob:', error);
              this.currentFileType = 'unsupported';
            }
          }
        }
        
        console.log('Final currentFileType:', this.currentFileType);
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error loading file preview:', err);
        this.closePreview();
      }
    });
  }

  closePreview() {
    // Clean up blob URL to prevent memory leaks
    if (this.fileContent && this.currentFileType === 'pdf' && this.fileContent.startsWith('blob:')) {
      URL.revokeObjectURL(this.fileContent);
    }
    
    this.showPreview = false;
    this.currentFileType = '';
    this.fileContent = undefined;
  }

  // Helper method to determine image MIME type
  private getImageMimeType(fileName?: string): string {
    if (!fileName) return 'image/png'; // Default fallback
    
    const extension = fileName.toLowerCase().split('.').pop();
    switch (extension) {
      case 'jpg':
      case 'jpeg':
        return 'image/jpeg';
      case 'png':
        return 'image/png';
      case 'gif':
        return 'image/gif';
      case 'webp':
        return 'image/webp';
      case 'svg':
        return 'image/svg+xml';
      case 'bmp':
        return 'image/bmp';
      case 'tiff':
      case 'tif':
        return 'image/tiff';
      default:
        return 'image/png'; // Default to PNG for unknown image types
    }
  }

  // Helper method to check if file is an image based on content signature
  private isImageByContent(base64Content: string): boolean {
    if (!base64Content) return false;
    
    // Check for common image file signatures in base64
    const imageSignatures = [
      'iVBORw0KGgo', // PNG
      '/9j/',        // JPEG
      'R0lGODlh',    // GIF
      'UklGR',       // WEBP (starts with RIFF)
      'PHN2Zw',      // SVG (starts with <svg)
      'Qk',          // BMP (starts with BM)
    ];
    
    return imageSignatures.some(sig => base64Content.startsWith(sig));
  }

  // Helper method to check if file is an image based on extension
  private isImageExtension(fileName?: string): boolean {
    if (!fileName) return false;
    
    const extension = fileName.toLowerCase().split('.').pop();
    const imageExtensions = ['jpg', 'jpeg', 'png', 'gif', 'webp', 'svg', 'bmp', 'tiff', 'tif'];
    const result = imageExtensions.includes(extension || '');
    console.log('Checking image extension for:', fileName, 'Extension:', extension, 'Is image:', result);
    return result;
  }

  // Helper method to check if current file is PDF
  isPdfFile(): boolean {
    return this.currentFileType === 'pdf';
  }

  // Helper method to check if current file is image
  isImageFile(): boolean {
    return this.currentFileType === 'image';
  }

  // Helper method to check if file is other type (not image or PDF)
  isOtherFile(): boolean {
    return this.currentFileType === 'other';
  }

  // Helper method to check if file is unsupported
  isUnsupportedFile(): boolean {
    return this.currentFileType === 'unsupported';
  }

  triggerEdit(type: ClubFileType): void {
    const existingFile = this.getFileByType(type);
    if (!existingFile) return;

    const input = document.createElement('input');
    input.type = 'file';

    input.onchange = (event: Event) => {
      const target = event.target as HTMLInputElement;
      if (target.files && target.files.length > 0) {
        const newFile = target.files[0];

        this.clubService.updateClubFile(this.clubId, newFile, existingFile.id).subscribe({
          next: (updatedFile: ClubFile) => {
            // Force reload the files from server to ensure we get the latest data
            this.loadClubFiles();
            
            // If the preview modal is open for this file, refresh it
            if (this.showPreview && this.previewTitle === type) {
              // Wait a bit for the reload to complete, then refresh preview
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
      this.clubService.deleteClubFile(this.clubId, fileId).subscribe({
        next: () => {
          // Force reload the files from server to ensure we get the latest data
          this.loadClubFiles();
          
          // Close preview if the deleted file was being previewed
          if (this.showPreview) {
            this.closePreview();
          }
        },
        error: err => console.error('Delete error:', err)
      });
    }
  }
}