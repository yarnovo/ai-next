# TodoList 前端项目

基于 Expo 和 React Native 开发的跨平台移动应用。

## 🚀 快速开始

### 安装依赖
```bash
npm install
```

### 启动开发服务器
```bash
npm start
```

然后：
- 扫描二维码在手机上查看（需要安装 Expo Go App）
- 按 `w` 在浏览器中打开
- 按 `i` 在 iOS 模拟器中打开
- 按 `a` 在 Android 模拟器中打开

## 📁 项目结构

```
frontend/
├── src/
│   ├── components/      # 可复用组件
│   ├── screens/         # 页面组件
│   ├── services/        # API 服务
│   ├── contexts/        # React Context
│   ├── types/           # TypeScript 类型定义
│   └── utils/           # 工具函数
├── assets/              # 静态资源
├── App.tsx              # 应用入口
├── app.json             # Expo 配置
├── package.json         # 依赖配置
└── tsconfig.json        # TypeScript 配置
```

## 🛠️ 技术栈

- **Expo**: ~50.0.0
- **React Native**: 0.73.0
- **TypeScript**: 5.1.3
- **React Navigation**: 6.x
- **React Native Paper**: 5.x (Material Design)
- **Zustand**: 4.x (状态管理)
- **Axios**: 1.x (HTTP 客户端)

## 📝 开发规范

### 代码风格
- 使用 TypeScript
- 遵循 ESLint 规则
- 组件使用函数式组件 + Hooks

### 命名规范
- 组件文件：PascalCase（如 `TodoItem.tsx`）
- 工具函数：camelCase（如 `formatDate.ts`）
- 常量：UPPER_SNAKE_CASE（如 `API_URL`）

## 🧪 测试

```bash
npm test
```

## 📦 构建

### EAS Build（推荐）
```bash
# 安装 EAS CLI
npm install -g eas-cli

# 登录
eas login

# 配置项目
eas build:configure

# 构建 iOS
eas build --platform ios

# 构建 Android
eas build --platform android
```

## 🔐 环境变量

创建 `.env` 文件：
```env
EXPO_PUBLIC_API_URL=http://localhost:8000
```

## 📚 学习资源

- [Expo 文档](https://docs.expo.dev/)
- [React Native 文档](https://reactnative.dev/)
- [React Navigation](https://reactnavigation.org/)
- [React Native Paper](https://reactnativepaper.com/)
