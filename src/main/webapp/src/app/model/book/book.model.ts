import {FormControl} from "@angular/forms";

export class Book {
  title?: string;
  ISBN?: string;
  author?: string;
  publisher?: string;
  date?: Date;
  quant?: number;
  bookState?: string;
  bookId?: string;
}
