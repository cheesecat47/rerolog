import { CommentListType } from "../model";
import { ApiResponse } from "./api";

export type getPostCommentRequest = {
    postTitle: string
}


export type getPostCommentResponse = ApiResponse<{
    comments: CommentListType;
    numOfComments: number;
}>

export type writePostCommentRequest = {
    content: string;
}