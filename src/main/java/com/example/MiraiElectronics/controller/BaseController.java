package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.repository.realization.User;
import com.example.MiraiElectronics.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;

public abstract class BaseController {
    protected final SessionService sessionService;

    public BaseController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    protected User getFullUserOrThrow(HttpServletRequest request) {
        return sessionService.getFullUserFromSession(request);
    }
}
