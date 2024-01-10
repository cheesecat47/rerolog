import { useState } from 'react';

const Introduce = () => {
    // @TODO: 사용자 한줄소개 받아오기
    const [userIntroduce, setUserIntroduce] =
        useState<string>('개발자 지망생 임니당..');

    // @TODO: 내 페이지인지의 여부
    const [isMy, setIsMy] = useState<boolean>(true);

    console.log(setUserIntroduce, setIsMy);

    // @TODO: 한줄 소개 작성
    const handleWriteIntroduce = () => {};

    // @TODO: 한줄 소개 수정
    const handleModifyIntroduce = () => {};

    return (
        <div className="w-full h-full">
            {userIntroduce?.length > 0 ? (
                <div>
                    {isMy && (
                        <button
                            type="button"
                            className="mt-1 px-3 py-2 float-right bg-ml-pink-100 rounded-10 text-ml-pink-300"
                            onClick={handleModifyIntroduce}
                        >
                            수정하기
                        </button>
                    )}
                    <div>{userIntroduce}</div>
                </div>
            ) : (
                <div className="flex flex-col justify-center items-center pt-20">
                    <div className="text-xl">작성된 소개글이 없습니다</div>
                    {isMy && (
                        <button
                            type="button"
                            className="mt-4 px-3 py-2 bg-ml-pink-100 rounded-10 text-ml-pink-300"
                            onClick={handleWriteIntroduce}
                        >
                            작성하기
                        </button>
                    )}
                </div>
            )}
        </div>
    );
};

export default Introduce;
