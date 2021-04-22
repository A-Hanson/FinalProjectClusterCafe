import { Category } from "./category";
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
  category: Category = new Category();


  constructor(
    id?: number,
    title?: string,
    content?: string,
    createdAt?: string,
    updatedAt?: string,
    enabled?: boolean,
    flagged?: boolean,
    user?: User,
    category?: Category
    ){
      this.id = id;
      this.title = title;
      this.content = content;
      this.createdAt = createdAt;
      this.updatedAt = updatedAt;
      this.enabled = enabled;
      this.flagged = flagged;
      this.user = user;

      this.category = category === undefined ? new Category() : category;
}

}
