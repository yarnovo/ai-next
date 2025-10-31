import { Table, Tag, Space, Button } from 'antd';
import { EditOutlined, DeleteOutlined } from '@ant-design/icons';
import type { ColumnsType } from 'antd/es/table';
import type { Todo } from '@/types';

export default function TodoList() {
  // TODO: 从 API 获取数据
  const mockData: Todo[] = [
    {
      id: '1',
      title: '完成项目文档',
      description: '编写技术设计文档',
      completed: false,
      priority: 'high',
      dueDate: '2025-11-05',
      createdAt: '2025-10-31T10:00:00Z',
      updatedAt: '2025-10-31T10:00:00Z',
      userId: 'user1',
    },
  ];

  const columns: ColumnsType<Todo> = [
    {
      title: '任务标题',
      dataIndex: 'title',
      key: 'title',
    },
    {
      title: '优先级',
      dataIndex: 'priority',
      key: 'priority',
      render: (priority: string) => {
        const colorMap = {
          high: 'red',
          medium: 'orange',
          low: 'green',
        };
        const textMap = {
          high: '高',
          medium: '中',
          low: '低',
        };
        return <Tag color={colorMap[priority as keyof typeof colorMap]}>
          {textMap[priority as keyof typeof textMap]}
        </Tag>;
      },
    },
    {
      title: '状态',
      dataIndex: 'completed',
      key: 'completed',
      render: (completed: boolean) => (
        <Tag color={completed ? 'success' : 'processing'}>
          {completed ? '已完成' : '进行中'}
        </Tag>
      ),
    },
    {
      title: '截止日期',
      dataIndex: 'dueDate',
      key: 'dueDate',
    },
    {
      title: '操作',
      key: 'action',
      render: () => (
        <Space size="middle">
          <Button type="link" icon={<EditOutlined />}>编辑</Button>
          <Button type="link" danger icon={<DeleteOutlined />}>删除</Button>
        </Space>
      ),
    },
  ];

  return (
    <div>
      <h1>任务管理</h1>
      <Table
        columns={columns}
        dataSource={mockData}
        rowKey="id"
      />
    </div>
  );
}
