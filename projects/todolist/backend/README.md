# TodoList 后端 - 微服务架构

基于微服务架构设计的 TodoList 后端系统，采用多语言技术栈。

## 🏗️ 架构概览

```mermaid
graph TB
    Client[前端应用<br/>App/Admin]

    subgraph Backend[后端微服务]
        UserService[user-service<br/>Python/FastAPI<br/>:8001]
        TodoService[todo-service<br/>Java/Spring Boot<br/>:8002]
    end

    UserDB[(用户数据库<br/>PostgreSQL)]
    TodoDB[(任务数据库<br/>MySQL)]

    Client -->|认证/用户管理| UserService
    Client -->|任务管理| TodoService
    TodoService -.->|验证Token| UserService

    UserService --> UserDB
    TodoService --> TodoDB

    style UserService fill:#3776AB,color:#fff
    style TodoService fill:#007396,color:#fff
    style Client fill:#61DAFB,color:#000
```

## 📦 服务列表

| 服务 | 技术栈 | 端口 | 职责 | 状态 |
|------|--------|------|------|------|
| **user-service** | Python + FastAPI | 8001 | 用户认证和授权 | ✅ |
| **todo-service** | Java + Spring Boot | 8002 | 任务管理 | ✅ |

---

## 🔧 user-service (Python/FastAPI)

### 职责
- 用户注册和登录
- JWT Token 签发和验证
- 用户信息管理
- 提供统一的认证中心

### 技术栈
- Python 3.11
- FastAPI 0.109.0
- SQLAlchemy 2.0
- PostgreSQL
- JWT (python-jose)

### 快速开始
```bash
cd user-service
python -m venv venv
source venv/bin/activate  # Windows: venv\Scripts\activate
pip install -r requirements.txt
uvicorn main:app --reload --port 8001
```

### API 端点
- `POST /api/v1/auth/register` - 用户注册
- `POST /api/v1/auth/login` - 用户登录
- `POST /api/v1/auth/verify` - 验证 Token
- `GET /api/v1/users/me` - 获取当前用户信息
- `PUT /api/v1/users/me` - 更新用户信息

📖 [详细文档](./user-service/README.md)

---

## ☕ todo-service (Java/Spring Boot)

### 职责
- 任务的增删改查
- 任务状态管理
- 优先级和截止日期管理
- 调用 user-service 验证用户身份

### 技术栈
- Java 17
- Spring Boot 3.2
- Spring Data JPA
- MySQL 8.0
- RestTemplate/Feign (服务间通信)

### 快速开始
```bash
cd todo-service
./mvnw spring-boot:run
# 或使用 Gradle
./gradlew bootRun
```

### API 端点
- `GET /api/v1/todos` - 获取任务列表
- `POST /api/v1/todos` - 创建任务
- `GET /api/v1/todos/{id}` - 获取单个任务
- `PUT /api/v1/todos/{id}` - 更新任务
- `DELETE /api/v1/todos/{id}` - 删除任务
- `PATCH /api/v1/todos/{id}/toggle` - 切换完成状态

📖 [详细文档](./todo-service/README.md)

---

## 🔐 服务间通信

### 认证流程

```mermaid
sequenceDiagram
    participant Client as 前端
    participant TodoSvc as todo-service
    participant UserSvc as user-service
    participant DB as 数据库

    Client->>UserSvc: 1. POST /auth/login
    UserSvc->>DB: 验证用户
    UserSvc-->>Client: 2. 返回 JWT Token

    Client->>TodoSvc: 3. GET /todos (带 Token)
    TodoSvc->>UserSvc: 4. POST /auth/verify (验证 Token)
    UserSvc-->>TodoSvc: 5. 返回用户信息
    TodoSvc->>DB: 6. 查询任务
    TodoSvc-->>Client: 7. 返回任务列表
```

### Token 验证机制
1. 前端从 **user-service** 获取 JWT Token
2. 前端调用 **todo-service** 时在 Header 中携带 Token
3. **todo-service** 调用 **user-service** 的 `/auth/verify` 验证 Token
4. 验证通过后，**todo-service** 处理业务逻辑

---

## 🚀 本地开发

### 前置条件
- Python 3.11+
- Java 17+
- PostgreSQL 14+
- MySQL 8.0+
- Docker (可选)

### 启动所有服务

#### 方式 1: 手动启动
```bash
# Terminal 1 - 启动 user-service
cd user-service
python -m venv venv && source venv/bin/activate
pip install -r requirements.txt
uvicorn main:app --reload --port 8001

# Terminal 2 - 启动 todo-service
cd todo-service
./mvnw spring-boot:run
```

#### 方式 2: Docker Compose (推荐)
```bash
docker-compose up -d
```

### 健康检查
```bash
# user-service
curl http://localhost:8001/health

# todo-service
curl http://localhost:8002/actuator/health
```

---

## 📊 数据库设计

### user-service (PostgreSQL)
```sql
-- users 表
CREATE TABLE users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    hashed_password VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);
```

### todo-service (MySQL)
```sql
-- todos 表
CREATE TABLE todos (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    completed BOOLEAN DEFAULT FALSE,
    priority ENUM('high', 'medium', 'low') DEFAULT 'medium',
    due_date TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id),
    INDEX idx_completed (completed)
);
```

---

## 🐳 Docker 部署

### 构建镜像
```bash
# user-service
cd user-service
docker build -t todolist-user-service:latest .

# todo-service
cd todo-service
docker build -t todolist-todo-service:latest .
```

### 使用 Docker Compose
```bash
docker-compose up -d
```

---

## 🧪 测试

### user-service
```bash
cd user-service
pytest tests/ -v --cov=.
```

### todo-service
```bash
cd todo-service
./mvnw test
```

---

## 📈 监控和日志

### 日志收集
- user-service: 输出到 `logs/user-service.log`
- todo-service: 输出到 `logs/todo-service.log`

### 健康监控
- user-service: `GET http://localhost:8001/health`
- todo-service: `GET http://localhost:8002/actuator/health`

### 指标收集 (可选)
- Prometheus + Grafana
- ELK Stack (Elasticsearch + Logstash + Kibana)

---

## 🔧 配置管理

### 环境变量

**user-service (.env)**
```env
DATABASE_URL=postgresql://user:password@localhost:5432/userdb
SECRET_KEY=your-secret-key
ALGORITHM=HS256
ACCESS_TOKEN_EXPIRE_MINUTES=30
```

**todo-service (application.yml)**
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/tododb
    username: root
    password: password

user-service:
  url: http://localhost:8001
```

---

## 📚 微服务架构优势

### ✅ 技术栈独立
- 每个服务可以选择最适合的技术栈
- user-service 用 Python（快速开发，丰富的认证库）
- todo-service 用 Java（企业级稳定性，强类型安全）

### ✅ 独立部署和扩展
- 服务可以独立部署和更新
- 根据负载独立扩展（如 todo-service 访问量大，可以多部署几个实例）

### ✅ 故障隔离
- 单个服务故障不会影响整个系统
- 易于定位和修复问题

### ✅ 团队协作
- 不同团队可以负责不同的服务
- Python 团队负责 user-service
- Java 团队负责 todo-service

---

## 🗺️ 未来扩展

可以继续添加更多微服务：

- **notification-service** (Node.js) - 通知推送
- **analytics-service** (Python) - 数据分析
- **file-service** (Go) - 文件存储
- **api-gateway** (Nginx/Kong) - API 网关

---

## 📖 相关文档

- [user-service 详细文档](./user-service/README.md)
- [todo-service 详细文档](./todo-service/README.md)
- [API 文档](../docs/API-doc/)
- [架构设计](../docs/Tech-design/)

---

**最后更新**: 2025-10-31
