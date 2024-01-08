import { POST_OPTION } from '../../constants/post';

export type PostOption = keyof typeof POST_OPTION;

export type PostOptionType = {
    option: keyof typeof POST_OPTION;
};

export type Post = {
    post_id: number;
    category_id: number;
    title: string;
    author: string;
    datetime: string;
    description: string;
    hit: number;
    excerpt: string;
    thumbnail: string;
};
