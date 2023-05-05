import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PoopMapComponent } from './poop-map.component';

describe('PoopMapComponent', () => {
  let component: PoopMapComponent;
  let fixture: ComponentFixture<PoopMapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PoopMapComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PoopMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
