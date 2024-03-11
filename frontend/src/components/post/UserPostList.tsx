import { usePost } from '@/hooks/usePost';
import useUserStore from "@/stores/useUserStore";
import { IPost } from '@/types/model';
import { useParams } from "react-router-dom";
import PostCard from './PostCard';

const UserPostList = () => {
    const { userId } = useUserStore();
    const { getPostList } = usePost();
    const { categoryName } = useParams();
    const { isLoading, error, data: posts } = getPostList({ userId,  });
    
    console.log(categoryName);
    
    if (isLoading) return <div>로딩중입니다</div>;

    if (error) return <div>데이터를 받아오지 못했습니다</div>;

    if (!categoryName) {
        return (
            <div className="w-full p-6 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
                {
                    !posts?.length && <div>게시물이 없습니다.</div>
                }
                {posts &&
                    posts.map((post: IPost) => (
                        <PostCard key={post.postId} post={post} />
                    ))}
            </div>
        );
    }

    const filteredPost = posts && posts.filter(x => x.categoryName === categoryName); 
    
    return (
        <div className="w-full p-6 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
            {
                !filteredPost?.length && <div>게시물이 없습니다.</div>
            }
            {filteredPost &&
                filteredPost.map((post: IPost) => (
                    <PostCard key={post.postId} post={post} />
                ))}
        </div>
    );
};

export default UserPostList;
