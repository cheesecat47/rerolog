import { POST_OPTION } from '../../constants/post';
import { IAuthor } from './Author';
import { CommentListType } from './Comment';

export type PostOption = keyof typeof POST_OPTION;

export type PostOptionType = {
    option: keyof typeof POST_OPTION;
};

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
