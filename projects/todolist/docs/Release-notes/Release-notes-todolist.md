# TodoList v1.0.0 发布说明

> **模板版本**: v1.0.0（基于 templates/Release-notes）

---

## 📦 版本信息

| 项目 | 内容 |
|------|------|
| **版本号** | v1.0.0 |
| **发布日期** | 2025-11-30 |
| **发布类型** | 🎉 首次发布 (Major Release) |
| **发布负责人** | Bob (Tech Lead) |

---

## ✨ 新功能

### 用户认证

- ✅ 用户注册功能
  - 邮箱 + 用户名 + 密码注册
  - 自动生成 JWT Token
  - 数据验证和错误提示

- ✅ 用户登录功能
  - 邮箱 + 密码登录
  - 30分钟 Access Token
  - 7天 Refresh Token

- ✅ 用户信息管理
  - 查看个人信息
  - 更新用户名和邮箱

### 任务管理

- ✅ 创建任务
  - 任务标题（必填）
  - 任务描述（可选）
  - 优先级（高/中/低）
  - 截止日期（可选）

- ✅ 任务列表
  - 查看所有任务
  - 按完成状态筛选
  - 任务排序

- ✅ 任务操作
  - 编辑任务信息
  - 标记完成/未完成
  - 删除任务

### 多端支持

- ✅ 移动端 App (Expo/React Native)
  - iOS 和 Android 支持
  - Material Design 风格
  - 流畅的用户体验

- ✅ Web 管理后台 (Vite/React)
  - 数据统计仪表盘
  - 用户管理
  - 任务查看

---

## 🛠️ 技术栈

### 后端微服务

**user-service** (Python + FastAPI)
- Python 3.11
- FastAPI 0.109.0
- PostgreSQL 14
- JWT 认证

**todo-service** (Java + Spring Boot)
- Java 17
- Spring Boot 3.2.0
- MySQL 8.0
- Spring Data JPA

### 前端应用

**App** (Expo/React Native)
- Expo ~50.0.0
- React Native 0.73.0
- TypeScript 5.3.3

**Admin** (Vite/React)
- Vite 5.0.8
- React 18.2.0
- Ant Design 5.12.0

---

## 📊 数据统计

- **代码行数**: ~9000+ 行
- **API 端点**: 12 个
- **数据库表**: 2 个（users, todos）
- **测试用例**: 30+ 个（BDD 场景）

---

## 🐛 已知问题

暂无已知问题。

---

## 📝 升级指南

### 新用户

**部署步骤**:

```bash
# 1. 克隆代码
git clone https://github.com/example/todolist.git
cd todolist/backend

# 2. 启动服务
docker-compose up -d

# 3. 验证
curl http://localhost:8001/health
curl http://localhost:8002/actuator/health
```

### 前端部署

**移动端 App**:
```bash
cd frontend/app
npm install
npm start
```

**Web Admin**:
```bash
cd frontend/admin
npm install
npm run dev
```

---

## 🔗 相关链接

- [用户手册](../docs/用户手册.md)
- [API 文档](../docs/API-doc/API-doc-todolist.md)
- [运维手册](../docs/Runbook/Runbook-todolist.md)
- [GitHub 仓库](https://github.com/example/todolist)

---

## 👥 贡献者

感谢以下团队成员的贡献：

- Alice - 产品设计
- Bob - 技术架构
- Eva - 后端开发
- David - 前端开发
- Frank - 测试
- Grace - UI 设计

---

**发布**: 2025-11-30 | **版本**: v1.0.0
