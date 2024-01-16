import { useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';

import { DropBox } from 'components/common';
import PostList from 'components/post/PostList';

const MainPage = () => {
    const navigate = useNavigate();

    const [searchParams] = useSearchParams();
    const sort = searchParams.get('sort');

    const [selectedOption, setSelectedOption] = useState<string>(
        sort || 'recent'
    );

    const handleOption = (option: string) => {
        setSelectedOption(option);
        navigate(`/post/${option}`);
    };

    return (
        <>
            <div className=" mt-20 flex items-center">
                <div className="flex-1">&nbsp;</div>
                <div className="w-[250px] text-center text-4xl font-semibold">
                    {selectedOption === 'recent'
                        ? 'Recent Posts'
                        : 'Trend Posts'}
                </div>
                <div className="flex-1">&nbsp;</div>
                {/* <div className="flex-1 cursor-pointer">&nbsp;&nbsp;See all posts</div> */}
            </div>
            <p className="text-center mt-2">
                {selectedOption === 'recent'
                    ? '최근 업로드한'
                    : '최근 인기있는'}{' '}
                포스트입니다
            </p>
            <div className="w-4/5 m-auto">
                <div className="h-10 float-right">
                    <DropBox
                        showText={selectedOption}
                        menuList={['recent', 'trend']}
                        handleOption={handleOption}
                    />
                </div>
                <br />
                {/* 게시물 리스트 */}
                <div className="w-full">
                    <PostList selectedOption={selectedOption} />
                </div>
            </div>
        </>
    );
};

export default MainPage;
