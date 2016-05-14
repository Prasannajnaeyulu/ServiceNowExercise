package com.servicenow.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TableUtil extends AbstractPageObject{
	static Logger logger = Logger.getLogger(TableUtil.class);
	
	public static WebElement getTableElement(String tableidentifier){
		String xpath = "//table[contains(@class,'"+tableidentifier+"')]";
		WebElement table = driver.findElement(By.xpath(xpath));
		if(table!=null){
			return table;
		}
		else
		{
			logger.error("Table not found");
			return null;
		}
	}
	
	public static int getTableRowsCount(String tableidentifier){
		 WebElement table = getTableElement(tableidentifier);
		if(table!=null){
			List<WebElement> tablerows = table.findElements(By.xpath("//tbody/tr"));
			if(tablerows.isEmpty())
				return 0;
			else
				return tablerows.size();
		}
		else
		  logger.error("Table not found");
		
		return 0;
	}
	
	public static int getTableColumnsCount(String tableidentifier){
		WebElement table = getTableElement(tableidentifier);
		if(table!=null){
			List<WebElement> tablecols = table.findElements(By.xpath("//thead/tr/th"));
			if(tablecols.isEmpty())
				return 0;
			else
				tablecols.size();
		}
		else
		  logger.error("Table not found");
		
		return 0;
	}
	
	public static String getTableCellData(String tableidentifier,int row, int col){
		WebElement table = getTableElement(tableidentifier);
		if(table!=null){
			WebElement tablecell = table.findElement(By.xpath("//tbody/tr["+row+"]/td["+col+"]"));
			if(tablecell!=null)
				return tablecell.getText();
		}
		
		return "";
	}

	@Override
	public void assertInPage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void navigateToPage() {
		// TODO Auto-generated method stub
		
	}
}
