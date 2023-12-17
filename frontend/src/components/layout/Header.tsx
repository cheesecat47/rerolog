// import React, { useEffect, useState } from 'react';

import flower from '../../assets/icons/ML_flower-icon.png';
// import book from '../../assets/icons/ML_book-icon.png';
import login from '../../assets/icons/ML_login_icon.png';
import logout from '../../assets/icons/ML_logout-icon.png';
import moon from '../../assets/icons/ML_moon-icon.png';
import sun from '../../assets/icons/ML_sun-icon.png';
import my from '../../assets/icons/ML_my-icon.png';
import pencil from '../../assets/icons/ML_pencil-icon.png';

type blogNameType = {
    blogName: string;
}

const Header = ({ blogName }: blogNameType) => {
    // 추후 로그인 여부 확인
    // const [isLogin, setIsLogin] = useState(false);

    const checkIsLogin = () => {
        return true;
    }

    return (
        <header className="w-full h-16 grid grid-cols-11 place-items-center shadow-md font-PyeongChangPeace">
            <div className="w-full col-span-5 bg-white" />
            <div className="w-full col-span-1 flex justify-center items-center">
                <img className="w-8" src={flower} alt="logo" />
                <span className="text-gray-700 font-semibold text-lg pl-1">{blogName}</span>
            </div>
            <div className="w-full col-span-5 flex justify-end">
                {
                    checkIsLogin() ? <div className="invisible md:visible w-full flex justify-end">
                        <div className="flex justify-center items-center w-16">
                            <img className="w-6" src={pencil} alt="pencil" />
                            <span className="text-gray-600 font-mediun text-xs pl-1">Write</span>
                        </div>
                        <div className="flex justify-center items-center w-16">
                            <img className="w-6" src={my} alt="my" />
                            <span className="text-gray-600 font-mediun text-xs pl-1">My</span>
                        </div>
                        <div className="flex justify-center items-center w-16">
                            <img className="w-4" src={logout} alt="logout" />
                            <span className="text-gray-600 font-mediun text-xs pl-1">Logout</span>
                        </div>
                    </div>
                        :
                        <div className="col-span-1 flex justify-center items-center">
                            <img className="w-8" src={login} alt="login" />
                            <span className="text-gray-600 font-mediun text-xs">Logout</span>
                        </div>
                }
                <div className="flex justify-center items-center mx-4">
                    <img className="w-6" src={sun} alt="sun" />
                    <div className="w-24 rounded-full mx-2 bg-gray-100">
                        &nbsp;
                    </div>
                    <img className="w-6" src={moon} alt="moon" />
                </div>
            </div>
        </header>
    );
};

export default Header;