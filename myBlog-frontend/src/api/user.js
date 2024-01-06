import { localAxios } from '../util/http-commons';

const local = localAxios();

export const getUserInfoById = async (id, success, fail) => {
    local.get(`/user/${id}`).then(success).catch(fail);
  };