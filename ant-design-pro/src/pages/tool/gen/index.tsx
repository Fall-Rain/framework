import { type ActionType, PageContainer, ProColumns, ProTable } from '@ant-design/pro-components';
import { Button, message, Popconfirm } from 'antd';
import SqlParseModal from '@/pages/tool/gen/compoents/SqlParseModal';
import { useRef, useState } from 'react';
import { PlusOutlined } from '@ant-design/icons';
import { addGenTable, deleteGenTable, editGenTable, listGenTable } from '@/services/ant-design-pro/genTable';
import GenTableForm from '@/pages/tool/gen/compoents/GenTableForm';
import { saveUserInfo, updateUserInfo } from '@/services/ant-design-pro/userInfo';

const ToolGen: React.FC = () => {
  const [sqlParseModalVisible, setSqlParseModalVisible] = useState(false);

  const [genTableFormVisible, setGenTableFormVisible] = useState(false);

  const actionRef = useRef<ActionType>();

  const [editData, setEditData] = useState<any>(null);
  const [readOnly, setReadOnly] = useState(false);
  const [title, setTitle] = useState('');
  const columns: ProColumns[] = [
    {
      title: '表名称',
      dataIndex: 'tableName',
    },
    {
      title: '表描述',
      dataIndex: 'tableComment',
    },
    {
      title: '实体类名',
      dataIndex: 'className',
    },
    {
      title: '作者',
      dataIndex: 'functionAuthor',
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      valueType: 'dateTime',
    },
    {
      title: '操作',
      valueType: 'option',
      render: (_, record) => [
        <a
          key="edit"
          onClick={() => {
            setEditData(record);
            setTitle('编辑表结构');
            setReadOnly(false);
            setGenTableFormVisible(true);
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
            deleteGenTable(record.id).then((e) => {
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
        headerTitle={'数据库表'}
        columns={columns}
        actionRef={actionRef}
        request={async (params) => {
          return listGenTable({
            genTable: {},
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
            type={'primary'}
            key={'gen'}
            onClick={() => {
              setSqlParseModalVisible(true);
            }}
          >
            解析sql
          </Button>,
          <Button
            key="save"
            type="primary"
            onClick={() => {
              setEditData(null);
              setGenTableFormVisible(true);
              setTitle('新建表格');
              setReadOnly(false);
            }}
            icon={<PlusOutlined />}
          >
            新建
          </Button>
        ]}
      ></ProTable>

      <SqlParseModal
        visible={sqlParseModalVisible}
        onCancel={() => {
          setSqlParseModalVisible(false);
          // actionRef.current?.reload();
        }}
        onParsed={() => {
          setSqlParseModalVisible(false);
          actionRef.current?.reload();
        }}
      ></SqlParseModal>
      <GenTableForm
        visible={genTableFormVisible}
        onVisibleChange={setGenTableFormVisible}
        initialValues={editData}
        title={title}
        readOnly={readOnly}
        onSubmit={async (values) => {
          console.log('values===>',values);
          const isEdit = !!values.id;
          if (isEdit) {
            await editGenTable(values);
            message.success('更新成功');
          } else {
            await addGenTable(values);
            message.success('新增成功');
          }
          setGenTableFormVisible(false);
          actionRef.current?.reload();
        }}
      ></GenTableForm>
    </PageContainer>
  );
};

export default ToolGen;
