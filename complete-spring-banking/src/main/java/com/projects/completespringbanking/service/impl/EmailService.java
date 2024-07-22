package com.projects.completespringbanking.service.impl;

import com.projects.completespringbanking.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
}
