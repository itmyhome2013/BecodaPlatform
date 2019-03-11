package com.becoda.bkms.util;

import com.becoda.bkms.common.exception.RollbackableException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
/**
 * Created by IntelliJ IDEA.
 * User: xunuo
 * Date: 2015-2-1
 * Time: 19:55:24
 * To change this template use File | Settings | File Templates.
 */
public class JavaMail {

    private MimeMessage mimeMsg; //MIME邮件对象

    private Session session; //邮件会话对象
    private Properties props; //系统属性
    private boolean needAuth = false; //smtp是否需要认证

    private String username = ""; //smtp认证用户名和密码
    private String password = "";

    private Multipart mp; //Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象

    /**
     *
     */
    public JavaMail() throws RollbackableException {
        setSmtpHost(""); //如果没有指定邮件服务器,就从getConfig类中获取
        createMimeMessage();
    }

    public JavaMail(String smtp) throws RollbackableException {
        setSmtpHost(smtp);
        createMimeMessage();
    }

    /**
     * @param hostName String
     */
    public void setSmtpHost(String hostName) {
        if (props == null)
            props = System.getProperties(); //获得系统属性对象

        props.put("mail.smtp.host", hostName); //设置SMTP主机
    }

    /**
     * @return boolean
     */
    public void createMimeMessage() throws RollbackableException {
        try {
            session = Session.getDefaultInstance(props, null); //获得邮件会话对象
            mimeMsg = new MimeMessage(session); //创建MIME邮件对象
            mp = new MimeMultipart();
        }
        catch (Exception e) {
            throw new RollbackableException("创建MIME邮件对象失败", e, getClass());
        }
    }

    /**
     * @param need boolean
     */
    public void setNeedAuth(boolean need) {
//    System.out.println("设置smtp身份认证：mail.smtp.auth = " + need);
        if (props == null)
            props = System.getProperties();

        if (need) {
            props.put("mail.smtp.auth", "true");
        } else {
            props.put("mail.smtp.auth", "false");
        }
    }

    /**
     * @param name String
     * @param pass String
     */
    public void setNamePass(String name, String pass) {
        username = name;
        password = pass;
    }

    /**
     * @param mailSubject String
     * @return boolean
     */
    public void setSubject(String mailSubject) throws RollbackableException {
        try {
            mimeMsg.setSubject(mailSubject);
        }
        catch (Exception e) {
            throw new RollbackableException("设置邮件主题发生错误", e, getClass());
        }
    }

    /**
     * @param mailBody String
     */
    public void setBody(String mailBody) throws RollbackableException {
        try {
            BodyPart bp = new MimeBodyPart();

            bp.setContent(mailBody, "text/html;charset=GB2312");
            mp.addBodyPart(bp);
        }
        catch (Exception e) {
            throw new RollbackableException("设置邮件正文时发生错误", e, getClass());
        }
    }

    public void addFileAffix(String filename) throws RollbackableException {
        try {
            BodyPart bp = new MimeBodyPart();
            FileDataSource fileds = new FileDataSource(filename);
            bp.setDataHandler(new DataHandler(fileds));
            bp.setFileName(fileds.getName());

            mp.addBodyPart(bp);
        }
        catch (Exception e) {
            throw new RollbackableException("增加邮件附件：" + filename + "发生错误", e, getClass());
        }
    }

    public void setFrom(String from,String fromName) throws RollbackableException {
        try {
            mimeMsg.setFrom(new InternetAddress(from, fromName)); //设置发信人
        }
        catch (Exception e) {
            throw new RollbackableException("设置发信人出错", e, getClass());
        }
    }


    public void setTo(String to) throws RollbackableException {
        if (to == null) return;
        try {
            mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        }
        catch (Exception e) {
            throw new RollbackableException("设置收件人出错", e, getClass());
        }

    }


    public void sendout() throws RollbackableException {
        try {
            mimeMsg.setContent(mp);
            mimeMsg.saveChanges();
            Session mailSession = Session.getInstance(props, null);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect((String) props.get("mail.smtp.host"), username, password);
            transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
            transport.close();
        }
        catch (Exception e) {
            throw new RollbackableException("邮件发送失败", e, getClass());
        }
    }
}
