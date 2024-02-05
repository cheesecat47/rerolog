export interface IComment {
    commentId: string;
    content: string;
    createdAt: string;
    userId: string;
}

export type CommentListType = IComment[];
