import { Table, Space, Button } from 'antd';
import { EyeOutlined, DeleteOutlined } from '@ant-design/icons';
import type { ColumnsType } from 'antd/es/table';
import type { User } from '@/types';

export default function UserList() {
  // TODO: 从 API 获取数据
  const mockData: User[] = [
    {
      id: '1',
      email: 'user@example.com',
      username: 'Alice',
      createdAt: '2025-10-20T10:00:00Z',
      updatedAt: '2025-10-31T10:00:00Z',
    },
  ];

  const columns: ColumnsType<User> = [
    {
      title: '用户名',
      dataIndex: 'username',
      key: 'username',
    },
    {
      title: '邮箱',
      dataIndex: 'email',
      key: 'email',
    },
    {
      title: '注册时间',
      dataIndex: 'createdAt',
      key: 'createdAt',
      render: (date: string) => new Date(date).toLocaleDateString('zh-CN'),
    },
    {
      title: '操作',
      key: 'action',
      render: () => (
        <Space size="middle">
          <Button type="link" icon={<EyeOutlined />}>查看</Button>
          <Button type="link" danger icon={<DeleteOutlined />}>删除</Button>
        </Space>
      ),
    },
  ];

  return (
    <div>
      <h1>用户管理</h1>
      <Table
        columns={columns}
        dataSource={mockData}
        rowKey="id"
      />
    </div>
  );
}
