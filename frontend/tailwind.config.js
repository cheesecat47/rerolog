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
    plugins: [
        function ({ addBase, config }) {
            addBase({
                h1: {
                    fontSize: config('theme.fontSize.4xl'),
                    fontWeight: config('theme.fontWeight.bold'),
                },
                h2: {
                    fontSize: config('theme.fontSize.3xl'),
                    fontWeight: config('theme.fontWeight.semibold'),
                },
                h3: {
                    fontSize: config('theme.fontSize.2xl'),
                    fontWeight: config('theme.fontWeight.medium'),
                },
                h4: {
                    fontSize: config('theme.fontSize.xl'),
                    fontWeight: config('theme.fontWeight.normal'),
                },
                h5: {
                    fontSize: config('theme.fontSize.lg'),
                    fontWeight: config('theme.fontWeight.light'),
                },
                h6: {
                    fontSize: config('theme.fontSize.base'),
                    fontWeight: config('theme.fontWeight.thin'),
                },
                blockquote: {
                    paddingLeft: '1rem',
                    borderLeft: '4px solid #ddd',
                },
                ul: { listStyleType: 'disc', paddingLeft: '1rem' },
                ol: { listStyleType: 'decimal', paddingLeft: '1rem' },
            });
        },
    ],
};
