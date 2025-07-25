import { useState } from 'react';
import { Button, Input, message, Modal, Upload, UploadProps } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import { createGenTable } from '@/services/ant-design-pro/genTable';

const SqlParseModal: React.FC<{
  visible: boolean;
  onCancel: () => void;
  onParsed: (data: any) => void; // 解析后回调
}> = ({ visible, onCancel, onParsed }) => {
  const [sql, setSql] = useState('');
  const [loading, setLoading] = useState(false);

  const handleParse = async () => {
    if (!sql.trim()) return message.warning('请输入 SQL');

    setLoading(true);
    try {
      createGenTable({
        sql: sql,
      }).then((res) => {
        if (res.success) {
          setLoading(false);
          message.success(res.message);
          onParsed(res);
        }
      });
    } catch (res) {
      message.error('解析出错');
    } finally {
      setLoading(false);
    }
  };

  const uploadProps: UploadProps = {
    beforeUpload: async (file) => {
      const text = await file.text();
      setSql(text);
      return false;
    },
    showUploadList: false,
  };

  return (
    <Modal
      title={'解析建表sql'}
      open={visible}
      onCancel={onCancel}
      confirmLoading={loading}
      width={700}
      onOk={handleParse}
    >
      <Upload {...uploadProps}>
        <Button icon={<UploadOutlined />}>上传 .sql 文件</Button>
      </Upload>
      <Input.TextArea
        style={{ marginTop: 16 }}
        rows={10}
        value={sql}
        onChange={(e) => setSql(e.target.value)}
        placeholder="请粘贴建表语句，例如：CREATE TABLE ..."
      />
    </Modal>
  );
};
export default SqlParseModal;
