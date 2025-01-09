import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { SecureTextEditorComponent } from './secure-text-editor/secure-text-editor.component';

@NgModule({
  declarations: [
  ],
  imports: [
    BrowserModule,
    FormsModule,
    CommonModule,
    AppRoutingModule,
    SecureTextEditorComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
