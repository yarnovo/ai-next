# BDD 行为驱动开发文档模板

## 📋 模板简介

BDD (Behavior-Driven Development) 行为驱动开发文档，是将产品需求转化为可执行行为规范的桥梁。采用 **Gherkin 语法**，用业务语言描述系统行为，让产品、开发、测试达成共识。

**当前版本**: v1.0.0

---

## 🎯 使用场景

### 适用于
- 将 PRD 需求转化为可验证的行为
- 定义功能的验收标准
- 自动化测试的规范基础
- 产品、开发、测试的沟通桥梁
- 回归测试的场景清单

### 不适用于
- 产品需求定义（请使用 PRD 模板）
- 项目执行计划（请使用 Project-plan 模板）
- 详细的测试用例（BDD 是规范，测试用例是实现）

---

## 📚 模板内容

### 核心章节（14 个）

1. **文档信息** - 基本信息、关联 PRD、状态
2. **功能概述** - 功能描述、业务价值、关联需求
3. **用户角色定义** - 角色、权限、使用场景
4. **业务规则** - 核心规则、约束条件
5. **功能场景 (Gherkin)** - Given-When-Then 格式
6. **场景矩阵** - Scenario Outline 数据驱动
7. **非功能性行为** - 性能、安全、兼容性
8. **数据验证规则** - 输入验证、业务验证
9. **集成场景** - 外部系统、模块交互
10. **验收标准** - 功能、体验、技术验收
11. **测试数据准备** - 正常、边界、异常数据
12. **依赖关系** - 前置功能、后续功能
13. **实现说明** - 技术要点、测试工具、自动化
14. **附录** - 术语表、参考资料、变更记录

### 核心语法：Gherkin

```gherkin
Feature: 功能名称
  作为 [用户角色]
  我想要 [完成的操作]
  以便 [达到的目标]

Background: 场景背景
  Given [所有场景的公共前置条件]

Scenario: 场景名称
  Given [前置条件]
  When [触发动作]
  Then [预期结果]
  And [额外步骤]
  But [例外情况]
```

---

## 🔄 工作流程

```
1. PRD 评审通过 →
2. 编写 BDD 场景文档 →
3. 产品/开发/测试评审 BDD →
4. BDD 文档作为开发和测试规范 →
5. 编写自动化测试（基于 BDD）→
6. 开发实现功能 →
7. 运行 BDD 测试验证 →
8. 上线后回归测试
```

---

## 👥 责任人

- **创建者**: 产品经理 / 测试工程师 / 开发工程师
- **评审者**: 产品、开发、测试团队
- **维护者**: 测试工程师（需求变更时更新）
- **使用者**: 开发（实现参考）、测试（测试依据）

---

## ✅ 最佳实践

### 编写建议

1. **使用业务语言，避免技术术语**
   - ✅ "用户点击登录按钮"
   - ❌ "系统调用 POST /api/login"

2. **每个 Scenario 独立且完整**
   - 可以单独运行
   - 不依赖其他 Scenario 的执行顺序
   - 有明确的前置条件和预期结果

3. **Given-When-Then 职责分明**
   - **Given**: 描述状态，不是动作
     - ✅ "用户已登录"
     - ❌ "用户登录系统"
   - **When**: 单一动作
     - ✅ "用户点击提交按钮"
     - ❌ "用户填写表单并点击提交"
   - **Then**: 验证业务结果
     - ✅ "显示成功提示信息"
     - ❌ "HTTP 状态码返回 200"

4. **覆盖多种场景类型**
   - 正常流程（Happy Path）
   - 边界条件（Boundary）
   - 异常处理（Exception）
   - 权限控制（Permission）

5. **使用 Scenario Outline 减少重复**
   ```gherkin
   Scenario Outline: 参数化场景
     Given 用户输入 <输入值>
     When 点击提交
     Then 显示 <结果>

   Examples:
     | 输入值 | 结果 |
     | 值1   | 结果1 |
     | 值2   | 结果2 |
   ```

### Gherkin 语法规范

**关键词**:
- `Feature`: 功能描述
- `Background`: 所有场景的公共前置条件
- `Scenario`: 具体场景
- `Scenario Outline`: 场景模板（数据驱动）
- `Given`: 前置条件
- `When`: 触发动作
- `Then`: 预期结果
- `And` / `But`: 额外条件
- `Examples`: 场景模板的数据表

### 常见错误

❌ **技术细节过多**
- BDD 应该用业务语言
- 技术实现细节应在代码中

❌ **场景之间有依赖**
- 每个场景应该独立
- 使用 Background 处理公共前置条件

❌ **步骤粒度不一致**
- Given/When/Then 应该保持类似的抽象层次
- 避免混合高层和底层描述

❌ **缺少边界和异常场景**
- 不要只写正常流程
- 异常处理同样重要

---

## 🔗 关联文档

- **PRD**: BDD 的需求来源
- **项目执行计划**: 包含 BDD 的测试策略
- **自动化测试代码**: BDD 的具体实现
- **测试报告**: BDD 场景的执行结果

---

## 🛠️ 推荐工具

### BDD 框架

**JavaScript/TypeScript**:
- Cucumber.js - 最流行的 BDD 框架
- Jest-Cucumber - 结合 Jest 的 BDD
- Playwright + Cucumber - E2E 测试

**Python**:
- Behave - Python 的 BDD 框架
- pytest-bdd - 结合 pytest

**Java**:
- Cucumber-JVM - Java 版本
- JBehave - 另一个选择

**Ruby**:
- Cucumber - BDD 框架的原始实现

### 编辑器插件

- VS Code: Cucumber (Gherkin) Full Support
- IntelliJ IDEA: Cucumber for Java / Gherkin
- Sublime Text: Cucumber Bundle

---

## 📝 快速开始

### 1. 创建 BDD 文档

```bash
# 复制模板
cp BDD-template.md features/用户登录.feature.md

# 或使用 .feature 文件格式（可被 BDD 框架直接识别）
cp BDD-template.md features/user-login.feature
```

### 2. 编写场景

```gherkin
Feature: 用户登录
  作为网站用户
  我想要登录系统
  以便访问个人数据

Scenario: 使用正确的用户名和密码登录
  Given 用户在登录页面
    And 存在注册用户 "alice@example.com" 密码 "Password123"
  When 用户输入用户名 "alice@example.com"
    And 用户输入密码 "Password123"
    And 用户点击登录按钮
  Then 用户应该看到欢迎消息
    And 用户应该被重定向到首页
```

### 3. 实现步骤定义（Step Definitions）

```javascript
// 示例：使用 Cucumber.js
const { Given, When, Then } = require('@cucumber/cucumber');

Given('用户在登录页面', async function() {
  await this.page.goto('/login');
});

When('用户输入用户名 {string}', async function(username) {
  await this.page.fill('#username', username);
});

Then('用户应该看到欢迎消息', async function() {
  const message = await this.page.textContent('.welcome');
  expect(message).toContain('欢迎');
});
```

### 4. 运行测试

```bash
# Cucumber.js
npm run cucumber

# Behave (Python)
behave

# 生成报告
cucumber-js --format html:report.html
```

---

## 📦 文件说明

- `BDD-template.md` - BDD 文档模板
- `CHANGELOG.md` - 模板版本变更历史
- `README.md` - 本说明文档

---

## 🆘 常见问题

**Q: BDD 文档和测试用例有什么区别？**
A: BDD 是**行为规范**，描述"系统应该如何表现"；测试用例是**具体实现**，包含详细的测试步骤和数据。BDD 更抽象、更稳定。

**Q: 谁来写 BDD 文档？**
A: 理想情况是产品、开发、测试共同编写。实践中可以由其中一方主导，其他方评审。

**Q: 所有功能都需要写 BDD 吗？**
A: 不一定。优先覆盖核心功能、复杂业务逻辑、容易出错的部分。

**Q: BDD 文档需要多详细？**
A: 足够让团队理解行为即可。过于详细会降低可维护性，过于简单会产生歧义。

**Q: BDD 和 TDD 有什么关系？**
A: TDD (Test-Driven Development) 关注单元测试和代码设计；BDD 关注系统行为和业务价值。BDD 是 TDD 的补充和扩展。

**Q: Gherkin 语法必须用英文吗？**
A: 不是。Gherkin 支持多语言关键词，可以用中文写（但工具支持度可能不如英文）。

---

## 📚 学习资源

- [Cucumber 官方文档](https://cucumber.io/docs)
- [Gherkin 语法参考](https://cucumber.io/docs/gherkin/reference/)
- [BDD 最佳实践](https://cucumber.io/docs/bdd/)
- [《BDD in Action》](https://www.manning.com/books/bdd-in-action) - 经典书籍

---

## 📞 反馈与改进

如有模板使用问题或改进建议，请：
1. 查看 `CHANGELOG.md` 了解最新变更
2. 参考 Gherkin 官方语法规范
3. 向团队分享 BDD 实践经验

---

**最后更新**: 2025-10-31 | **模板版本**: v1.0.0
