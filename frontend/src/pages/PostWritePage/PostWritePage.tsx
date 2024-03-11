import { useCategory } from "@/hooks/useCategory";
import { usePost } from "@/hooks/usePost";
import useUserStore from "@/stores/useUserStore";
import { writePostRequest } from "@/types/api/post";
import Parser from 'html-react-parser';
import { useState } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import { useNavigate } from 'react-router-dom';
import './PostWritePage.css';

const PostWritePage = () => {
    const { userId } = useUserStore();
    const { getCategoryList } = useCategory();
    const { data: categoryList } = getCategoryList({ userId });

    const { writePost } = usePost();
    const { mutate } = writePost();

    const myColors = [
        'purple',
        '#785412',
        '#452632',
        '#856325',
        '#963254',
        '#254563',
        'white',
    ];
    const modules = {
        toolbar: [
            [{ header: [1, 2, 3, 4, 5, 6, false] }],
            ['bold', 'italic', 'underline', 'strike', 'blockquote'],
            [{ align: ['right', 'center', 'justify'] }],
            [{ list: 'ordered' }, { list: 'bullet' }],
            ['link', 'image'],
            [{ color: myColors }],
            [{ background: myColors }],
        ],
    };

    const formats = [
        'header',
        'bold',
        'italic',
        'underline',
        'strike',
        'blockquote',
        'list',
        'bullet',
        'link',
        'color',
        'image',
        'background',
        'align',
    ];

    const [postData, setPostData] = useState<writePostRequest>({
        userId: userId,
        categoryName: '',
        title: '',
        excerpt: '',
        content: ''
      });

    const handleProcedureContentChange = (content: string) => {
        setPostData((prev) => {
            return {
                ...prev,
                content: content
            }
        })
        
    };

    const navigate = useNavigate();

    const goBack = () => {
        navigate(-1);
    };

    const handleWritePost = () => {
        mutate(postData);
        navigate('/');
    };

    return (
        <div className="grid grid-cols-2">
            {/* 왼쪽 - 에디터 */}
            <div className="h-screen flex flex-col">
                {/* 헤더 => 뒤로가기, 작성하기 버튼 포함 */}
                <div className="h-15 flex justify-between my-2 mx-1 text-sm">
                    <button
                        type="button"
                        className="text-gray-800 mx-3 my-2 rounded-md hover:bg-gray-100"
                        onClick={goBack}
                    >
                        뒤로가기
                    </button>
                    <button
                        type="button"
                        className="text-white bg-ml-pink-200 px-3 py-2 rounded-md hover:bg-ml-pink-300"
                        onClick={handleWritePost}
                    >
                        작성하기
                    </button>
                </div>
                {/* 제목 */}
                <div className="h-16">
                    <input
                        className="text-4xl m-2 w-full"
                        type="text"
                        placeholder="제목을 입력하세요"
                        value={postData.title}
                        onChange={(e) => setPostData((prev) => {
                            return {
                                ...prev,
                                title: e.target.value
                            }
                        })}
                    />
                </div>
                <div className="flex">
                    {
                        categoryList && categoryList.map((category, idx) => {
                            return <div onClick={(e) => setPostData((prev) => {
                                return {
                                    ...prev,
                                    categoryName: category.categoryName
                                }
                            })} className={`m-1 px-2 py-1 border border-gray-200 rounded-lg cursor-pointer ${category.categoryName === postData.categoryName && 'bg-gray-200'}`} key={idx}>{category.categoryName}</div>
                        })
                    }
                </div>
                {/* 에디터 */}
                <ReactQuill
                    theme="snow"
                    modules={modules}
                    formats={formats}
                    value={postData.content}
                    placeholder="내용을 입력하세요"
                    onChange={handleProcedureContentChange}
                />
            </div>
            {/* 오른쪽 = 미리보기 */}
            <div className="p-8 w-full text-ellipsis overflow-scroll">
                <div className="text-5xl pb-4">{postData.title}</div>
                <div>{Parser(postData.content)}</div>
            </div>
        </div>
    );
};

export default PostWritePage;
