import { ModalForm, ProFormText } from '@ant-design/pro-components';
import AvatarUpload from '@/components/AvatarUpload';
import { Form } from 'antd';

// export type FormProps = {
//   visible: boolean;
//   onVisibleChange: (v: boolean) => void;
//   onSubmit: (values: any) => Promise<void>;
//   initialValues?: any;
//   title: any;
// };

interface UserListFormProps {
  visible: boolean;
  onVisibleChange: (v: boolean) => void;
  onSubmit: (values: any) => Promise<void>;
  initialValues?: any;
  title: any;
  readOnly: boolean;
}

const UserListForm: React.FC<UserListFormProps> = ({
  visible,
  onVisibleChange,
  onSubmit,
  initialValues,
  title,
  readOnly = false,
}) => {
  return (
    <ModalForm
      title={title}
      open={visible}
      modalProps={{ destroyOnClose: true }}
      onOpenChange={onVisibleChange}
      onFinish={onSubmit}
      initialValues={initialValues}
      submitter={{
        render: (_, dom) => (readOnly ? null : dom), // 只读时隐藏提交按钮
      }}
    >
      <ProFormText disabled={readOnly} name="id" hidden />
      <ProFormText
        disabled={readOnly}
        name="username"
        label="用户名"
        rules={[{ required: true }]}
      />
      <ProFormText disabled={readOnly} name="phone" label="手机号" />
      <Form.Item
        label={'头像'}
        name={'avatar'}
        valuePropName={'value'}
        getValueFromEvent={(val) => val}
      >
        <AvatarUpload
          disabled={readOnly}
        />
      </Form.Item>
    </ModalForm>
  );
};
export default UserListForm;
