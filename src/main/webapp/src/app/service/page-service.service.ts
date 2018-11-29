import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PageServiceService {

  constructor() {
  }

  public returnpages(currentyPage: number, totalPages: number): Array<number> {
    let tmp: Array<number> = new Array<number>(null);
    let end, start

    if (totalPages <= 9) {
      start = 1;
      end = totalPages;

    }
    else if(currentyPage<=4){
     start=1;
     end=9;

    }
    else if (currentyPage >= totalPages) {
      start = totalPages - 8;
      end = totalPages;
    } else if (currentyPage + 1 >= totalPages) {
      start = currentyPage - 7;
      end = totalPages;
    } else if (currentyPage + 2 >= totalPages) {
      start = currentyPage - 6;
      end = totalPages;
    } else if (currentyPage + 3 >= totalPages) {
      start = currentyPage - 5;
      end = totalPages
    } else {

      if (totalPages >= currentyPage) {
        start = currentyPage - 4;
      } else {
        start = currentyPage;
      }
      if (currentyPage + 4 <= totalPages) {
        end = currentyPage + 4;
      } else {
        end = totalPages;
      }
    }


    let pages = Array.from(Array((end + 1) - start).keys()).map(i => start + i);
    return pages;
  }


//   let tmp: Array<number> = new Array<number>(null);
//   let end, start
//
//   if (totalPages <= 5) {
//   start = 1;
//   end = totalPages;
// } else if (currentyPage + 1 >= totalPages) {
//   start = totalPages - 4;
//   end = totalPages;
// } else {
//   if (totalPages >= currentyPage) {
//     start = currentyPage - 2;
//   } else {
//     start = currentyPage;
//   }
//   if (currentyPage + 2 <= totalPages) {
//     end = currentyPage + 2;
//   } else {
//     end = totalPages;
//   }
// }
//
// let pages = Array.from(Array((end + 1) - start).keys()).map(i => start + i);
// console.log(pages);
// return tmp;
}
