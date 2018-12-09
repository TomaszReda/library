import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyReservBookListComponent } from './my-reserv-book-list.component';

describe('MyReservBookListComponent', () => {
  let component: MyReservBookListComponent;
  let fixture: ComponentFixture<MyReservBookListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyReservBookListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyReservBookListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
