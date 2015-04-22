智能云中控灯
=============

Gizwits Central Control LED Android Demo App

功能介绍

▪这是一款使用XPGWifiSDK的开源代码示例APP，可以帮助开发者快速入手，使用XPGWifiSDK开发连接机智云的物联APP。
▪该APP针对的是智能家电中的中控类产品。

▪包括了以下几点中控功能：

1.子设备添加删除
2.子设备控制
3.分组添加删除修改
4.分组控制

▪如果开发者希望开发的设备与以上功能类似，可参考或直接使用该APP进行修改进行快速开发自己的智能家电App。

▪以下功能是机智云开源App的几个通用功能，除UI有些许差异外，流程和代码都几乎一致：

1.机智云账户系统的注册、登陆、修改密码、注销等功能
2.机智云设备管理系统的AirLink配置入网、SoftAP配置入网，设备与账号绑定、解绑定，修改设备别名等功能
3.机智云设备的登陆，控制指令发送，状态接收，设备连接断开等功能

▪另外，因为该项目不支持虚拟设备操控，所以只能通过申请中控设备进行配置入网和绑定操控等流程。

项目依赖和安装

▪	XPGWifiSDK的jar包和支持库
登录机智云官方网站http://gizwits.com的开发者中心，下载并解压最新版本的SDK。
下载后，将解压后的目录拷贝到复制到 Android 项目 libs 目录即可。

▪	中控设备
使用汉枫生产的中控产品eachone设备并烧写对应固件后，可体验设备配置上线，操控等功能。

项目工程结构

▪包结构说明
com.gizwits.smartlight                               -智能云中控灯独有代码，包含控制部分和侧边栏部分
com.gizwits.smartlight.activity                    	 -智能云中控控制界面activity
com.gizwits.smartlight.adapter					     -智能云中控主界面数据适配器

com.gizwits.framework                                -机智云设备开源APP框架,包含除控制界面Activity外的代码，暂时机智云实验室中的其他开源APP所用框架一致
com.gizwits.framework.activity                       -机智云设备开源APP框架相关activity
com.gizwits.framework.adapter                        -机智云设备开源APP框架相关数据适配器
com.gizwits.framework.config                         -机智云设备开源APP框架配置类
com.gizwits.framework.entity                         -机智云设备开源APP框架实体类
com.gizwits.framework.sdk                            -机智云设备开源APP框架操作SDK相关类
com.gizwits.framework.utils                          -机智云设备开源APP框架工具类
com.gizwits.framework.widget                         -机智云设备开源APP框架自定义控件
com.gizwits.framework.XpgApplication                 -机智云设备开源APP框架自定义Application

com.xpg.XXX                                          -机智云通用开发API

zxing                                                -第三方二维码扫描控件

使用流程

▪eachone＋app使用流程（体验配置、绑定实体设备、指令发、状态获取等流程）

1.在app上注册并登录帐号
2.点击中控上发光圆圈，待圆圈中红灯闪烁，进入配对模式
3.通过我要配置中控按钮，使用Airlink或SoftAP模式配置中控入网
4.绑定中控
5.进入控制界面
6.将灯泡上电，灯泡光暗缓慢交替闪时，点击我的LED灯中ADD按钮配置灯泡


问题反馈

您可以给机智云的技术支持人员发送邮件，反馈您在使用过程中遇到的任何问题。
邮箱：janel@gizwits.com
