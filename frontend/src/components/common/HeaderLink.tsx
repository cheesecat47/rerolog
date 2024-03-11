const HeaderLink = ({ label, onClick }: {
    label: string;
    onClick?: () => void;
    }) => {
    return (
        <div
            className="h-10 flex justify-center items-center cursor-pointer text-gray-600 font-mediun text-sm p-2 mx-1 rounded-md hover:bg-gray-100"
            onClick={onClick}>
            {label}
        </div>
    )
};

export default HeaderLink;
