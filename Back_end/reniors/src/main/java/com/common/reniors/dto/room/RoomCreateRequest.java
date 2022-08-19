package com.common.reniors.dto.room;

import com.common.reniors.domain.entity.Type.IsActive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
@NoArgsConstructor
public class RoomCreateRequest {
    private String title;
    private IsActive isActive;
    private String sessionId;
    private Long userId;
    private Long jobOpeningId;
}
