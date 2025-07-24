// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

export type RoleEntity = {
  id?: string;
  name?: string;
  code?: string;
  description?: string;
  createTime?: string;
  updateTime?: string;
};

export type R<T = any> = {
  code: number;
  success: boolean;
  result: T;
  message?: string;
};

/** 保存角色信息 POST /role/save */
export async function saveSysRole(data: RoleEntity, options?: { [key: string]: any }) {
  return request<R>('/api/sysRole/save', {
    method: 'POST',
    data,
    ...(options || {}),
  });
}

/** 更新角色信息 PUT /role/update */
export async function updateSysRole(data: RoleEntity, options?: { [key: string]: any }) {
  return request<R>('/api/sysRole/update', {
    method: 'PUT',
    data,
    ...(options || {}),
  });
}

/** 获取角色列表 GET /role/list */
export async function listSysRole(
  params: {
    arg0: RoleEntity;
    pageNo: number;
    pageSize: number;
  },
  options?: { [key: string]: any },
) {
  return request<R>('/api/sysRole/list', {
    method: 'GET',
    params,
    ...(options || {}),
  });
}

export async function getSysRoleList() {
  return request<R>('/api/sysRole/getRoleList', {
    method: 'GET',
  });
}

/** 根据ID获取角色信息 GET /role/getById */
export async function getSysRoleById(id: string, options?: { [key: string]: any }) {
  return request<R>('/api/sysRole/getById', {
    method: 'GET',
    params: { id },
    ...(options || {}),
  });
}

/** 删除角色 DELETE /role/delete */
export async function deleteSysRole(id: string, options?: { [key: string]: any }) {
  return request<R>('/api/sysRole/delete', {
    method: 'DELETE',
    params: { id },
    ...(options || {}),
  });
}
