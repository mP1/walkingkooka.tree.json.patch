/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.tree.json.patch;

import walkingkooka.tree.json.InvalidPropertyJsonNodeException;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.json.UnknownPropertyJsonNodeException;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

/**
 * A Java interface, for types that support patching probably from the body of PATCH http request returning the updated result.
 */
public interface Patchable<T> {

    /**
     * Accepts a JSON object and performs a patch returning the result.
     */
    T patch(final JsonNode json,
            final JsonNodeUnmarshallContext context);

    /**
     * Used to report an invalid property was encountered during a PATCH.
     */
    static void invalidPropertyPresent(final JsonPropertyName property,
                                       final JsonNode node) {
        throw new InvalidPropertyJsonNodeException(property, node);
    }

    /**
     * Used to report an unknown property was encountered during a PATCH.
     */
    static void unknownPropertyPresent(final JsonPropertyName property,
                                       final JsonNode node) {
        throw new UnknownPropertyJsonNodeException(property, node);
    }
}
