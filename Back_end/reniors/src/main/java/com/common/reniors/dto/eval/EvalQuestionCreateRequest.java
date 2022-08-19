package com.common.reniors.dto.eval;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
@NoArgsConstructor
public class EvalQuestionCreateRequest {
    private Long jobOpeningId;
    private String contents;
}
