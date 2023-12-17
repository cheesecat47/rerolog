import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import MainPage from './pages/MainPage';
import AdminPage from "./pages/AdminPage";
import AdminInfo from "./components/admin/AdminInfo";
import PostPage from "./pages/PostPage";
import PostDetail from "./components/post/PostDetail";
import PostList from "./components/post/PostList";
import PostWrite from "./components/post/PostWrite";
import LoginPage from "./pages/LoginPage";

import { POST_MODE } from "./constants/post";

const router = createBrowserRouter([
    {
        path: '/',
        element: <MainPage />,
        children: [
            { index: true, element: <PostList mode={POST_MODE.recent} /> },
            { path: `${POST_MODE.trend}`, element: <PostList mode={POST_MODE.trend} /> },
            { path: `${POST_MODE.recent}`, element: <PostList mode={POST_MODE.recent} /> },
            { path: '/write', element: <PostWrite /> },
            { path: '/login', element: <LoginPage /> },

        ]
    },
    {
        path: '/admin',
        element: <AdminPage />,
        children: [
            { index: true, element: <AdminInfo /> },
        ]
    },
    {
        path: '/:userId',
        element: <PostPage />,
        children: [
            { index: true, element: <PostList mode={POST_MODE.recent} /> },
            { path: '/:userId/:postId', element: <PostDetail /> },

        ]
    }
])

const App = () => {
    return (
        <RouterProvider router={router} />
    );
}

export default App;
