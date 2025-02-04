import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatDeleteComponent } from './chat-delete.component';

describe('ChatDeleteComponent', () => {
  let component: ChatDeleteComponent;
  let fixture: ComponentFixture<ChatDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChatDeleteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChatDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
