import {LibraryPageRequest} from "../page/library.page.request";

export class BookRequestSearch {
  totalElements?: number;
  totalPages?: number;
  size?: number;
  number?: number;
  numberOfElements?: number;
  content?: Array<Book>;
}
