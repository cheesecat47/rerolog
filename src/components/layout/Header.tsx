// import React, { useEffect, useState } from 'react';

import { HeaderIconText, PopOver } from 'components/common';
import { IPopOverProps } from 'interfaces/common/PopOverProps';
import { Link, useNavigate } from 'react-router-dom';

import { logout } from 'apis/user';
import flower from 'assets/icons/ML_flower-icon.png';
import login from 'assets/icons/ML_login_icon.png';
import logoutIcon from 'assets/icons/ML_logout-icon.png';
import moon from 'assets/icons/ML_moon-icon.png';
import my from 'assets/icons/ML_my-icon.png';
import pencil from 'assets/icons/ML_pencil-icon.png';
import sun from 'assets/icons/ML_sun-icon.png';
import useUserStore from 'stores/useUserStore';
import {
    deleteAccessToken,
    deleteIsLogin,
    deleteRefreshToken,
    deleteUserId,
    getAccessToken,
    getUserId,
} from 'utils/localStorage';

const Header = ({ blogName }: { blogName: string }) => {
    const { isLogin, userId: myId, logoutUser } = useUserStore();

    const navigate = useNavigate();

    const goWrite = () => {
        navigate('/write');
    };

    const popOverList: IPopOverProps[] = [
        {
            text: '내 블로그',
            link: `/${myId}/posts`,
        },
        {
            text: '설정하기',
            link: '/manage',
        },
    ];

    const handlePopOver = (pop: IPopOverProps) => {
        navigate(pop.link);
    };

    const handleLogout = async () => {
        const userId = getUserId();
        const accessToken = getAccessToken();

        if (!userId || !accessToken) {
            alert('로그아웃에 실패했습니다.');
            return;
        }

        await logout({
            userId,
            accessToken,
        });

        logoutUser();
        deleteIsLogin();
        deleteUserId();
        deleteAccessToken();
        deleteRefreshToken();
        navigate('/');
    };

    const handleLogin = () => {
        navigate('/login');
    };

    return (
        <header className="w-full h-16 flex justify-end bg-transparent font-PyeongChangPeace ">
            <Link
                to="/"
                className="basis-[20%] flex justify-center items-center cursor-pointer"
            >
                <img className="w-8" src={flower} alt="logo" />
                <span className="text-gray-700 font-semibold text-lg pl-1">
                    {blogName}
                </span>
            </Link>
            <div className="basis-[40%] flex justify-end">
                {isLogin ? (
                    <div className="hidden sm:flex w-full justify-end">
                        <HeaderIconText
                            icon={pencil}
                            alt="pencil"
                            text="Write"
                            onClick={goWrite}
                        />
                        <PopOver
                            popOverList={popOverList}
                            handlePopOver={handlePopOver}
                        >
                            <HeaderIconText icon={my} alt="my" text="My" />
                        </PopOver>

                        <HeaderIconText
                            icon={logoutIcon}
                            alt="logout"
                            text="Logout"
                            onClick={handleLogout}
                        />
                    </div>
                ) : (
                    <HeaderIconText
                        icon={login}
                        alt="login"
                        text="Login"
                        onClick={handleLogin}
                    />
                )}
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
