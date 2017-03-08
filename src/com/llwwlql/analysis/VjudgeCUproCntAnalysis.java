package com.llwwlql.analysis;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class VjudgeCUproCntAnalysis {
	
	public static int getProCount(StringBuffer pageContent){
		try {
			Parser parser = new Parser(pageContent.toString());
			NodeFilter filter= new HasAttributeFilter("class","prob-num text-xs-center");
			NodeList nodes = parser.extractAllNodesThatMatch(filter);
			return nodes.size();
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
