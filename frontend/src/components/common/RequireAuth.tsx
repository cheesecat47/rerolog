import useUserStore from '@/stores/useUserStore';
import { Navigate, useLocation } from 'react-router-dom';

const RequireAuth = ({ children }: { children: JSX.Element }) => {
    const { isLogin } = useUserStore();
    const location = useLocation();

    if (!isLogin) {
        return <Navigate to="/login" state={{ from: location }} replace />;
    }

    return children;
};

export default RequireAuth;
