import { IAuthor } from './Author';
import { CommentListType } from './Comment';

export interface IPost {
    postId: number;
    categoryId: string;
    categoryName: string;
    title: string;
    author: IAuthor;
    createdAt: string;
    hit: number;
    excerpt: string;
    thumbnail: string;
    content: string;
    numOfComments: number;
}

export type PostListType = IPost[];

export interface IPostDetail extends IPost {
    comments: CommentListType;
}
