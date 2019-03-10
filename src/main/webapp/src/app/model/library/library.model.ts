import {UserMenager} from "../user/userMenager.model";
import {User} from "../user/user.model";

export class Library {
  id?: string
  city?: string
  email?: string
  latitude?: string
  local?: string
  longitude?: string
  name?: string
  number?: string
  postalCode?: string
  street?: string
  isbn?: string
  userMenager?: UserMenager;
  owner?: User;
}
