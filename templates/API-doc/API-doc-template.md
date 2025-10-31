# API 接口文档

> **模板版本**: v1.0.0 | **最后更新**: 2025-10-31

## 1. 文档信息
- **项目名称**: [项目名称]
- **API 版本**: v1.0
- **文档版本**: v1.0
- **创建日期**: [YYYY-MM-DD]
- **维护人**: [姓名]
- **Base URL**: `https://api.example.com/v1`

---

## 2. 接口概览

### 2.1 认证方式
- **认证类型**: Bearer Token / API Key / OAuth2
- **Token 获取**: [说明如何获取 Token]
- **Token 使用**: 在 Header 中添加 `Authorization: Bearer {token}`
- **Token 过期**: [过期时间和刷新机制]

### 2.2 请求规范
- **协议**: HTTPS
- **Content-Type**: `application/json`
- **字符编码**: UTF-8
- **时间格式**: ISO 8601 (YYYY-MM-DDTHH:mm:ssZ)

### 2.3 响应格式
所有接口统一返回格式：
```json
{
  "code": 0,
  "message": "success",
  "data": {},
  "timestamp": "2025-10-31T10:00:00Z"
}
```

### 2.4 状态码说明
| HTTP 状态码 | 业务码 | 说明 |
|------------|-------|------|
| 200 | 0 | 成功 |
| 400 | 40001 | 参数错误 |
| 401 | 40101 | 未授权 |
| 403 | 40301 | 无权限 |
| 404 | 40401 | 资源不存在 |
| 500 | 50001 | 服务器错误 |

---

## 3. 接口列表

### 3.1 认证接口
| 接口 | 方法 | 路径 | 描述 |
|------|------|------|------|
| 用户登录 | POST | `/auth/login` | 用户登录获取 Token |
| 刷新Token | POST | `/auth/refresh` | 刷新过期 Token |
| 用户登出 | POST | `/auth/logout` | 用户登出 |

### 3.2 用户接口
| 接口 | 方法 | 路径 | 描述 |
|------|------|------|------|
| 获取用户信息 | GET | `/users/:id` | 获取指定用户信息 |
| 更新用户信息 | PUT | `/users/:id` | 更新用户信息 |
| 获取用户列表 | GET | `/users` | 获取用户列表 |

### 3.3 [业务模块]接口
| 接口 | 方法 | 路径 | 描述 |
|------|------|------|------|
| [接口名] | [方法] | [路径] | [描述] |

---

## 4. 接口详细说明

### 4.1 用户登录

#### 基本信息
- **接口路径**: `POST /auth/login`
- **接口描述**: 用户通过用户名和密码登录，获取访问令牌
- **是否认证**: 否

#### 请求参数
**Headers**:
```
Content-Type: application/json
```

**Body** (application/json):
| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| username | string | 是 | 用户名或邮箱 | user@example.com |
| password | string | 是 | 密码 | Password123 |

**请求示例**:
```json
{
  "username": "user@example.com",
  "password": "Password123"
}
```

#### 响应结果
**成功响应** (200 OK):
```json
{
  "code": 0,
  "message": "登录成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "refresh_token_string",
    "expiresIn": 3600,
    "user": {
      "id": "123",
      "username": "user@example.com",
      "nickname": "用户昵称"
    }
  },
  "timestamp": "2025-10-31T10:00:00Z"
}
```

**错误响应** (400 Bad Request):
```json
{
  "code": 40001,
  "message": "用户名或密码错误",
  "data": null,
  "timestamp": "2025-10-31T10:00:00Z"
}
```

#### 错误码
| 错误码 | 说明 | 处理建议 |
|--------|------|---------|
| 40001 | 参数错误 | 检查请求参数 |
| 40002 | 用户名或密码错误 | 提示用户重新输入 |
| 40003 | 账号已被锁定 | 提示联系管理员 |

---

### 4.2 获取用户信息

#### 基本信息
- **接口路径**: `GET /users/:id`
- **接口描述**: 获取指定用户的详细信息
- **是否认证**: 是

#### 请求参数
**Headers**:
```
Authorization: Bearer {token}
Content-Type: application/json
```

**Path Parameters**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | string | 是 | 用户 ID |

**Query Parameters**:
| 参数名 | 类型 | 必填 | 说明 | 默认值 |
|--------|------|------|------|--------|
| fields | string | 否 | 返回字段（逗号分隔） | 全部 |

**请求示例**:
```bash
GET /users/123?fields=id,username,email
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

#### 响应结果
**成功响应** (200 OK):
```json
{
  "code": 0,
  "message": "success",
  "data": {
    "id": "123",
    "username": "user@example.com",
    "email": "user@example.com",
    "nickname": "用户昵称",
    "avatar": "https://example.com/avatar.jpg",
    "createdAt": "2025-01-01T00:00:00Z",
    "updatedAt": "2025-10-31T10:00:00Z"
  },
  "timestamp": "2025-10-31T10:00:00Z"
}
```

**错误响应** (404 Not Found):
```json
{
  "code": 40401,
  "message": "用户不存在",
  "data": null,
  "timestamp": "2025-10-31T10:00:00Z"
}
```

---

### 4.3 [其他接口]

#### 基本信息
- **接口路径**: `[方法] [路径]`
- **接口描述**: [描述]
- **是否认证**: [是/否]

[按照上述格式继续补充其他接口]

---

## 5. 数据模型

### 5.1 User（用户）
| 字段 | 类型 | 说明 |
|------|------|------|
| id | string | 用户 ID |
| username | string | 用户名 |
| email | string | 邮箱 |
| nickname | string | 昵称 |
| avatar | string | 头像 URL |
| status | string | 状态：active/inactive/banned |
| createdAt | string | 创建时间 (ISO 8601) |
| updatedAt | string | 更新时间 (ISO 8601) |

### 5.2 [其他数据模型]
[补充其他数据模型定义]

---

## 6. 通用说明

### 6.1 分页
分页请求统一使用以下参数：
| 参数 | 类型 | 必填 | 说明 | 默认值 |
|------|------|------|------|--------|
| page | integer | 否 | 页码（从 1 开始） | 1 |
| pageSize | integer | 否 | 每页数量 | 20 |

分页响应格式：
```json
{
  "code": 0,
  "message": "success",
  "data": {
    "list": [],
    "pagination": {
      "page": 1,
      "pageSize": 20,
      "total": 100,
      "totalPages": 5
    }
  }
}
```

### 6.2 排序
排序参数：
- `sortBy`: 排序字段
- `sortOrder`: 排序方向（asc/desc）

示例: `?sortBy=createdAt&sortOrder=desc`

### 6.3 过滤
支持通用过滤参数：
- `filter[字段名]=值`

示例: `?filter[status]=active&filter[role]=admin`

### 6.4 限流
- 每个 IP 限制: 1000 次/小时
- 每个用户限制: 5000 次/小时
- 超出限制返回 429 Too Many Requests

---

## 7. Webhook（可选）

### 7.1 Webhook 配置
[说明如何配置 Webhook]

### 7.2 Webhook 事件
| 事件 | 触发时机 | Payload |
|------|---------|---------|
| [事件名] | [时机] | [数据结构] |

---

## 8. SDK 示例

### 8.1 JavaScript/TypeScript
```javascript
// 安装
npm install @example/api-client

// 使用
import { ApiClient } from '@example/api-client';

const client = new ApiClient({
  baseURL: 'https://api.example.com/v1',
  token: 'your_token'
});

// 登录
const { data } = await client.auth.login({
  username: 'user@example.com',
  password: 'Password123'
});

// 获取用户信息
const user = await client.users.get('123');
```

### 8.2 Python
```python
# 安装
pip install example-api-client

# 使用
from example_api import ApiClient

client = ApiClient(
    base_url='https://api.example.com/v1',
    token='your_token'
)

# 登录
response = client.auth.login(
    username='user@example.com',
    password='Password123'
)

# 获取用户信息
user = client.users.get('123')
```

---

## 9. 变更日志

### v1.1.0 (计划中)
- [ ] 新增批量操作接口
- [ ] 支持 GraphQL

### v1.0.0 (当前版本)
- [x] 基础 CRUD 接口
- [x] 用户认证授权
- [x] 分页、排序、过滤

---

## 10. 附录

### 10.1 环境地址
| 环境 | Base URL |
|------|----------|
| 开发环境 | https://dev-api.example.com/v1 |
| 测试环境 | https://test-api.example.com/v1 |
| 生产环境 | https://api.example.com/v1 |

### 10.2 联系方式
- 技术支持: support@example.com
- API 问题反馈: [GitHub Issues]

### 10.3 相关文档
- 技术设计: [链接]
- 快速开始: [链接]
- FAQ: [链接]
