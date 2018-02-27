package com.thinkgem.jeesite.common.utils;

import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailHelper {
	 private String host;
	    private String username;
	    private String password;
	    private String from;
	    
	    private String to;
	    private String subject;
	    private String htmlContent;
	    private String attachedFileName;
	    private List<String> attachedFileNames;
	    private String sendFileName;
	    private List<String> sendFileNames;
	    private String personName = "微帮扶小程序";
	    
	    /**发送邮件**/
	    public  void sendMails(String file,String sendFileName,List<String> filePaths,List<String> fileNames){
/*	    	 String host = "smtp.163.com";// use your smtp server host

	         final String username = "15904793789@163.com"; // use your username
	         final String password = "bqwzy@158wy"; // use your password

	         String from = "15904793789@163.com";// use your sender email address

	         String to = "463718998@qq.com";// use your reciever email address
*/	         try {
	             EmailHelper emailHelper = new EmailHelper(host, username, password, from,to);
	             emailHelper.setTo(to);
	             emailHelper.setSubject("帮扶申请表");
	             //emailHelper.setAttachedFileName(getClass().getClassLoader().getResource(File.separator).toString()+GhConstants.WORD_SAVE_DIR+File.separator+"problemEmployee.docx");
	             emailHelper.setAttachedFileName(file);
	             emailHelper.setAttachedFileNames(filePaths);
	             emailHelper.setSendFileName(sendFileName);
	             emailHelper.setSendFileNames(fileNames);
	             emailHelper.sends();
	             
	         } catch (Exception e) {
	             e.printStackTrace();
	         }
	    }
	    
	    /**发送邮件**/
	    public  void sendMail(String file,String sendFileName){
	    	/* String host = "smtp.163.com";// use your smtp server host

	         final String username = "15904793789@163.com"; // use your username
	         final String password = "bqwzy@158wy"; // use your password

	         String from = "15904793789@163.com";// use your sender email address

	         String to = "463718998@qq.com";// use your reciever email address
*/	         try {
	             EmailHelper emailHelper = new EmailHelper(host, username, password, from,to);
	             emailHelper.setTo(to);
	             emailHelper.setSubject("帮扶申请表");
	             //emailHelper.setAttachedFileName(getClass().getClassLoader().getResource(File.separator).toString()+GhConstants.WORD_SAVE_DIR+File.separator+"problemEmployee.docx");
	             emailHelper.setAttachedFileName(file);
	             emailHelper.setSendFileName(sendFileName);
	             emailHelper.send();
	             
	         } catch (Exception e) {
	             e.printStackTrace();
	         }
	    }
	    
	    public EmailHelper(){
	    	
	    }
	    
	    public EmailHelper(String host, String username, String password, String from,String to) throws AddressException, MessagingException{
	        this.host = host;
	        this.username = username;
	        this.password = password;
	        this.from = from;
	        this.to = to;
	    }
	    
    public void sends() throws Exception{
	        
    	    final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.setProperty("mail.smtp.host", "smtp.163.com");
			props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.port", "465");
			props.setProperty("mail.smtp.socketFactory.port", "465");
	        props.put("mail.smtp.host", host);
	        props.put("mail.smtp.starttls.enable", "false");
	        
	        final String username1 = username;
	        final String password1 = password;
	        
	        Session session = Session.getInstance(props, 
	                new javax.mail.Authenticator(){
	             protected PasswordAuthentication getPasswordAuthentication() {
	                   return new PasswordAuthentication(username1, password1);
	           }
	        });        
	        
	        Message message = new MimeMessage(session);
	        
	        
	        message.setFrom(new InternetAddress(from,personName));        
	        
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	        
	        message.setSubject(subject);

	        
	        Multipart multipart = new MimeMultipart();
	        
	        if (htmlContent != null){
	            System.out.println(" has html ");

	            BodyPart htmlPart = new MimeBodyPart();
	            htmlPart.setContent(htmlContent, "text/html");
	            multipart.addBodyPart(htmlPart);
	        }
	        
	        //附件
	        if (attachedFileNames != null && null!=sendFileNames){
	            System.out.println(" has attached file ");

	            for(int i=0;i<attachedFileNames.size();i++){
	            	    BodyPart attchmentPart = new MimeBodyPart();
		  	            DataSource source = new FileDataSource(attachedFileNames.get(i));
		  	            attchmentPart.setDataHandler(new DataHandler(source));
		  	            attchmentPart.setFileName(sendFileNames.get(i));
		  	            multipart.addBodyPart(attchmentPart);
	            }
	        }
	        
	      //附件
	        if (attachedFileName != null){
	            System.out.println(" has attached file ");

	            BodyPart attchmentPart = new MimeBodyPart();
	            DataSource source = new FileDataSource(attachedFileName);
	            attchmentPart.setDataHandler(new DataHandler(source));
	            attchmentPart.setFileName(sendFileName);
	            multipart.addBodyPart(attchmentPart);
	        }
	        
	        message.setContent(multipart);
	        Transport.send(message);

	        System.out.println(" Sent ");
	    }

	    
	    public void send() throws Exception{
	        
	    	final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.setProperty("mail.smtp.host", "smtp.163.com");
			props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.port", "465");
			props.setProperty("mail.smtp.socketFactory.port", "465");
	        props.put("mail.smtp.host", host);
	        props.put("mail.smtp.starttls.enable", "false");
	        
	        final String username1 = username;
	        final String password1 = password;
	        
	        Session session = Session.getInstance(props, 
	                new javax.mail.Authenticator(){
	             protected PasswordAuthentication getPasswordAuthentication() {
	                   return new PasswordAuthentication(username1, password1);
	           }
	        });        
	        
	        Message message = new MimeMessage(session);
	        
	        
	        message.setFrom(new InternetAddress(from,personName));        
	        
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	        
	        message.setSubject(subject);

	        
	        Multipart multipart = new MimeMultipart();
	        
	        if (htmlContent != null){
	            System.out.println(" has html ");

	            BodyPart htmlPart = new MimeBodyPart();
	            htmlPart.setContent(htmlContent, "text/html");
	            multipart.addBodyPart(htmlPart);
	        }
	        
	        //附件
	        if (attachedFileName != null){
	            System.out.println(" has attached file ");

	            BodyPart attchmentPart = new MimeBodyPart();
	            DataSource source = new FileDataSource(attachedFileName);
	            attchmentPart.setDataHandler(new DataHandler(source));
	            attchmentPart.setFileName(sendFileName);
	            multipart.addBodyPart(attchmentPart);
	        }
	        
	        message.setContent(multipart);
	        Transport.send(message);

	        System.out.println(" Sent ");
	    }

	    public void setTo(String to) {
	        this.to = to;
	    }

	    public void setSubject(String subject) {
	        this.subject = subject;
	    }

	    public void setHtmlContent(String htmlContent) {
	        this.htmlContent = htmlContent;
	    }
	    
	    public void setAttachedFileName(String attachedFileName) {
	        this.attachedFileName = attachedFileName;
	    }

		public String getSendFileName() {
			return sendFileName;
		}

		public void setSendFileName(String sendFileName) {
			this.sendFileName = sendFileName;
		}



		public List<String> getSendFileNames() {
			return sendFileNames;
		}

		public void setSendFileNames(List<String> sendFileNames) {
			this.sendFileNames = sendFileNames;
		}

		public List<String> getAttachedFileNames() {
			return attachedFileNames;
		}

		public void setAttachedFileNames(List<String> attachedFileNames) {
			this.attachedFileNames = attachedFileNames;
		}

		
	    
	    
}
