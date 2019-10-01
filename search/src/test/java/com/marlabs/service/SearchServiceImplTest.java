package com.marlabs.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.marlabs.exception.BusinessException;

@DisplayName("Search service implementation testing class")
public class SearchServiceImplTest {
	
    private SearchService searchService;
    
    @Spy
    private SearchService searchServiceOne;
    
	@BeforeEach
	void init() {
		 searchService = new SearchServiceImpl();
		 MockitoAnnotations.initMocks(this);
	}
	
	@DisplayName("Search Keyword Testing")
	@ParameterizedTest
	@MethodSource("provideValues")
	public void testSearchKeyword(int i, String input) throws BusinessException {
		
		//data preparation
		Map<Integer, String> maps = new HashMap<>();
		maps.put(1, "book.txt");
		maps.put(2, "book1.txt");
		maps.put(3, "data.txt");
		maps.put(4, "python.txt");
		
		//populate all
		when(searchServiceOne.openDirectory()).thenReturn(maps);

		//Test
		assertTrue(searchService.searchKeyword(i, input, maps));
	}
	
	static Stream<Arguments> provideValues(){
		return Stream.of(
				Arguments.of(1, "book"),
				Arguments.of(2, "BOOK"),
				Arguments.of(1, "The"),
				Arguments.of(3, "this")
				);
	}
	
	@DisplayName("Search keyword Failed cases")
	@Test
	public void testSearchKeywordFailed() throws BusinessException {
		
		//data preparation
		Map<Integer, String> maps = new HashMap<>();
		maps.put(1, "book.txt");
		maps.put(2, "books.txt");
		
		//populate all
		when(searchServiceOne.openDirectory()).thenReturn(maps);
		
		assertThrows(BusinessException.class, () -> {
			//Test 
			searchService.searchKeyword(4, "The", maps);
		    });
	}
}
