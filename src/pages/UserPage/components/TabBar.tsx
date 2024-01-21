import { ITabBarProps, TabBarPropsList } from 'types/common/TabBarProps';

const TabBar = ({
    selectedTab,
    tabList,
    handleOption,
}: {
    selectedTab: string;
    tabList: TabBarPropsList;
    handleOption: (tab: ITabBarProps) => void;
}) => (
    <div className="flex flex-cols w-full h-full items-center">
        {tabList &&
            tabList.map((tab) => (
                <button
                    type="button"
                    className={
                        selectedTab === tab.text
                            ? 'h-full px-3 border-b-[1px] border-gray-700'
                            : 'px-3 h-full'
                    }
                    key={tab.text}
                    onClick={() => handleOption(tab)}
                >
                    {tab.text}
                </button>
            ))}
    </div>
);

export default TabBar;
