import {
  type ActionType,
  PageContainer,
  ProColumns,
  ProTable,
  TableDropdown,
} from '@ant-design/pro-components';

import {
  deleteUserInfo,
  listUserInfo,
  saveUserInfo,
  updateUserInfo,
  generatePassword
} from '@/services/ant-design-pro/userInfo';
import { Button, message, Modal } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import Form from '@/pages/UserList/components/Form';
import { useRef, useState } from 'react';
const { confirm } = Modal;

const UserList: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const [modalVisible, setModalVisible] = useState(false);
  const [editData, setEditData] = useState<any>(null);
  const [title, setTitle] = useState('');

  const handleDelete = (id) => {
    confirm({
      title: '确认删除',
      content: '删除后数据无法恢复，请谨慎操作。',
      okText: '确认',
      cancelText: '取消',
      onOk: () => {
        deleteUserInfo(id).then((e) => {
          message.success(e.message);
          actionRef.current?.reload();
        });
      },
    });
  };

  const columns: ProColumns<API.RuleListItem>[] = [
    {
      title: 'id',
      dataIndex: 'id',
    },
    {
      title: '名字',
      dataIndex: 'username',
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
        <a key="editable">编辑</a>,
        <a key="view">查看</a>,
        <a key="generatePassword" onClick={async () => {
          await generatePassword({
            id: record.id
          });
          message.success('密码重置成功');
        }}>重置密码</a>,
        <TableDropdown
          onSelect={(key) => {
            if (key === 'delete') {
              console.log('删除', record);
              handleDelete(record.id);
            }
          }}
          key="actionGroup"
          menus={[
            { key: 'copy', name: '复制' },
            { key: 'delete', name: '删除' },
          ]}
        />,
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
          return listUserInfo({
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
            }}
            icon={<PlusOutlined />}
          >
            新建
          </Button>,
        ]}
      ></ProTable>
      <Form
        visible={modalVisible}
        onVisibleChange={setModalVisible}
        initialValues={editData}
        title={title}
        onSubmit={async (values) => {
          const isEdit = !!values.id;
          console.log(values);
          if (isEdit) {
            await updateUserInfo(values);
            message.success('更新成功');
          } else {
            await saveUserInfo(values);
            message.success('新增成功');
          }
          setModalVisible(false);
          actionRef.current?.reload();
        }}
      ></Form>
    </PageContainer>
  );
};
export default UserList;
