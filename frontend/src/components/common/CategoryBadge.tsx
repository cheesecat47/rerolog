const CategoryBadge = ({ categoryName }: { categoryName: string }) => (
    <div className="text-xs mr-4 px-2 bg-ml-pink-100 rounded-full text-gray-700">
        {categoryName}
    </div>
);

export default CategoryBadge;
