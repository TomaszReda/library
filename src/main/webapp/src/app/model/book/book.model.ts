import {FormControl} from "@angular/forms";

export class Book {
  title?: string;
  ISBN?: string;
  author?: string;
  publisher?: string;
  date?: Date;
  quant?: number;
  bookState?: string;
  description?:string;
  bookId?: string;
  categoryType?: string;
}
