import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsignarComponentsComponent } from './consignar-components.component';

describe('ConsignarComponentsComponent', () => {
  let component: ConsignarComponentsComponent;
  let fixture: ComponentFixture<ConsignarComponentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConsignarComponentsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsignarComponentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
