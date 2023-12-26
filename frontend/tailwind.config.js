/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
        './src/**/*.{js,jsx,ts,tsx}',
        // src 하위 파일 중 확장자가 .js,.jsx,.ts,.tsx인 파일을 대상으로 한다는 의미
    ],
    theme: {
        extend: {
            colors: {
                lightGray: '#FAFAFA',
                'ml-pink': {
                    100: '#FFF4F4',
                    200: '#FCD4D4',
                    300: '#FFBDBD',
                    400: '#FF999A',
                },
            },
            borderRadius: {
                10: '10px',
                20: '20px',
            },
        },
        fontFamily: {
            PyeongChangPeace: ['PyeongChangPeace'],
        },
    },
    plugins: [],
};
