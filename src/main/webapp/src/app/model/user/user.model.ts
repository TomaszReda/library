import {UserCasual} from "./userCasual.model";
import {UserMenager} from "./userMengaer.model";

export class User {
  email?: string;
  firstname?: string;
  id?: string;
  lastname?: string;
  phoneNumber?: string;
  userCasual?: UserCasual;
  userMengaer?: UserMenager;
}
