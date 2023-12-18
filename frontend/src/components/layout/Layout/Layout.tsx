import React from 'react';
import { Header } from "../Header";
import { Footer } from "../Footer";

import { DEFAULT_BLOG_NAME } from "../../../constants/post";
import { Outlet } from "react-router-dom";

const Layout = () => {
    return (
        <div>
            <Header blogName={DEFAULT_BLOG_NAME} />
            <main>
                <Outlet />
            </main>
            <Footer />
        </div>
    );
}

export default Layout;
