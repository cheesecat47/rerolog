import calendar from '@/assets/icons/ML_calendar-icon.png';
import comment from '@/assets/icons/ML_comment-icon.png';
import CategoryBadge from '@/components/common/CategoryBadge';
import { useComment } from "@/hooks/useComment";
import { usePost } from '@/hooks/usePost';
import useUserStore from "@/stores/useUserStore";
import { useState } from "react";
import { useParams } from 'react-router-dom';
import CommentBoxList from "./components/CommentBoxList";

const PostDetailPage = () => {
    const { userId, postTitle } = useParams();
    const { userId: myId } = useUserStore();
    const [inputComment, setInputComment] = useState<string>('');

    if (!userId || !postTitle) return <div>잘못된 경로입니다.</div>;

    const { getPostDetail } = usePost();
    const { isLoading, error, data: post } = getPostDetail({ userId, postTitle });

    const { getPostComment, writePostComment } = useComment();
    const { data: comments } = getPostComment({ postTitle });

    const { mutate } = writePostComment(postTitle);

    if (!post) return <div>잘못된 경로입니다.</div>;

    if (isLoading) return <div>로딩중입니다</div>;

    if (error) return <div>데이터를 받아오지 못했습니다</div>;

    const handleComment = (e:React.ChangeEvent<HTMLTextAreaElement>) => {
        setInputComment(e.target.value);
    }

    // 댓글 작성
    const handleWriteComment = () => {
        mutate({ content: inputComment });
        setInputComment('');
    }

    const createMarkup = (content: string) => {
        return {__html: content};
      }

    console.log(post);

    return (
        <div className="w-[60%] m-auto">
            {/* header */}
            <div className="mt-14 mb-4 flex justify-between">
                <span className="text-3xl font-bold text-gray-600">
                    {post.title}
                </span>
                {post.author.userId === myId && (
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
            <div className="flex text-sm items-center">
                <div>
                    <CategoryBadge categoryName={post.categoryName} />
                </div>
                <div className="flex  items-center ml-2">
                    <img className="w-4 h-4" src={calendar} alt="calendar" />
                    <span className="px-2">{post?.createdAt.slice(0, 10)}</span>
                </div>
                <div className="flex">
                    <img className="w-4 h-4" src={comment} alt="comment" />
                    <span className="px-2">{comments?.numOfComments}</span>
                </div>
            </div>
            {/* 목차 */}
            {/* contents */}
            <div className="py-10 min-h-[30vh]" dangerouslySetInnerHTML={createMarkup(post.content)}/>
            
            <div className="py-5">
                <div className="flex">
                    <img className="w-4 h-4" src={comment} alt="comment" />
                    <span className="px-2 font-semibold">
                        {post.comments?.length} Comments
                    </span>
                </div>
                <div className="py-4">
                    <div className="bg-gray-100 p-4 rounded-10">
                        <textarea
                            className="bg-transparent outline-none w-full"
                            rows={5}
                            placeholder="댓글을 입력하세요"
                            onChange={handleComment}
                            value={inputComment}
                        />
                    </div>
                </div>
                <button
                    type="button"
                    className="bg-ml-pink-100 text-ml-pink-300 px-4 py-1 float-right rounded-10 hover:bg-ml-pink-200 hover:text-white"
                    onClick={handleWriteComment}
                >
                    작성하기
                </button>
            </div>
            <br />
            <hr />
            <CommentBoxList comments={comments?.comments} />
        </div>
    );
};

export default PostDetailPage;
