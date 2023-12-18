import React from 'react';
import { Header } from "../Header";
import { Footer } from "../Footer";

import { DEFAULT_BLOG_NAME } from "../../../constants/post";

const Layout = ({ children }: { children: React.ReactNode }) => {
    return (
        <div className="flex flex-col min-h-screen">
            <Header blogName={DEFAULT_BLOG_NAME} />
            <main className="flex-grow">
                {children}
            </main>
            <Footer />
        </div>
    );
}

export default Layout;
