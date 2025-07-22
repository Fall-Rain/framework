import { request } from '@umijs/max';

export type R<T = any> = {
  code: number;
  success: boolean;
  result: T;
  message?: string;
};

export type GenTable = {
  id?: string;
  tableName?: string;
  tableComment?: string;
  className?: string;
  tplCategory?: string;
  packageName?: string;
  moduleName?: string;
  businessName?: string;
  functionName?: string;
  functionAuthor?: string;
  genType?: string;
  createTime?: string;
  updateTime?: string;
};

/** 查询生成表列表 GET /genTable/list */
export async function listGenTable(
  params: {
    genTable: GenTable;
    pageNo: number;
    pageSize: number;
  },
  options?: { [key: string]: any },
) {
  return request<R>('/api/genTable/list', {
    method: 'GET',
    params,
    ...(options || {}),
  });
}

// 创建业务表
export async function createGenTable(data: Record<string, any>, options?: { [key: string]: any }) {
  return request<R>('/api/genTable/createTable', {
    method: 'POST',
    data,
    ...(options || {}),
  });
}

// 新增表结构（用于元数据管理）
export async function addGenTable(data: Record<string, any>, options?: { [key: string]: any }) {
  return request<R>('/api/genTable/addTable', {
    method: 'POST',
    data,
    ...(options || {}),
  });
}

// 编辑表结构
export async function editGenTable(data: Record<string, any>, options?: { [key: string]: any }) {
  return request<R>('/api/genTable/editTable', {
    method: 'PUT',
    data,
    ...(options || {}),
  });
}

// 删除表结构
export async function deleteGenTable(id: string, options?: { [key: string]: any }) {
  return request<R>('/api/genTable/deleteTable', {
    method: 'DELETE',
    params: { id },
    ...(options || {}),
  });
}

// 获取表结构列表
export async function listGenTables(params: {
  arg0: Record<string, any>;
  pageNo: number;
  pageSize: number;
}, options?: { [key: string]: any }) {
  return request<R>('/api/genTable/list', {
    method: 'GET',
    params,
    ...(options || {}),
  });
}

