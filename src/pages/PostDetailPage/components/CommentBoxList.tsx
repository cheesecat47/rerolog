import { CommentListType } from 'interfaces/model/Comment';
import CommentBox from './CommentBox';

const CommentBoxList = ({ comments }: { comments: CommentListType }) => (
    <div>
        {comments.map((comment) => (
            <CommentBox key={comment.userId} comment={comment} />
        ))}
    </div>
);

export default CommentBoxList;
