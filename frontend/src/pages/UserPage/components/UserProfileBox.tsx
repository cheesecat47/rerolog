import github from '@/assets/icons/ML_github-icon.png';
import linkedin from '@/assets/icons/ML_linkedin-icon.png';
import mail from '@/assets/icons/ML_mail-icon.png';
import web from '@/assets/icons/ML_web_icon.png';
import { useBlog } from '@/hooks/useBlog';
import { useUser } from "@/hooks/useUser";
import useUserStore from "@/stores/useUserStore";
import { Link } from 'react-router-dom';

const UserProfileBox = () => {
    const { userId } = useUserStore();
    const { getBlogInfo } = useBlog();
    const { getUserInfo } = useUser();

    const { isLoading, error, data: blogInfo } = getBlogInfo({ userId });
    const { data: userInfo } = getUserInfo({ userId });

    if (isLoading) return <div>로딩중입니다</div>;

    if (error) return <div>데이터를 받아오지 못했습니다</div>;

    console.log(userInfo, blogInfo);

    return (
        <div className="w-full h-full flex flex-col">
            {/* 프로필 이미지 */}
            {/* <div className="flex h-3/5">
                 <div className="w-40">
                    <img
                        className="w-full"
                        src={defaultProfile}
                        alt="profile"
                    />
                </div>
                
            </div> */}
            {/* 개인정보 */}
            <div className="h-2/5 pl-2">
                {/* 이름 */}
                <div className="flex pb-2">
                    <div><span className="text-3xl">{userInfo?.nickName}</span>
                    <span className="text-gray-600 pl-2">
                        {blogInfo?.data.userId}
                    </span></div>
                    <div className="flex flex-col flex-1">
                    {/* <div className="h-2/3">&nbsp;</div> */}
                    {/* 뱃지 */}
                    <div className="flex justify-end">
                        {
                            userInfo?.contacts.map((x, idx) => {
                                let type = '';
                                if (x.type === 'GitHub') {
                                    type = github;
                                }
                                else if (x.type === 'Email') {
                                    type = mail;
                                }
                                else if (x.type === 'LinkedIn') {
                                    type = linkedin;
                                }

                                else if (x.type === 'WebSite') {
                                    type = web;
                                }
                                
                                return <div className="w-10 mx-1" key={idx}>
                                    <a href={x.value} target="_blank">
                                        <img src={type} alt="type" />
                                    </a>
                            </div>
                            })
                        }
                    </div>
                </div>
                </div>
                <div className="flex justify-between">
                    <div className="pt-2 text-sm">{userInfo?.content}</div>
                    <Link
                        to="/manage"
                        className="px-3 py-2 bg-ml-pink-100 rounded-10 text-ml-pink-300"
                    >
                        프로필 편집
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default UserProfileBox;
