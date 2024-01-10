import { Popover, Transition } from '@headlessui/react';
import { Fragment, ReactElement } from 'react';
import { PopOverType } from '../layout/Header';

const PopOver = ({
    popOverList,
    handlePopOver,
    children,
}: {
    popOverList: PopOverType[];
    handlePopOver: (pop: PopOverType) => void;
    children: ReactElement;
}) => (
    <Popover className="relative">
        <Popover.Button className="inline-flex items-center h-full">
            {children}
        </Popover.Button>

        <Transition
            as={Fragment}
            enter="transition ease-out duration-200"
            enterFrom="opacity-0 translate-y-1"
            enterTo="opacity-100 translate-y-0"
            leave="transition ease-in duration-150"
            leaveFrom="opacity-100 translate-y-0"
            leaveTo="opacity-0 translate-y-1"
        >
            <Popover.Panel className="absolute left-1/2 z-10 mt-1 flex w-screen max-w-max -translate-x-1/2 px-4">
                <div className="w-auto flex-auto overflow-hidden rounded-lg bg-white text-sm leading-6 shadow-lg ring-1 ring-gray-900/5">
                    {popOverList.map((item) => (
                        <button
                            type="button"
                            key={item.text}
                            className="w-full group relative flex gap-x-6 rounded-sm px-8 py-2 hover:bg-gray-50"
                            onClick={() => handlePopOver(item)}
                        >
                            {item.text}
                        </button>
                    ))}
                </div>
            </Popover.Panel>
        </Transition>
    </Popover>
);

export default PopOver;
