package org.ayeseeem.test.hamcrest;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class HamcrestIdiomsTest {

    @Test
    public void listChecking_WithItemBasedDiagnostic() {
        List<String> expected = Arrays.asList("a", "b", "c");
        List<String> actual = Arrays.asList("a", "b", "c");

        // Works, but hard to see which element is wrong in a long list
        assertThat(actual, is(expected));

        // Better diagnostic than simple list compare
        assertThat(actual, contains(expected.toArray()));
    }

    @Test
    public void listChecking_WithItemBasedDiagnostic_ExampleDiagnostic() {
        List<String> expected = Arrays.asList("a", "b", "c");
        List<String> actual = Arrays.asList("a", "b", "ZZZ");

        AssertionError ex = assertThrows(AssertionError.class, () -> {
            assertThat(actual, contains(expected.toArray()));
        });
        assertThat(ex.getMessage(), containsString("item 2: was \"ZZZ\""));
        assertThat(ex.getMessage(), containsString("but: item 2: was \"ZZZ\""));
    }

}
