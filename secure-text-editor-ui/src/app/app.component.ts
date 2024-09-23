import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {ApiService} from "./ApiService";
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormsModule} from '@angular/forms';
import {MatDividerModule} from '@angular/material/divider';
import {MatButtonModule} from '@angular/material/button';
import {MatIcon} from "@angular/material/icon";


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, FormsModule, MatFormFieldModule, MatInputModule, MatDividerModule, MatButtonModule, MatIcon],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'secure-text-editor-ui';
  fileContent: string = '';  // This will hold the text from the uploaded file

  // Function that is triggered when a file is selected
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input?.files[0];  // Get the selected file
      console.log(file.type);
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
}
