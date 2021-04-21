import { User } from "./user";

export class Post {
  id: number;
  title: string;
  content: string;
  createdAt: string;
  updatedAt: string;
  enabled: boolean;
  flagged: boolean;
  user: User;



  constructor(
    id?: number,
    title?: string,
    content?: string,
    createdAt?: string,
    updatedAt?: string,
    enabled?: boolean,
    flagged?: boolean,
    user?: User
    ){
      this.id = id;
      this.title = title;
      this.content = content;
      this.createdAt = createdAt;
      this.updatedAt = updatedAt;
      this.enabled = enabled;
      this.flagged = flagged;
      this.user = user;
}

}
