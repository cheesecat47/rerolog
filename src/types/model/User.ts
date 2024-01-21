import { IContact } from './Contact';

export interface IUser {
    userId: string;
    nickName: string;
    content: string;
    createdAt: string;
    profileImage: string | null;
    contacts: IContact[];
}
