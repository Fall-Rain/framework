import { type ActionType, PageContainer, ProColumns, ProTable } from '@ant-design/pro-components';
import { useRef, useState } from 'react';
import { Button, message, Popconfirm, Tag } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import RoleListForm from '@/pages/permission/roleList/compoents/RoleListForm';
import { deleteSysRole, listSysRole, saveSysRole, updateSysRole } from '@/services/ant-design-pro/sysRole';



const RoleList: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const [modalVisible, setModalVisible] = useState(false);
  const [editData, setEditData] = useState<any>(null);
  const [title, setTitle] = useState('');
  const [readOnly, setReadOnly] = useState(false);

  const columns: ProColumns[] = [
    {
      title: '角色名',
      dataIndex: 'name',
    },
    {
      title: '角色编码',
      dataIndex: 'code',
    },
    {
      title: '详细信息',
      dataIndex: 'description',
    },
    {
      title: '状态',
      dataIndex: 'status',
      render: (text) => {
        if (text === 1)
          return (
            <>
              <Tag>启用</Tag>
            </>
          );
        if (text === 0)
          return (
            <>
              <Tag>禁用</Tag>
            </>
          );
      },
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
    },
    {
      title: '更新时间',
      dataIndex: 'updateTime',
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
        <Popconfirm
          key="delete"
          title="确认删除该记录？"
          description="删除后数据将无法恢复，是否继续？"
          okText="确认"
          cancelText="取消"
          onConfirm={() => {
            deleteSysRole(record.id).then((e) => {
              message.success(e.message);
              actionRef.current?.reload();
            });
          }}
        >
          <a>删除</a>
        </Popconfirm>
      ],
    }
  ];

  return (
    <PageContainer>
      <ProTable<API.RuleListItem, API.PageParams>
        headerTitle={'角色管理'}
        columns={columns}
        actionRef={actionRef}
        toolBarRender={() => [
          <Button
            key={'save'}
            type={'primary'}
            icon={<PlusOutlined />}
            onClick={() => {
              setTitle('新建角色');
              setEditData(null);
              setReadOnly(false);
              setModalVisible(true);
            }}
          >
            新建
          </Button>,
        ]}
        request={async (params) => {
          return listSysRole({
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
      ></ProTable>
      <RoleListForm
        visible={modalVisible}
        initialValues={editData}
        onVisibleChange={setModalVisible}
        onSubmit={async (data) => {
          console.log(data);
          if (!!data.id) {
            await updateSysRole(data);
            message.success('更新成功');

          } else {
            await saveSysRole(data);
            message.success('新增成功');
          }
          setModalVisible(false);
          actionRef.current?.reload();
        }}
        title={title}
        readOnly={readOnly}
      ></RoleListForm>
    </PageContainer>
  );
};

export default RoleList;
