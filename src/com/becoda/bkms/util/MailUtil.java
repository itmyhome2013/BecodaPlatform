package com.becoda.bkms.util;
 import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.variable.StaticVariable;
/**
 * Created by IntelliJ IDEA.
 * User: xunuo
 * Date: 2015-2-1
 * Time: 19:54:50
 * To change this template use File | Settings | File Templates.
 */
public class MailUtil {
    private static JavaMail mail;

    public static void main(String[] args) throws Exception {

        MailUtil.sendMail("测试", "hello 你好", "chenjl@czbank.net", "chenjl@czbank.net");

    }

    /**
     * send mail without affix
     *
     * @param suject：邮件的标题
     * @param body：邮件的正文内容
     * @param to：邮件接收者
     * @param from         ：邮件发送人 可以为空
     * @throws
     */
    public static void sendMail(String suject, String body, String to, String from) throws BkmsException {

        mail = new JavaMail(StaticVariable.get("SFJK_MAIL_SMTP"));
        mail.setNeedAuth(Boolean.valueOf(StaticVariable.get("SFJK_MAIL_USE_SMTP")).booleanValue());
        mail.setSubject(suject);
        mail.setBody(body);
        mail.setTo(to);
        if (from == null) from = StaticVariable.get("SFJK_MAIL_SEND_ADDRESS");
        mail.setFrom(from, from);
        mail.setNamePass(StaticVariable.get("SFJK_MAIL_SEND_ADDRESS"), StaticVariable.get("SFJK_MAIL_SEND_PASSWORD"));
        mail.sendout();
    }
//
//    /**
//     * send mail with affix
//     *
//     * @param suject：邮件的标题
//     * @param body：邮件的正文内容
//     * @param to：邮件接收者
//     * @param from         ：邮件发送人
//     * @param affix        ：附件
//     * @throws MailException
//     */
//    public static void sendMail(String suject, String body, String to, String from, String affix) throws MailException {
//        mail = new JavaMail(Constants.MAIL_SMTP);
//        mail.setNeedAuth(Constants.MAIL_NEEDAUTH);
//
//        mail = new JavaMail(Constants.MAIL_SMTP);
//        mail.setNeedAuth(Constants.MAIL_NEEDAUTH);
//        mail.setSubject(suject);
//        mail.setBody(body);
//        mail.setTo(to);
//        mail.setFrom(from);
//        mail.addFileAffix(affix);
//        mail.setNamePass(Constants.MAIL_USER, Constants.MAIL_PASSWORD);
//
//        mail.sendout();
//    }
//
//    /**
//     * send mail with affix
//     *
//     * @param suject：邮件的标题
//     * @param body：邮件的正文内容
//     * @param to：邮件接收者
//     * @param from         ：邮件发送人
//     * @param affix        ：附件
//     * @throws MailException
//     */
//    public static void sendMail(String suject, String body, String to, String from, String[] affix) throws MailException {
//        mail = new JavaMail(Constants.MAIL_SMTP);
//        mail.setNeedAuth(Constants.MAIL_NEEDAUTH);
//
//        mail = new JavaMail(Constants.MAIL_SMTP);
//        mail.setNeedAuth(Constants.MAIL_NEEDAUTH);
//        mail.setSubject(suject);
//        mail.setBody(body);
//        mail.setTo(to);
//        mail.setFrom(from);
//        if (affix != null) {
//            for (int i = 0; i < affix.length; i++) {
//                mail.addFileAffix(affix[i]);
//            }
//        }
//        mail.setNamePass(Constants.MAIL_USER, Constants.MAIL_PASSWORD);
//
//        mail.sendout();
//    }
}
