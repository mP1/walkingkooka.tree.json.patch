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
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContexts;
import walkingkooka.tree.json.patch.PatchableTestingTest.TestPatchable;

import java.util.Objects;
import java.util.function.Supplier;

public final class PatchableTestingTest implements PatchableTesting<TestPatchable> {

    private final static String BEFORE = "before";
    private final static String AFTER = "after";


    @Test
    public void testTestPatchApplyMethodNaming() {
        new PatchableTesting<TestPatchApplyMethodNaming>() {
            @Override
            public TestPatchApplyMethodNaming createPatchable() {
                return new TestPatchApplyMethodNaming();
            }

            @Override
            public JsonNode createPatch() {
                throw new UnsupportedOperationException();
            }

            @Override
            public JsonNodeUnmarshallContext createPatchContext() {
                throw new UnsupportedOperationException();
            }
        }.testPatchApplyMethodNaming();
    }


    @Test
    public void testTestPatchCreateMethodNaming() {
        new PatchableTesting<TestPatchApplyMethodNaming>() {
            @Override
            public TestPatchApplyMethodNaming createPatchable() {
                return new TestPatchApplyMethodNaming();
            }

            @Override
            public JsonNode createPatch() {
                throw new UnsupportedOperationException();
            }

            @Override
            public JsonNodeUnmarshallContext createPatchContext() {
                throw new UnsupportedOperationException();
            }
        }.testPatchCreateMethodNaming();
    }

    class TestPatchApplyMethodNaming implements Patchable<TestPatchApplyMethodNaming> {

        @Override
        public TestPatchApplyMethodNaming patch(final JsonNode json,
                                                final JsonNodeUnmarshallContext context) {
            throw new UnsupportedOperationException();
        }

        public JsonNode createPatch() {
            throw new UnsupportedOperationException();
        }
    }

    @Test
    public void testTestPatchApplyMethodNamingFails() {
        new PatchableTesting<TestPatchApplyMethodNamingFails>() {
            @Override
            public TestPatchApplyMethodNamingFails createPatchable() {
                return new TestPatchApplyMethodNamingFails();
            }

            @Override
            public JsonNode createPatch() {
                throw new UnsupportedOperationException();
            }

            @Override
            public JsonNodeUnmarshallContext createPatchContext() {
                throw new UnsupportedOperationException();
            }

            public void checkEquals(final Object expected,
                                    final Object actual,
                                    final Supplier<String> message) {
                PatchableTesting.super.checkEquals(
                        Lists.of("public walkingkooka.tree.json.JsonNode walkingkooka.tree.json.patch.PatchableTestingTest$TestPatchApplyMethodNamingFails.patchBadReturnType()"),
                        actual,
                        message
                );
            }
        }.testPatchApplyMethodNaming();
    }

    class TestPatchApplyMethodNamingFails implements Patchable<TestPatchApplyMethodNamingFails> {

        // method naming is ok.
        @Override
        public TestPatchApplyMethodNamingFails patch(final JsonNode json,
                                                     final JsonNodeUnmarshallContext context) {
            throw new UnsupportedOperationException();
        }

        // method return type is wrong. this will be reported as BAD!
        public JsonNode patchBadReturnType() {
            throw new UnsupportedOperationException();
        }

        public JsonNode createPatch() {
            throw new UnsupportedOperationException();
        }
    }


    @Test
    public void testTestPatchCreateMethodNamingFails() {
        new PatchableTesting<TestPatchCreateMethodNamingFails>() {
            @Override
            public TestPatchCreateMethodNamingFails createPatchable() {
                return new TestPatchCreateMethodNamingFails();
            }

            @Override
            public JsonNode createPatch() {
                throw new UnsupportedOperationException();
            }

            @Override
            public JsonNodeUnmarshallContext createPatchContext() {
                throw new UnsupportedOperationException();
            }

            public void checkEquals(final Object expected,
                                    final Object actual,
                                    final Supplier<String> message) {
                PatchableTesting.super.checkEquals(
                        Lists.of("public walkingkooka.tree.json.JsonNode walkingkooka.tree.json.patch.PatchableTestingTest$TestPatchCreateMethodNamingFails.patchCreateBadName()"),
                        actual,
                        message
                );
            }
        }.testPatchCreateMethodNaming();
    }

    class TestPatchCreateMethodNamingFails implements Patchable<TestPatchCreateMethodNamingFails> {

        // method naming is ok.
        @Override
        public TestPatchCreateMethodNamingFails patch(final JsonNode json,
                                                      final JsonNodeUnmarshallContext context) {
            throw new UnsupportedOperationException();
        }

        // method name is wrong!
        public JsonNode patchCreateBadName() {
            throw new UnsupportedOperationException();
        }

        public JsonNode createPatch() {
            throw new UnsupportedOperationException();
        }
    }

    @Test
    public void testPatchAndCheck() {
        this.patchAndCheck(this.createPatchable(), JsonNode.string(BEFORE));
    }

    @Test
    public void testPatchAndCheck2() {
        this.patchAndCheck(
                this.createPatchable(),
                this.createPatch(),
                new TestPatchable(AFTER)
        );
    }

    @Test
    public void testPatchJsonStringAndCheck() {
        this.patchAndCheck(
                this.createPatchable(),
                this.createPatch().toString(),
                new TestPatchable(AFTER)
        );
    }

    @Test
    public void testPatchInvalidProperty() {
        final JsonPropertyName name = JsonPropertyName.with("abc");
        final JsonNode value = JsonNode.string("xyz");

        this.patchInvalidPropertyFails(
                JsonNode.object()
                        .set(name, value),
                name,
                value
        );
    }

    @Test
    public void testPatchInvalidPropertyStringOverloads() {
        final JsonPropertyName name = JsonPropertyName.with("abc");
        final JsonNode value = JsonNode.string("xyz");

        this.patchInvalidPropertyFails(
                JsonNode.object()
                        .set(name, value).toString(),
                name.value(),
                value
        );
    }

    @Override
    public TestPatchable createPatchable() {
        return new TestPatchable(BEFORE);
    }

    @Override
    public JsonNode createPatch() {
        return JsonNode.string(AFTER);
    }

    @Override
    public JsonNodeUnmarshallContext createPatchContext() {
        return CONTEXT;
    }

    final static JsonNodeUnmarshallContext CONTEXT = JsonNodeUnmarshallContexts.fake();

    final class TestPatchable implements Patchable<TestPatchable> {


        TestPatchable(final String value) {
            this.value = value;
        }

        @Override
        public TestPatchable patch(final JsonNode json,
                                   final JsonNodeUnmarshallContext context) {
            Objects.requireNonNull(json, "json");
            Objects.requireNonNull(context, "context");
            checkEquals(PatchableTestingTest.CONTEXT, context, "context");

            if (json.isObject()) {
                final JsonNode first = json.objectOrFail().firstChild().get();
                Patchable.invalidPropertyPresent(first.name(), first);
            }

            return new TestPatchable(json.stringOrFail());
        }

        private final String value;

        @Override
        public int hashCode() {
            return this.value.hashCode();
        }

        @Override
        public boolean equals(final Object other) {
            return other instanceof TestPatchable && ((TestPatchable) other).value.equals(this.value);
        }

        @Override
        public String toString() {
            return this.value;
        }
    }
}