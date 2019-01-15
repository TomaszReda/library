import {Library} from "../library/library.model";

export class LibraryRequestSearch {
  totalElements?: number;
  totalPages?: number;
  size?: number;
  number?: number;
  numberOfElements?: number;
  content?: Array<Library>;
}
