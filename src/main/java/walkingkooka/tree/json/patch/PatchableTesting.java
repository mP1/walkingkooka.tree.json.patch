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
import walkingkooka.collect.list.Lists;
import walkingkooka.test.Testing;
import walkingkooka.tree.json.InvalidPropertyJsonNodeException;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * A mixin interface with tests and helpers to assist in testing a {@link Patchable}
 */
public interface PatchableTesting<T extends Patchable<T>> extends Testing {

    @Test
    default void testPatchApplyMethodNaming() {
        this.checkEquals(
            Lists.empty(),
            Arrays.stream(
                    this.createPatchable()
                        .getClass()
                        .getMethods()
                ).filter(m -> {
                    final String name = m.getName();
                    return name.equals("patch") || name.startsWith("patch");
                }).filter(m -> m.getReturnType().equals(JsonNode.class))
                .map(Method::toGenericString)
                .collect(Collectors.toList()),
            "patchXXX methods should NOT return " + JsonNode.class
        );
    }

    @Test
    default void testPatchCreateMethodNaming() {
        this.checkEquals(
            Lists.empty(),
            Arrays.stream(
                    this.createPatchable()
                        .getClass()
                        .getMethods()
                ).filter(m -> m.getReturnType().equals(JsonNode.class))
                .filter(m -> {
                    final String name = m.getName();
                    return false == name.endsWith("Patch") && name.toLowerCase().contains("patch");
                })
                .map(Method::toGenericString)
                .collect(Collectors.toList()),
            "XXXPatch methods should return " + JsonNode.class
        );
    }

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
                               final String patch) {
        this.patchAndCheck(
            before,
            JsonNode.parse(patch)
        );
    }

    default void patchAndCheck(final T before,
                               final JsonNode patch) {
        this.patchAndCheck(
            before,
            patch,
            this.createPatchContext()
        );
    }

    default void patchAndCheck(final T before,
                               final String patch,
                               final JsonNodeUnmarshallContext context) {
        this.patchAndCheck(
            before,
            JsonNode.parse(patch),
            context
        );
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
                               final String patch,
                               final T after) {
        this.patchAndCheck(
            before,
            JsonNode.parse(patch),
            after
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
                               final String patch,
                               final JsonNodeUnmarshallContext context,
                               final T after) {
        this.patchAndCheck(
            before,
            JsonNode.parse(patch),
            context,
            after
        );
    }

    default void patchAndCheck(final T before,
                               final JsonNode patch,
                               final JsonNodeUnmarshallContext context,
                               final T after) {
        this.checkEquals(
            after,
            before.patch(patch, context),
            () -> before + " patch " + patch
        );
    }

    default void patchInvalidPropertyFails(final String patch,
                                           final String propertyName,
                                           final JsonNode node) {
        this.patchInvalidPropertyFails(
            JsonNode.parse(patch),
            JsonPropertyName.with(propertyName),
            node
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
                                           final String patch,
                                           final String propertyName,
                                           final JsonNode node) {
        this.patchInvalidPropertyFails(
            before,
            JsonNode.parse(patch),
            JsonPropertyName.with(propertyName),
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
        this.checkEquals(propertyName, thrown.name(), "name");
        this.checkEquals(node.removeParent(), thrown.node().removeParent(), "node");
    }
}
