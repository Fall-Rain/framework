import { ModalForm, ProFormSelect, ProFormSwitch, ProFormText } from '@ant-design/pro-components';
import { Button } from 'antd';
import ButtonGroup from 'antd/es/button/button-group';

interface RoleListFormProps {
  visible: boolean;
  onVisibleChange: (v: boolean) => void;
  onSubmit: (values: any) => Promise<void>;
  initialValues?: any;
  title: any;
  readOnly: boolean;
}

const RoleListForm: React.FC<RoleListFormProps> = ({
  visible,
  onVisibleChange,
  onSubmit,
  initialValues,
  title,
  readOnly = false,
}) => {
  return (
    <ModalForm
      layout={'horizontal'}
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
      <ProFormText disabled={readOnly} name="name" label="角色名" rules={[{ required: true }]} />
      <ProFormText disabled={readOnly} name="code" label="编码" rules={[{ required: true }]} />
      <ProFormText
        disabled={readOnly}
        name="description"
        label="详细信息"
        rules={[{ required: true }]}
      />
      <ProFormSelect
        name={'status'}
        label={'状态'}
        disabled={readOnly}
        rules={[{ required: true }]}
        options={[
          { label: '启用', value: 1 },
          { label: '禁用', value: 0 },
        ]}
      />
    </ModalForm>
  );
};
export default RoleListForm;
