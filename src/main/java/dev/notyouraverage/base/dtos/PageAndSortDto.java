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
package dev.notyouraverage.base.dtos;

import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageAndSortDto {

    @Builder.Default
    @Min(value = 1, message = "Page number must be at least 1") private Integer page = 1;

    @Builder.Default
    @Min(value = 1, message = "Page size must be at least 1") private Integer size = 20;

    private List<SortDto> sort;
}
