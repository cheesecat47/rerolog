import { useQuery } from '@tanstack/react-query';
import calendar from 'assets/icons/ML_calendar-icon.png';
import comment from 'assets/icons/ML_comment-icon.png';
import axios from 'axios';
import CategoryBadge from 'components/common/CategoryBadge';
import { useParams } from 'react-router-dom';
import CommentBoxList from './components/CommentBoxList';

const PostDetailPage = () => {
    const { userId, postId } = useParams();

    // @TODO userId, postId를 가지고 통신
    const {
        isLoading,
        error,
        data: post,
    } = useQuery({
        queryKey: [`posts${postId}`],
        queryFn: async () =>
            axios.get('/data/posts/detail.json').then((res) => res.data),
        staleTime: 5 * 60 * 1000,
    });

    if (isLoading) return <div>로딩중입니다</div>;

    if (error) return <div>데이터를 받아오지 못했습니다</div>;

    return (
        <div className="w-[60%] m-auto">
            {/* header */}
            <div className="mt-14 mb-4 flex justify-between">
                <span className="text-3xl font-bold text-gray-600">
                    {post.title}
                </span>
                {post.author.userId === userId && (
                    <div className="pr-2 text-sm text-gray-500">
                        <button
                            className="px-1 hover:text-gray-800"
                            type="button"
                        >
                            수정
                        </button>
                        <button
                            className="px-1 hover:text-gray-800"
                            type="button"
                        >
                            삭제
                        </button>
                    </div>
                )}
            </div>
            {/* category */}
            <div className="flex text-sm">
                <div>
                    <CategoryBadge categoryName={post.categoryName} />
                </div>
                <div className="flex">
                    <img className="w-4 h-4" src={calendar} alt="calendar" />
                    <span className="px-2">{post.createdAt}</span>
                </div>
                <div className="flex">
                    <img className="w-4 h-4" src={comment} alt="comment" />
                    <span className="px-2">{post.comments?.length}</span>
                </div>
            </div>
            {/* 목차 */}
            {/* contents */}
            <div className="py-10 min-h-[30vh]">{post.content}</div>

            <hr />
            <div className="py-5">
                <div className="flex">
                    <img className="w-4 h-4" src={comment} alt="comment" />
                    <span className="px-2 font-semibold">
                        {post.comments?.length} Comments
                    </span>
                </div>
                {/* textarea */}
                <div className="py-4">
                    <div className="bg-gray-100 p-4 rounded-10">
                        <textarea
                            className="bg-transparent outline-none w-full"
                            rows={5}
                            placeholder="댓글을 입력하세요"
                        />
                    </div>
                </div>
                <button
                    type="button"
                    className="bg-ml-pink-100 text-ml-pink-300 px-4 py-1 float-right rounded-10 hover:bg-ml-pink-200 hover:text-white"
                >
                    작성하기
                </button>
            </div>

            <br />
            <hr />

            <CommentBoxList comments={post.comments} />
        </div>
    );
};

export default PostDetailPage;
