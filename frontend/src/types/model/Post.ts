import { POST_MODE } from '../../constants/post';

export type PostMode = keyof typeof POST_MODE;

export type PostModeType = {
    mode: keyof typeof POST_MODE;
};
