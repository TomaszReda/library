import {LibraryPageRequest} from "./library.page.request";

export class PageRequest {
  totalElements?: number;
  totalPages?: number;
  size?: number;
  number?: number;
  numberOfElements?: number;
  content?: Array<LibraryPageRequest>;

}
