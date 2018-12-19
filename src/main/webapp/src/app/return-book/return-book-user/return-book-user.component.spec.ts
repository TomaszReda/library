import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReturnBookUserComponent } from './return-book-user.component';

describe('ReturnBookUserComponent', () => {
  let component: ReturnBookUserComponent;
  let fixture: ComponentFixture<ReturnBookUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReturnBookUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReturnBookUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
