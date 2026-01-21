package com.SCM.advice;

public class CountReponse {

	private long countofdata;
	private long pages;

	public CountReponse() {
	}

	public CountReponse(long countofdata, long pages) {
		super();
		this.countofdata = countofdata;
		this.pages = pages;
	}

	public long getCountofdata() {
		return countofdata;
	}

	public void setCountofdata(long countofdata) {
		this.countofdata = countofdata;
	}

	public long getPages() {
		return pages;
	}

	public void setPages(long pages) {
		this.pages = pages;
	}

}
