import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservUserListComponent } from './reserv-user-list.component';

describe('ReservUserListComponent', () => {
  let component: ReservUserListComponent;
  let fixture: ComponentFixture<ReservUserListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReservUserListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReservUserListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
