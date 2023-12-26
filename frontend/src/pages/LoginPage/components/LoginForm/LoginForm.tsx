import React from 'react';
import useInput from "../../../../hooks/useInput/useInput";

const LoginForm = () => {
    const { values, handleChange } = useInput({
        'userId': '',
        'password': ''
    });

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        // 폼 제출 처리 로직
        console.log('userId: ', values.userId, ' password: ', values.password);
    }

    return (
        <div className="w-2/5 min-w-[450px] min-h-1/2 bg-white rounded-20 p-12 shadow-lg animate-slideInup">
            <div className="text-4xl font-medium font-PyeongChangPeace">Login</div>
            <p className="pt-4 pb-6 font-PyeongChangPeace text-gray-900">돌아오신 것을 환영합니다!</p>
            <form onSubmit={handleSubmit} className="flex flex-col">
                <label htmlFor="userId" className="flex flex-col mb-2">
                    <span className="text-gray-600">아이디</span>
                    <input id="userId" type="text" name='userId' onChange={handleChange} className="h-12 my-2 p-2 rounded-md border" />
                </label>
                <label htmlFor="userId" className="flex flex-col mb-2">
                    <span className="text-gray-600">비밀번호</span>
                    <input id="password" type="password" name="password" onChange={handleChange} className="h-12 my-2 p-2 rounded-md border" />
                </label>
                <p className="text-gray-600 text-sm">비밀번호를 잊으셨나요?</p>
                <div className="h-16 text-right">
                    <button type="submit" className="hover:bg-ml-pink-300 w-32 h-10 rounded-md bg-ml-pink-200 text-white font-semibold text-lg">로그인</button>
                </div>
            </form>
            <p className="text-center text-sm text-gray-600">아직 회원이 아니신가요? <span className="cursor-pointer text-ml-pink-300">회원가입 하러가기</span></p>
        </div>
    );
}

export default LoginForm;
