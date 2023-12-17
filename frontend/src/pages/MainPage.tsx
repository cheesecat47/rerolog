import { Outlet } from "react-router-dom";
import Header from "../components/layout/Header";
import { DEFAULT_BLOG_NAME } from "../constants/post";

const MainPage = () => {

    return (
        <div>
            <Header blogName={DEFAULT_BLOG_NAME} />
            <Outlet />
        </div>
    );
}

export default MainPage;