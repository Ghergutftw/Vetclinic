import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdoptionsComponent } from './adoptions.component';

describe('AdoptionsComponent', () => {
  let component: AdoptionsComponent;
  let fixture: ComponentFixture<AdoptionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdoptionsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdoptionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
