# User Service - 用户认证微服务

基于 Python + FastAPI 的用户认证和授权微服务。

## 🎯 服务职责

- 用户注册和登录
- JWT Token 签发和验证
- 用户信息管理
- 为其他微服务提供统一的认证中心

## 🛠️ 技术栈

- **Python**: 3.11+
- **FastAPI**: 0.109.0 - 高性能异步框架
- **SQLAlchemy**: 2.0.25 - ORM
- **PostgreSQL**: 14+ - 数据库
- **JWT**: python-jose - Token 认证
- **Bcrypt**: passlib - 密码加密

## 🚀 快速开始

### 1. 创建虚拟环境

```bash
python -m venv venv

# 激活虚拟环境
# macOS/Linux:
source venv/bin/activate
# Windows:
venv\Scripts\activate
```

### 2. 安装依赖

```bash
pip install -r requirements.txt
```

### 3. 配置环境变量

```bash
cp .env.example .env
# 编辑 .env 文件，配置数据库连接等
```

### 4. 初始化数据库

```bash
# 创建数据库
createdb userdb

# 运行迁移（如果使用 Alembic）
alembic upgrade head
```

### 5. 启动服务

```bash
# 开发模式
uvicorn main:app --reload --port 8001

# 生产模式
uvicorn main:app --host 0.0.0.0 --port 8001
```

服务运行在: http://localhost:8001

### 6. 查看 API 文档

- **Swagger UI**: http://localhost:8001/docs
- **ReDoc**: http://localhost:8001/redoc

## 📁 项目结构

```
user-service/
├── api/                  # API 路由层
│   ├── auth.py          # 认证相关 API
│   └── users.py         # 用户管理 API
├── core/                # 核心配置
│   ├── config.py        # 应用配置
│   └── database.py      # 数据库连接
├── models/              # 数据模型
│   └── user.py          # 用户模型
├── schemas/             # Pydantic Schema
│   └── user.py          # 用户 Schema
├── services/            # 业务逻辑
│   └── auth_service.py  # 认证服务
├── tests/               # 测试文件
├── main.py              # 应用入口
├── requirements.txt     # Python 依赖
└── .env.example         # 环境变量示例
```

## 📊 数据库设计

### Users 表

```sql
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    hashed_password VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

-- 索引
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_username ON users(username);
```

## 📝 API 端点

### 认证 API

#### POST /api/v1/auth/register
用户注册

**请求体**:
```json
{
  "email": "user@example.com",
  "username": "alice",
  "password": "securepassword"
}
```

**响应**:
```json
{
  "access_token": "eyJhbGciOiJIUzI1NiIs...",
  "refresh_token": "eyJhbGciOiJIUzI1NiIs...",
  "token_type": "bearer"
}
```

#### POST /api/v1/auth/login
用户登录

**请求体**:
```json
{
  "email": "user@example.com",
  "password": "securepassword"
}
```

**响应**: 同注册

#### POST /api/v1/auth/verify
验证 Token（供其他微服务调用）

**请求体**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIs..."
}
```

**响应**:
```json
{
  "valid": true,
  "user_id": "uuid",
  "email": "user@example.com",
  "username": "alice"
}
```

### 用户管理 API

#### GET /api/v1/users/me
获取当前用户信息

**Headers**: `Authorization: Bearer <token>`

**响应**:
```json
{
  "id": "uuid",
  "email": "user@example.com",
  "username": "alice",
  "is_active": true,
  "created_at": "2025-10-31T10:00:00",
  "updated_at": "2025-10-31T10:00:00"
}
```

#### PUT /api/v1/users/me
更新当前用户信息

**Headers**: `Authorization: Bearer <token>`

**请求体**:
```json
{
  "username": "new_username",
  "email": "newemail@example.com"
}
```

## 🔐 安全

### 密码安全
- 使用 Bcrypt 加密密码
- 密码最小长度: 6 位
- 存储时永不保存明文密码

### JWT Token
- **Access Token**: 30 分钟有效期（可配置）
- **Refresh Token**: 7 天有效期（可配置）
- 使用 HS256 算法签名
- Token 包含用户 ID 和过期时间

### 环境变量安全
- 密钥存储在环境变量中
- 生产环境必须更换默认密钥
- 使用 `.env` 文件管理配置（不提交到 Git）

## 🧪 测试

### 运行测试

```bash
# 运行所有测试
pytest

# 运行测试并查看覆盖率
pytest --cov=. --cov-report=html

# 运行特定测试
pytest tests/test_auth.py -v
```

### 测试用例

- 用户注册（正常/重复邮箱/重复用户名）
- 用户登录（正常/错误密码/不存在的用户）
- Token 验证（有效/无效/过期）
- 用户信息获取和更新

## 🐳 Docker

### 构建镜像

```bash
docker build -t user-service:latest .
```

### 运行容器

```bash
docker run -d \
  -p 8001:8001 \
  -e DATABASE_URL=postgresql://... \
  -e SECRET_KEY=your-secret-key \
  user-service:latest
```

## 📈 监控

### 健康检查

```bash
curl http://localhost:8001/health
```

**响应**:
```json
{
  "status": "healthy",
  "service": "user-service"
}
```

### 日志

日志输出到标准输出，可以通过 Docker 或日志收集工具查看：

```bash
# Docker
docker logs <container-id>

# 开发模式
# 控制台直接显示
```

## 🔧 配置

### 环境变量

| 变量 | 说明 | 默认值 |
|------|------|--------|
| `DATABASE_URL` | PostgreSQL 连接字符串 | `postgresql://postgres:postgres@localhost:5432/userdb` |
| `SECRET_KEY` | JWT 签名密钥 | `your-secret-key-change-in-production` |
| `ALGORITHM` | JWT 算法 | `HS256` |
| `ACCESS_TOKEN_EXPIRE_MINUTES` | Access Token 有效期（分钟） | `30` |
| `REFRESH_TOKEN_EXPIRE_DAYS` | Refresh Token 有效期（天） | `7` |
| `ALLOWED_ORIGINS` | CORS 允许的源 | `["http://localhost:3000", ...]` |

## 🔗 服务间通信

### 被调用接口

其他微服务（如 todo-service）通过以下接口验证用户身份：

```http
POST /api/v1/auth/verify
Content-Type: application/json

{
  "token": "eyJhbGciOiJIUzI1NiIs..."
}
```

### 示例（从 Java 调用）

```java
RestTemplate restTemplate = new RestTemplate();
String url = "http://user-service:8001/api/v1/auth/verify";

TokenVerifyRequest request = new TokenVerifyRequest(token);
TokenVerifyResponse response = restTemplate.postForObject(
    url, request, TokenVerifyResponse.class
);

if (response.isValid()) {
    String userId = response.getUserId();
    // 处理业务逻辑
}
```

## 📚 相关文档

- [FastAPI 文档](https://fastapi.tiangolo.com/)
- [SQLAlchemy 文档](https://docs.sqlalchemy.org/)
- [JWT 介绍](https://jwt.io/)
- [后端架构文档](../README.md)

---

**版本**: 1.0.0
**最后更新**: 2025-10-31
