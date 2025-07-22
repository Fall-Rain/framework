import { request } from '@umijs/max';
import genTableForm from '@/pages/tool/gen/compoents/GenTableForm';

export type R<T = any> = {
  code: number;
  success: boolean;
  result: T;
  message?: string;
};

export type GenTableColumn = {
  id?: string;
  tableId?: string;
  columnName?: string;
  columnComment?: string;
  columnType?: string;
  javaType?: string;
  javaField?: string;
  isPk?: boolean;
  isIncrement?: boolean;
  isRequired?: boolean;
  isInsert?: boolean;
  isEdit?: boolean;
  isList?: boolean;
  isQuery?: boolean;
  queryType?: string;
  htmlType?: string;
  sort?: number;
  createTime?: string;
  updateTime?: string;
};

/** 查询字段列表 GET /genTableColumn/list */
export async function listGenTableColumn(
  params: {
    genTableColumn: GenTableColumn;
    pageNo: number;
    pageSize: number;
  },
  options?: { [key: string]: any },
) {
  const { genTableColumn, pageNo, pageSize } = params;
  return request<R>('/api/genTableColumn/list', {
    method: 'GET',
    params: {
      ...genTableColumn,
      pageNo,
      pageSize,
    },
    ...(options || {}),
  });
}
