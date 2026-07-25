package com.yashdotdev.analytic_service.dtos;

import com.yashdotdev.analytic_service.enums.BrowserType;
import com.yashdotdev.analytic_service.enums.DeviceType;
import com.yashdotdev.analytic_service.enums.OperatingSystem;

public record  UserAgentMetadata (


        BrowserType browser,

        OperatingSystem operatingSystem,

        DeviceType deviceType
)
{}