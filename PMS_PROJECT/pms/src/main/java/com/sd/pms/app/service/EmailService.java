package com.sd.pms.app.service;

import com.sd.pms.app.dto.EmailDto;

public interface EmailService {
    void sendEmail(EmailDto dto);
}
