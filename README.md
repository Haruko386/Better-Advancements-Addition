# Minecraft Progress Show

一个适用于 Minecraft 1.21.11 / Fabric 的纯客户端附属模组。

它会把 Better Advancements 的进度条件显示模式固定为 `All`：

- 绿色 `+`：已经完成的条件；
- 红色 `x`：尚未完成的条件；
- 不再只显示“剩余 N 个”，而是列出每个未完成条件的名称；
- 无需按住 Shift。

## 安装

将下面两个 JAR 同时放进当前 Minecraft 实例的 `mods` 文件夹：

1. `BetterAdvancements-Fabric-1.21.11-0.4.8.54.jar`
2. `minecraft-progress-show-1.0.0.jar`

同时需要 Fabric API。服务端不需要安装本模组。

## 构建

Windows：

```powershell
.\gradlew.bat build
```

成品位于 `build/libs/minecraft-progress-show-1.0.0.jar`。

本项目没有引用任何经过映射的 Minecraft 类，因此使用标准 Java 构建即可，
不需要 Loom 的名称重映射步骤。`src/stubs` 只提供编译期 API 声明，Gradle
不会把这些声明打包进成品；游戏运行时会使用 Fabric Loader 和
Better Advancements 里的真实类。

## 兼容范围

- Minecraft 1.21.11
- Fabric Loader 0.18.4 或更高版本
- Java 21 或更高版本
- Better Advancements 0.4.8.54 或更高的兼容版本
