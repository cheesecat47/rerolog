import { DropBox } from '@/components/common';
import { tabBarMenus } from '@/constants/tabBarMenus';
import { tabBarProps } from '@/constants/tabBarProps';
import { useCategory } from '@/hooks/useCategory';
import useUserStore from '@/stores/useUserStore';
import { ITabBarProps } from '@/types/common/TabBarProps';
import { useState } from 'react';
import { Outlet, useLocation, useNavigate } from 'react-router-dom';
import { Category, TabBar, UserProfileBox } from './components';

const UserPage = () => {
    const { userId } = useUserStore();
    const location = useLocation();
    const navigate = useNavigate();
    
    const { getCategoryList } = useCategory();
    const { data: categoryList } = getCategoryList({ userId });

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
    const [selectedCategory, setSelectedCategory] = useState<string>('');

    const handleTabBar = (tab: ITabBarProps) => {
        setSelectedTab(tab.text);
        navigate(`/${userId}/${tab.link}`);
    };

    console.log(categoryList);

    const handleCategory = (category: string) => {
        navigate(`/${userId}/category/${category}`);
        setSelectedCategory(category);
        setSelectedTab('포스트');
    };

    const viewOptionList = ['board', 'list'];

    const [selectedViewOption, setSelectedViewOption] = useState<string>('board');

    const handleViewOption = (option: string) => {
        setSelectedViewOption(option);
    };

    return (
        <div>
            <div className="w-full h-48 bg-ml-pink-100">
                &nbsp;
            </div>
            <div className="my-10 w-10/12 mx-auto">
                <div className="w-full p-2">
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
                            selectedCategory={selectedCategory}
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
