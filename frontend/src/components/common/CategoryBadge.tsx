const CategoryBadge = ({ categoryName }: { categoryName: string }) => (
    <div className="text-xs px-2 py-1 bg-gray-100 rounded-full text-gray-700">
        {categoryName}
    </div>
);

export default CategoryBadge;
