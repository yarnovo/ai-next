# TodoList 管理后台

基于 Vite + React + TypeScript + Ant Design 开发的 Web 管理后台。

## 🚀 快速开始

### 安装依赖
```bash
npm install
# 或
yarn install
```

### 启动开发服务器
```bash
npm run dev
```

应用将运行在 http://localhost:3000

### 构建生产版本
```bash
npm run build
```

### 预览生产版本
```bash
npm run preview
```

## 📁 项目结构

```
admin/
├── src/
│   ├── components/       # 可复用组件
│   │   └── Layout.tsx    # 布局组件
│   ├── pages/            # 页面组件
│   │   ├── Login.tsx     # 登录页
│   │   ├── Dashboard.tsx # 仪表盘
│   │   ├── TodoList.tsx  # 任务管理
│   │   └── UserList.tsx  # 用户管理
│   ├── services/         # API 服务
│   │   └── api.ts        # API 封装
│   ├── types/            # TypeScript 类型
│   │   └── index.ts      # 类型定义
│   ├── contexts/         # React Context
│   ├── hooks/            # 自定义 Hooks
│   ├── utils/            # 工具函数
│   ├── App.tsx           # 应用入口
│   ├── main.tsx          # ReactDOM 入口
│   └── index.css         # 全局样式
├── index.html            # HTML 模板
├── vite.config.ts        # Vite 配置
├── tsconfig.json         # TypeScript 配置
└── package.json          # 依赖配置
```

## 🛠️ 技术栈

- **Vite**: 5.0.8 - 快速的构建工具
- **React**: 18.2.0 - UI 框架
- **TypeScript**: 5.3.3 - 类型安全
- **React Router**: 6.21.0 - 路由管理
- **Ant Design**: 5.12.0 - UI 组件库
- **Axios**: 1.6.0 - HTTP 客户端
- **Zustand**: 4.4.0 - 状态管理
- **Recharts**: 2.10.0 - 图表库

## 📝 功能模块

### 1. 登录认证
- 邮箱/密码登录
- JWT Token 认证
- 自动跳转

### 2. 仪表盘
- 数据统计展示
- 快速概览
- 图表展示

### 3. 任务管理
- 任务列表展示
- 任务 CRUD 操作
- 状态筛选
- 优先级标记

### 4. 用户管理
- 用户列表展示
- 用户详情查看
- 用户操作

## 🎨 UI 组件

使用 Ant Design 5.x 组件库：
- **Layout**: 布局组件
- **Menu**: 导航菜单
- **Table**: 数据表格
- **Form**: 表单组件
- **Card**: 卡片容器
- **Statistic**: 统计数值
- **Tag**: 标签
- **Button**: 按钮

## 🔧 开发配置

### 路径别名
```typescript
import Component from '@/components/Component'
```

### 环境变量
创建 `.env` 文件：
```env
VITE_API_URL=http://localhost:8000
VITE_APP_TITLE=TodoList 管理后台
```

### 代理配置
开发环境自动代理 `/api` 请求到后端服务器。

## 📊 路由结构

```
/login              # 登录页
/                   # 布局容器
  ├── /dashboard    # 仪表盘
  ├── /todos        # 任务管理
  └── /users        # 用户管理
```

## 🔐 权限管理

- 未登录自动跳转到登录页
- Token 存储在 localStorage
- Token 过期自动跳转登录

## 📚 学习资源

- [Vite 文档](https://vitejs.dev/)
- [React 文档](https://react.dev/)
- [Ant Design 文档](https://ant.design/)
- [React Router 文档](https://reactrouter.com/)

## 🐛 常见问题

**Q: 如何修改端口？**
A: 在 `vite.config.ts` 中修改 `server.port` 配置。

**Q: 如何添加新路由？**
A: 在 `App.tsx` 中添加新的 `<Route>` 配置。

**Q: 如何自定义主题？**
A: 在 `main.tsx` 的 `ConfigProvider` 中配置 `theme` 属性。
