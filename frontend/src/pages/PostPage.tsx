import React from 'react';
import { Outlet } from "react-router-dom";
import Header from "../components/layout/Header";
import { DEFAULT_BLOG_NAME } from "../constants/post";
import Navbar from "../components/layout/Navbar";

const PostPage = () => {
    return (
        <div>
            <Header blogName={DEFAULT_BLOG_NAME} />
            <Navbar />
            <Outlet />
        </div>
    );
}

export default PostPage;
