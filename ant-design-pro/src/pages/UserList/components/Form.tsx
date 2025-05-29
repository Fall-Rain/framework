import { ModalForm, ProFormText } from '@ant-design/pro-components';

// export type FormProps = {
//   visible: boolean;
//   onVisibleChange: (v: boolean) => void;
//   onSubmit: (values: any) => Promise<void>;
//   initialValues?: any;
//   title: any;
// };

interface FormProps {
  visible: boolean;
  onVisibleChange: (v: boolean) => void;
  onSubmit: (values: any) => Promise<void>;
  initialValues?: any;
  title: any;
}

const Form: React.FC<FormProps> = (props: FormProps) => {
  const { visible, onVisibleChange, onSubmit, initialValues, title } = props;

  return (
    <ModalForm
      title={title}
      open={visible}
      modalProps={{ destroyOnClose: true }}
      onOpenChange={onVisibleChange}
      onFinish={onSubmit}
      initialValues={initialValues}
    >
      <ProFormText name="id" hidden />
      <ProFormText name="username" label="用户名" rules={[{ required: true }]} />
      <ProFormText name="phone" label="手机号" />
    </ModalForm>
  );
};
export default Form;
