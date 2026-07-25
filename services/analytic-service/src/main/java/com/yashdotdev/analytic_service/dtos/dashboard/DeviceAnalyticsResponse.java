package com.yashdotdev.analytic_service.dtos.dashboard;


import lombok.Builder;

@Builder
public record  DeviceAnalyticsResponse (

        String device,
        Long clicks
){

}
