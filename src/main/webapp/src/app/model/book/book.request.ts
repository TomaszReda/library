import {LibraryPageRequest} from "../page/library.page.request";
import {Book} from "./book.model";

export class BookRequestSearch {
  totalElements?: number;
  totalPages?: number;
  size?: number;
  number?: number;
  numberOfElements?: number;
  content?: Array<Book>;
}
