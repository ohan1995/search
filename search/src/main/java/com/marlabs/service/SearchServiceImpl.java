package com.marlabs.service;

import com.marlabs.exception.BusinessException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

@Log4j2
public class SearchServiceImpl implements SearchService {
	
	//Constants
	public static final String DIR_NAME = "D:\\books";
    public static final String CANNOT_BE_NULL_EMPTY = "Input keyword cannot be null/empty.";
    public static final String FILE_READING_EXCEPTION = "Exception while reading the file.";
    
    /*
	 * Open a Directory 
	 * store the list of files in Map
	 * Print all the files Using For each loop
	 */
    
    @Override
	public Map<Integer, String> openDirectory() {
    	
    	log.info("openDirectory() initialized and returning file list in form of map");
    	
    	int countMap = 1;
		File dir = new File(DIR_NAME);;
	
			String[] files = dir.list();
			Map<Integer, String> map = new HashMap<>();
			
			for(int i=0;i<files.length;i++) {
				map.put(countMap++ , dir.list()[i]);
			}
		
			for(Entry<Integer, String> entry : map.entrySet()) {
				System.out.println(entry.getKey() +" "+(entry.getValue()).substring(0, (entry.getValue()).lastIndexOf(".")));
			}
				return map;
		}
    
    
    /*
	 * Taking fileNumber or word from user
	 * Open a File and search for keyword provided by user
	 * Display the Frequency of the keyword
	 */
    
    public boolean searchKeyword(int fileNumber, String keyword, Map<Integer, String> fileList) throws BusinessException {
    	
    	log.info("searchKeyword() initialized with values : {} {} {}",fileNumber,keyword,fileList);
		String fileName = fileList.get(fileNumber);
    	
    	StopWatch stopWatch = new StopWatch();
        stopWatch.start(); //Stopwatch starts
        
        if (StringUtils.isEmpty(keyword)) {
        	//If user input is Empty or Null then throw Cannot be null empty exception
            throw new BusinessException("100", CANNOT_BE_NULL_EMPTY);
        }
        

        try (Stream<String> stream = Files.lines(Paths.get(DIR_NAME + "\\" + fileName))) {
        	
        	long count = stream.parallel()
                                .filter(line -> line.matches(".*\\b" + keyword + "\\b.*"))
                                .count();
        	
            stopWatch.stop(); //Stopwatch stops

            if (count > 0) {
                System.out.println(keyword + " is present and frequency is " + count + " .Time taken " + stopWatch.getTime() + "ms.");
                return true;
            } else {
            	System.out.println(keyword + " is not present .Time taken" + stopWatch.getTime() + "ms.");
                return false;
            }
            
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new BusinessException("101", FILE_READING_EXCEPTION);
        }
    }
}
