package test;

import com.google.gwt.junit.client.GWTTestCase;

import walkingkooka.j2cl.locale.LocaleAware;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.patch.Patchable;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

@LocaleAware
public class TestGwtTest extends GWTTestCase {

    @Override
    public String getModuleName() {
        return "test.Test";
    }

    public void testAssertEquals() {
        assertEquals(
            1,
            1
        );
    }

    public void testParse() {
        new TestPatchable();
    }

    final static class TestPatchable implements Patchable<TestPatchable> {

        @Override
        public TestPatchable patch(final JsonNode json,
                                   final JsonNodeUnmarshallContext context) {
            throw new UnsupportedOperationException();
        }
    }
}
