import { useRef, useState } from 'react';
import {
  type ActionType,
  ModalForm,
  ProColumns,
  ProForm,
  ProFormText,
  ProTable,
} from '@ant-design/pro-components';
import { FormLayout } from 'antd/es/form/Form';
import { message, Popconfirm, Tabs } from 'antd';
import { listGenTableColumn } from '@/services/ant-design-pro/genTableColumn';
import { deleteGenTable, listGenTable } from '@/services/ant-design-pro/genTable';

const GenTableForm: React.FC<{
  visible: boolean;
  onVisibleChange: (v: boolean) => void;
  onSubmit: (values: any) => Promise<void>;
  initialValues?: any;
  title: any;
  readOnly: boolean;
}> = ({ visible, onVisibleChange, onSubmit, initialValues, title, readOnly }) => {
  const [activeKey, setActiveKey] = useState('base');
  const actionRef = useRef<ActionType>();
  const columns: ProColumns[] = [
    {
      title: '列名',
      dataIndex: 'columnName',
      formItemProps: { rules: [{ required: true, message: '请输入列名' }] },
    },
    {
      title: '列描述',
      dataIndex: 'columnComment',
    },
    {
      title: '列类型',
      dataIndex: 'columnType',
    },
    {
      title: 'Java类型',
      dataIndex: 'javaType',
    },
    {
      title: 'Java字段名',
      dataIndex: 'javaField',
    },
    // {
    //   title: '主键',
    //   dataIndex: 'isPk',
    //   valueType: 'switch',
    // },
    // {
    //   title: '自增',
    //   dataIndex: 'isIncrement',
    //   valueType: 'switch',
    // },
    {
      title: '必填',
      dataIndex: 'isRequired',
      valueType: 'switch',
    },
    // {
    //   title: '插入字段',
    //   dataIndex: 'isInsert',
    //   valueType: 'switch',
    // },
    // {
    //   title: '编辑字段',
    //   dataIndex: 'isEdit',
    //   valueType: 'switch',
    // },
    // {
    //   title: '列表字段',
    //   dataIndex: 'isList',
    //   valueType: 'switch',
    // },
    // {
    //   title: '查询字段',
    //   dataIndex: 'isQuery',
    //   valueType: 'switch',
    // },
    // {
    //   title: '查询方式',
    //   dataIndex: 'queryType',
    //   valueType: 'select',
    //   valueEnum: {
    //     EQ: { text: '等于' },
    //     NE: { text: '不等于' },
    //     LIKE: { text: '模糊' },
    //     GT: { text: '大于' },
    //     LT: { text: '小于' },
    //   },
    // },
    {
      title: '控件类型',
      dataIndex: 'htmlType',
      valueType: 'select',
      valueEnum: {
        input: { text: '文本框' },
        textarea: { text: '文本域' },
        select: { text: '下拉框' },
        radio: { text: '单选框' },
        checkbox: { text: '复选框' },
        date: { text: '日期选择器' },
      },
    },
    {
      title: '排序',
      dataIndex: 'sort',
      valueType: 'digit',
    },
    {
      title: '操作',
      valueType: 'option',
      render: (_, record) => [
        <a key={'edit'} onClick={() => {}}>
          编辑
        </a>,
        <Popconfirm
          key="delete"
          title="确认删除该记录？"
          description="删除后数据将无法恢复，是否继续？"
          okText="确认"
          cancelText="取消"
          onConfirm={() => {
          }}
        >
          <a>删除</a>
        </Popconfirm>,
      ],
    },
  ];

  return (
    <ModalForm
      layout={'horizontal'}
      title={title}
      open={visible}
      grid
      modalProps={{
        destroyOnClose: true,
        style: { maxWidth: 1500, minWidth: 1200 },
      }}
      onOpenChange={onVisibleChange}
      onFinish={onSubmit}
      initialValues={initialValues}
      submitter={{
        render: (_, dom) => (readOnly ? null : dom), // 只读时隐藏提交按钮
      }}
    >
      <Tabs
        defaultActiveKey={'1'}
        activeKey={activeKey}
        onChange={setActiveKey}
        items={[
          { key: 'base', label: '基本信息' },
          { key: 'field', label: '字段信息', disabled: !initialValues?.id },
        ]}
      />
      {activeKey === 'base' && (
        <>
          <ProFormText disabled={readOnly} name="id" hidden />

          <ProForm.Group>
            <ProFormText
              disabled={readOnly}
              label="表名称"
              name="tableName"
              colProps={{ xs: 24, sm: 12, md: 8 }}
            />
            <ProFormText
              disabled={readOnly}
              label="表描述"
              name="tableComment"
              colProps={{ xs: 24, sm: 12, md: 8 }}
            />
            <ProFormText
              disabled={readOnly}
              label="类名"
              name="className"
              colProps={{ xs: 24, sm: 12, md: 8 }}
            />
          </ProForm.Group>

          <ProForm.Group>
            <ProFormText
              disabled={readOnly}
              label="作者"
              name="functionAuthor"
              colProps={{ xs: 24, sm: 12, md: 8 }}
            />
            <ProFormText
              disabled={readOnly}
              label="业务名称"
              name="businessName"
              colProps={{ xs: 24, sm: 12, md: 8 }}
            />
            <ProFormText
              disabled={readOnly}
              label="功能名称"
              name="functionName"
              colProps={{ xs: 24, sm: 12, md: 8 }}
            />
          </ProForm.Group>

          <ProForm.Group>
            <ProFormText
              disabled={readOnly}
              label="模板分类"
              name="tplCategory"
              colProps={{ xs: 24, sm: 12, md: 8 }}
            />
            <ProFormText
              disabled={readOnly}
              label="生成类型"
              name="genType"
              colProps={{ xs: 24, sm: 12, md: 8 }}
            />
            <ProFormText
              disabled={readOnly}
              label="包名"
              name="packageName"
              colProps={{ xs: 24, sm: 12, md: 8 }}
            />
          </ProForm.Group>

          <ProForm.Group>
            <ProFormText
              disabled={readOnly}
              label="模块名"
              name="moduleName"
              colProps={{ xs: 24, sm: 12, md: 8 }}
            />
            <ProFormText
              disabled={readOnly}
              label="创建时间"
              name="createTime"
              colProps={{ xs: 24, sm: 12, md: 8 }}
            />
            <ProFormText
              disabled={readOnly}
              label="更新时间"
              name="updateTime"
              colProps={{ xs: 24, sm: 12, md: 8 }}
            />
          </ProForm.Group>
        </>
      )}
      {activeKey === 'field' && (
        <>
          <ProTable
            headerTitle={'字段信息'}
            scroll={{ x: 'max-content', y: 400 }} // 水平方向允许滚动，纵向固定高度
            columns={columns}
            actionRef={actionRef}
            request={async (params) => {
              return listGenTableColumn({
                genTableColumn: {
                  tableId: initialValues.id,
                },
                pageNo: params.current || 1,
                pageSize: params.pageSize || 10,
              }).then((res) => {
                console.log(res);
                return {
                  data: res.result.records,
                  total: res.result.total,
                  success: true,
                };
              });
            }}
          ></ProTable>
        </>
      )}
    </ModalForm>
  );
};
export default GenTableForm;
