# TodoList BDD 行为驱动开发文档

> **模板版本**: v1.0.0（基于 templates/BDD）

## 1. 文档信息

| 项目 | 内容 |
|------|------|
| **项目名称** | TodoList |
| **文档版本** | v1.0 |
| **创建日期** | 2025-10-25 |
| **负责人** | Frank (QA) + Alice (PM) |
| **关联文档** | [PRD-todolist.md](../PRD/PRD-todolist.md) |

---

## 2. Feature: 用户认证

### Scenario: 用户注册成功

```gherkin
Given 用户打开应用
And 用户点击"注册"按钮
When 用户输入邮箱 "test@example.com"
And 用户输入用户名 "alice"
And 用户输入密码 "password123"
And 用户点击"提交"按钮
Then 系统应该创建新用户
And 系统应该返回 JWT Token
And 系统应该跳转到首页
```

### Scenario: 邮箱已被注册

```gherkin
Given 数据库中已存在邮箱 "test@example.com"
When 用户尝试使用相同邮箱注册
Then 系统应该显示错误消息 "Email already registered"
And 系统应该停留在注册页面
```

### Scenario: 用户登录成功

```gherkin
Given 用户已注册账号 "test@example.com"
When 用户输入正确的邮箱和密码
And 用户点击"登录"按钮
Then 系统应该验证凭据
And 系统应该返回 JWT Token
And 系统应该跳转到任务列表页
```

---

## 3. Feature: 任务管理

### Scenario: 创建新任务

```gherkin
Given 用户已登录
And 用户在任务列表页
When 用户点击"添加任务"按钮
And 用户输入任务标题 "完成项目文档"
And 用户选择优先级 "高"
And 用户选择截止日期 "2025-11-05"
And 用户点击"保存"按钮
Then 系统应该创建新任务
And 任务应该出现在列表顶部
And 任务状态应该是"未完成"
```

### Scenario: 标记任务为完成

```gherkin
Given 用户已登录
And 存在未完成任务 "Code Review"
When 用户点击任务的复选框
Then 系统应该更新任务状态为"已完成"
And 任务应该显示删除线
And 任务应该从"待办"列表移到"已完成"列表
```

### Scenario: 删除任务

```gherkin
Given 用户已登录
And 存在任务 "旧任务"
When 用户长按任务
And 用户点击"删除"按钮
And 用户确认删除
Then 系统应该删除任务
And 任务应该从列表中消失
```

---

## 4. Feature: 任务筛选

### Scenario: 查看所有任务

```gherkin
Given 用户已登录
And 存在3个已完成任务和5个未完成任务
When 用户选择"所有任务"筛选器
Then 系统应该显示8个任务
```

### Scenario: 只查看未完成任务

```gherkin
Given 用户已登录
And 存在3个已完成任务和5个未完成任务
When 用户选择"未完成"筛选器
Then 系统应该只显示5个未完成任务
And 已完成任务应该被隐藏
```

---

## 5. 验收标准

### 用户认证

- [ ] 用户可以成功注册账号
- [ ] 重复邮箱注册会显示错误
- [ ] 用户可以成功登录
- [ ] 错误的密码会显示错误消息
- [ ] Token 过期后需要重新登录

### 任务 CRUD

- [ ] 用户可以创建任务
- [ ] 任务标题必填
- [ ] 用户可以编辑任务
- [ ] 用户可以删除任务
- [ ] 删除需要确认

### 任务状态

- [ ] 用户可以标记任务为完成
- [ ] 用户可以取消完成标记
- [ ] 完成的任务显示删除线

### 任务筛选

- [ ] 可以查看所有任务
- [ ] 可以只查看未完成任务
- [ ] 可以只查看已完成任务

---

**编写**: Frank + Alice | **日期**: 2025-10-25
