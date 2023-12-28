import { Menu, Transition } from '@headlessui/react';
import { Fragment } from 'react';

const DropBox = ({ showText, menuList, handleOption }: { showText: string, menuList: string[], handleOption: (menu: string) => void }) => {
    return (
        <div className="w-56 text-right">
            <Menu as="div" className="relative inline-block text-left">
                <div>
                    <Menu.Button className="inline-flex w-full justify-center rounded-full border border-ml-pink-200 text-ml-pink-200 px-4 py-2 text-sm font-medium hover:bg-ml-pink-200 hover:text-white focus:outline-none focus-visible:ring-2 focus-visible:ring-white/75">
                        {showText}
                    </Menu.Button>
                </div>
                <Transition
                    as={Fragment}
                    enter="transition ease-out duration-100"
                    enterFrom="transform opacity-0 scale-95"
                    enterTo="transform opacity-100 scale-100"
                    leave="transition ease-in duration-75"
                    leaveFrom="transform opacity-100 scale-100"
                    leaveTo="transform opacity-0 scale-95"
                >
                    <Menu.Items className="absolute right-0 mt-2 w-28 origin-top-right divide-y divide-gray-100 rounded-md bg-white shadow-lg ring-1 ring-black/5 focus:outline-none">
                        {
                            menuList.map((menu) => {
                                return <div className="px-1 py-1" key={menu}>
                                    <Menu.Item>
                                        {({ active }) => (
                                            <button
                                                type='button'
                                                className={`${active ? 'bg-ml-pink-200 text-white' : 'text-gray-900'
                                                    } group flex w-full items-center rounded-md px-2 py-2 text-sm`}
                                                onClick={() => { handleOption(menu) }}
                                            >

                                                {menu}
                                            </button>
                                        )}
                                    </Menu.Item>
                                </div>
                            })
                        }

                    </Menu.Items>
                </Transition>
            </Menu>
        </div>
    )
}

export default DropBox;