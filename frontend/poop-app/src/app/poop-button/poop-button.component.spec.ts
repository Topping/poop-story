import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PoopButtonComponent } from './poop-button.component';

describe('PoopButtonComponent', () => {
  let component: PoopButtonComponent;
  let fixture: ComponentFixture<PoopButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PoopButtonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PoopButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
