# FSP-whitelist
### 一个 velocity 插件
* 适用于正版服务器的白名单插件
* 权限 `fwhitelist.admin`

## 重构进行中
### 这并不是一个完美的插件，内部有着很多糟糕的实现
### 源代码现不能构建出一个可以正常使用的插件

* 指令
```
/fwhitelist on/off          开关白名单
/fwhitelist reload          重载白名单
/fwhitelist list            展示白名单
/fwhitelist add <name>      添加玩家到白名单
/fwhitelist remove <name>   在白名单中删除玩家
```
* 完全兼容原版白名单格式
```json5
[
  {
    // UUID
    "uuid": "b83565e6-b0d0-4265-bb4f-fdb5e8d00655",
    // 玩家名
    "name": "tangsu99"
  }
]
```

#### 像素仙缘 FSP-Fantasy story in pixel world
* 是一个 Minecraft Java 版的服务器
* 公益
* 正版验证
* 入服需审核
* [点我](https://space.bilibili.com/661916647)
