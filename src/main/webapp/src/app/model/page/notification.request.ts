import {notification} from "../notification/notification.model";

export class NotificationRequest {
  totalElements?: number;
  totalPages?: number;
  size?: number;
  number?: number;
  numberOfElements?: number;
  content?: Array<notification>;
}
