import { useQuery } from '@tanstack/react-query';
import axios from 'axios';
import { POST_OPTION } from 'constants/post';
import { PostType } from 'types/model';
import PostCard from './PostCard';

const PostList = ({ selectedOption }: { selectedOption?: string }) => {
    const {
        isLoading,
        error,
        data: posts,
    } = useQuery({
        queryKey: ['posts', selectedOption || POST_OPTION.recent],
        queryFn: async () =>
            axios
                .get(
                    `/data/posts/${
                        selectedOption || POST_OPTION.recent
                    }Post.json`
                )
                .then((res) => res.data),
        staleTime: 5 * 60 * 1000,
    });

    if (isLoading) return <div>로딩중입니다</div>;

    if (error) return <div>데이터를 받아오지 못했습니다</div>;

    return (
        <div className="w-full p-6 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
            {posts.map((post: PostType) => (
                <PostCard key={post.postId} post={post} />
            ))}
        </div>
    );
};

export default PostList;
