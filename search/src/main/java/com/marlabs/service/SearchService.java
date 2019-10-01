package com.marlabs.service;

import java.util.Map;

import com.marlabs.exception.BusinessException;

public interface SearchService {
	public Map<Integer, String> openDirectory();
    boolean searchKeyword(int fileNumber, String keyword, Map<Integer, String> fileList) throws BusinessException;
}
