import React from 'react';
import { Link } from 'react-router-dom';

import github from '../../../../assets/icons/ML_github-icon.png';
import linkedin from '../../../../assets/icons/ML_linkedin-icon.png';
import mail from '../../../../assets/icons/ML_mail-icon.png';
import defaultProfile from '../../../../assets/images/ML_test-profile.png';

const UserProfileBox = () => {

    return (
        <div className="w-full h-full flex flex-col">
            {/* 프로필 이미지 */}
            <div className="flex h-3/5">
                <div className="w-40">
                    <img className='w-full' src={defaultProfile} alt="profile" />
                </div>
                <div className="flex flex-col flex-1">
                    <div className="h-2/3">
                        &nbsp;
                    </div>
                    {/* 뱃지 */}
                    <div className="flex justify-end h-1/3">
                        <div className='w-10 mx-1'><img src={github} alt="github" /></div>
                        <div className='w-10 mx-1'><img src={linkedin} alt="linkedin" /></div>
                        <div className='w-10 mx-1'><img src={mail} alt="mail" /></div>
                    </div>
                </div>
            </div>
            {/* 개인정보 */}
            <div className="h-2/5 pl-2">
                {/* 이름 */}
                <div>
                    <span className="text-3xl">춘장이</span>
                    <span className="text-gray-600 pl-2">@rosielsh</span>
                </div>
                <div className="flex justify-between">
                    <div className="pt-2 text-sm">
                        개발을 잘하고 싶으요
                    </div>
                    <Link to='/manage' className="px-3 py-2 bg-ml-pink-100 rounded-10 text-ml-pink-300">프로필 편집</Link>
                </div>
            </div>
        </div>
    );
}

export default UserProfileBox;
