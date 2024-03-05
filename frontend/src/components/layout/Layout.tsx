import { Outlet } from 'react-router-dom';

import Footer from './Footer';
import Header from './Header';

import { DEFAULT_BLOG_NAME } from '@/constants/post';

const Layout = () => (
    <div className="flex flex-col min-h-screen">
        <Header blogName={DEFAULT_BLOG_NAME} />
        <main className="flex-grow">
            <Outlet />
        </main>
        <Footer />
    </div>
);

export default Layout;
