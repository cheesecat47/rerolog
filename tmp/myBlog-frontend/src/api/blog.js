import { localAxios } from '../util/http-commons';

const local = localAxios();

export const getCategories = async (id, success, fail) => {
  local.get(`/blog/${id}/category`).then(success).catch(fail);
};
