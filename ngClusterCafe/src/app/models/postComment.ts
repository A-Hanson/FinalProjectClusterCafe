import { Post } from "./post";
import { User } from "./user";

export class PostComment {
  id: number;
  content: string;
  user: User;
  post: Post;
  enabled: boolean;
  flagged: boolean;
  createdAt: string;
  updatedAt: string;

  constructor(
    id?: number,
    content?: string,
    user?: User,
    post?: Post,
    enabled?: boolean,
    flagged?: boolean,
    createdAt?: string,
    updatedAt?: string
  ) {
    this.id = id;
    this.content = content;
    this.user = user;
    this.post = post;
    this.enabled = enabled;
    this.flagged = flagged;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}
