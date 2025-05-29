// @ts-ignore
/* eslint-disable */
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

/** 保存用户信息 POST /userInfo/save */
export async function saveUserInfo(data: UserInfoEntity, options?: { [key: string]: any }) {
  return request<R>('/api/userInfo/save', {
    method: 'POST',
    data,
    ...(options || {}),
  });
}

/** 更新用户信息 PUT /userInfo/update */
export async function updateUserInfo(data: UserInfoEntity, options?: { [key: string]: any }) {
  return request<R>('/api/userInfo/update', {
    method: 'PUT',
    data,
    ...(options || {}),
  });
}

/** 获取用户列表 GET /userInfo/list */
export async function listUserInfo(
  params: {
    arg0: UserInfoEntity;
    pageNo: number;
    pageSize: number;
  },
  options?: { [key: string]: any },
) {
  return request<R>('/api/userInfo/list', {
    method: 'GET',
    params,
    ...(options || {}),
  });
}

/** 根据ID获取用户信息 GET /userInfo/getById */
export async function getUserInfoById(arg0: number, options?: { [key: string]: any }) {
  return request<R>('/userInfo/getById', {
    method: 'GET',
    params: { arg0 },
    ...(options || {}),
  });
}

/** 删除用户 DELETE /userInfo/delete */
export async function deleteUserInfo(id: string, options?: { [key: string]: any }) {
  return request<R>('/api/userInfo/delete', {
    method: 'DELETE',
    params: { id },
    ...(options || {}),
  });
}
