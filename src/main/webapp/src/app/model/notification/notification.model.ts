import {taskForLibrary, taskForUser} from "../task/task.model";

export  class notification{
  content?: string
  title?: string
  dataCreate?: string
  dateRead?: string
  taskForLibrary?: Array<taskForLibrary>
  taskForUser?: Array<taskForUser>
  messageDisplay? : string;
  id?: string
}
