/*
 * Copyright 2021 Miroslav Pokorny (github.com/mP1)
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

import org.junit.jupiter.api.Test;
import walkingkooka.tree.json.InvalidPropertyJsonNodeException;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * A mixin interface with tests and helpers to assist in testing a {@link Patchable}
 */
public interface PatchableTesting<T extends Patchable<T>> {

    @Test
    default void testPatchNullJsonFails() {
        final T patchable = this.createPatchable();

        assertThrows(
                NullPointerException.class,
                () -> patchable.patch(null, this.createPatchContext())
        );
    }


    @Test
    default void testPatchNullContextFails() {
        final T patchable = this.createPatchable();

        assertThrows(
                NullPointerException.class,
                () -> patchable.patch(this.createPatch(), null)
        );
    }

    T createPatchable();

    JsonNode createPatch();

    JsonNodeUnmarshallContext createPatchContext();

    default void patchAndCheck(final T before,
                               final JsonNode patch) {
        this.patchAndCheck(before, patch, before);
    }

    default void patchAndCheck(final T before,
                               final JsonNode patch,
                               final JsonNodeUnmarshallContext context) {
        this.patchAndCheck(
                before,
                patch,
                context,
                before
        );
    }

    default void patchAndCheck(final T before,
                               final JsonNode patch,
                               final T after) {
        this.patchAndCheck(
                before,
                patch,
                this.createPatchContext(),
                after
        );
    }

    default void patchAndCheck(final T before,
                               final JsonNode patch,
                               final JsonNodeUnmarshallContext context,
                               final T after) {
        assertEquals(
                after,
                before.patch(patch, context),
                () -> before + " patch " + patch
        );
    }

    default void patchInvalidPropertyFails(final JsonNode patch,
                                           final JsonPropertyName propertyName,
                                           final JsonNode node) {
        this.patchInvalidPropertyFails(
                this.createPatchable(),
                patch,
                propertyName,
                node
        );
    }

    default void patchInvalidPropertyFails(final T before,
                                           final JsonNode patch,
                                           final JsonPropertyName propertyName,
                                           final JsonNode node) {
        this.patchInvalidPropertyFails(
                before,
                patch,
                this.createPatchContext(),
                propertyName,
                node
        );
    }

    default void patchInvalidPropertyFails(final T before,
                                           final JsonNode patch,
                                           final JsonNodeUnmarshallContext context,
                                           final JsonPropertyName propertyName,
                                           final JsonNode node) {
        final InvalidPropertyJsonNodeException thrown = assertThrows(
                InvalidPropertyJsonNodeException.class,
                () -> before.patch(patch, context)
        );
        assertEquals(propertyName, thrown.name(), "name");
        assertEquals(node.removeParent(), thrown.node().removeParent(), "node");
    }
}
