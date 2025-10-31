# Todo Service - 任务管理微服务

基于 Java + Spring Boot 的任务管理微服务。

## 🎯 服务职责

- 任务的增删改查 (CRUD)
- 任务状态管理（完成/未完成）
- 优先级管理（高/中/低）
- 截止日期管理
- 调用 user-service 验证用户身份

## 🛠️ 技术栈

- **Java**: 17
- **Spring Boot**: 3.2.0 - 企业级应用框架
- **Spring Data JPA**: ORM 框架
- **MySQL**: 8.0 - 数据库
- **Lombok**: 简化代码
- **Maven**: 依赖管理和构建工具
- **Spring Boot Actuator**: 监控和健康检查

## 🚀 快速开始

### 前置条件

- Java 17+
- Maven 3.6+
- MySQL 8.0+

### 1. 安装依赖

```bash
# 使用 Maven
./mvnw clean install

# 或使用系统的 Maven
mvn clean install
```

### 2. 配置数据库

创建数据库：
```sql
CREATE DATABASE tododb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

配置连接（编辑 `src/main/resources/application.yml`）：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/tododb
    username: root
    password: your_password
```

### 3. 配置 User Service 地址

编辑 `application.yml`：
```yaml
user-service:
  url: http://localhost:8001
```

### 4. 启动服务

```bash
# 使用 Maven
./mvnw spring-boot:run

# 使用 JAR
./mvnw package
java -jar target/todo-service-1.0.0.jar

# 指定环境
java -jar target/todo-service-1.0.0.jar --spring.profiles.active=dev
```

服务运行在: http://localhost:8002

### 5. 健康检查

```bash
curl http://localhost:8002/actuator/health
```

## 📁 项目结构

```
todo-service/
├── src/
│   ├── main/
│   │   ├── java/com/todolist/todoservice/
│   │   │   ├── TodoServiceApplication.java  # 应用入口
│   │   │   ├── controller/      # 控制器层
│   │   │   │   └── TodoController.java
│   │   │   ├── service/         # 服务层
│   │   │   │   └── TodoService.java
│   │   │   ├── repository/      # 数据访问层
│   │   │   │   └── TodoRepository.java
│   │   │   ├── entity/          # 实体类
│   │   │   │   └── Todo.java
│   │   │   ├── dto/             # 数据传输对象
│   │   │   │   ├── TodoRequest.java
│   │   │   │   ├── TodoResponse.java
│   │   │   │   └── UserInfo.java
│   │   │   ├── client/          # 外部服务客户端
│   │   │   │   └── UserServiceClient.java
│   │   │   └── config/          # 配置类
│   │   │       └── AppConfig.java
│   │   └── resources/
│   │       ├── application.yml          # 主配置
│   │       ├── application-dev.yml      # 开发环境
│   │       └── application-prod.yml     # 生产环境
│   └── test/
│       └── java/com/todolist/todoservice/
├── pom.xml                      # Maven 配置
└── README.md                    # 本文件
```

## 📊 数据库设计

### Todos 表

```sql
CREATE TABLE todos (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    priority ENUM('HIGH', 'MEDIUM', 'LOW') NOT NULL DEFAULT 'MEDIUM',
    due_date DATETIME,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id),
    INDEX idx_completed (completed),
    INDEX idx_priority (priority)
);
```

## 📝 API 端点

所有请求都需要在 Header 中携带 JWT Token：
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIs...
```

### GET /api/v1/todos
获取任务列表

**Query 参数**:
- `completed` (可选): `true` 或 `false`

**响应**:
```json
[
  {
    "id": "uuid",
    "userId": "user-uuid",
    "title": "完成项目文档",
    "description": "编写技术设计文档",
    "completed": false,
    "priority": "HIGH",
    "dueDate": "2025-11-05T10:00:00",
    "createdAt": "2025-10-31T10:00:00",
    "updatedAt": "2025-10-31T10:00:00"
  }
]
```

### POST /api/v1/todos
创建任务

**请求体**:
```json
{
  "title": "新任务",
  "description": "任务描述",
  "priority": "MEDIUM",
  "dueDate": "2025-11-05T10:00:00"
}
```

### GET /api/v1/todos/{id}
获取单个任务

### PUT /api/v1/todos/{id}
更新任务

**请求体**: 同创建任务

### PATCH /api/v1/todos/{id}/toggle
切换任务完成状态

### DELETE /api/v1/todos/{id}
删除任务

## 🔐 认证机制

### 服务间通信流程

```java
// 1. 从请求头获取 Token
String token = authorization.substring(7); // 去掉 "Bearer "

// 2. 调用 user-service 验证 Token
UserInfo userInfo = userServiceClient.verifyToken(token);

// 3. 检查验证结果
if (!userInfo.getValid()) {
    throw new RuntimeException("Invalid token");
}

// 4. 使用用户信息处理业务逻辑
String userId = userInfo.getUserId();
```

### UserServiceClient

```java
@Component
public class UserServiceClient {
    private final RestTemplate restTemplate;

    public UserInfo verifyToken(String token) {
        String url = userServiceUrl + "/api/v1/auth/verify";
        // 调用 user-service 验证 Token
        return restTemplate.postForObject(url, request, UserInfo.class);
    }
}
```

## 🧪 测试

### 运行测试

```bash
# 运行所有测试
./mvnw test

# 运行特定测试类
./mvnw test -Dtest=TodoServiceTest

# 查看测试覆盖率
./mvnw verify
```

### 集成测试示例

```java
@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetTodos() throws Exception {
        mockMvc.perform(get("/api/v1/todos")
                .header("Authorization", "Bearer " + validToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
```

## 🐳 Docker

### Dockerfile

```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/todo-service-1.0.0.jar app.jar
EXPOSE 8002
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 构建和运行

```bash
# 构建镜像
docker build -t todo-service:latest .

# 运行容器
docker run -d \
  -p 8002:8002 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host:3306/tododb \
  -e USER_SERVICE_URL=http://user-service:8001 \
  todo-service:latest
```

## 📈 监控

### Actuator 端点

- **健康检查**: `GET /actuator/health`
- **应用信息**: `GET /actuator/info`

配置（`application.yml`）：
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
  endpoint:
    health:
      show-details: always
```

### 日志

日志级别配置：
```yaml
logging:
  level:
    com.todolist.todoservice: DEBUG
    org.springframework.web: INFO
```

查看日志：
```bash
# 开发模式（控制台）
./mvnw spring-boot:run

# 生产模式（文件）
tail -f logs/todo-service.log
```

## 🔧 配置

### 环境变量

| 变量 | 说明 | 默认值 |
|------|------|--------|
| `SPRING_DATASOURCE_URL` | MySQL 连接字符串 | `jdbc:mysql://localhost:3306/tododb` |
| `SPRING_DATASOURCE_USERNAME` | 数据库用户名 | `root` |
| `SPRING_DATASOURCE_PASSWORD` | 数据库密码 | `password` |
| `USER_SERVICE_URL` | User Service 地址 | `http://localhost:8001` |
| `SERVER_PORT` | 服务端口 | `8002` |

### Profile 配置

- **dev**: 开发环境（`application-dev.yml`）
- **prod**: 生产环境（`application-prod.yml`）

切换环境：
```bash
# 启动时指定
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod

# 或使用环境变量
export SPRING_PROFILES_ACTIVE=prod
./mvnw spring-boot:run
```

## 🚨 错误处理

### 常见错误

| 状态码 | 说明 | 原因 |
|--------|------|------|
| 401 | Unauthorized | Token 无效或过期 |
| 404 | Not Found | 任务不存在或不属于当前用户 |
| 400 | Bad Request | 请求参数错误 |
| 500 | Internal Server Error | 服务器内部错误 |

## 📚 相关文档

- [Spring Boot 文档](https://spring.io/projects/spring-boot)
- [Spring Data JPA 文档](https://spring.io/projects/spring-data-jpa)
- [MySQL 文档](https://dev.mysql.com/doc/)
- [后端架构文档](../README.md)

## 🔗 依赖服务

- **user-service**: http://localhost:8001
  - 用于验证用户 Token
  - 提供用户信息

## 🛠️ 开发工具推荐

- **IDE**: IntelliJ IDEA / Eclipse
- **数据库客户端**: MySQL Workbench / DBeaver
- **API 测试**: Postman / Insomnia
- **代码格式化**: Google Java Format

---

**版本**: 1.0.0
**最后更新**: 2025-10-31
