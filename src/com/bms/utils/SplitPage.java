package com.bms.utils;

import java.io.Serializable;

public class SplitPage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//作分页显示的工具类
	//1.首先要在页面上为其设置显示的格式e
	//2.然后设计页数处理的类
	private int CurrentPage;//当前页
	private int PageSize;//每页要显示的数据
	private int TotalSize;//总条数
	
	//获得首页--->其对应的当前页自然就是第1.页咯
	public SplitPage(int pageSize, int totalSize) {
		this(1,pageSize, totalSize);
		PageSize = pageSize;
		TotalSize = totalSize;
	}

	
	
	
	
	//获得当前页
	public SplitPage(int currentPage, int pageSize, int totalSize) {
		CurrentPage = currentPage;
		PageSize = pageSize;
		TotalSize = totalSize;
	}
	
	
	
	
	//获取到总页数：
	public int totalPage(){
		int tatalPage=TotalSize/PageSize;
		if(tatalPage%PageSize!=0){
			tatalPage=tatalPage+1;
		}
		return tatalPage;
	}
	

	//获取到上一页
	public int prePage(){
		int preIndex=this.CurrentPage-1;
		if(preIndex<1){
			preIndex=1;
		}
		return preIndex;
	}
	
	//获取到下一页
	public int nextPage(){
		int nextPage=this.CurrentPage+1;
		if(nextPage>totalPage()){
			nextPage=totalPage();
		}
		return nextPage;
	}
	
	//获取到开始索引
	public int BeginIndex(){
		int beginIndex=(this.CurrentPage-1)*PageSize;
		return beginIndex;
		
	}
	
	//获取到最终的索引
	public int EndIndex(){
		int endIndex=this.CurrentPage*PageSize;
		if(endIndex>TotalSize){
			endIndex=TotalSize;
		}
		return endIndex;
	}
	
}