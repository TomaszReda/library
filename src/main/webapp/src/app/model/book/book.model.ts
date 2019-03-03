import {Library} from "../library/library.model";

export class Book {
  title?: string;
  isbn?: string;
  author?: string;
  publisher?: string;
  date?: Date;
  quant?: number;
  bookState?: string;
  description?:string;
  bookId?: string;
  categoryType?: string;
  library?:Library;
  deitalsGeneralSearch?: string;
  libraryName?: string;
  id?: string;
  bookSearch?: string;

}
