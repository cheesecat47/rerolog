import React from 'react';
// import { useParams } from "react-router-dom";
import { useQuery } from '@tanstack/react-query';
import axios from 'axios';
import { PostCard } from '../PostCard';
import { POST_OPTION } from '../../../constants/post';

import { Post } from '../../../types/model';

const PostList = ({ selectedOption }: { selectedOption?: string }) => {

    const { isLoading, error, data: posts } = useQuery({
        queryKey: ['posts', selectedOption || POST_OPTION.recent],
        queryFn: async () => axios.get(`/data/posts/${selectedOption || POST_OPTION.recent}Post.json`).then((res) => {
            return res.data;
        }),
        staleTime: 5 * 60 * 1000,
    });

    if (isLoading) return <div>로딩중입니다</div>;

    if (error) return <div>데이터를 받아오지 못했습니다</div>;

    return (
        <div className="w-full p-6 grid grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4" >
            {
                posts.map((post: Post) => {
                    return <PostCard key={post.id} post={post} />
                })

            }
        </div>
    );
}

export default PostList;
