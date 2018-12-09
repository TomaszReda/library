import { MyReservBookModule } from './my-reserv-book.module';

describe('MyReservBookModule', () => {
  let myReservBookModule: MyReservBookModule;

  beforeEach(() => {
    myReservBookModule = new MyReservBookModule();
  });

  it('should create an instance', () => {
    expect(myReservBookModule).toBeTruthy();
  });
});
