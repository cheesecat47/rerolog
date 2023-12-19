const HeaderIconText = ({ icon, alt, text }: { icon: string, alt: string, text: string }) => (
    <div className="flex justify-center items-center mr-4 cursor-pointer">
        <img className="w-6" src={icon} alt={alt} />
        <span className="text-gray-600 font-mediun text-xs pl-1">{text}</span>
    </div>
);

export default HeaderIconText;