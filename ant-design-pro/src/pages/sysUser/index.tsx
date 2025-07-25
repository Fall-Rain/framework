import { type ActionType, PageContainer, ProColumns, ProTable } from '@ant-design/pro-components';

import {
  deleteSysUser,
  generatePassword,
  listSysUser,
  saveSysUser,
  updateSysUser,
} from '@/services/ant-design-pro/sysUser';
import { Button, message, Popconfirm } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import SysUserForm from '@/pages/sysUser/components/SysUserForm';
import { useRef, useState } from 'react';
import { useModel } from '@umijs/max';

const UserList: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const [modalVisible, setModalVisible] = useState(false);
  const [editData, setEditData] = useState<any>(null);
  const [title, setTitle] = useState('');

  const [readOnly, setReadOnly] = useState(false);

  const columns: ProColumns[] = [
    // {
    //   title: 'id',
    //   dataIndex: 'id',
    // },
    {
      title: '登录名',
      dataIndex: 'username',
    },
    {
      title: '用户名',
      dataIndex: 'name',
    },
    {
      title: '手机号',
      dataIndex: 'phone',
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      valueType: 'dateTime',
    },
    {
      title: '更新时间',
      dataIndex: 'updateTime',
      valueType: 'dateTime',
    },
    {
      title: '操作',
      valueType: 'option',
      render: (_, record) => [
        <a
          key="view"
          onClick={() => {
            setModalVisible(true);
            setEditData(record);
            setTitle('查看用户');
            setReadOnly(true);
          }}
        >
          查看
        </a>,
        <a
          key="editable"
          onClick={() => {
            setModalVisible(true);
            setEditData(record);
            setTitle('修改用户');
            setReadOnly(false);
          }}
        >
          编辑
        </a>,
        <a
          key="generatePassword"
          onClick={async () => {
            await generatePassword({
              id: record.id,
            });
            message.success('密码重置成功');
          }}
        >
          重置密码
        </a>,
        // <a
        //   key={'delete'}
        //   onClick={() => {
        //     handleDelete(record.id);
        //   }}
        // >
        //   删除
        // </a>,
        <Popconfirm
          key="delete"
          title="确认删除该记录？"
          description="删除后数据将无法恢复，是否继续？"
          okText="确认"
          cancelText="取消"
          onConfirm={() => {
            deleteSysUser(record.id).then((e) => {
              message.success(e.message);
              actionRef.current?.reload();
            });
          }}
        >
          <a>删除</a>
        </Popconfirm>,
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.RuleListItem, API.PageParams>
        headerTitle={'用户列表'}
        columns={columns}
        actionRef={actionRef}
        request={async (params) => {
          return listSysUser({
            arg0: {},
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
        toolBarRender={() => [
          <Button
            key="save"
            type="primary"
            onClick={() => {
              setEditData(null);
              setModalVisible(true);
              setTitle('新建用户');
              setReadOnly(false);
            }}
            icon={<PlusOutlined />}
          >
            新建
          </Button>,
        ]}
      ></ProTable>
      <SysUserForm
        visible={modalVisible}
        onVisibleChange={setModalVisible}
        initialValues={editData}
        title={title}
        readOnly={readOnly}
        onSubmit={async (values) => {
          const isEdit = !!values.id;
          console.log(values);
          if (isEdit) {
            await updateSysUser(values);
            message.success('更新成功');
          } else {
            await saveSysUser(values);
            message.success('新增成功');
          }
          setModalVisible(false);
          actionRef.current?.reload();
        }}
      ></SysUserForm>
    </PageContainer>
  );
};
export default UserList;
