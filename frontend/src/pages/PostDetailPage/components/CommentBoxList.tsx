import { CommentListType } from '@/types/model/Comment';
import CommentBox from './CommentBox';

const CommentBoxList = ({ comments }: { comments: CommentListType | undefined }) => {
    return (
        <div>
            {comments && comments.map((comment, idx) => (
                <CommentBox key={idx} comment={comment} />
            ))}
        </div>
    )
};

export default CommentBoxList;
