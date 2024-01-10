import { POST_OPTION } from '../../constants/post';
import { IAuthor } from './Author';

export type PostOption = keyof typeof POST_OPTION;

export type PostOptionType = {
    option: keyof typeof POST_OPTION;
};

export interface IPost {
    postId: number;
    categoryName: string;
    title: string;
    author: IAuthor;
    createdAt: string;
    description: string;
    hit: number;
    excerpt: string;
    thumbnail: string;
    numOfComments: number;
}
