# WorkShift

<div align="center">

![Version](https://img.shields.io/badge/版本-1.0.0-blue.svg)
![Platform](https://img.shields.io/badge/平台-Android-green.svg)
![License](https://img.shields.io/badge/许可证-MIT-yellow.svg)

</div>

## 📱 项目简介

WorkShift 是一款专为轮班工作者设计的 Android 排班管理应用。它提供直观的界面和强大的功能，帮助用户轻松管理和跟踪轮班时间表，支持自定义轮班模式，并提供智能提醒功能。

## ✨ 核心功能

### 🔄 轮班管理
- 创建和管理自定义轮班模式
- 灵活配置轮班周期和持续时间
- 直观的轮班模式预览
- 支持轮班模式的导入导出

### 📅 排班功能
- 基于轮班模式智能生成排班表
- 按日期查看和管理排班信息
- 日历视图展示排班情况
- 支持灵活调整和修改排班

### ⏰ 提醒功能
- 自定义提醒时间
- 多样化的提醒方式
  - 通知栏提醒
  - 声音提醒
  - 震动提醒
- 灵活的提醒设置

### ⚙️ 辅助功能
- 个性化偏好设置
- 系统日志导出
- 数据备份与恢复

## 🛠️ 技术实现

### 架构设计
```
┌─────────┐    ┌─────────┐    ┌─────────┐
│   View  │ ←→ │ViewModel│ ←→ │   Repo  │
└─────────┘    └─────────┘    └─────────┘
                                   ↑
                                   │
                            ┌─────────┐
                            │Database │
                            └─────────┘
```

### 核心技术
- **架构模式**：MVVM + Repository
- **框架组件**
  - Android Jetpack
    - Room：数据持久化
    - ViewModel：UI 数据管理
    - LiveData：数据观察者
    - Lifecycle：生命周期管理
  - Material Design：UI 设计规范
  - WorkManager：后台任务调度

### 数据模型
```
实体关系：
┌─────────────┐     ┌──────────┐     ┌──────────┐
│RotationPattern│ ─→ │ShiftSchedule│ ←─ │ShiftType │
└─────────────┘     └──────────┘     └──────────┘
      ↑                                    ↑
      │                                    │
┌──────────┐                         ┌──────────┐
│UserPrefs │                         │AlarmSettings│
└──────────┘                         └──────────┘
```

## 🔧 开发环境

### 基本要求
- Android Studio 2021.1.1 或更高版本
- Gradle 7.0+
- Android SDK 21+
- JDK 11

### 项目结构
```
WorkShift/
├── app/                        # 应用主模块
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/         # 源代码目录
│   │   │   │   └── com/shiftschedule/app/
│   │   │   │       ├── activity/    # 界面活动
│   │   │   │       ├── adapter/     # 列表适配器
│   │   │   │       ├── dao/         # 数据访问层
│   │   │   │       ├── database/    # 数据库相关
│   │   │   │       ├── fragment/    # 界面片段
│   │   │   │       ├── model/       # 数据模型
│   │   │   │       ├── repository/  # 数据仓库
│   │   │   │       ├── util/        # 工具类
│   │   │   │       └── viewmodel/   # 视图模型
│   │   │   └── res/          # 资源文件目录
│   │   └── test/            # 测试代码目录
│   └── build.gradle         # 模块构建配置
└── build.gradle             # 项目构建配置
```

## 📋 待办功能

- [ ] 深色主题支持
- [ ] 国际化支持
  - [ ] 英语
  - [ ] 日语
  - [ ] 韩语
- [ ] 数据统计与分析
  - [ ] 工时统计
  - [ ] 班次分布
  - [ ] 数据导出
- [ ] 团队管理功能
  - [ ] 多人排班
  - [ ] 权限管理
  - [ ] 班次交换
- [ ] 云端同步
  - [ ] 数据备份
  - [ ] 多设备同步
  - [ ] 实时更新

## 🤝 参与贡献

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request

### 贡献准则
- 遵循项目代码规范
- 添加必要的注释和文档
- 确保通过所有测试
- 提交信息要清晰明了

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 📞 联系我们

- 项目主页：[GitHub](https://github.com/yourusername/WorkShift)
- 问题反馈：[Issues](https://github.com/yourusername/WorkShift/issues)
- 电子邮件：your.email@example.com

---
<div align="center">
<b>用心做好每一次排班</b>
</div> 