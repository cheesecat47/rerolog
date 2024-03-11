import calendarIcon from '@/assets/icons/ML_calendar-icon.png';
import commentIcon from '@/assets/icons/ML_comment-icon.png';
import CategoryBadge from '@/components/common/CategoryBadge';
import { useNavigate } from 'react-router-dom';

import { IPost } from '@/types/model';

const PostCard = ({ post }: { post: IPost }) => {
    const navigate = useNavigate();

    const handleClick = () => {
        console.log(post.title, encodeURI(post.title));
        navigate(`/${post.author.userId}/${encodeURI(post.title)}`);
    };

    return (
        <button
            className="rounded-10 shadow-md h-92 cursor-pointer p-4 hover:shadow-lg"
            type="button"
            onClick={handleClick}
        >
            <div>
                <div className="flex item-center justify-between mb-1">
                    <div className="flex item-center">
                        <img
                            src={calendarIcon}
                            alt="calendar"
                            className="w-4 h-4 mr-2 "
                        />
                        <span className="text-xs">{post?.createdAt.slice(0, 10)}</span>
                    </div>
                    <CategoryBadge categoryName={post.categoryName} />
                </div>
                <div className="my-2 text-left">{post.title}</div>
                <div className="flex item-center">
                    <img
                        src={commentIcon}
                        alt="comment"
                        className="w-4 h-4 mr-1"
                    />
                    <span className="text-xs">{post.numOfComments}</span>
                </div>
            </div>
        </button>
    );
};

export default PostCard;
