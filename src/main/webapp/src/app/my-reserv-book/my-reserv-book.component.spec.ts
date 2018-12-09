import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyReservBookComponent } from './my-reserv-book.component';

describe('MyReservBookComponent', () => {
  let component: MyReservBookComponent;
  let fixture: ComponentFixture<MyReservBookComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyReservBookComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyReservBookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
