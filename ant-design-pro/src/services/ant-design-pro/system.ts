
import { request } from '@umijs/max';


export type UserInfoEntity = {
  id?: string;
  username?: string;
  password?: string;
  phone?: string;
  createTime?: string;
  updateTime?: string;
};

export type R<T = any> = {
  code: number;
  success: boolean;
  result: T;
  message?: string;
};


export async function login(data: UserInfoEntity, options?: { [key: string]: any }) {
  return request<R>('/api/system/login', {
    method: 'POST',
    data,
    ...(options || {}),
  });
}

/** 获取当前登录用户信息 */
export async function getCurrentUserInfo() {
  return request<R>(`/api/system/getUserInfo`, {
    method: 'GET',
  });
}

export async function upload(file:File){
  const formData = new FormData();
  formData.append('file', file);
  return request<R>('/api/system/upload', {
    method: 'POST',
    data: formData
  })
}
