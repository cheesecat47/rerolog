import { POST_OPTION } from '../../constants/post';

export type PostOption = keyof typeof POST_OPTION;

export type PostOptionType = {
    option: keyof typeof POST_OPTION;
};

export type Post = {
    id: string;
    createdAt: string;
    title: string;
    description: string;
    category: string;
    commentsCount: number;
    thumbNail: string;
    hits: number;
};
