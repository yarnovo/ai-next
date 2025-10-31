# TodoList API 接口文档

> **模板版本**: v1.0.0（基于 templates/API-doc）

---

## 1. 文档信息

| 项目 | 内容 |
|------|------|
| **项目名称** | TodoList API |
| **文档版本** | v1.0 |
| **API 版本** | v1 |
| **创建日期** | 2025-10-23 |
| **最后更新** | 2025-10-27 |
| **负责人** | Eva (Backend Lead) |
| **评审状态** | ✅ 已批准（2025-10-28） |
| **关联文档** | [Tech-design-todolist.md](../Tech-design/Tech-design-todolist.md) |

---

## 2. API 概述

### 2.1 服务列表

| 服务 | 基础 URL | 端口 | 说明 |
|------|----------|------|------|
| **user-service** | http://localhost:8001 | 8001 | 用户认证服务 |
| **todo-service** | http://localhost:8002 | 8002 | 任务管理服务 |

### 2.2 认证方式

**JWT Bearer Token**:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Token 获取**:
通过 `/api/v1/auth/login` 或 `/api/v1/auth/register` 获取

**Token 有效期**:
- Access Token: 30 分钟
- Refresh Token: 7 天

### 2.3 通用响应格式

**成功响应** (200/201/204):
```json
{
  "id": "uuid",
  "title": "示例数据",
  ...
}
```

**错误响应** (4xx/5xx):
```json
{
  "detail": "Error message"
}
```

### 2.4 HTTP 状态码

| 状态码 | 说明 | 使用场景 |
|--------|------|----------|
| 200 | OK | 成功获取资源 |
| 201 | Created | 成功创建资源 |
| 204 | No Content | 成功删除资源 |
| 400 | Bad Request | 请求参数错误 |
| 401 | Unauthorized | 未授权或 Token 无效 |
| 403 | Forbidden | 无权限访问 |
| 404 | Not Found | 资源不存在 |
| 500 | Internal Server Error | 服务器内部错误 |

---

## 3. user-service API (认证服务)

基础 URL: `http://localhost:8001/api/v1`

### 3.1 用户注册

**POST** `/auth/register`

用户注册并返回 JWT Token。

**请求头**:
```
Content-Type: application/json
```

**请求体**:
```json
{
  "email": "user@example.com",
  "username": "alice",
  "password": "securepassword123"
}
```

**字段说明**:

| 字段 | 类型 | 必需 | 说明 |
|------|------|------|------|
| email | string | ✅ | 邮箱地址，唯一 |
| username | string | ✅ | 用户名，3-50字符，唯一 |
| password | string | ✅ | 密码，最少6字符 |

**成功响应** (201):
```json
{
  "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "token_type": "bearer"
}
```

**错误响应** (400):
```json
{
  "detail": "Email already registered"
}
```

**示例 (cURL)**:
```bash
curl -X POST http://localhost:8001/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "alice@example.com",
    "username": "alice",
    "password": "password123"
  }'
```

---

### 3.2 用户登录

**POST** `/auth/login`

使用邮箱和密码登录，返回 JWT Token。

**请求体**:
```json
{
  "email": "user@example.com",
  "password": "securepassword123"
}
```

**成功响应** (200):
```json
{
  "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "token_type": "bearer"
}
```

**错误响应** (401):
```json
{
  "detail": "Incorrect email or password"
}
```

---

### 3.3 验证 Token (服务间调用)

**POST** `/auth/verify`

验证 JWT Token 的有效性，供其他微服务调用。

**请求体**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**成功响应** (200):
```json
{
  "valid": true,
  "user_id": "550e8400-e29b-41d4-a716-446655440000",
  "email": "user@example.com",
  "username": "alice"
}
```

**Token 无效** (200):
```json
{
  "valid": false,
  "user_id": null,
  "email": null,
  "username": null
}
```

---

### 3.4 获取当前用户信息

**GET** `/users/me`

获取当前登录用户的信息。

**请求头**:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**成功响应** (200):
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "email": "user@example.com",
  "username": "alice",
  "is_active": true,
  "created_at": "2025-10-20T10:00:00Z",
  "updated_at": "2025-10-20T10:00:00Z"
}
```

**错误响应** (401):
```json
{
  "detail": "Could not validate credentials"
}
```

---

### 3.5 更新用户信息

**PUT** `/users/me`

更新当前用户的信息。

**请求头**:
```
Authorization: Bearer {token}
Content-Type: application/json
```

**请求体**:
```json
{
  "username": "new_username",
  "email": "newemail@example.com"
}
```

**成功响应** (200):
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "email": "newemail@example.com",
  "username": "new_username",
  "is_active": true,
  "created_at": "2025-10-20T10:00:00Z",
  "updated_at": "2025-10-31T14:30:00Z"
}
```

---

## 4. todo-service API (任务服务)

基础 URL: `http://localhost:8002/api/v1`

### 4.1 获取任务列表

**GET** `/todos`

获取当前用户的任务列表。

**请求头**:
```
Authorization: Bearer {token}
```

**Query 参数**:

| 参数 | 类型 | 必需 | 说明 |
|------|------|------|------|
| completed | boolean | ❌ | 过滤完成状态 (true/false) |

**成功响应** (200):
```json
[
  {
    "id": "task-uuid-1",
    "user_id": "user-uuid",
    "title": "完成项目文档",
    "description": "编写技术设计文档",
    "completed": false,
    "priority": "HIGH",
    "due_date": "2025-11-05T10:00:00Z",
    "created_at": "2025-10-31T10:00:00Z",
    "updated_at": "2025-10-31T10:00:00Z"
  },
  {
    "id": "task-uuid-2",
    "user_id": "user-uuid",
    "title": "Code Review",
    "description": "审查前端代码",
    "completed": true,
    "priority": "MEDIUM",
    "due_date": "2025-11-01T16:00:00Z",
    "created_at": "2025-10-30T08:00:00Z",
    "updated_at": "2025-10-31T09:30:00Z"
  }
]
```

**示例 (cURL)**:
```bash
# 获取所有任务
curl -X GET http://localhost:8002/api/v1/todos \
  -H "Authorization: Bearer {token}"

# 只获取未完成的任务
curl -X GET "http://localhost:8002/api/v1/todos?completed=false" \
  -H "Authorization: Bearer {token}"
```

---

### 4.2 创建任务

**POST** `/todos`

创建新的任务。

**请求头**:
```
Authorization: Bearer {token}
Content-Type: application/json
```

**请求体**:
```json
{
  "title": "准备技术分享",
  "description": "准备微服务架构分享的 PPT",
  "priority": "MEDIUM",
  "due_date": "2025-11-10T14:00:00Z"
}
```

**字段说明**:

| 字段 | 类型 | 必需 | 说明 |
|------|------|------|------|
| title | string | ✅ | 任务标题，1-100字符 |
| description | string | ❌ | 任务描述，最多500字符 |
| priority | string | ❌ | 优先级：HIGH/MEDIUM/LOW，默认 MEDIUM |
| due_date | string | ❌ | 截止日期，ISO 8601 格式 |

**成功响应** (201):
```json
{
  "id": "new-task-uuid",
  "user_id": "user-uuid",
  "title": "准备技术分享",
  "description": "准备微服务架构分享的 PPT",
  "completed": false,
  "priority": "MEDIUM",
  "due_date": "2025-11-10T14:00:00Z",
  "created_at": "2025-10-31T15:00:00Z",
  "updated_at": "2025-10-31T15:00:00Z"
}
```

**错误响应** (400):
```json
{
  "detail": [
    {
      "loc": ["body", "title"],
      "msg": "field required",
      "type": "value_error.missing"
    }
  ]
}
```

---

### 4.3 获取单个任务

**GET** `/todos/{id}`

根据 ID 获取任务详情。

**路径参数**:
- `id`: 任务 UUID

**请求头**:
```
Authorization: Bearer {token}
```

**成功响应** (200):
```json
{
  "id": "task-uuid",
  "user_id": "user-uuid",
  "title": "完成项目文档",
  "description": "编写技术设计文档",
  "completed": false,
  "priority": "HIGH",
  "due_date": "2025-11-05T10:00:00Z",
  "created_at": "2025-10-31T10:00:00Z",
  "updated_at": "2025-10-31T10:00:00Z"
}
```

**错误响应** (404):
```json
{
  "detail": "Todo not found"
}
```

---

### 4.4 更新任务

**PUT** `/todos/{id}`

更新任务信息。

**路径参数**:
- `id`: 任务 UUID

**请求头**:
```
Authorization: Bearer {token}
Content-Type: application/json
```

**请求体**:
```json
{
  "title": "完成项目文档（更新）",
  "description": "编写技术设计文档和 API 文档",
  "priority": "HIGH",
  "due_date": "2025-11-06T10:00:00Z"
}
```

**成功响应** (200):
```json
{
  "id": "task-uuid",
  "user_id": "user-uuid",
  "title": "完成项目文档（更新）",
  "description": "编写技术设计文档和 API 文档",
  "completed": false,
  "priority": "HIGH",
  "due_date": "2025-11-06T10:00:00Z",
  "created_at": "2025-10-31T10:00:00Z",
  "updated_at": "2025-10-31T15:30:00Z"
}
```

---

### 4.5 切换任务完成状态

**PATCH** `/todos/{id}/toggle`

切换任务的完成状态（完成 ↔ 未完成）。

**路径参数**:
- `id`: 任务 UUID

**请求头**:
```
Authorization: Bearer {token}
```

**成功响应** (200):
```json
{
  "id": "task-uuid",
  "user_id": "user-uuid",
  "title": "完成项目文档",
  "description": "编写技术设计文档",
  "completed": true,
  "priority": "HIGH",
  "due_date": "2025-11-05T10:00:00Z",
  "created_at": "2025-10-31T10:00:00Z",
  "updated_at": "2025-10-31T16:00:00Z"
}
```

---

### 4.6 删除任务

**DELETE** `/todos/{id}`

删除指定的任务。

**路径参数**:
- `id`: 任务 UUID

**请求头**:
```
Authorization: Bearer {token}
```

**成功响应** (204):
```
No Content
```

**错误响应** (404):
```json
{
  "detail": "Todo not found"
}
```

---

## 5. 错误码说明

### 5.1 通用错误码

| HTTP 状态码 | 错误信息 | 说明 | 解决方案 |
|------------|---------|------|----------|
| 400 | Bad Request | 请求参数错误 | 检查请求参数格式 |
| 401 | Unauthorized | Token 无效或过期 | 重新登录获取 Token |
| 403 | Forbidden | 无权限访问 | 确认用户权限 |
| 404 | Not Found | 资源不存在 | 确认资源 ID 是否正确 |
| 500 | Internal Server Error | 服务器错误 | 联系技术支持 |

### 5.2 业务错误码

#### user-service

| 错误信息 | 说明 | HTTP 状态 |
|---------|------|----------|
| Email already registered | 邮箱已被注册 | 400 |
| Username already taken | 用户名已被占用 | 400 |
| Incorrect email or password | 邮箱或密码错误 | 401 |
| User account is inactive | 账户已被禁用 | 403 |
| Could not validate credentials | Token 验证失败 | 401 |

#### todo-service

| 错误信息 | 说明 | HTTP 状态 |
|---------|------|----------|
| Todo not found | 任务不存在 | 404 |
| Missing or invalid authorization header | Token 缺失或格式错误 | 401 |
| Invalid token | Token 无效 | 401 |

---

## 6. 数据模型

### 6.1 User (用户)

```typescript
interface User {
  id: string;                    // UUID
  email: string;                 // 邮箱
  username: string;              // 用户名
  is_active: boolean;            // 账户状态
  created_at: string;            // 创建时间 (ISO 8601)
  updated_at: string;            // 更新时间 (ISO 8601)
}
```

### 6.2 Todo (任务)

```typescript
interface Todo {
  id: string;                    // UUID
  user_id: string;               // 所属用户 ID
  title: string;                 // 任务标题
  description?: string;          // 任务描述（可选）
  completed: boolean;            // 完成状态
  priority: 'HIGH' | 'MEDIUM' | 'LOW';  // 优先级
  due_date?: string;             // 截止日期（可选，ISO 8601）
  created_at: string;            // 创建时间 (ISO 8601)
  updated_at: string;            // 更新时间 (ISO 8601)
}
```

### 6.3 Token (认证令牌)

```typescript
interface Token {
  access_token: string;          // 访问令牌
  refresh_token: string;         // 刷新令牌
  token_type: string;            // 类型，固定为 "bearer"
}
```

---

## 7. SDK 示例

### 7.1 JavaScript/TypeScript

```typescript
import axios from 'axios';

const API_URL = 'http://localhost:8001/api/v1';

class UserAPI {
  private token: string | null = null;

  // 登录
  async login(email: string, password: string) {
    const response = await axios.post(`${API_URL}/auth/login`, {
      email,
      password
    });
    this.token = response.data.access_token;
    return response.data;
  }

  // 获取当前用户
  async getCurrentUser() {
    const response = await axios.get(`${API_URL}/users/me`, {
      headers: {
        'Authorization': `Bearer ${this.token}`
      }
    });
    return response.data;
  }
}

// 使用示例
const api = new UserAPI();
await api.login('user@example.com', 'password123');
const user = await api.getCurrentUser();
console.log(user);
```

### 7.2 Python

```python
import requests

API_URL = "http://localhost:8001/api/v1"

class UserAPI:
    def __init__(self):
        self.token = None

    def login(self, email: str, password: str):
        response = requests.post(f"{API_URL}/auth/login", json={
            "email": email,
            "password": password
        })
        data = response.json()
        self.token = data["access_token"]
        return data

    def get_current_user(self):
        response = requests.get(
            f"{API_URL}/users/me",
            headers={"Authorization": f"Bearer {self.token}"}
        )
        return response.json()

# 使用示例
api = UserAPI()
api.login("user@example.com", "password123")
user = api.get_current_user()
print(user)
```

---

## 8. 测试环境

### 8.1 环境信息

| 环境 | user-service | todo-service | 说明 |
|------|--------------|--------------|------|
| **本地开发** | http://localhost:8001 | http://localhost:8002 | 开发和调试 |
| **测试环境** | http://test-api.todolist.com/user | http://test-api.todolist.com/todo | 集成测试 |
| **生产环境** | https://api.todolist.com/user | https://api.todolist.com/todo | 正式环境 |

### 8.2 测试账号

**仅测试环境可用**:
```
邮箱: test@example.com
密码: testpass123
```

---

## 9. 变更历史

| 版本 | 日期 | 变更内容 | 负责人 |
|------|------|----------|--------|
| v1.0 | 2025-10-27 | 初始版本，完整 API 文档 | Eva |

---

## 10. 附录

### 10.1 Swagger/OpenAPI 文档

**user-service**:
- Swagger UI: http://localhost:8001/docs
- OpenAPI JSON: http://localhost:8001/openapi.json

**todo-service**:
- Swagger UI: http://localhost:8002/swagger-ui.html
- OpenAPI JSON: http://localhost:8002/v3/api-docs

### 10.2 Postman Collection

完整的 Postman Collection 可从以下位置获取：
- 文件位置: `docs/API-doc/TodoList.postman_collection.json`
- 包含所有 API 端点的示例请求

### 10.3 联系方式

**API 问题**:
- 负责人: Eva (Backend Lead)
- Email: eva@example.com

**技术支持**:
- Slack: #todolist-api
- 工单系统: https://jira.example.com/todolist

---

**文档编写**: Eva (Backend Lead)
**评审人员**: Bob, David, Frank
**批准日期**: 2025-10-28
