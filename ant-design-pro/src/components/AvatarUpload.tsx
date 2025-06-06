import { Upload, UploadFile, message, Image } from 'antd';
import { useEffect, useState, useRef } from 'react';
import { LoadingOutlined, PlusOutlined } from '@ant-design/icons';
import { upload } from '@/services/ant-design-pro/system';
import { PageContainer } from '@ant-design/pro-components';

interface AvatarUploadProps {
  value?: string | string[];
  onChange?: (value: string | string[]) => void;
  customUpload?: (file: File) => Promise<string>;
  maxCount?: number;
  type?: 'avatar' | 'single' | 'multiple';
  accept?: string;
  disabled?: boolean;
}

const getBase64 = (file: File): Promise<string> =>
  new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result as string);
    reader.onerror = (error) => reject(error);
  });

const AvatarUpload: React.FC<AvatarUploadProps> = ({
  value,
  onChange,
  customUpload,
  maxCount = 1,
  type = 'avatar',
  accept = 'image/*',
  disabled = false,
}) => {
  const [fileList, setFileList] = useState<UploadFile[]>([]);
  const [loadingMap, setLoadingMap] = useState<Record<string, boolean>>({});
  const previewUrlMap = useRef<Map<string, string>>(new Map());
  const isMultiple = type === 'multiple';

  const [previewOpen, setPreviewOpen] = useState(false);
  const [previewImage, setPreviewImage] = useState('');

  useEffect(() => {
    if (isMultiple && Array.isArray(value)) {
      setFileList(
        value.map((url, idx) => ({
          uid: `${url}-${idx}`,
          name: url.split('/').pop() || `file-${idx}`,
          status: 'done',
          url,
        })),
      );
    } else if (typeof value === 'string' && value) {
      setFileList([
        {
          uid: value,
          name: value.split('/').pop() || 'file-0',
          status: 'done',
          url: value,
        },
      ]);
    } else {
      setFileList([]);
    }
  }, [value, type]);

  useEffect(() => {
    // 组件卸载时释放所有临时URL，防止内存泄漏
    return () => {
      previewUrlMap.current.forEach((url) => URL.revokeObjectURL(url));
      previewUrlMap.current.clear();
    };
  }, []);

  const uploadFile = async (file: File): Promise<string> => {
    const res = await upload(file);
    if (!res.success) throw new Error('上传失败');
    return res.result; // 修改为你的接口字段
  };

  const handleCustomRequest = async (options: any) => {
    const { file, onSuccess, onError } = options;
    const uid = file.uid;

    setLoadingMap((prev) => ({ ...prev, [uid]: true }));

    try {
      const url = customUpload ? await customUpload(file) : await uploadFile(file);

      const newFile: UploadFile = {
        uid,
        name: file.name,
        status: 'done',
        url,
      };

      setFileList((prev) => {
        const updated = isMultiple ? [...prev, newFile] : [newFile];
        return updated;
      });

      const currentValue = Array.isArray(value) ? value : value ? [value] : [];
      const newValue = isMultiple ? [...currentValue, url] : url;

      onChange?.(newValue);
      message.success('上传成功');
      onSuccess?.(file);
    } catch (e) {
      message.error('上传失败');
      onError?.(e);
    } finally {
      setLoadingMap((prev) => {
        const updated = { ...prev };
        delete updated[uid];
        return updated;
      });
    }
  };

  const handleRemove = (file: UploadFile) => {
    if (isMultiple && Array.isArray(value)) {
      const newVal = value.filter((url) => url !== file.url);
      onChange?.(newVal);
    } else {
      onChange?.('');
    }
    // 删除文件时释放对应预览URL
    const prevUrl = previewUrlMap.current.get(file.uid);
    if (prevUrl) {
      URL.revokeObjectURL(prevUrl);
      previewUrlMap.current.delete(file.uid);
    }
  };

  const handlePreview = async (file: UploadFile) => {
    if (!file.url && !file.preview) {
      file.preview = await getBase64(file.originFileObj as File);
    }
    setPreviewImage(file.url || (file.preview as string));
    setPreviewOpen(true);
    // 让 antd Upload 组件使用 file.preview 或 file.url 自动预览
  };

  const uploadButton = (
    <div>
      <PlusOutlined />
      <div style={{ marginTop: 8 }}>上传</div>
    </div>
  );

  return (
    <PageContainer>
      <Upload
        onPreview={handlePreview}
        listType="picture-card"
        accept={accept}
        fileList={fileList}
        onRemove={handleRemove}
        customRequest={handleCustomRequest}
        disabled={disabled}
        maxCount={isMultiple ? maxCount : 1}
        multiple={isMultiple}
        showUploadList={{
          showPreviewIcon: true,
          showRemoveIcon: !disabled,
        }}
        itemRender={(originNode, file) => {
          const isLoading = loadingMap[file.uid];
          return (
            <div style={{ position: 'relative' }}>
              {originNode}
              {isLoading && (
                <div
                  style={{
                    position: 'absolute',
                    inset: 0,
                    background: 'rgba(255, 255, 255, 0.7)',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    zIndex: 10,
                  }}
                >
                  <LoadingOutlined />
                </div>
              )}
            </div>
          );
        }}
      >
        {(isMultiple || fileList.length === 0) && uploadButton}
      </Upload>
      {previewImage && (
        <Image
          wrapperStyle={{ display: 'none' }}
          preview={{
            visible: previewOpen,
            onVisibleChange: (visible) => setPreviewOpen(visible),
            afterOpenChange: (visible) => !visible && setPreviewImage(''),
          }}
          src={previewImage}
        />
      )}
    </PageContainer>
  );
};

export default AvatarUpload;
