import { Component } from '@angular/core';
import {MatRadioButton} from "@angular/material/radio";

@Component({
  selector: 'app-secure-text-editor',
  standalone: true,
  imports: [
    MatRadioButton
  ],
  templateUrl: './secure-text-editor.component.html',
  styleUrl: './secure-text-editor.component.scss'
})
export class SecureTextEditorComponent {

}
