package com.hades.api.dtos;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class getFilesDto extends Dto {
    private String userUuid;
    private int offset;
    private int limit;

    public getFilesDto(String userUuid, int offset, int limit) {
        this.userUuid = userUuid;
        this.offset = offset;
        this.limit = limit;
    }

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("userUuid", this.userUuid);
        attributes.put("offset", this.offset);
        attributes.put("limit", this.limit);

        return attributes;
    }
}
