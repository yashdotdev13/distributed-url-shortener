package com.yashdotdev.common.events;

import lombok.Builder;

@Builder
public record CacheEvictEvent (


        String shortCode
){}
