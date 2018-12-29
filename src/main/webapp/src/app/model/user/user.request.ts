import {User} from "./user.model";


export class UserRequestSearch {
  totalElements?: number;
  totalPages?: number;
  size?: number;
  number?: number;
  numberOfElements?: number;
  content?: Array<User>;
}
