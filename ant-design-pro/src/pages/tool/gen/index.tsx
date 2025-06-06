import { PageContainer } from '@ant-design/pro-components';
import { Button } from 'antd';
import SqlParseModal from '@/pages/tool/gen/compoents/SqlParseModal';
import { useState } from 'react';

const ToolGen: React.FC = () => {
  const [modalVisible, setModalVisible] = useState(false);

  return (
    <PageContainer>
      <Button type={'primary'} onClick={() => {setModalVisible(true)}}>
        解析sql
      </Button>
      <SqlParseModal
        visible={modalVisible}
        onCancel={() => {
          setModalVisible(false);
        }}
        onParsed={(data)=>{
          console.log(data);
        }}
      ></SqlParseModal>
    </PageContainer>
  );
};

export default ToolGen;
