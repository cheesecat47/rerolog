import axios from 'axios';

function MainPage() {
    const handleClick = async () => {
        try {
            const response = await axios.get('http://localhost:8080/user/1');
            console.log(response.data);
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <div>
            <button type="button" onClick={handleClick}>
                유저 페이지로
            </button>
        </div>
    );
}

export default MainPage;
