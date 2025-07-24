import { ModalForm, ProFormSelect, ProFormText } from '@ant-design/pro-components';
import AvatarUpload from '@/components/AvatarUpload';
import { Form } from 'antd';
import { getSysRoleList } from '@/services/ant-design-pro/sysRole';

interface UserListFormProps {
  visible: boolean;
  onVisibleChange: (v: boolean) => void;
  onSubmit: (values: any) => Promise<void>;
  initialValues?: any;
  title: any;
  readOnly: boolean;
}

const SysUserForm: React.FC<UserListFormProps> = ({
  visible,
  onVisibleChange,
  onSubmit,
  initialValues,
  title,
  readOnly = false,
}) => {
  console.log(initialValues)
  return (
    <ModalForm
      title={title}
      open={visible}
      modalProps={{ destroyOnClose: true }}
      onOpenChange={onVisibleChange}
      onFinish={onSubmit}
      initialValues={{
        ...initialValues,
        roleIds: initialValues?.roleIds?.split(','),
      }}
      submitter={{
        render: (_, dom) => (readOnly ? null : dom), // 只读时隐藏提交按钮
      }}
    >
      <Form.Item
        label={'头像'}
        name={'avatar'}
        valuePropName={'value'}
        getValueFromEvent={(val) => val}
      >
        <AvatarUpload type={'avatar'} disabled={readOnly} />
      </Form.Item>
      <ProFormText disabled={readOnly} name="id" hidden />
      <ProFormText
        disabled={readOnly}
        name="username"
        label="用户名"
        rules={[{ required: true }]}
      />
      <ProFormText disabled={readOnly} name="phone" label="手机号" />
      <ProFormSelect
        disabled={readOnly}
        mode={'multiple'}
        name="roleIds"
        label="角色"
        request={async () => {
          const res = await getSysRoleList();
          return res.result.map((item: { name: any; id: any }) => ({
            label: item.name,
            value: item.id,
          }));
        }}
      />
    </ModalForm>
  );
};
export default SysUserForm;
