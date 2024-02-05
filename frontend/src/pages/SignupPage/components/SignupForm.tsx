import kakao from 'assets/icons/ML_kakao-icon.png';
import naver from 'assets/icons/ML_naver-icon.png';
import useInput from 'hooks/useInput';
import React from 'react';
import { Link } from 'react-router-dom';

const SignupForm = () => {
    const { values, handleChange } = useInput({
        userName: '',
        userId: '',
        password: '',
    });

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        // 폼 제출 처리 로직
        console.log('userId: ', values.userId, ' password: ', values.password);
    };

    return (
        <div className="w-2/5 min-w-[450px] min-h-1/2 bg-white rounded-20 p-12 shadow-lg animate-slideInup">
            <div className="text-4xl font-medium font-PyeongChangPeace">
                Signup
            </div>
            <p className="pt-4 pb-6 text-gray-900">
                회원가입 후 서비스를 이용해보세요!
            </p>
            <form onSubmit={handleSubmit} className="flex flex-col">
                <label htmlFor="userId" className="flex flex-col mb-2">
                    <span className="text-gray-600">이름</span>
                    <input
                        id="userName"
                        type="text"
                        name="userName"
                        onChange={handleChange}
                        className="h-12 my-2 p-2 rounded-md border"
                    />
                </label>
                <label htmlFor="userId" className="flex flex-col mb-2">
                    <span className="text-gray-600">아이디</span>
                    <input
                        id="userId"
                        type="text"
                        name="userId"
                        onChange={handleChange}
                        className="h-12 my-2 p-2 rounded-md border"
                    />
                </label>
                <label htmlFor="userId" className="flex flex-col mb-2">
                    <span className="text-gray-600">비밀번호</span>
                    <input
                        id="password"
                        type="password"
                        name="password"
                        onChange={handleChange}
                        className="h-12 my-2 p-2 rounded-md border"
                    />
                </label>
                <div className="h-16 text-right">
                    <button
                        type="submit"
                        className="hover:bg-ml-pink-300 w-32 h-10 rounded-md bg-ml-pink-200 text-white font-semibold text-lg"
                    >
                        회원가입
                    </button>
                </div>
            </form>
            <p className="text-center text-sm text-gray-600">
                이미 가입하셨나요?{' '}
                <Link to="/login" className="cursor-pointer text-ml-pink-300">
                    로그인 하러가기
                </Link>
            </p>
            <div className="pt-6 flex justify-center items-center">
                <span className="text-sm">또는 SNS로 가입하기</span>
                <div className="flex">
                    <button type="button" className="p-2 w-14">
                        <img src={kakao} alt="kakao" />
                    </button>
                    <button type="button" className="p-2 w-14">
                        <img src={naver} alt="naver" />
                    </button>
                </div>
            </div>
        </div>
    );
};

export default SignupForm;
