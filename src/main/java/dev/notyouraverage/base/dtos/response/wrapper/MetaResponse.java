/*
 * Copyright (C) 2025 NotYourAverageDev
 *
 * Licensed under the Apache License, version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.notyouraverage.base.dtos.response.wrapper;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.notyouraverage.base.constants.Constants;
import dev.notyouraverage.base.enums.ResponseStatus;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record MetaResponse(
        ResponseStatus status,

        @JsonFormat(pattern = Constants.RESPONSE_TIMESTAMP_FORMAT, shape = JsonFormat.Shape.STRING) LocalDateTime timestamp
) {

}
