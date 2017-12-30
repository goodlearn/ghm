package com.thinkgem.jeesite.common.utils;

import org.apache.poi.xwpf.usermodel.*;  
import org.junit.Test;


import java.io.*;  
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  

public class XwpfTUtils {
	
	/**
	 * 导入WordData
	 */
	public String importWordData(Map<String, Object> params,String fileName){

		OutputStream os = null;
		String path = null;
        try {
        	 String fileNameInResource = CommonConstants.WORD_SAVE_DIR+File.separator+"problemEmployee.docx";
        	 path = getClass().getClassLoader().getResource("").getPath();
        	 path = path + CommonConstants.WORDS+File.separator;
        	 File tempFile = new File(path);
        	 if (!tempFile.exists()) {
                 tempFile.mkdirs();
             }
    	     XwpfTUtils xwpfTUtil = new XwpfTUtils();  
    	     XWPFDocument doc;  
             InputStream is;
             is = getClass().getClassLoader().getResourceAsStream(fileNameInResource);  
			 doc = new XWPFDocument(is);
			 xwpfTUtil.replaceInPara(doc, params);  

	         //替换表格里面的变量  
	         xwpfTUtil.replaceInTable(doc, params);  

	         os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
	  
	         doc.write(os);  
	  
	         xwpfTUtil.close(os);  
	         xwpfTUtil.close(is);  
	  
	         os.flush();  
	         
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null!=os){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				} 	
			}
		}
        return path;
	}

	  /** 
     * 替换段落里面的变量 
     * 
     * @param doc    要替换的文档 
     * @param params 参数 
     */  
    public void replaceInPara(XWPFDocument doc, Map<String, Object> params) {  
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();  
        XWPFParagraph para;  
        while (iterator.hasNext()) {  
            para = iterator.next();  
            this.replaceInPara(para, params);  
        }  
    }  
    
    /** 
     * 替换段落里面的变量 
     * 
     * @param para   要替换的段落 
     * @param params 参数 
     */  
    public void replaceInPara(XWPFParagraph para, Map<String, Object> params) {  
        List<XWPFRun> runs;  
        Matcher matcher;  
        if (this.matcher(para.getParagraphText()).find()) {  
            runs = para.getRuns();  
            int start = -1;  
            int end = -1;  
            String str = "";  
            for (int i = 0; i < runs.size(); i++) {  
                XWPFRun run = runs.get(i);  
                String runText = run.toString();  
                if ('$' == runText.charAt(0)&&'{' == runText.charAt(1)) {  
                    start = i;  
                }  
                if ((start != -1)) {  
                    str += runText;  
                }  
                if ('}' == runText.charAt(runText.length() - 1)) {  
                    if (start != -1) {  
                        end = i;  
                        break;  
                    }  
                }  
            }  
            for (int i = start; i <= end; i++) {  
                para.removeRun(i);  
                i--;  
                end--;  
            }  
       
           
            for (String key : params.keySet()) { 
                if (str.equals(key)) {  
                  para.createRun().setText((String) params.get(key));  
                    break;  
                }  
            }  
  
  
        }  
    }  
    
    /** 
     * 替换表格里面的变量 
     * 
     * @param doc    要替换的文档 
     * @param params 参数 
     */  
    public void replaceInTable(XWPFDocument doc, Map<String, Object> params) {  
        Iterator<XWPFTable> iterator = doc.getTablesIterator();  
        XWPFTable table;  
        List<XWPFTableRow> rows;  
        List<XWPFTableCell> cells;  
        List<XWPFParagraph> paras;  
        while (iterator.hasNext()) {  
            table = iterator.next();  
            rows = table.getRows();  
            for (XWPFTableRow row : rows) {  
                cells = row.getTableCells();  
                for (XWPFTableCell cell : cells) {  
                    paras = cell.getParagraphs();  
                    for (XWPFParagraph para : paras) {  
                        this.replaceInPara(para, params);  
                    }  
                }  
            }  
        }  
    }  
  
    /** 
     * 正则匹配字符串 
     * 
     * @param str 
     * @return 
     */  
    private Matcher matcher(String str) {  
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);  
        Matcher matcher = pattern.matcher(str);  
        return matcher;  
    }  
  
    /** 
     * 关闭输入流 
     * 
     * @param is 
     */  
    public void close(InputStream is) {  
        if (is != null) {  
            try {  
                is.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    /** 
     * 关闭输出流 
     * 
     * @param os 
     */  
    public void close(OutputStream os) {  
        if (os != null) {  
            try {  
                os.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
	
}
