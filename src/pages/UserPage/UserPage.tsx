import { DropBox } from 'components/common';
import { tabBarMenus } from 'constants/tabBarMenus';
import { tabBarProps } from 'constants/tabBarProps';
import { useCategory } from 'hooks/useCategory';
import { useState } from 'react';
import { Outlet, useLocation, useNavigate } from 'react-router-dom';
import useUserStore from 'stores/useUserStore';
import { ITabBarProps } from 'types/common/TabBarProps';
import { Category, TabBar, UserProfileBox } from './components';

const UserPage = () => {
    // @TODO: 본인 아이디 받아오기
    const { userId } = useUserStore();
    const location = useLocation();

    const getCurrentTabUrl = (): string => {
        const path = location.pathname;

        let tab = '';
        for (let i = 0; i < tabBarMenus.length; i += 1) {
            const regex = new RegExp(tabBarMenus[i][0]);
            if (regex.test(path)) {
                [, tab] = tabBarMenus[i];
                break;
            }
        }

        return tab;
    };

    const [selecteTab, setSelectedTab] = useState<string>(getCurrentTabUrl());
    const navigate = useNavigate();

    const handleTabBar = (tab: ITabBarProps) => {
        setSelectedTab(tab.text);
        navigate(`/${userId}/${tab.link}`);
    };

    const { getCategoryList } = useCategory();

    const { data: categoryList } = getCategoryList({ userId });

    console.log(categoryList);

    const handleCategory = (category: string) => {
        // category가 선택됨
        navigate(`/${userId}/category/${category}`);
        setSelectedTab('포스트');
    };

    const viewOptionList = ['board', 'list'];

    const [selectedViewOption, setSelectedViewOption] =
        useState<string>('board');

    const handleViewOption = (option: string) => {
        setSelectedViewOption(option);
    };

    return (
        <div>
            <div className="absolute top-0 w-full h-48 bg-ml-pink-100 -z-10">
                &nbsp;
            </div>
            <div className="my-8 w-10/12 mx-auto">
                <div className="h-72 w-full">
                    <UserProfileBox />
                </div>
                <div className="h-10 border-b-[1px]">
                    <TabBar
                        selectedTab={selecteTab}
                        tabList={tabBarProps}
                        handleOption={handleTabBar}
                    />
                </div>
                <div className="grid grid-cols-5 p-4">
                    <div className="col-span-1">
                        <Category
                            categoryList={categoryList || []}
                            handleCategory={handleCategory}
                        />
                    </div>
                    <div className="col-span-4 min-h-96">
                        {selecteTab === '포스트' && (
                            <div>
                                <div className="h-10 float-right">
                                    <DropBox
                                        showText={selectedViewOption}
                                        menuList={viewOptionList}
                                        handleOption={handleViewOption}
                                    />
                                </div>
                            </div>
                        )}
                        <Outlet />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default UserPage;
