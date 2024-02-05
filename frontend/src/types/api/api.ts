export interface ApiResponse<T> {
    message: string;
    code: string;
    data: T;
}
