import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatCadastroComponent } from './chat-cadastro.component';

describe('ChatCadastroComponent', () => {
  let component: ChatCadastroComponent;
  let fixture: ComponentFixture<ChatCadastroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChatCadastroComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChatCadastroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
