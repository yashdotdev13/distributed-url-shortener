package com.yashdotdev.analytic_service.service.parser;

import com.yashdotdev.analytic_service.dtos.UserAgentMetadata;

public interface UserAgentParserService {

    UserAgentMetadata parse(String userAgent);
}
