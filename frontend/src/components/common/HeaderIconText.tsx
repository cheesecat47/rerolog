const HeaderIconText = ({
    icon,
    alt,
    text,
    onClick,
}: {
    icon?: string;
    alt: string;
    text: string;
    onClick?: React.MouseEventHandler<HTMLButtonElement>;
}) => (
    <button
        className="flex justify-center items-center mr-4 cursor-pointer"
        type="button"
        onClick={onClick}
    >
        { icon && <img className="w-6" src={icon} alt={alt} />}
        <span className="text-gray-600 font-mediun text-sm pl-1">{text}</span>
    </button>
);

export default HeaderIconText;
