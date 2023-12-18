// import React, { useEffect, useState } from 'react';

import flower from '../../../assets/icons/ML_flower-icon.png';
import login from '../../../assets/icons/ML_login_icon.png';
import logout from '../../../assets/icons/ML_logout-icon.png';
import moon from '../../../assets/icons/ML_moon-icon.png';
import sun from '../../../assets/icons/ML_sun-icon.png';
import my from '../../../assets/icons/ML_my-icon.png';
import pencil from '../../../assets/icons/ML_pencil-icon.png';

import { HeaderIconText } from "../../common/HeaderIconText";

const Header = ({ blogName }: { blogName: string }) => {
    // 추후 로그인 여부 확인
    // const [isLogin, setIsLogin] = useState(false);

    const checkIsLogin = () => {
        return true;
    }

    return (
        <header className="w-full h-16 flex bg-transparent font-PyeongChangPeace ">
            <div className="basis-[40%]" />
            <div className="basis-[20%] flex justify-center items-center">
                <img className="w-8" src={flower} alt="logo" />
                <span className="text-gray-700 font-semibold text-lg pl-1">{blogName}</span>
            </div>
            <div className="basis-[40%] flex justify-end">
                {
                    checkIsLogin() ? <div className="hidden sm:flex w-full justify-end">
                        <HeaderIconText icon={pencil} alt="pencil" text="Write" />
                        <HeaderIconText icon={my} alt="my" text="My" />
                        <HeaderIconText icon={logout} alt="logout" text="Logout" />
                    </div>
                        :
                        <HeaderIconText icon={login} alt="login" text="Login" />
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