import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import {SecureTextEditorComponent} from "./secure-text-editor/secure-text-editor.component";

const routes: Routes = [
  { path: '', component: SecureTextEditorComponent }, // default route
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
