package com.yashdotdev.analytic_service.dtos.dashboard;


import lombok.Builder;

@Builder
public record  OperatingSystemAnalyticsResponse(

        String operatingSystem,
        Long clicks
){

}