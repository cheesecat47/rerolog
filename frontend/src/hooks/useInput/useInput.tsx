import { useState, ChangeEvent } from 'react';

interface State {
    [key: string]: string;
}

const useInput = (initialValue: State) => {
    const [values, setValues] = useState(initialValue);

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setValues({ ...values, [name]: value });
    };

    return { values, handleChange };
};

export default useInput;