import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReturnBookListComponent } from './return-book-list.component';

describe('ReturnBookListComponent', () => {
  let component: ReturnBookListComponent;
  let fixture: ComponentFixture<ReturnBookListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReturnBookListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReturnBookListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
