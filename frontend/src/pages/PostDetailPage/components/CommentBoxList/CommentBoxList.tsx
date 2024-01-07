import React from 'react';
import CommentBox from '../CommentBox/CommentBox';

export type CommentType = {
    commentId: string,
    content: string,
    createdAt: string,
    userId: string
};

type CommentListType = CommentType[];

const CommentBoxList = ({ comments }: { comments: CommentListType }) => {
    return (
        <div>
            {
                comments.map((comment) => {
                    return <CommentBox key={comment.userId} comment={comment} />
                })
            }
        </div>
    );
}

export default CommentBoxList;
