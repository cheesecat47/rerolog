import { CommentListType } from 'types/model/Comment';
import CommentBox from './CommentBox';

const CommentBoxList = ({ comments }: { comments: CommentListType }) => (
    <div>
        {comments.map((comment) => (
            <CommentBox key={comment.userId} comment={comment} />
        ))}
    </div>
);

export default CommentBoxList;
