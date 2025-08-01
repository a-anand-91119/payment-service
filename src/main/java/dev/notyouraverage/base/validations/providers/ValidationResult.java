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
package dev.notyouraverage.base.validations.providers;

import dev.notyouraverage.base.dtos.response.wrapper.ErrorCodeTrait;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationResult {
    private final boolean isValid;

    private final ErrorCodeTrait errorCode;

    public static ValidationResult valid() {
        return new ValidationResult(true, null);
    }

    public static ValidationResult invalid(ErrorCodeTrait errorCode) {
        return new ValidationResult(false, errorCode);
    }

    public void onError(Consumer<ValidationResult> failureConsumer) {
        if (!isValid)
            failureConsumer.accept(this);
    }
}
