# Minecraft Progress Show

适用于 Minecraft 1.21.11 / Fabric 的纯客户端辅助 mod。

它会把 Better Advancements 的进度条件显示模式固定为 `All`：

- 绿色 `+` 表示已经完成的条件；
- 红色 `x` 表示尚未完成的条件；
- 直接列出每一个未完成条件的名称，不再只显示“剩余 N 个”；
- 无需按住 Shift。

## 安装

将以下内容放进当前 Minecraft 实例的 `mods` 文件夹：

1. `minecraft-progress-show-1.0.0.jar`；
2. 与 Minecraft 1.21.11 兼容的 Better Advancements；
3. Fabric API。

本 mod 只需安装在客户端，服务端无需安装。

## 构建

在仓库根目录运行：

```powershell
.\gradlew.bat :minecraft-progress-show:build
```

成品位于 `minecraft-progress-show/build/libs/minecraft-progress-show-1.0.0.jar`。

这个 mod 不直接引用经过映射的 Minecraft 类，因此使用标准 Java 编译即可。`src/stubs` 只包含编译期 API 声明，不会被打包到成品中；运行时会使用 Fabric Loader 和 Better Advancements 中的真实类。

## 兼容范围

- Minecraft 1.21.11
- Fabric Loader 0.18.4 或更高版本
- Java 21 或更高版本
- Better Advancements 0.4.8.54 或兼容版本
