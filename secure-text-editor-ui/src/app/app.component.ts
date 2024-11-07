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
import { ReactiveFormsModule } from '@angular/forms';
import { ToastrService } from "ngx-toastr";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet, FormsModule, MatFormFieldModule, MatInputModule, MatDividerModule, MatButtonModule,
    MatIcon, MatOption, MatSelect, MatRadioGroup, MatRadioButton, CommonModule, MatMenu, MatMenuModule,
    MatSidenavContainer, MatSidenavModule, MatSnackBarModule, ReactiveFormsModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'secure-text-editor-ui';
  fileContent: string = '';  // This will hold the text from the uploaded file
  encryptedContent: string = ''; // Holds the encrypted content
  selectedKeySize: string = '';
  selectedPasswordAlgorithm: string = '';
  selectedChaCha20Algorithm:string = '';
  selectedEncryptionType: string = '';
  selectedPadding: string = '';
  selectedBlockMode: string = '';
  submitted: boolean = false;
  fileName: string= '';

  constructor(private encryptionService: EncryptionService, private snackBar: MatSnackBar, private toastr:ToastrService) {


  }
  // Function that is triggered when a file is selected
  onFileSelected(event: any, isDecrypt: boolean = false) {
    let file = event.target.files[0];
    const reader = new FileReader();

    reader.onload = (e: any) => {
      const fileContent = e.target.result; // Store file content temporarily
      if (isDecrypt) {
        // Call the decryption service
        if(fileContent.indexOf(".") >= 0){
        this.encryptionService.decryptText(fileContent).subscribe({
            next: (decryptedText) => {
              this.toastr.success('Decryption successful');
              this.fileContent = decryptedText; // Update editor with decrypted text
              console.log(fileContent.length);
            },
            error: (err) => {
              console.error('Decryption failed:', err);
              this.toastr.error('Decryption failed', 'Decryption Failure');
            }
          });
        }else {
          this.toastr.error('This does not seem to be an encrypted text, try upload "Plain Text"', );
        }

      } else {
        // If no decryption is needed, load the file content as is
        this.fileContent = fileContent; // Load the file content to the editor
        this.snackBar.open('File loaded without decryption.', 'Close', { duration: 3000 });
      }
      // Reset the file input to allow the same file to be uploaded again
      event.target.value = '';
    };

    reader.readAsText(file); // Read file content
  }

  // Encrypt the content by sending it to the backend
  encryptFileContent(): void {
    this.submitted = true;

    // Validate if the encryption option is selected
    if (!this.selectedEncryptionType) {
      //alert('Please select an encryption type.');
      this.toastr.warning('Please select an encryption type.');
      return;
    }

    // Validate if specific fields for the selected encryption type are filled
    if (this.selectedEncryptionType === 'AES_SYM' && (!this.selectedKeySize || !this.selectedPadding || !this.selectedBlockMode)) {
      this.toastr.warning('Please fill in all the fields for AES Symmetric encryption.');
      return;
    }

    if (this.selectedEncryptionType === 'AES_PAS' && !this.selectedPasswordAlgorithm) {
      this.toastr.warning('Please fill in the key length for AES Password-based encryption.');
      return;
    }

    if (this.selectedEncryptionType === 'ChaCha20_PAS' && !this.selectedChaCha20Algorithm) {
      this.toastr.warning('Please fill in the key length for ChaCha20 Password-based encryption.');
      return;
    }

    const payload = {
      text: this.fileContent,
      encryptionType: this.selectedEncryptionType,
      keySize: this.selectedKeySize,
      passwordAlgorithm: this.selectedPasswordAlgorithm,
      chaCha20Algorithm: this.selectedChaCha20Algorithm,
      padding: this.selectedPadding,
      blockMode: this.selectedBlockMode
    };
    if('NoPadding_SYM' === payload.padding &&  !this.validateForAESNoPadding(payload.text, payload.blockMode)){

      this.toastr.error('The text length must be a multiple of 16 bytes for AES with NoPadding.');
      return;
    }else if('CTS_SYM' === payload.blockMode && !this.validateCTS(payload.text)){
      console.log(payload.text.length)
      this.toastr.error('The text length must be a at least 16 bytes for AES with CTSPadding.');
      return;
    }else{
      this.snackBar.open('Encrypting the payload!', 'Close', { duration: 3000 });
      this.encryptionService.encryptText(payload).subscribe({
        next: (encryptedData) => {
          this.encryptedContent = encryptedData;
          this.saveFile(this.encryptedContent);
        },
        error: (err) => {
          console.error('Encryption failed', err)
          alert('Encryption failed :( ');
        }
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
    if (this.fileName === ''){
      this.fileName  = 'encrypted-text.txt';
    }else if(!this.fileName.indexOf(".txt")){
      this.fileName += ".txt";
    }
    a.download = this.fileName; // Name of the saved file
    a.click();

    window.URL.revokeObjectURL(url); // Clean up the object URL
    this.toastr.success('Encryption successful');
  }
  validateCTS(text: string): boolean{
    return text.length > 15;
  }
  validateForAESNoPadding(text: string, blockMode: string): boolean {
    // Convert the string to a UTF-8 byte array
    if(blockMode === 'GCM_SYM' || blockMode === 'CTS_SYM'){
      return true;
    }
    if (text.length > 1) {
      const encoder = new TextEncoder();
      const textBytes = encoder.encode(text);
      // Check if the length of the byte array is a multiple of 16
      return textBytes.length % 16 === 0;
    }
    console.log(3)
    return false;
  }

  onBlockModeChange(): void {
    if (this.selectedBlockMode === 'GCM_SYM' || this.selectedBlockMode === 'CTS_SYM') {
      this.selectedPadding = 'NoPadding_SYM';
    }
  }
}
