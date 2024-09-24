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

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, FormsModule, MatFormFieldModule, MatInputModule, MatDividerModule, MatButtonModule, MatIcon, MatOption, MatSelect, MatRadioGroup, MatRadioButton, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'secure-text-editor-ui';
  fileContent: string = '';  // This will hold the text from the uploaded file
  encryptedContent: string = ''; // Holds the encrypted content
  selectedKeyLength: string = '128';
  selectedEncryptionMethod: string = 'symmetric'; // Default option
  selectedPasswordAlgorithm: string = '';
  selectedChaCha20Algorithm:string = '';
  selectedSignatureAlgorithm: string = 'SHA256withDSA';
  selectedEncryptionType: string = '';

  constructor(private encryptionService: EncryptionService) {}

  // Function that is triggered when a file is selected
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input?.files[0];  // Get the selected file
      if(file.type === 'text/plain'){
      const reader = new FileReader();

      // This event is triggered once the file has been read
      reader.onload = (e: any) => {
        this.fileContent = e.target.result;  // Set the file content to textarea
      };

      reader.onerror = (error) => {
        console.error('Error reading file:', error);
      };

      reader.readAsText(file);  // Read the file as text
    }
    }
  }
  // Encrypt the content by sending it to the backend
  encryptFileContent(): void {
    console.log('Encrypting with:', this.selectedEncryptionType);
    this.encryptionService.encryptText(this.fileContent).subscribe({
      next: (encryptedData) => {
        this.encryptedContent = encryptedData;
        this.saveFile(this.encryptedContent); // Save the encrypted content
      },
      error: (err) => console.error('Encryption failed', err)
    });
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
}
