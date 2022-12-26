# FSP-whitelist
### 一个 velocity 插件
* 适用于正版服务器的白名单插件
* 权限 `fwhitelist.admin`

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
