
按Windows+R打开”运行”，输入 "Regedit"，然后点击 “确定”(如果收到UAC提示，点击 “是”)

左侧树形导航栏到 "HKEY_LOCAL_MACHINE/SOFTWARE/Policies/Microsoft/Windows/WindowsUpdate"

右键点击 "WindowsUpdate”、"，点击 "新建">"DWORD（32位）值"。

命名DWORD "TargetReleaseVersion"（不含引号），并将其值设置为 "1"（不含引号）。

右键点击 "WindowsUpdate"，导航到 "新建">"字符串值"。

将字符串值命名为 "TargetReleaseVersionInfo"（不带引号），并将该值设置为您要最终保留的Windows 10版本。例如，如果不想更新到Windows 10 v2004，而想坚持使用1909，那么就输入 “1909”（不加引号）作为值。

这样就可以轻松暂停你设备上的Windows 10更新。现在，您可以关闭注册表编辑器，继续您的生活，而不必担心Bug跟随着系统升级的到来，且这种升级并不影响常规的安全更新，如果今后需要升级系统，可以将这些新建的DWORD值和字符串值删除即可，也可以选择手动下载升级镜像安装。

请注意，在注册表编辑器中更改/添加键值和字符串值是有风险的，入门者请三思后行。