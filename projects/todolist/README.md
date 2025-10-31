# TodoList 项目

一个使用 Expo (React Native) + Python (FastAPI) 开发的全栈待办事项应用示例项目。

**项目状态**: 开发中 | **版本**: v1.0.0

---

## 📁 项目结构

```
todolist/
├── frontend/          # 前端项目 (Expo/React Native)
│   ├── src/
│   ├── app.json
│   └── package.json
├── backend/           # 后端项目 (Python/FastAPI)
│   ├── app/
│   ├── requirements.txt
│   └── main.py
├── docs/              # 项目文档
│   ├── PRD-todolist-v1.0.md
│   ├── Tech-design-todolist-v1.0.md
│   ├── API-doc-todolist-v1.0.md
│   └── ...
└── README.md          # 本文件
```

---

## 🚀 快速开始

### 前提条件
- Node.js 18+ 和 npm/yarn
- Python 3.10+
- Expo CLI
- Git

### 1. 克隆项目
```bash
git clone <repository-url>
cd todolist
```

### 2. 启动后端
```bash
cd backend

# 创建虚拟环境
python -m venv venv
source venv/bin/activate  # Windows: venv\Scripts\activate

# 安装依赖
pip install -r requirements.txt

# 启动服务
uvicorn main:app --reload
```

后端将运行在 http://localhost:8000

### 3. 启动前端
```bash
cd frontend

# 安装依赖
npm install

# 启动 Expo
npm start
```

扫描二维码在手机上查看，或按 `w` 在浏览器中打开。

---

## 📱 技术栈

### 前端
- **框架**: Expo (React Native)
- **语言**: TypeScript
- **状态管理**: React Context / Zustand
- **UI 库**: React Native Paper
- **导航**: React Navigation
- **HTTP 客户端**: Axios

### 后端
- **框架**: FastAPI
- **语言**: Python 3.10+
- **数据库**: SQLite (开发) / PostgreSQL (生产)
- **ORM**: SQLAlchemy
- **认证**: JWT (python-jose)
- **API 文档**: OpenAPI (Swagger)

---

## 📚 文档

详细文档请查看 `docs/` 目录：

| 文档 | 描述 | 状态 |
|------|------|------|
| [README](docs/README.md) | 文档导航 | ✅ |
| [PRD](docs/PRD-todolist-v1.0.md) | 产品需求文档 | ✅ |
| [Tech-design](docs/Tech-design-todolist-v1.0.md) | 技术设计文档 | ✅ |
| [API-doc](docs/API-doc-todolist-v1.0.md) | API 接口文档 | 🚧 |
| [BDD](docs/BDD-todolist-v1.0.md) | 行为驱动开发 | 🚧 |

---

## 🔧 开发指南

### 前端开发

```bash
cd frontend

# 启动开发服务器
npm start

# 运行 iOS 模拟器
npm run ios

# 运行 Android 模拟器
npm run android

# 运行测试
npm test

# 代码检查
npm run lint
```

### 后端开发

```bash
cd backend

# 运行开发服务器（热重载）
uvicorn main:app --reload

# 运行测试
pytest

# 代码检查
flake8 app/
black app/

# 数据库迁移
alembic upgrade head
```

### API 文档

启动后端后，访问：
- Swagger UI: http://localhost:8000/docs
- ReDoc: http://localhost:8000/redoc

---

## 🧪 测试

### 后端测试
```bash
cd backend
pytest --cov=app tests/
```

### 前端测试
```bash
cd frontend
npm test
```

---

## 📦 构建与部署

### 前端构建

```bash
cd frontend

# 构建 iOS
eas build --platform ios

# 构建 Android
eas build --platform android
```

### 后端部署

```bash
cd backend

# 使用 Docker
docker build -t todolist-backend .
docker run -p 8000:8000 todolist-backend

# 或使用其他平台
# - Railway
# - Render
# - AWS Lambda
```

---

## 🔐 环境变量

### 后端 `.env`
```env
DATABASE_URL=sqlite:///./todolist.db
SECRET_KEY=your-secret-key-here
ALGORITHM=HS256
ACCESS_TOKEN_EXPIRE_MINUTES=30
```

### 前端 `.env`
```env
EXPO_PUBLIC_API_URL=http://localhost:8000
```

---

## 📊 项目进度

- [x] 项目初始化
- [x] 需求文档（PRD）
- [x] 技术设计
- [x] 后端 API 基础框架
- [x] 前端基础架构
- [ ] 用户认证功能 (60%)
- [ ] 任务 CRUD 功能 (40%)
- [ ] 任务分类功能 (0%)
- [ ] 任务提醒功能 (0%)
- [ ] 测试覆盖 (20%)

---

## 👥 团队

- **产品经理**: Alice
- **技术负责人**: Bob
- **前端开发**: Charlie
- **后端开发**: Dave
- **测试工程师**: Eve

---

## 📝 开发规范

### Git 提交规范
遵循 Conventional Commits:
```
feat: 新增功能
fix: 修复 bug
docs: 文档更新
style: 代码格式调整
refactor: 重构
test: 测试相关
chore: 构建/工具相关
```

### 代码审查
- 所有代码必须经过 Code Review
- PR 需要至少 1 人 approve
- 通过所有 CI 检查

---

## 🐛 问题反馈

- GitHub Issues: [链接]
- 项目群: DingTalk - TodoList 项目群

---

## 📄 许可证

MIT License

---

**最后更新**: 2025-10-31
