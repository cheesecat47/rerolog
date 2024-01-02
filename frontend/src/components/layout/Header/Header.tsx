// import React, { useEffect, useState } from 'react';

import { Link, useNavigate } from 'react-router-dom';

import flower from '../../../assets/icons/ML_flower-icon.png';
import login from '../../../assets/icons/ML_login_icon.png';
import logout from '../../../assets/icons/ML_logout-icon.png';
import moon from '../../../assets/icons/ML_moon-icon.png';
import sun from '../../../assets/icons/ML_sun-icon.png';
import my from '../../../assets/icons/ML_my-icon.png';
import pencil from '../../../assets/icons/ML_pencil-icon.png';

import { HeaderIconText } from '../../common/HeaderIconText';

const Header = ({ blogName }: { blogName: string }) => {
    // 추후 로그인 여부 확인
    // const [isLogin, setIsLogin] = useState(false);

    const checkIsLogin = () => {
        return true;
    };

    const navigate = useNavigate();

    const goWrite = () => {
        navigate('/write');
    };

    const goMy = () => {
        navigate('/my');
    };

    const handleLogout = () => {
        // 로그아웃 로직
    };

    return (
        <header className="w-full h-16 flex justify-end bg-transparent font-PyeongChangPeace ">
            <Link to="/" className="basis-[20%] flex justify-center items-center cursor-pointer">
                <img className="w-8" src={flower} alt="logo" />
                <span className="text-gray-700 font-semibold text-lg pl-1">{blogName}</span>
            </Link>
            <div className="basis-[40%] flex justify-end">
                {
                    checkIsLogin()
                        ?
                        <div className="hidden sm:flex w-full justify-end">
                            <HeaderIconText icon={pencil} alt="pencil" text="Write" onClick={goWrite} />
                            <HeaderIconText icon={my} alt="my" text="My" onClick={goMy} />
                            <HeaderIconText icon={logout} alt="logout" text="Logout" onClick={handleLogout} />
                        </div>
                        :
                        <HeaderIconText icon={login} alt="login" text="Login" onClick={handleLogout} />
                }
                <div className="hidden md:flex justify-center items-center mr-4">
                    <img className="w-5" src={sun} alt="sun" />
                    <div className="w-24 rounded-full mx-1 bg-gray-100">
                        &nbsp;
                    </div>
                    <img className="w-5" src={moon} alt="moon" />
                </div>
            </div>
        </header>
    );
};

export default Header;