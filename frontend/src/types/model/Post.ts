import { POST_OPTION } from '../../constants/post';
import { AuthorType } from './Author';

export type PostOption = keyof typeof POST_OPTION;

export type PostOptionType = {
    option: keyof typeof POST_OPTION;
};

export type PostType = {
    postId: number;
    categoryName: string;
    title: string;
    author: AuthorType;
    createdAt: string;
    description: string;
    hit: number;
    excerpt: string;
    thumbnail: string;
    numOfComments: number;
};
