
package com.example.mybatisdemo.dto;

import lombok.Data;

@Data
public class PageParam {
	// 分页大小
	private Integer pageSize;
	// 页码, 0为第一页
	private Integer pageNo;

	public PageParam() {
	}

	public PageParam(Integer pageSize, Integer pageNo) {
		this.pageSize = pageSize;
		this.pageNo = pageNo;
	}

}
