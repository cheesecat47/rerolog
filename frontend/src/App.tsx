import { createBrowserRouter, Outlet, RouterProvider } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";

import { PostWritePage } from "./pages/PostWritePage";
import { ManagePage } from "./pages/ManagePage";
import { UserPage } from "./pages/UserPage";
import { LoginPage } from "./pages/LoginPage";
import { PostList } from "./components/post/PostList";
import { PostDetail } from "./pages/PostDetailPage/components/PostDetail";
import { GuestBook } from "./pages/UserPage/components/GuestBook";
import { Introduce } from "./pages/UserPage/components/Introduce";

import { Layout } from "./components/layout/Layout";
import { MainPage } from "./pages/MainPage";

const queryClient = new QueryClient();

const router = createBrowserRouter([
    {
        path: '/',
        element: <Layout />,
        children: [
            { index: true, element: <MainPage /> },
            { path: 'manage', element: <ManagePage /> },
            { path: 'post/:sort?', element: <MainPage /> },
            {
                path: ':userId',
                element: <UserPage />,
                children: [
                    { index: true, element: <PostList /> },
                    { path: ':postId', element: <PostDetail /> },
                    { path: 'category/:categoryName?', element: <PostList /> },
                    { path: 'guestbook', element: <GuestBook /> },
                    { path: 'introduce', element: <Introduce /> },
                ]
            }
        ]
    },
    {
        path: '/',
        element: <Outlet />,
        children: [
            { path: 'login', element: <LoginPage /> },
            { path: 'write', element: <PostWritePage /> },
        ]
    }
])

const App = () => {
    return (
        // react query를 router 내의 어디서든 쓸 수 있도록
        <QueryClientProvider client={queryClient}>
            <RouterProvider router={router} />
            <ReactQueryDevtools initialIsOpen={false} />
        </QueryClientProvider>
    );
}

export default App;
