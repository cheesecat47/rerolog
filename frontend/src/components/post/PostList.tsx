import { usePost } from 'hooks/usePost';
import { IPost } from 'types/model';
import PostCard from './PostCard';

const PostList = ({ selectedOption }: { selectedOption?: string }) => {
    const { getPostList } = usePost();

    const {
        isLoading,
        error,
        data: posts,
    } = getPostList({ order: selectedOption });

    if (isLoading) return <div>로딩중입니다</div>;

    if (error) return <div>데이터를 받아오지 못했습니다</div>;

    return (
        <div className="w-full p-6 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
            {posts &&
                posts.map((post: IPost) => (
                    <PostCard key={post.postId} post={post} />
                ))}
        </div>
    );
};

export default PostList;
