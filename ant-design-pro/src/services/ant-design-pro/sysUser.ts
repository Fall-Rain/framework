// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

export type SysUseEntity = {
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

/** 保存用户信息 POST /sysUser/save */
export async function saveSysUser(data: SysUseEntity, options?: { [key: string]: any }) {
  return request<R>('/api/sysUser/save', {
    method: 'POST',
    data,
    ...(options || {}),
  });
}

/** 更新用户信息 PUT /sysUser/update */
export async function updateSysUser(data: SysUseEntity, options?: { [key: string]: any }) {
  return request<R>('/api/sysUser/update', {
    method: 'PUT',
    data,
    ...(options || {}),
  });
}

/** 获取用户列表 GET /sysUser/list */
export async function listSysUser(
  params: {
    arg0: SysUseEntity;
    pageNo: number;
    pageSize: number;
  },
  options?: { [key: string]: any },
) {
  return request<R>('/api/sysUser/list', {
    method: 'GET',
    params,
    ...(options || {}),
  });
}

/** 根据ID获取用户信息 GET /sysUser/getById */
export async function getSysUserById(arg0: number, options?: { [key: string]: any }) {
  return request<R>('/sysUser/getById', {
    method: 'GET',
    params: { arg0 },
    ...(options || {}),
  });
}

/** 删除用户 DELETE /sysUser/delete */
export async function deleteSysUser(id: string, options?: { [key: string]: any }) {
  return request<R>('/api/sysUser/delete', {
    method: 'DELETE',
    params: { id },
    ...(options || {}),
  });
}

export async function generatePassword(data: SysUseEntity, options?: { [key: string]: any }) {
  return request<R>('/api/sysUser/generatePassword', {
    method: 'PUT',
    data,
    ...(options || {}),
  });
}
