package com.yashdotdev.common.events;


import lombok.Builder;

@Builder
public record UrlCreatedEvent (

        String shortCode,

        Long userId
){

}
