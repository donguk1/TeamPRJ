package kopo.poly1.service;

import kopo.poly1.dto.MailDTO;

public interface IMailService {
    int doSendMail(MailDTO pDTO); // 메일 발송
}
