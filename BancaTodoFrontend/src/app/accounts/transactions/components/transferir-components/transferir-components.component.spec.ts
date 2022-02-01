import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransferirComponentsComponent } from './transferir-components.component';

describe('TransferirComponentsComponent', () => {
  let component: TransferirComponentsComponent;
  let fixture: ComponentFixture<TransferirComponentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransferirComponentsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TransferirComponentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
