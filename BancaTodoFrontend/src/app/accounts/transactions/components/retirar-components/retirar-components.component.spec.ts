import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RetirarComponentsComponent } from './retirar-components.component';

describe('RetirarComponentsComponent', () => {
  let component: RetirarComponentsComponent;
  let fixture: ComponentFixture<RetirarComponentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RetirarComponentsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RetirarComponentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
