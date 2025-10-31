# TodoList 前端项目

TodoList 前端分为两个独立项目：移动端（App）和管理后台（Admin）。

---

## 📱 App - 移动端

基于 **Expo (React Native)** 开发的跨平台移动应用。

### 技术栈
- Expo ~50.0.0
- React Native 0.73.0
- TypeScript
- React Navigation
- React Native Paper

### 快速开始
```bash
cd app
npm install
npm start
```

📖 [详细文档](./app/README.md)

---

## 💻 Admin - 管理后台

基于 **Vite + React + Ant Design** 开发的 Web 管理后台。

### 技术栈
- Vite 5.0.8
- React 18.2.0
- TypeScript
- Ant Design 5.12.0
- React Router

### 快速开始
```bash
cd admin
npm install
npm run dev
```

访问: http://localhost:3000

📖 [详细文档](./admin/README.md)

---

## 🏗️ 项目结构

```
frontend/
├── app/              # 移动端（Expo/React Native）
│   ├── src/
│   │   ├── screens/
│   │   ├── components/
│   │   ├── services/
│   │   └── types/
│   ├── App.tsx
│   └── package.json
│
└── admin/            # 管理后台（Vite/React）
    ├── src/
    │   ├── pages/
    │   ├── components/
    │   ├── services/
    │   └── types/
    ├── index.html
    └── package.json
```

---

## 🎯 功能对比

| 功能 | App（移动端） | Admin（管理后台） |
|------|--------------|-----------------|
| 用户端 | ✅ 任务管理 | ❌ |
| 管理端 | ❌ | ✅ 数据统计、用户管理 |
| 平台 | iOS / Android / Web | Web (桌面浏览器) |
| UI 库 | React Native Paper | Ant Design |
| 认证 | JWT | JWT |

---

## 🔗 API 接口

两个前端项目都连接到同一个后端 API：

- **开发环境**: http://localhost:8000
- **API 文档**: http://localhost:8000/docs

---

## 📝 开发规范

### 代码风格
- 使用 TypeScript
- 遵循 ESLint 规则
- 组件使用函数式组件 + Hooks

### 命名规范
- 组件文件: PascalCase (如 `TodoItem.tsx`)
- 工具函数: camelCase (如 `formatDate.ts`)
- 常量: UPPER_SNAKE_CASE (如 `API_URL`)

### Git 提交
遵循 Conventional Commits：
```
feat: 新增功能
fix: 修复 bug
style: UI 样式调整
refactor: 代码重构
```

---

## 🔧 环境变量

### App (.env)
```env
EXPO_PUBLIC_API_URL=http://localhost:8000
```

### Admin (.env)
```env
VITE_API_URL=http://localhost:8000
VITE_APP_TITLE=TodoList 管理后台
```

---

## 🚀 部署

### App 部署
使用 EAS Build：
```bash
cd app
eas build --platform ios     # iOS
eas build --platform android  # Android
```

### Admin 部署
构建静态文件：
```bash
cd admin
npm run build
# dist/ 目录可部署到任何静态托管服务
```

支持的平台：
- Vercel
- Netlify
- GitHub Pages
- AWS S3 + CloudFront

---

## 📚 相关文档

- [后端 API 文档](../backend/README.md)
- [项目文档](../docs/README.md)
- [项目总览](../README.md)

---

## 🆘 常见问题

**Q: 为什么分成两个前端项目？**
A: App 面向用户（移动端），Admin 面向管理员（Web 端），功能和用户群体不同，分开开发更清晰。

**Q: 可以只开发一个吗？**
A: 可以。如果只需要移动端，只开发 App；如果只需要管理后台，只开发 Admin。

**Q: 两个项目可以共享代码吗？**
A: 可以抽取共同的类型定义、API 服务到独立的 npm 包或 monorepo 结构。

---

**最后更新**: 2025-10-31
