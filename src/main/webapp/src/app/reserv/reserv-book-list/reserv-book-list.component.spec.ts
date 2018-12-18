import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservBookListComponent } from './reserv-book-list.component';

describe('ReservBookListComponent', () => {
  let component: ReservBookListComponent;
  let fixture: ComponentFixture<ReservBookListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReservBookListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReservBookListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
