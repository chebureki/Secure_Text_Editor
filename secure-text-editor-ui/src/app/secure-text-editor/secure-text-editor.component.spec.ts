import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SecureTextEditorComponent } from './secure-text-editor.component';

describe('SecureTextEditorComponent', () => {
  let component: SecureTextEditorComponent;
  let fixture: ComponentFixture<SecureTextEditorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SecureTextEditorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SecureTextEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
