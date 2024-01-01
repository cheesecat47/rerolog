import React, { useState } from 'react';
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";
import { useNavigate } from "react-router-dom";
import './PostWritePage.css';

const PostWritePage = () => {
    const myColors = [
        "purple",
        "#785412",
        "#452632",
        "#856325",
        "#963254",
        "#254563",
        "white"
    ];
    const modules = {
        toolbar: [
            [{ header: [1, 2, 3, 4, 5, 6, false] }],
            ["bold", "italic", "underline", "strike", "blockquote"],
            [{ align: ["right", "center", "justify"] }],
            [{ list: "ordered" }, { list: "bullet" }],
            ["link", "image"],
            [{ color: myColors }],
            [{ background: myColors }]
        ]
    };

    const formats = [
        "header",
        "bold",
        "italic",
        "underline",
        "strike",
        "blockquote",
        "list",
        "bullet",
        "link",
        "color",
        "image",
        "background",
        "align"
    ];

    const [title, setTitle] = useState<string>('');
    const [editorText, setEditorText] = useState<string>("");

    const handleProcedureContentChange = (content: string) => {
        setEditorText(content);
    };

    // console.log(editorText, title);

    const navigate = useNavigate();

    const goBack = () => {
        navigate(-1);
    }

    // @TODO: post 작성 메서드 작성
    const writePost = () => {

    }

    return (
        <div className="grid grid-cols-2">
            {/* 왼쪽 - 에디터 */}
            <div className="h-screen flex flex-col">
                {/* 헤더 => 뒤로가기, 작성하기 버튼 포함 */}
                <div className="flex justify-between my-2 mx-1 text-sm">
                    <button type="button" className="text-gray-800 mx-3 my-2 rounded-md hover:bg-gray-100" onClick={goBack}>
                        뒤로가기
                    </button>
                    <button type="button" className="text-white bg-ml-pink-200 px-3 py-2 rounded-md hover:bg-ml-pink-300" onClick={writePost}>
                        작성하기
                    </button>
                </div>
                {/* 제목 */}
                <div className="h-16">
                    <input className='text-4xl m-2 w-full' type="text" placeholder="제목을 입력하세요" value={title} onChange={(e) => setTitle(e.target.value)} />
                </div>
                {/* 에디터 */}
                <ReactQuill
                    theme="snow"
                    modules={modules}
                    formats={formats}
                    value={editorText}
                    placeholder="내용을 입력하세요"
                    onChange={handleProcedureContentChange}
                />
            </div>
            {/* 오른쪽 = 미리보기 */}
            <div className="p-8 w-full text-ellipsis">
                <h1>{title}</h1>
                <div dangerouslySetInnerHTML={{ __html: editorText }} />
            </div>
        </div>
    );
}

export default PostWritePage;
