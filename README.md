# AcmRank
	origin:
	0: null
	1: http://acm.hdu.edu.cn/
	2: https://vjudge.net/
	3: HDU Solved Problem
	4: POJ Solved Problem
	5: HDU Contest Rank
	6: VJudge Contest Rank
	7: VJudge Solved Problem
	
	3、签到题（还没想好）
	3、集训，提交的相当于签到。签到加1分
		集训题目，每个题目1分
	
	Rating计算方式：
	1、一个题一分
	2、每次比赛第一名6分
		比赛第二名4分
		比赛第三名3分
		比赛其余名1分
	同时比赛做出的题目依旧统计得分
	
	1000分以上红名
	800-1000 橙色
	600-800 紫色
	400-600 蓝色
	200-400 绿色
	200以下灰色
	
	以后再相应调整
	
AcmRank 2.0版本
更新时间：2017年3月7日
更新内容：
	1、增加了VjudgeUser表的NickName字段
	2、增加了对Vjudge Contest比赛排名的获取，并根据之前的排名Rating计算规则，加到相应的用户上。
	3、增加了IP代理功能，用来处理Poj访问频繁问题。该IP代理获取的网上免费的IP代理，连通成功率不高
	