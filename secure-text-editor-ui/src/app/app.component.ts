import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormsModule} from '@angular/forms';
import {MatDividerModule} from '@angular/material/divider';
import {MatButtonModule} from '@angular/material/button';
import {MatIcon} from "@angular/material/icon";
import {EncryptionService} from "./EncryptionService";
import {MatOption} from "@angular/material/autocomplete";
import {MatSelect} from "@angular/material/select";
import {MatRadioButton, MatRadioGroup} from "@angular/material/radio";
import { CommonModule } from '@angular/common';
import {MatMenu} from "@angular/material/menu";
import { MatMenuModule} from '@angular/material/menu';
import {MatSidenavContainer, MatSidenavModule} from "@angular/material/sidenav";
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet, FormsModule, MatFormFieldModule, MatInputModule, MatDividerModule, MatButtonModule,
    MatIcon, MatOption, MatSelect, MatRadioGroup, MatRadioButton, CommonModule, MatMenu, MatMenuModule,
    MatSidenavContainer, MatSidenavModule, MatSnackBarModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'secure-text-editor-ui';
  fileContent: string = '';  // This will hold the text from the uploaded file
  encryptedContent: string = ''; // Holds the encrypted content
  selectedKeyLength: string = '128';
  selectedPasswordAlgorithm: string = '';
  selectedChaCha20Algorithm:string = '';
  selectedEncryptionType: string = '';
  selectedPadding: string = '';
  selectedBlockMode: string = '';
  submitted: boolean = false;

  constructor(private encryptionService: EncryptionService, private snackBar: MatSnackBar) {}

  // Function that is triggered when a file is selected
  onFileSelected(event: any, isDecrypt: boolean = false) {
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onload = (e: any) => {
      const fileContent = e.target.result; // Store file content temporarily
      console.log("File loaded, checking if decryption is needed.");

      if (isDecrypt) {
        // Call the decryption service
        this.encryptionService.decryptText(fileContent).subscribe({
          next: (decryptedText) => {
            console.log("Decryption successful");
            this.fileContent = decryptedText; // Update editor with decrypted text
          },
          error: (err) => {
            console.error('Decryption failed:', err);
            this.snackBar.open('Decryption failed', 'Close', { duration: 3000 });
          }
        });
      } else {
        // If no decryption is needed, load the file content as is
        this.fileContent = fileContent; // Load the file content to the editor
        console.log("File loaded without decryption.");
      }
    };

    reader.readAsText(file); // Read file content
  }

  // Encrypt the content by sending it to the backend
  encryptFileContent(): void {
    this.submitted = true;

    // Validate if the encryption option is selected
    if (!this.selectedEncryptionType) {
      this.snackBar.open('Please select an encryption type.', 'Close', { duration: 3000 });
      return;
    }

    // Validate if specific fields for the selected encryption type are filled
    if (this.selectedEncryptionType === 'AES_SYM' && (!this.selectedKeyLength || !this.selectedPadding || !this.selectedBlockMode)) {
      this.snackBar.open('Please fill in all the fields for AES Symmetric encryption.', 'Close', { duration: 3000 });
      return;
    }

    if (this.selectedEncryptionType === 'AES_PAS' && !this.selectedPasswordAlgorithm) {
      this.snackBar.open('Please fill in the key length for AES Password-based encryption.', 'Close', { duration: 3000 });
      return;
    }

    if (this.selectedEncryptionType === 'ChaCha20_PAS' && !this.selectedChaCha20Algorithm) {
      this.snackBar.open('Please fill in the key length for ChaCha20 Password-based encryption.', 'Close', { duration: 3000 });
      return;
    }

    const payload = {
      text: this.fileContent,
      encryptionType: this.selectedEncryptionType,
      keyLength: this.selectedKeyLength,
      passwordAlgorithm: this.selectedPasswordAlgorithm,
      chaCha20Algorithm: this.selectedChaCha20Algorithm,
      padding: this.selectedPadding,
      blockMode: this.selectedBlockMode
    };
    if('NoPadding_SYM' === payload.padding &&  !this.validateForAESNoPadding(payload.text)){
      this.snackBar.open('The text length must be a multiple of 16 bytes for AES with NoPadding.', 'Close', { duration: 3000 });
      return;
    }else{
      console.log('Encrypting with payload:', payload);
      this.encryptionService.encryptText(payload).subscribe({
        next: (encryptedData) => {
          this.encryptedContent = encryptedData;
          this.saveFile(this.encryptedContent); // Save the encrypted content
        },
        error: (err) => console.error('Encryption failed', err)
      });
    }

  }

  normalSave(): void {
    this.saveFile(this.fileContent); // Save the encrypted content
  }

  // Save the encrypted content to a file on the client's machine
  saveFile(content: string): void {
    const blob = new Blob([content], { type: 'text/plain' });
    const url = window.URL.createObjectURL(blob);

    const a = document.createElement('a');
    a.href = url;
    a.download = 'encrypted-text.txt'; // Name of the saved file
    a.click();

    window.URL.revokeObjectURL(url); // Clean up the object URL
  }

  validateForAESNoPadding(text: string): boolean {
    // Convert the string to a UTF-8 byte array
    if (text.length > 0) {
      const encoder = new TextEncoder();
      const textBytes = encoder.encode(text);

      // Check if the length of the byte array is a multiple of 16
      return textBytes.length % 16 === 0;
    }
    return false;
  }
}
