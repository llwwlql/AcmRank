## 1.1.4
 - 更新时间：
 - 更新内容：
 		1.设置持久化的Cookies
 		主要获取以上的Cookies信息，根据登录是否成功判断Cookies是否失效，失效后重新获取。
		2.poj信息获取失败率较高，需要修改
 		
## 1.1.3
 - 更新时间：2017年3月22日
 - 更新内容：  
 		1. Vjudge Contest Rank解析有异常，没有合理处理异常
 		
 		2. 更改了获取Vjudge Contest Rank的登录账号
 		
 		3. 更改了Contest Rank的部分css样式
 		
 		4. 更改了用户曲线在个人信息的显示，默认显示近期时间（时间条在90%处）
 		
 		5. 修复了在比赛未结束的时候就获取比赛榜单，更新比赛Rating的BUG  
 		
 		6. 修复了计算Contest Rank积分计算错误的BUG
 		
 		7. 更改了页面的标题

## 1.1.2
 - 更新时间：2017年3月10日
 - 更新内容：  
 		1. 增加了ico图标
 		
 		2. Rating与图表显示的积分不匹配，有BUG，需要调整  
 		
 		3. 更新了Contest列表，由按ID排序显示，改成了按开始时间排序显示  
 		
 		4. 更新了在榜单上打开用户信息，在当前页面跳转，不会打开新的标签页  
 		
 		5. 修复了在比赛未结束的时候就获取比赛榜单，更新比赛Rating的BUG  
 		
 		6. 修复了计算Contest Rank积分计算错误的BUG
 		

##1.1.1

 - 更新时间：2017年3月9日
 - 更新内容：  
	  	1. 修复了Vjudge Contest Rank的排行榜BUG  
	  	
		2. 修复了根据Contest Rank加分错误的BUG  
		
		3. 优化了代理IP的访问成功几率  
		

##1.1.0
 - 更新时间：2017年3月7日
 - 更新内容：  
		1. 增加了VjudgeUser表的NickName字段  
		
		2. 增加了对Vjudge Contest比赛排名的获取，并根据之前的排名Rating计算规则，加到相应的用户上。  
		
		3. 增加了IP代理功能，用来处理Poj访问频繁问题。该IP代理获取的网上免费的IP代理，连通成功率不高  
		
	
##1.0.0

 - 更新时间：2016年12月20日
 - 更新内容：规定Rating计算方式
	Rating计算方式：
	1. 一个题一分  
	
	2. 每次比赛第一名6分  
	
		 比赛第二名4分  
		 
		 比赛第三名3分  
		 
		 比赛其余名1分  
		 
	     同时比赛做出的题目依旧统计得分
	
	3. Rating积分名称颜色：  
	
		1000分以上红名  
		
		800-1000 橙色  
		
		600-800 紫色  
		
		400-600 蓝色  
		
		200-400 绿色  
		
		200以下灰色  

