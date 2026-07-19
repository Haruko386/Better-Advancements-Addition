# Haruko386 的 Minecraft Mod 合集

这是一个面向个人使用的 Fabric mod 合集。每个 mod 都放在独立子目录中，并拥有自己的源码、构建配置和说明文档。

## Mod 列表

| Mod | 说明 | 环境 |
| --- | --- | --- |
| [Minecraft Progress Show](minecraft-progress-show/README.md) | 让 Better Advancements 直接列出已完成与未完成的进度条件 | 纯客户端 |
| [Better Pet](better-pet/README.md) | 让驯服的猫、狼和鹦鹉在指定位置附近自由活动 | 客户端与服务端 |

当前目标版本：Minecraft `1.21.11`、Fabric Loader `0.18.4+`、Java `21+`。

## 构建

在仓库根目录运行：

```powershell
.\gradlew.bat build
```

成品分别位于：

- `minecraft-progress-show/build/libs/`
- `better-pet/build/libs/`

只构建其中一个 mod：

```powershell
.\gradlew.bat :minecraft-progress-show:build
.\gradlew.bat :better-pet:build
```
