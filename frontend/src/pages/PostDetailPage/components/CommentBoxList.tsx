import CommentBox from './CommentBox';

export type CommentType = {
    commentId: string;
    content: string;
    createdAt: string;
    userId: string;
};

type CommentListType = CommentType[];

const CommentBoxList = ({ comments }: { comments: CommentListType }) => (
    <div>
        {comments.map((comment) => (
            <CommentBox key={comment.userId} comment={comment} />
        ))}
    </div>
);

export default CommentBoxList;
