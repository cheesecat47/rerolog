import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { MainPage } from "./pages/MainPage";
import { PostWritePage } from "./pages/PostWritePage";
import { ManagePage } from "./pages/ManagePage";
import { UserPage } from "./pages/UserPage";
import { LoginPage } from "./pages/LoginPage";
import { PostList } from "./components/post/PostList";
import { PostDetail } from "./pages/PostDetailPage/components/PostDetail";
import { GuestBook } from "./pages/UserPage/components/GuestBook";
import { Introduce } from "./pages/UserPage/components/Introduce";

import { POST_MODE } from "./constants/post";

const router = createBrowserRouter([
    {
        path: '/',
        element: <MainPage />,
    },
    {
        path: '/:userId',
        element: <UserPage />,
        children: [
            { index: true, element: <PostList mode={POST_MODE.recent} /> },
            { path: '/:userId/:postId', element: <PostDetail /> },
            { path: '/:userId/guestbook', element: <GuestBook /> },
            { path: '/:userId/introduce', element: <Introduce /> },
        ]
    },
    {
        path: '/login',
        element: <LoginPage />
    },
    {
        path: '/write',
        element: <PostWritePage />
    },
    {
        path: '/manage',
        element: <ManagePage />
    }
])

const App = () => {
    return (
        <RouterProvider router={router} />
    );
}

export default App;
