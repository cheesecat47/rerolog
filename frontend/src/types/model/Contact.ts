export type ContactValueType = 'Email' | 'GitHub' | 'LinkedIn' | 'WebSite';

export interface IContact {
    type: ContactValueType;
    value: string;
}
