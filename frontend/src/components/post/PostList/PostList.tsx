import React from 'react';
import { POST_MODE } from "../../../constants/post";
import { PostModeType } from "../../../types/model";

const PostList = ({ mode }: PostModeType) => {
    return (
        <div>
            {mode === POST_MODE.recent ? "최신순" : "인기순"}
        </div>
    );
}

export default PostList;
